package com.tedu.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer主类
 * @author adminitartor
 *
 */
public class WebServer {
	private ServerSocket server;
	private ExecutorService threadPool;
	/**
	 * 初始化服务端
	 */
	public WebServer(){
		try {
			/*
			 * Tomcat的默认服务端口就是8080
			 */
			server = new ServerSocket(8080);
			
			threadPool = Executors.newFixedThreadPool(40);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 服务端启动操作
	 */
	public void start(){
		try {
			while(true){
				Socket socket = server.accept();
				//启动线程处理该客户端操作
				ClientHandler handler 
					= new ClientHandler(socket);
				
				threadPool.execute(handler);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
	}
}









