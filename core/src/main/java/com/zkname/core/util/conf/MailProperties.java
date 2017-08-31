package com.zkname.core.util.conf;

import com.zkname.core.util.conf.base.BaseProperties;


/**
 * 配置项
 */
public final class MailProperties extends BaseProperties {

	private static MailProperties m_conf = null;

	private MailProperties() {
		super();
	}

	public String getPorpFileName() {
		return "mail.properties";
	}

	public static MailProperties getInstance() {
		if (m_conf == null) {
			synchronized (MailProperties.class) {
				if (m_conf == null) {
					m_conf = new MailProperties();
				}
			}
		}
		return m_conf;
	}
}
