package com.zkname.core.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class ParamType {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

	public static int getint(String s) {
		int i = 0;
		if (s == null)
			return 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
		return i;
	}

	public static byte getbyte(String s) {
		byte b = 0;
		if (s == null)
			return 0;
		try {
			b = Byte.parseByte(s);
		} catch (Exception e) {
			return 0;
		}
		return b;
	}

	public static short getshort(String s) {
		short i = 0;
		if (s == null)
			return 0;
		try {
			i = Short.parseShort(s);
		} catch (Exception e) {
			return 0;
		}
		return i;
	}

	public static long getlong(String s) {
		long l = 0;
		if (s == null)
			return 0;
		try {
			l = Long.parseLong(s);
		} catch (Exception e) {
			return 0;
		}
		return l;
	}

	public static float getfloat(String s) {
		float f = 0;
		if (s == null)
			return 0;
		try {
			f = Float.parseFloat(s);
		} catch (Exception e) {
			return 0;
		}
		return f;
	}

	public static double getdouble(String s) {
		double d = 0;
		if (s == null)
			return 0;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {
			return 0;
		}
		return d;
	}

	public static Date getDate(String s) {
		Date d = null;
		if (s == null)
			return null;

		try { // d=new java.text.SimpleDateFormat("yyyy-MM-dd").parse(s);
			if (s.length() > 10)
				d = new java.text.SimpleDateFormat(TIME_FORMAT).parse(s);
			else
				d = new java.text.SimpleDateFormat(DATE_FORMAT).parse(s);
		} catch (Exception e) {
		}
		return d;
	}

	public static boolean getboolean(String s) {
		if (s == null)
			return false;
		if (s.trim().toLowerCase().equals("true"))
			return true;
		if (s.trim().equals("1"))
			return true;
		return false;
	}

	public static String getString(String s) {
		return s;
	}

	public static String getStringKey(String s) {
		return s;
	}

	public static BigDecimal getBigDecimal(String s) {
		return new BigDecimal(s);
	}

	// public static NumberKey getNumberKey(String s)
	// { if(s==null)return null;
	// if (s.trim().length()==0)return null;
	// try
	// { return new NumberKey(s);
	// }catch(Exception e)
	// {}
	// return null;
	// }

	public static char getchar(String s) {
		if (s.length() == 0)
			return ' ';
		char[] chs = s.toCharArray();
		return chs[0];
	}

	public static Date getDate(String s, int off) {
		Date d = getDate(s);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, off);
		return c.getTime();
	}

	public static Boolean getBoolean(String s) {
		return new Boolean(getboolean(s));
	}

	public static Integer getInteger(String s) {
		return new Integer(getint(s));
	}

	public static Byte getByte(String s) {
		return new Byte(s);
	}

	public static Short getShort(String s) {
		return new Short(getshort(s));
	}

	public static Long getLong(String s) {
		return new Long(getlong(s));
	}

	public static Float getFloat(String s) {
		return new Float(getfloat(s));
	}

	public static Double getDouble(String s) {
		return new Double(getdouble(s));
	}

	public static float getfloat(float s, int length) {
		StringBuffer sb=new StringBuffer();
		sb.append("#");
		for(int i=0;i<=length;i++){
			if(i==0)sb.append(".");
			sb.append("#");
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(sb.toString());
		return getfloat(df.format(s));
	}
	
	public static double getdouble(double s, int length) {
		StringBuffer sb=new StringBuffer();
		sb.append("#");
		for(int i=0;i<=length;i++){
			if(i==0)sb.append(".");
			sb.append("#");
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(sb.toString());
		return getdouble(df.format(s));
	}
}
