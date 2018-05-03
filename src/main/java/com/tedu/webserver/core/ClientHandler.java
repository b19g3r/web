package com.tedu.webserver.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;
import com.tedu.webserver.servlet.HttpServlet;

/**
 * 线程任务类,用于处理某个客户端的请求并予以响应
 * @author adminitartor
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run() {
		System.out.println("一个客户端连接了!");
		try {
			/*
			 * 处理客户端请求的流程
			 * 1:解析请求
			 * 2:处理请求
			 * 3:给予响应 
			 */
			//1
			HttpRequest request = new HttpRequest(socket);
			HttpResponse response = new HttpResponse(socket);	
			
			//2
			//获取请求的路径
			String url = request.getRequestURI();
			System.out.println("url:"+url);
			
			//判断该请求是否为请求某个业务操作
			if(ServerContext.hasServlet(url)){
				//处理业务
				//1获取该请求对应的Servlet的名字
				String servletName 
					= ServerContext.getServletByUrl(url);
				//2通过反射机制,加载该Servlet
				Class cls = Class.forName(servletName);
				//3实例化该Servlet
				HttpServlet servlet 
					= (HttpServlet)cls.newInstance();
				System.out.println("实例化:"+servletName);
				//4调用service方法处理业务
				servlet.service(request, response);
				
			}else{
				File file = new File("webapps"+url);
				if(file.exists()){
					System.out.println("找到该文件");
					//将客户端请求的文件设置到response中
					response.setEntity(file);				
				}else{
					//响应404错误
					//1设置响应的状态代码为404
					response.setStatusCode(404);
					//2设置错误页面
					File notFoundPage = new File("webapps/sys/404.html");
					response.setEntity(notFoundPage);
				}
			}
			
			//响应客户端
			response.flush();
			
		} catch (EmptyRequestException e){
			System.out.println("空请求.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//与客户端断开连接
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	
}






