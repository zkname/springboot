package com.zkname.hd.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.zkname.frame.controller.BaseController;
import com.zkname.frame.util.CompuUtils;
import com.zkname.frame.util.DateUtil;
import com.zkname.frame.util.ParamType;
import com.zkname.frame.util.json.JsonUtil;
import com.zkname.frame.util.memcache.XMemcacheKey;
import com.zkname.frame.util.memcache.XMemcachedClientImp;
import com.zkname.hd.entity.HdRoom;
import com.zkname.hd.entity.HdRoomUser;
import com.zkname.hd.entity.HdShop;
import com.zkname.hd.entity.HdUser;
import com.zkname.hd.entity.HdUserPayLog;
import com.zkname.hd.entity.HdUserWallet;
import com.zkname.hd.entity.SysParam;
import com.zkname.hd.service.HdRoomService;
import com.zkname.hd.service.HdRoomUserService;
import com.zkname.hd.service.HdShopService;
import com.zkname.hd.service.HdUserPayLogService;
import com.zkname.hd.service.HdUserService;
import com.zkname.hd.service.HdUserWalletService;
import com.zkname.hd.service.SysParamService;
import com.zkname.hd.util.AES;
import com.zkname.hd.util.CodeUtil;
import com.zkname.hd.util.EncodeUtils;
import com.zkname.hd.util.UuidUtils;
import com.zkname.hd.util.WxPKCS7Encoder;
import com.zkname.hd.util.memcache.IMemSyn;
import com.zkname.hd.util.memcache.MemcacheSynchronize;
import com.zkname.hd.util.pay.PayInfo;
import com.zkname.hd.util.pay.PlayUnifiedorder;
import com.zkname.hd.util.wechat.WeChatSmallProgramUtil;
import com.zkname.hd.util.xml.XmlUtil;
import com.zkname.hd.vo.GameVo;
import com.zkname.hd.vo.UserInfoVo;
import com.zkname.hd.vo.UserStatusVo;

/**
 * 小程序接口
 */
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Resource(name = "baseXMemcachedClient")
	private XMemcachedClientImp baseXMemcachedClient;

	@Resource(name = "userXMemcachedClient")
	private XMemcachedClientImp userXMemcachedClient;

	@Autowired
	private HdUserService hdUserService;

	@Autowired
	private HdRoomService hdRoomService;

	@Autowired
	private HdRoomUserService hdRoomUserService;

	@Autowired
	private HdUserWalletService hdUserWalletService;

	@Autowired
	private HdShopService hdShopService;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private HdUserPayLogService hdUserPayLogService;

	/**
	 * 获取session key
	 * 
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/getCodeSessionKey", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String getCodeSessionKey(String code, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String json = WeChatSmallProgramUtil.getCode_exchange_session_key(code);
			if (StringUtils.isBlank(json)) {
				return CodeUtil.getJson(CodeUtil.获取SESSION_KEY错误);
			}
			HashMap map = JsonUtil.jsonToObject(json, new TypeReference<HashMap>() {
			});
			if (map == null) {
				return CodeUtil.getJson(CodeUtil.获取SESSION_KEY错误);
			}
			if (map.containsKey("errcode")) {
				return CodeUtil.getJson(CodeUtil.获取SESSION_KEY错误, json);
			}
			String openid = (String) map.get("openid");
			String session_key = (String) map.get("session_key");
//			String unionid = (String) map.get("unionid");
			HdUser hdUser = hdUserService.getDAO().findByOpenid(openid);
			if (hdUser == null) {
				hdUser = new HdUser();
				hdUser.setCreateTime(new Date());
				hdUser.setDeleStatus("1");
				hdUser.setSubscribe(0);
				hdUser.setType(0);
				hdUser.setOpenId(openid);
			}
//			hdUser.setUnionId(unionid);
			hdUser.setSessionKey(session_key);
			hdUser.setUpdateTime(new Date());
			hdUserService.saveOrUpdate(hdUser);
			String uuid = UuidUtils.base58Uuid();
			int expires_in = ParamType.getint(map.get("expires_in").toString());
			expires_in = expires_in == 0 ? 7000 : expires_in - 200;
			userXMemcachedClient.set(uuid, expires_in, session_key + "#" + openid + "#" + hdUser.getId());
			return CodeUtil.getJson(CodeUtil.成功, ImmutableMap.of("sessionId", uuid));
		} catch (Exception e) {
			logger.error("ApiController.getCodeSessionKey", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 获取用户回话值
	 * 
	 * @param sessionId
	 * @return
	 */
	private String getSessionValue(String sessionId) {
		return userXMemcachedClient.get(sessionId);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param sessionId
	 *            返回的sessionId
	 * @param rawData
	 *            不包括敏感信息的原始数据字符串，用于计算签名。
	 * @param encryptedData
	 *            包括敏感数据在内的完整用户信息的加密数据，详细见加密数据解密算法
	 * @param iv
	 *            加密算法的初始向量，详细见加密数据解密算法
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo(String sessionId, String rawData, String signature, String encryptedData, String iv, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			String userInfo = null;
			try {
				AES cu = new AES();
				byte[] aeskey = Base64Utils.decodeFromString(vs[0]);
				byte[] content = Base64Utils.decodeFromString(encryptedData);
				byte[] ivByte = Base64Utils.decodeFromString(iv);
				byte[] resultByte = cu.decrypt(content, aeskey, ivByte);
				userInfo = new String(WxPKCS7Encoder.decode(resultByte));
			} catch (Exception e) {
				logger.error("微信解密错误：ApiController.updateUserInfo", e);
				return CodeUtil.getJson(CodeUtil.微信解密错误);
			}
			if (StringUtils.isBlank(userInfo)) {
				return CodeUtil.getJson(CodeUtil.微信解密错误);
			}
			UserInfoVo userInfoVo = JsonUtil.jsonToObject(userInfo, UserInfoVo.class);
			HdUser hdUser = hdUserService.getDAO().findById(vs[2]);
			if (hdUser == null) {
				return CodeUtil.getJson(CodeUtil.错误, "查询用户不存在");
			}
			hdUser.setUnionId(userInfoVo.getUnionId());
			hdUser.setOpenId(userInfoVo.getOpenId());
			hdUser.setNickname(userInfoVo.getNickName());
			hdUser.setCity(userInfoVo.getCity());
			hdUser.setProvince(userInfoVo.getProvince());
			hdUser.setSex(userInfoVo.getGender());
			hdUser.setHeadimgurl(userInfoVo.getAvatarUrl());
			hdUserService.saveOrUpdate(hdUser);
			return CodeUtil.getJson(CodeUtil.成功);
		} catch (Exception e) {
			logger.error("ApiController.updateUserInfo", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 二维码充值
	 * 
	 * @param sessionId
	 *            返回的sessionId
	 * @param qr
	 *            二维码数据。
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/qrRechargeInfo", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String qrRechargeInfo(String sessionId, String qr, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			HdUser hdUser = hdUserService.getDAO().findById(vs[2]);
			if (hdUser == null) {
				return CodeUtil.getJson(CodeUtil.错误, "查询用户不存在");
			}
			Map<String, Object> map = Maps.newHashMap();
			// 充值
			if (!StringUtils.startsWith(qr, "CZ_")) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			HdShop hdShop = hdShopService.getDAO().findByUuid(qr);
			if (hdShop == null) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			map.put("name", hdShop.getName());
			map.put("rmb", CompuUtils.divide(sysParamService.findByKey(SysParam.人民币兑换金币默认值).getIntV(), 100, 2));
			map.put("rechargePercentage", hdShop.getRechargePercentage());
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.qrRechargeInfo", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 充值支付下单支付
	 * 
	 * @param sessionId
	 * @param qr
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrder", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String getOrder(String sessionId, String qr, int totalFee, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			HdUser hdUser = hdUserService.getDAO().findById(vs[2]);
			if (hdUser == null) {
				return CodeUtil.getJson(CodeUtil.错误, "查询用户不存在");
			}
			// 充值
			if (!StringUtils.startsWith(qr, "CZ_")) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			HdShop hdShop = hdShopService.getDAO().findByUuid(qr);
			if (hdShop == null) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			HdUserPayLog hdUserPayLog = new HdUserPayLog();
			hdUserPayLog.setCreateTime(DateUtil.Str2Date(DateUtil.Date2Str(new Date()), "yyyy-MM-dd HH:mm:ss"));
			hdUserPayLog.setPayCode(EncodeUtils.getUUID());
			hdUserPayLog.setPayStatus(0);
			hdUserPayLog.setShopId(hdShop.getId());
			hdUserPayLog.setPayType(HdUserPayLog.微信);
			hdUserPayLog.setTotalMoney((long) totalFee);
			hdUserPayLog.setUserId(hdUser.getId());
			hdUserPayLogService.save(hdUserPayLog);
			PayInfo payinfo = new PayInfo();
			payinfo.setTotal_fee((long) totalFee);
			payinfo.setAppid(PayInfo.APPID);
			payinfo.setAttach(Long.toString(hdUserPayLog.getCreateTime().getTime()));
			payinfo.setBody("钱包充值");
			payinfo.setMch_id(PayInfo.MCH_ID);
			payinfo.setNonce_str(EncodeUtils.getUUID());
			String prepay_id = PlayUnifiedorder.unifiedorder(payinfo, hdUserPayLog.getPayCode(), super.getIpAddr(request), hdUser.getOpenId());
			if (prepay_id == null) {
				return CodeUtil.getJson(CodeUtil.失败, "订单创建失败!");
			}
			hdUserPayLog.setPayId(prepay_id);
			hdUserPayLogService.updatePayId(hdUserPayLog);
			Map<String, Object> map = Maps.newHashMap();
			map.put("package", "prepay_id=" + prepay_id);
			map.put("timeStamp", hdUserPayLog.getCreateTime().getTime() / 1000);
			map.put("nonceStr", EncodeUtils.getUUID());
			map.put("signType", "MD5");
			map.put("appId", PayInfo.APPID);
			map.put("paySign", super.getPaySignature(map, PayInfo.KEY));
			map.put("payCode", hdUserPayLog.getPayCode());
			map.remove("appId");
			map.remove("key");
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.getOrder", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 查询支付是否成功
	 * 
	 * @param sessionId
	 * @param payCode
	 *            订单号
	 * @param timeStamp
	 *            时间戳
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findOrder", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String findOrder(String sessionId, String payCode, long timeStamp, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			HdUser hdUser = hdUserService.getDAO().findById(vs[2]);
			if (hdUser == null) {
				return CodeUtil.getJson(CodeUtil.错误, "查询用户不存在");
			}
			Map<String, Object> map = Maps.newHashMap();
			HdUserPayLog hdUserPayLog = hdUserPayLogService.getDAO().findByPayCode(payCode, new Date(timeStamp * 1000));
			if (hdUserPayLog == null) {
				map.put("status", 0);
			}
			map.put("status", hdUserPayLog.getPayStatus());
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.findOrder", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 微信支付订单回执
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/weixinOrderCallback", produces = "application/xml; charset=utf-8")
	public void weixinOrderCallback(HttpServletRequest request, HttpServletResponse response) {
		try {
			String xml = PlayUnifiedorder.getBodyString(request.getReader());
			if (StringUtils.isBlank(xml)) {
				super.renderText("fail", response);
				return;
			}
			Map<String, Object> map = XmlUtil.xmlToObject(xml, HashMap.class);
			if (map.get("attach") == null) {
				super.renderText("fail", response);
				return;
			}
			String attach = map.get("attach").toString();
			String payCode = map.get("out_trade_no").toString();
			Date d = new Date(ParamType.getlong(attach));
			HdUserPayLog vmPayLog = hdUserPayLogService.getDAO().findByPayCode(payCode, HdUserPayLog.微信, d);
			if (vmPayLog == null) {
				super.renderText("success", response);
				return;
			}
			// 支付成功
			if (vmPayLog != null && map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
				vmPayLog.setPayTime(DateUtil.Str2Date(map.get("time_end").toString(), "yyyyMMddHHmmss"));
				vmPayLog.setPayId(map.get("transaction_id").toString());
				vmPayLog.setPayReturnValue(xml);
				try {
					vmPayLog.setPayStatus(1);
					// 更新支付状态并下单
					hdUserPayLogService.updatePaySuccess(vmPayLog);
				} catch (Exception e) {
					logger.error("ApiController.weixinOrderCallback", e);
					vmPayLog.setPayStatus(3);
					// 下单失败，只更新支付状态
					hdUserPayLogService.updatePayStatus(vmPayLog);
				}
			} else {
				vmPayLog.setPayStatus(3);
				vmPayLog.setPayReturnValue(xml);
				hdUserPayLogService.update(vmPayLog);
			}
			super.renderText("success", response);
		} catch (Exception e) {
			logger.error("ApiController.weixinOrderCallback", e);
			super.renderText("fail", response);
		}
	}

	/**
	 * 钱包查询
	 * 
	 * @param sessionId
	 * @param goldCoin
	 *            金币数量
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWallet", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String getWallet(String sessionId, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			HdUser hdUser = hdUserService.getDAO().findById(vs[2]);
			if (hdUser == null) {
				return CodeUtil.getJson(CodeUtil.错误, "查询用户不存在");
			}
			HdUserWallet hdUserWallet = hdUserWalletService.findById(hdUser.getId());
			if (hdUserWallet == null) {
				hdUserWallet = new HdUserWallet();
				hdUserWallet.setUserId(hdUser.getId());
				hdUserWallet.setCreateTime(new Date());
				hdUserWallet.setGoldBean(0L);
				hdUserWallet.setGoldCoin(0L);
				hdUserWalletService.save(hdUserWallet);
			}
			Map<String, Object> map = Maps.newHashMap();
			map.put("goldBean", hdUserWallet.getGoldBean());
			map.put("goldCoin", hdUserWallet.getGoldCoin());
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.getWallet", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 金币兑换金豆
	 * 
	 * @param sessionId
	 * @param goldCoin
	 *            金币数量
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchangeGoldBeans", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String exchangeGoldBeans(String sessionId, int goldCoin, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			HdUser hdUser = hdUserService.getDAO().findById(vs[2]);
			if (hdUser == null) {
				return CodeUtil.getJson(CodeUtil.错误, "查询用户不存在");
			}
			HdUserWallet hdUserWallet = hdUserWalletService.findById(hdUser.getId());
			if (hdUserWallet == null) {
				hdUserWallet = new HdUserWallet();
				hdUserWallet.setUserId(hdUser.getId());
				hdUserWallet.setCreateTime(new Date());
				hdUserWallet.setGoldBean(0L);
				hdUserWallet.setGoldCoin(0L);
				hdUserWalletService.save(hdUserWallet);
			}
			if (hdUserWallet.getGoldCoin() < goldCoin) {
				return CodeUtil.getJson(CodeUtil.可兑换金币不足);
			}
			String re = hdUserWalletService.goldCoinToGoldBean(hdUser, hdUserWallet, goldCoin);
			if (re != null) {
				return re;
			}
			return CodeUtil.getJson(CodeUtil.成功);
		} catch (Exception e) {
			logger.error("ApiController.exchangeGoldBeans", e);
			return CodeUtil.getJson(CodeUtil.失败);
		}
	}

	/**
	 * 创建房间
	 * 
	 * @param sessionId
	 * @param roomName
	 * @param remark
	 * @param num
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createRoom", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String createRoom(String sessionId, String roomName, String remark, Integer num, Integer type, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			HdUser user = hdUserService.findById(userId);
			HdRoom room = hdRoomService.getDAO().findByUserId(userId);
			// 默认1对1,最大人数2
			type = type == null ? HdRoom.TYPE_一对一 : type;
			num = num == null ? 2 : num;
			String result = null;
			// 判断是否是房主,不是则尝试进入房间
			// 房间为空时新建房间
			if (room == null) {
				room = new HdRoom();
				room.setName(roomName == null ? "未命名" : roomName);
				room.setRemark(remark);
				room.setType(type);
				room.setMaxNum(num);
				room.setUserId(userId);
				room.setCreateTime(new Date());
				room.setUpdateTime(room.getCreateTime());
				room.setDeleStatus("1");
				room.setCreatorId(0L);
			}
			// 不为空判断房间类型
			if (type.intValue() != room.getType().intValue()) {
				room.setName(roomName);
				room.setRemark(remark);
				room.setType(type);
				room.setMaxNum(num);
				room.setUpdateTime(new Date());
			}
			room.setStatus(HdRoom.STATUS_开放);
			result = hdRoomService.saveUpdate(room, userId);
			if (result != null) {
				return result;
			}
			// 用户状态
			IMemSyn syn = () -> {
				UserStatusVo userStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
				if (userStatus == null) {
					userStatus = new UserStatusVo();
				}
				userStatus.setRoomChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), 172800, userStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			// 返回
			HashMap<Object, Object> map = Maps.newHashMap();
			logger.debug(room.getId().toString());
			map.put("qrCode", "FJ_" + EncodeUtils.encodeHex(user.getOpenId().getBytes()));
			map.put("roomName", room.getName());
			map.put("remark", room.getRemark());
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.createRoom", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 进入房间
	 * 
	 * @param sessionId
	 * @param qr
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enterRoom", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String enterRoom(String sessionId, String qr, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null || !StringUtils.startsWith(qr, "FJ_")) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			String openid = null;
			try {
				openid = new String(EncodeUtils.decodeHex(StringUtils.substring(qr, 3)));
			} catch (Exception e) {
				return CodeUtil.getJson(CodeUtil.房间不存在);
			}
			HdUser user = hdUserService.getDAO().findByOpenid(openid);
			if (user == null) {
				return CodeUtil.getJson(CodeUtil.房间不存在);
			}
			HdRoom room = hdRoomService.getDAO().findByUserId(user.getId());
			if (room == null) {
				return CodeUtil.getJson(CodeUtil.房间不存在);
			}
			String result = hdRoomService.enterRoom(userId, room);
			if (result != null) {
				return result;
			}
			// 用户状态
			IMemSyn syn1 = () -> {
				UserStatusVo userStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
				if (userStatus == null) {
					userStatus = new UserStatusVo();
				}
				userStatus.setRoomChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), 172800, userStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), MemcacheSynchronize.EXP_方法同步超时秒, syn1);
			// 房主状态
			HdRoomUser host = hdRoomUserService.getDAO().findOppo(room.getId(), userId);
			IMemSyn syn = () -> {
				UserStatusVo hostStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + host.getUserId()));
				if (hostStatus == null) {
					hostStatus = new UserStatusVo();
				}
				hostStatus.setRoomChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + host.getUserId()), 172800, hostStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + host.getUserId()), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			// 返回
			HashMap<Object, Object> map = Maps.newHashMap();
			map.put("roomName", room.getName());
			map.put("remark", room.getRemark());
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.enterRoom", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 金豆交易
	 * 
	 * @param userId
	 * @param toUserId
	 * @param beanNum
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/trade", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String trade(String sessionId, Long toUserId, Long beanNum, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null || toUserId == null || toUserId.equals(0L) || beanNum == null || beanNum.equals(0L)) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			String result = hdUserWalletService.trade(userId, toUserId, beanNum, 0);
			if (result != null) {
				return result;
			}
			IMemSyn syn = () -> {
				UserStatusVo toUserStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + toUserId));
				if (toUserStatus == null) {
					toUserStatus = new UserStatusVo();
				}
				toUserStatus.setMoneyChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + toUserId), 172800, toUserStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + toUserId), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			return CodeUtil.getJson(CodeUtil.成功);
		} catch (Exception e) {
			logger.error("ApiController.trade", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 心跳
	 * 
	 * @param sessionId
	 * @param roomId
	 * @param betNum
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String getInfo(String sessionId, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			UserStatusVo userStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
			// if (userStatus != null) {
			// hdRoomUserService.updateTime(userId);
			// }
			return CodeUtil.getJson(CodeUtil.成功, userStatus);
		} catch (Exception e) {
			logger.error("ApiController.getInfo", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 房间信息
	 * 
	 * @param roomId
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/roomInfo", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String roomInfo(String sessionId, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			HdRoomUser roomUser = hdRoomUserService.getDAO().findByUserId(userId);
			if (roomUser == null) {
				return CodeUtil.getJson(CodeUtil.房主退出房间);
			}
			HdRoom room = hdRoomService.findById(roomUser.getRoomId());
			if (room == null) {
				return CodeUtil.getJson(CodeUtil.房间不存在);
			}
			HashMap<Object, Object> map = Maps.newHashMap();
			HashMap<Object, Object> player1 = Maps.newHashMap();
			HashMap<Object, Object> player2 = null;
			player1.put("userId", roomUser.getUserId());
			player1.put("userName", roomUser.getUserName());
			player1.put("headImgUrl", roomUser.getHeadimgurl());
			if (room.getUserId().longValue() == roomUser.getUserId().longValue()) {
				player1.put("host", 1);
			}
			HdRoomUser roomUser2 = hdRoomUserService.getDAO().findOppo(roomUser.getRoomId(), userId);
			if (roomUser2 != null) {
				player2 = Maps.newHashMap();
				player2.put("userId", roomUser2.getUserId());
				player2.put("userName", roomUser2.getUserName());
				player2.put("headImgUrl", roomUser2.getHeadimgurl());
				if (room.getUserId().longValue() == roomUser2.getUserId().longValue()) {
					player2.put("host", 1);
				} else {
					player2.put("host", 0);
				}
			}
			map.put("self", player1);
			map.put("player2", player2);
			return CodeUtil.getJson(CodeUtil.成功, map);
		} catch (Exception e) {
			logger.error("ApiController.roomInfo", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 对局开始
	 * 
	 * @param sessionId
	 * @param betNum
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/startGame", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String start(String sessionId, Long betNum, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null || betNum == null || betNum.equals(0L)) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			// 房间
			HdRoom room = hdRoomService.getDAO().findByUserId(userId);
			if (room == null) {
				return CodeUtil.getJson(CodeUtil.房间不存在);
			}
			// 房间用户
			HdRoomUser roomUser = hdRoomUserService.getDAO().findByUserId(userId);
			if (roomUser == null) {
				return CodeUtil.getJson(CodeUtil.不在房间中);
			}
			// 判断是否房主
			if (room.getId().longValue() != roomUser.getRoomId().longValue()) {
				return CodeUtil.getJson(CodeUtil.当前房间不是房主);
			}
			// 判断用户余额
			HdUserWallet hostWallet = hdUserWalletService.findById(userId);
			if (hostWallet == null || hostWallet.getGoldBean().longValue() < betNum) {
				return CodeUtil.getJson(CodeUtil.余额不足, hostWallet == null ? 0 : hostWallet.getGoldBean().longValue());
			}
			// 对手
			HdRoomUser guestUser = hdRoomUserService.getDAO().findGuestByHostId(userId);
			if (guestUser == null) {
				return CodeUtil.getJson(CodeUtil.房间人未满);
			}
			// 判断对手余额
			HdUserWallet guestWallet = hdUserWalletService.findById(guestUser.getUserId());
			if (guestWallet == null || guestWallet.getGoldBean().longValue() < betNum) {
				return CodeUtil.getJson(CodeUtil.对方余额不足, guestWallet == null ? 0 : guestWallet.getGoldBean().longValue());
			}
			// 写入对局信息
			String uuid = UuidUtils.uuid().replace("-", "");
			GameVo gameVo = new GameVo();
			gameVo.setKey(uuid);
			gameVo.setCreatorId(userId);
			gameVo.setBetNum(betNum);
			gameVo.setStatus(GameVo.对局中);
			baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.对局信息 + uuid), 172800, gameVo);
			// 更新房主状态缓存
			UserStatusVo userStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
			if (userStatus == null) {
				return CodeUtil.getJson(CodeUtil.缓存读取错误);
			}
			IMemSyn syn = () -> {
				userStatus.setLastGameKey(userStatus.getGameKey());
				userStatus.setGameKey(uuid);
				userStatus.setGameChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), 172800, userStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			// 更新玩家状态缓存
			UserStatusVo guestStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestUser.getUserId()));
			if (guestStatus == null) {
				return CodeUtil.getJson(CodeUtil.缓存读取错误);
			}
			IMemSyn syn1 = () -> {
				guestStatus.setLastGameKey(guestStatus.getGameKey());
				guestStatus.setGameKey(uuid);
				guestStatus.setGameChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestUser.getUserId()), 172800, guestStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestUser.getUserId()), MemcacheSynchronize.EXP_方法同步超时秒, syn1);
			return CodeUtil.getJson(CodeUtil.成功, uuid);
		} catch (Exception e) {
			logger.error("ApiController.startGame", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 更改赌注数
	 * 
	 * @param sessionId
	 * @param key
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setBetNum", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String setBetNum(String sessionId, String key, Long betNum, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			// 读取对局信息
			GameVo gameVo = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.对局信息 + key));
			if (gameVo == null) {
				return CodeUtil.getJson(CodeUtil.对局不存在);
			}
			if (gameVo.getCreatorId().longValue() != userId) {
				return CodeUtil.getJson(CodeUtil.当前房间不是房主);
			}
			// 判断用户余额
			HdUserWallet hostWallet = hdUserWalletService.findById(userId);
			if (hostWallet == null || hostWallet.getGoldBean().longValue() < betNum) {
				return CodeUtil.getJson(CodeUtil.余额不足, hostWallet == null ? 0 : hostWallet.getGoldBean().longValue());
			}
			// 玩家
			HdRoomUser guestUser = hdRoomUserService.getDAO().findGuestByHostId(userId);
			if (guestUser == null) {
				return CodeUtil.getJson(CodeUtil.玩家退出);
			}
			// 判断玩家余额
			HdUserWallet guestWallet = hdUserWalletService.findById(guestUser.getUserId());
			if (guestWallet == null || guestWallet.getGoldBean().longValue() < betNum) {
				return CodeUtil.getJson(CodeUtil.对方余额不足, guestWallet == null ? 0 : guestWallet.getGoldBean().longValue());
			}
			// 对局信息缓存更新
			gameVo.setBetNum(betNum);
			baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.对局信息 + key), 172800, gameVo);
			// 房主状态缓存更新
			UserStatusVo userStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
			if (userStatus == null) {
				return CodeUtil.getJson(CodeUtil.缓存读取错误);
			}
			IMemSyn syn = () -> {
				userStatus.setGameChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), 172800, userStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			// 玩家状态缓存更新
			HdRoomUser guestRoomUser = hdRoomUserService.getDAO().findGuestByHostId(userId);
			UserStatusVo guestStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestRoomUser.getUserId()));
			if (guestStatus == null) {
				return CodeUtil.getJson(CodeUtil.缓存读取错误);
			}
			IMemSyn syn1 = () -> {
				guestStatus.setGameChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestRoomUser.getUserId()), 172800, guestStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestRoomUser.getUserId()), MemcacheSynchronize.EXP_方法同步超时秒, syn1);
			return CodeUtil.getJson(CodeUtil.成功);
		} catch (Exception e) {
			logger.error("ApiController.setBetNum", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 对局结束
	 * 
	 * @param sessionId
	 * @param win
	 * @param player2Id
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/endGame", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String endGame(String sessionId, Integer win, Long player2Id, String key, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (win == null || sessionId == null || player2Id == null || player2Id.equals(0L)) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			GameVo gameVo = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.对局信息 + key));
			if (gameVo == null) {
				return CodeUtil.getJson(CodeUtil.对局不存在);
			}
			if (gameVo.getCreatorId().longValue() != userId) {
				return CodeUtil.getJson(CodeUtil.当前房间不是房主);
			}
			Long betNum = gameVo.getBetNum();
			String result = null;
			if (win.intValue() == 1) {
				// 房主赢
				result = hdUserWalletService.trade(player2Id, userId, betNum, 1);
				gameVo.setWinnerId(userId);

			} else {
				// 房主输
				result = hdUserWalletService.trade(userId, player2Id, betNum, 1);
				gameVo.setWinnerId(player2Id);
			}
			if (result != null) {
				return result;
			}
			gameVo.setStatus(GameVo.已结束);
			baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.对局信息 + key), 172800, gameVo);
			// 房主状态
			UserStatusVo userStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
			if (userStatus == null) {
				return CodeUtil.getJson(CodeUtil.缓存读取错误);
			}
			IMemSyn syn = () -> {
				userStatus.setGameChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), 172800, userStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			// 玩家状态
			HdRoomUser guestRoomUser = hdRoomUserService.getDAO().findGuestByHostId(userId);
			UserStatusVo guestStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestRoomUser.getUserId()));
			if (guestStatus == null) {
				return CodeUtil.getJson(CodeUtil.缓存读取错误);
			}
			IMemSyn syn1 = () -> {
				guestStatus.setGameChangedTime(System.currentTimeMillis());
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestRoomUser.getUserId()), 172800, guestStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestRoomUser.getUserId()), MemcacheSynchronize.EXP_方法同步超时秒, syn1);
			return CodeUtil.getJson(CodeUtil.成功);
		} catch (Exception e) {
			logger.error("ApiController.endGame", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 对局信息
	 * 
	 * @param sessionId
	 * @param key
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gameInfo", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String gameInfo(String sessionId, String key, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			GameVo gameVo = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.对局信息 + key));
			if (gameVo == null) {
				return CodeUtil.getJson(CodeUtil.对局不存在);
			}
			return CodeUtil.getJson(CodeUtil.成功, gameVo);
		} catch (Exception e) {
			logger.error("ApiController.gameInfo", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	/**
	 * 退出房间(房主退出解散房间)
	 * 
	 * @param roomId
	 * @param sessionId
	 * @param guestSessionId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exitRoom", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String exitRoom(String sessionId, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!isSignature(request)) {
				return CodeUtil.getJson(CodeUtil.签名错误);
			}
			if (sessionId == null) {
				return CodeUtil.getJson(CodeUtil.参数错误);
			}
			// user
			String v = getSessionValue(sessionId);
			if (StringUtils.isBlank(v)) {
				return CodeUtil.getJson(CodeUtil.获取SESSIONID超时);
			}
			String[] vs = v.split("#");
			Long userId = Long.parseLong(vs[2]);
			// 清除缓存
			IMemSyn syn = () -> {
				baseXMemcachedClient.delete(XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId));
				logger.debug("清除玩家缓存[" + userId + "]");
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + userId), MemcacheSynchronize.EXP_方法同步超时秒, syn);
			// 退出房间
			HdRoomUser roomUser = hdRoomUserService.getDAO().findByUserId(userId);
			if (roomUser == null) {
				return CodeUtil.getJson(CodeUtil.不在房间中);
			}
			HdRoom room = hdRoomService.findById(roomUser.getRoomId());
			if (room.getUserId().longValue() == userId.longValue()) {
				hdRoomService.dismiss(room, userId);
			} else {
				hdRoomService.exit(room, userId);
			}
			return CodeUtil.getJson(CodeUtil.成功);
		} catch (Exception e) {
			logger.error("ApiController.exitRoom", e);
			return CodeUtil.getJson(CodeUtil.错误);
		}
	}

	public static void main(String[] args) {
	}
}
