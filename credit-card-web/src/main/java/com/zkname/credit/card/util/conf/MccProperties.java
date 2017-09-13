package com.zkname.credit.card.util.conf;

import com.zkname.core.util.conf.base.BaseProperties;


/**
 * 配置项
 */
public final class MccProperties extends BaseProperties {

	private static MccProperties m_conf = null;

	private MccProperties() {
		super();
	}

	public String getPorpFileName() {
		return "mcc.properties";
	}

	public static MccProperties getInstance() {
		if (m_conf == null) {
			synchronized (MccProperties.class) {
				if (m_conf == null) {
					m_conf = new MccProperties();
				}
			}
		}
		return m_conf;
	}
	
	public static void main(String[] args) {
		System.out.println(MccProperties.getInstance().getProperty("5712"));
	}
}
