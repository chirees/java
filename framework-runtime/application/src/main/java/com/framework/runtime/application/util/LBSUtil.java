package com.framework.runtime.application.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

public class LBSUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	
	private static final String BAIDU_LOCATION_API = "http://api.map.baidu.com/place/v2/search?ak=28frpjN74axQGxvDS8NHA4r1&output=json&query=%{addr}&page_size=20&page_num=0&scope=2&region=%{region}";

	private static double EARTH_RADIUS = 6378137;
	
	
	public static void main(String[] args) {
		System.out.println(LBSUtil.location("泓毅大厦", "上海"));
	}
	
	
	public static List<LBSLocation> location(String addr, String city) {
		String url = BAIDU_LOCATION_API.replace("%{addr}",  addr).replace("%{region}", city);
		HttpRequest request = HttpRequest.get(url);
		String json = request.body();
		
		Gson gson = new Gson();
		Map map = gson.fromJson(json, HashMap.class);
		
		List<LBSLocation> locations = new ArrayList();
		if(map.get("message").equals("ok")) {
			List<Map> results = (List<Map>)map.get("results");
			for(Map m:results) {
				LBSLocation l = new LBSLocation();
				String name = (String)m.get("name");
				
				if(m.containsKey("location") && ((Map)m.get("location")).containsKey("lat")) {
					Double lat = (Double)((Map)m.get("location")).get("lat");
					l.setLat(lat);
				}
				
				if(m.containsKey("location") && ((Map)m.get("location")).containsKey("lng")) {
					Double lng = (Double)((Map)m.get("location")).get("lng");
					l.setLng(lng);
				}
				
				String address = (String)m.get("address");
				String telephone = (String)m.get("telephone");
				
				l.setName(name);
				
				l.setAddress(address);
				l.setTelephone(telephone);
				
				locations.add(l);
			}
		}
		
		return locations;
	}
	

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	// 球面上的2点间的距离 纬度，经度，纬度，经度

	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	// 椭球面上的2点间的距离 纬度，经度，纬度，经度
	static double getFlatternDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double f = rad((lat1 + lat2) / 2);
		double g = rad((lat1 - lat2) / 2);
		double l = rad((lng1 - lng2) / 2);

		double sg = Math.sin(g);
		double sl = Math.sin(l);
		double sf = Math.sin(f);

		double s, c, w, r, d, h1, h2;
		double a = EARTH_RADIUS;
		double fl = 1 / 298.257;

		sg = sg * sg;
		sl = sl * sl;
		sf = sf * sf;

		s = sg * (1 - sl) + (1 - sf) * sl;
		c = (1 - sg) * (1 - sl) + sf * sl;

		w = Math.atan(Math.sqrt(s / c));
		r = Math.sqrt(s * c) / w;
		d = 2 * w * a;
		h1 = (3 * r - 1) / 2 / c;
		h2 = (3 * r + 1) / 2 / s;

		return d * (1 + fl * (h1 * sf * (1 - sg) - h2 * (1 - sf) * sg));
	}
}
