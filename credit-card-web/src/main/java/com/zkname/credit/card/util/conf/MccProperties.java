package com.zkname.credit.card.util.conf;

import java.util.Enumeration;

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
//		System.out.println(MccProperties.getInstance().getProperty("5712"));
//		MccProperties.getInstance().getProperties().entrySet();
		
		Enumeration enu2=MccProperties.getInstance().getProperties().propertyNames();
		while(enu2.hasMoreElements()){
		    String key = (String)enu2.nextElement();
		    System.out.println("<option value=\""+key+"\"  <c:if test=\"${entity.mcc==key}\">selected=\"selected\"</c:if> >["+key+"]"+MccProperties.getInstance().getProperty(key)+"</option>");
		}
//		<option>Alabama</option>
	}
}
