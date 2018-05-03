package com.tedu.webserver.servlet;

import java.io.File;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 所有Servlet的超类.规定所有Servlet都应具备的
 * 功能.
 * @author adminitartor
 *
 */
public abstract class HttpServlet {
	/**
	 * 该方法用来处理业务逻辑
	 * @param request
	 * @param response
	 */
	public abstract void service(HttpRequest request,HttpResponse response);
	
	/**
	 * 
	 * @param url
	 * @param request
	 * @param response
	 */
	public void forward(
		String url,
		HttpRequest request,
		HttpResponse response
	){
		File file = new File("webapps"+url);
		response.setEntity(file);
	}
	
}






