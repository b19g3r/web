package com.tedu.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 登录业务
 * @author adminitartor
 *
 */
public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		//获取用户名及密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try (
			RandomAccessFile raf = new RandomAccessFile("user.dat","r");	
		){
			boolean tf = false;//默认状态:登录失败
			for(int i=0;i<raf.length()/100;i++){
				//移动指针到该记录的开始位置
				raf.seek(i*100);
				//读取用户名
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				//是不是该用户
				if(name.equals(username)){
					//读密码
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					if(pwd.equals(password)){
						//登录成功
						tf = true;
					}
					break;
				}
			}
			if(tf){
				//跳转登录成功的页面
				forward("/myweb/login_success.html", request, response);
			}else{
				//跳转登录失败的页面
				forward("/myweb/login_fail.html", request, response);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}








