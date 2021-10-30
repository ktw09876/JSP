package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class Nana extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //charset을 UTF-8로 브라우저에게 보내겠다
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 text/html로  해석하고 charset은 UTF-8로 해석해라
		
		PrintWriter out = response.getWriter();
		
		String cnt_ = request.getParameter("cnt");
		
		int cnt = 100;
		if(cnt_ != null && !cnt_.equals("")) //요청값이 null이 아니거나 빈 문자열이 아닌 경우
			cnt = Integer.parseInt(cnt_); //QueryStirng ?cnt=횟수
		/*
		 http://.../hello?cnt=3 --> "3"
		 http://.../hello?cnt     --> ""
		 http://.../hello?          --> null
		 http://.../hello           --> null
		 */

		for(int i=0; i<cnt; i++) 
			out.println((i+1)+": 안녕 Servlet!! <br>");	
	}
}







