package com.zkname.core.util.conf;

import com.zkname.core.util.conf.base.BaseProperties;


/**
 * 配置项
 */
public final class ConfigurationProperties extends BaseProperties {

	private static ConfigurationProperties m_conf = null;

	private ConfigurationProperties() {
		super();
	}

	public String getPorpFileName() {
		return "configuration.properties";
	}

	public static ConfigurationProperties getInstance() {
		if (m_conf == null) {
			synchronized (ConfigurationProperties.class) {
				if (m_conf == null) {
					m_conf = new ConfigurationProperties();
				}
			}
		}
		return m_conf;
	}
	
	public static void main(String[] args) {
		ConfigurationProperties.getInstance();
	}
}
