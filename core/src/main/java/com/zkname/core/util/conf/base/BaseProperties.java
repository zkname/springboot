package com.zkname.core.util.conf.base;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseProperties {

	private static Logger logger = LoggerFactory.getLogger(BaseProperties.class);

	private static long expireTime = 60 * 1000L;

	private long lastCheckTime;

	private Properties m_props;

	private long lastModified;

	private String filepath;

	final static Logger log = LoggerFactory.getLogger(BaseProperties.class);

	public BaseProperties() {
		this.lastCheckTime = System.currentTimeMillis();
		this.load();
	}

	public void load() {
        try (InputStream in = BaseProperties.class.getClassLoader().getResourceAsStream(this.getPorpFileName());){
        	if(in.available()==lastModified){
        		return;
        	}
        	lastModified=in.available();
        	Properties m_props = new Properties();
        	m_props.load(in);
        	this.m_props=m_props;
        	logger.info("!Load properties {}",BaseProperties.class.getClassLoader().getResource(this.getPorpFileName()).getFile());
        } catch (Exception e) {
            logger.error(this.getPorpFileName()+"文件未找到");
        }
        
	}

	public void store() {
		if (m_props.size() > 0) {
			try {
				load();
			} catch (Exception ex) {
				System.out.println("Store to " + filepath + " Error");
			}
		}
	}

	public abstract String getPorpFileName();

	public String getProperty(String key, String defVal) {
		refresh();
		String result = m_props.getProperty(key, defVal);
		return result;
	}

	public int getProperty(String key, int defVal) {
		refresh();
		int res = defVal;
		try {
			res = Integer.parseInt(m_props.getProperty(key, ""));
		} catch (Exception ex) {
		}
		return res;
	}

	public String getProperty(String key) {
		return getProperty(key, "");
	}

	public Properties getProperties() {
		return m_props;
	}

	public void setProperty(String key, String value) {
		if (key != null && value != null) {
			m_props.setProperty(key, value);
		}
	}

	private void refresh() {
		long t1 = System.currentTimeMillis();
		if (t1 - this.lastCheckTime > expireTime) {
			// 如果超时，则检查文件内容是否变化

//			File f = new File(filepath);
//			if (f.lastModified() != lastModified) // 如果文件发生变化则重新读入内存

				load();

			this.lastCheckTime = System.currentTimeMillis();

		}
	}

}
