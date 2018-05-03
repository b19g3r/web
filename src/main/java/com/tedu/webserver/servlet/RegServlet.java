package com.tedu.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 处理注册业务
 * @author adminitartor
 *
 */
public class RegServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		/*
		 * 注册业务的流程
		 * 1:通过request获取用户注册信息
		 * 2:将信息写入文件user.dat中
		 * 3:设置response对象给客户端响应注册结果
		 */
		//1
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(request.getParameter("age"));
		
		
		//2
		try(
			RandomAccessFile raf 
				= new RandomAccessFile("user.dat","rw");
		) {
			//先将指针移动到文件末尾,以便追加新记录
			raf.seek(raf.length());
			/*
			 * 每条记录占用100字节,其中用户名,密码,
			 * 昵称各占32字节,int型的age占4字节.
			 * 数据"留白"的目的是便于后期修改信息.
			 */
			//写用户名
			byte[] data = username.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);//一次写了32字节
			
			data = password.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			data = nickname.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			raf.writeInt(age);
			
			System.out.println("注册完毕!");
			
			//3响应注册成功页面
//			File file = new File("webapps/myweb/reg_success.html");
//			response.setEntity(file);
			forward("/myweb/reg_success.html", request, response);
		} catch (Exception e) {
			
		}
		
	}
}





