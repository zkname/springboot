package com.zkname.patchca;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.Captcha;

public class PatchcaFilter implements Filter {

	public final static CaptchaService cs = new CaptchaService();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String filterFactory = filterConfig.getInitParameter("filterFactory");
		cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		if (MarbleRippleFilterFactory.class.getName().equals(filterFactory)) {
			cs.setFilterFactory(new MarbleRippleFilterFactory());
		} else if (DoubleRippleFilterFactory.class.getName().equals(filterFactory)) {
			cs.setFilterFactory(new DoubleRippleFilterFactory());
		} else if (WobbleRippleFilterFactory.class.getName().equals(filterFactory)) {
			cs.setFilterFactory(new WobbleRippleFilterFactory());
		} else if (DiffuseRippleFilterFactory.class.getName().equals(filterFactory)) {
			cs.setFilterFactory(new DiffuseRippleFilterFactory());
		} else {
			cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
		}
	}

	@Override
	public void doFilter(ServletRequest obj1, ServletResponse obj2, FilterChain chain) {

		HttpServletRequest request = (HttpServletRequest) obj1;
		HttpServletResponse response = (HttpServletResponse) obj2;
		response.setHeader("Cache-Control", "no-cache");
		if (request.getRequestURI().indexOf("/images/codeVerification.png") != -1) {
			try {
				response.getWriter().write(Boolean.toString(isValidationCode(request, request.getParameter("code"))));
				response.getWriter().flush();
			} catch (IOException e) {
			}
		} else {
			createImg(request, response);
		}
	}

	public void createImg(HttpServletRequest request, HttpServletResponse response) {
		Captcha captcha = cs.getCaptcha();
		response.setContentType("image/png");
		response.setHeader("cache", "no-cache");
		HttpSession session = request.getSession(true);
		try (OutputStream outputStream = response.getOutputStream(); ){
			// 取得验证码字符串放入Session
			String validationCode = captcha.getChallenge();
			session.setAttribute("validationCode", validationCode);
			// 取得验证码图片并输出
			BufferedImage bufferedImage = captcha.getImage();
			ImageIO.write(bufferedImage, "png", outputStream);
		} catch (Exception e) {
		}
	}

	@Override
	public void destroy() {

	}

	/**
	 * 验证码判断
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	public static boolean isValidationCode(HttpServletRequest request, String code) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return false;
		String validationCode = (String) session.getAttribute("validationCode");
		if (validationCode == null || !validationCode.equals(code)) {
			return false;
		}
		return true;
	}

}
