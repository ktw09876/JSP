package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notice-reg")
public class NoticeReg extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //charset을 UTF-8로 브라우저에게 보내겠다
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 text/html로  해석하고 charset은 UTF-8로 해석해라
//		request.setCharacterEncoding("UTF-8"); //한글로 읽어온다.
		
		PrintWriter out = response.getWriter();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		out.println(title);
		out.println(content);
	}
}







