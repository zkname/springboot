package com.zkname.hd.util.pay;

public interface IPay {
	/**
	 * appId
	 * @return
	 */
	public String getAppId();
	/**
	 * 商户号
	 * @return
	 */
	public String getMchId();
	/**
	 * 附加数据
	 * @return
	 */
	public String getAttach();
	/**
	 * 支付内容
	 * @return
	 */
	public String getPlayBody();
	/**
	 * 回调地址
	 * @return
	 */
	public String getNotifyUrl();

	/**
	 * 描述
	 * @return
	 */
	public String getPartnerKey();
}