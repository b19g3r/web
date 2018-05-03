package com.tedu.webserver.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器相关信息定义
 * @author adminitartor
 *
 */
public class ServerContext {
	/**
	 * Servlet映射信息
	 * key:url
	 * value:Servlet名字
	 */
	private static Map<String,String> SERVLET_MAPPING = new HashMap<String,String>();

	static{
		initServletMapping();
	}
	
	/**
	 * 初始化Servlet映射信息
	 */
	private static void initServletMapping(){
		SERVLET_MAPPING.put("/myweb/reg", "com.tedu.webserver.servlet.RegServlet");
		SERVLET_MAPPING.put("/myweb/login", "com.tedu.webserver.servlet.LoginServlet");
	}
	/**
	 * 检查给定的url是否对应Servlet处理
	 * @param url
	 * @return
	 */
	public static boolean hasServlet(String url){
		/*
		 * 用给定的url检查是否作为key存在即可知道
		 * 是否对应一个Servlet
		 */
		return SERVLET_MAPPING.containsKey(url);
	}
	/**
	 * 根据给定的url获取对应的Servlet名字
	 * @param url
	 * @return
	 */
	public static String getServletByUrl(String url){
		return SERVLET_MAPPING.get(url);
	}
	
	public static void main(String[] args) {
		boolean have = hasServlet("/myweb/reg");
		System.out.println(have);
		
		String name = getServletByUrl("/myweb/reg");
		System.out.println(name);
	}
}








