package com.zkname.credit.card.util;

public class DistanceUtil {

	private static double R = 6378137; //地球半径
	
	public static int POST_DISTANCE = 3000;
	
	public static double Distance(double long1, double lat1, double long2, double lat2) {
		double a, b;
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		a = Math.sin(a / 2.0);
		b = Math.sin(b / 2.0);
		return 2 * R * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b));
	}
	
	public static void main(String[] args) {
		System.out.println(Distance(30.2827370000, 120.1288160000, 30.2829480000, 120.1341660000));
	}
}
