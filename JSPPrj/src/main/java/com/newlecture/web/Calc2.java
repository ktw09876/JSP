package com.newlecture.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 1. Application
 사용범위: 전역 범위에서 사용하는 저장 공간
 생명주기: WAS가 시작해서 종료할 때 까지
 저장위치: WAS서버의 메모리
 2. Session
 사용범위: 세션 범위에서 사용하는 저장 공간
 생명주기: 세션이 시작해서 종료할 때 까지
 저장위치: WAS서버의 메모리
 3. Cookie
 사용범위: Web Browser별 지정한 path범주 공간
 생명주기: Browser에 전달한 시간부터 만료시간까지
 저장위치: Web Browser의 메모리 또는 파일
 
 저장기간이 길다(1년이상)면 Cookie를 사용해야한다.
 특정 범위(url)에서만 데이터를 사용한다면 Cookie를 사용해야한다.
 */


@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");		
		
		String v_ = request.getParameter("v"); //1. 사용자가 입력한 값 저장
		String op = request.getParameter("operator"); //2. 사용자가 입력한 계산식 저장
		int v = 0;
		
		if(!v_.equals("")) 
			v = Integer.parseInt(v_);		
		
		if(op.equals("=")) { //3. 만약 사용자가 입력한 계산식이 "="이면
									//계산
//			int x =(Integer)application.getAttribute("value"); //저장했던 값을 꺼낸다.
//			int x =(Integer)session.getAttribute("value"); //저장했던 값을 꺼낸다.
			int x = 0;
			for(Cookie c : cookies)
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			
			int y = v;
//			String operator = (String)application.getAttribute("op");
//			String operator = (String)session.getAttribute("op");
			
			String operator = "";
			for(Cookie c : cookies)
				if(c.getName().equals("op")) {
					operator =c.getValue();
					break;
				}
			
			int result = 0;
			
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			response.getWriter().printf("result is %d\n", result);
			
		}else { //3. 만약 사용자가 입력한 계산식이 "="이 아니면(="+"또는 "-"이면)
				//값을 저장
//			application.setAttribute("value", v);
//			application.setAttribute("op", op);
//			session.setAttribute("value", v);
//			session.setAttribute("op", op);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/calc2"); //쿠키옵션1. Path: 특정 url요청에만 쿠키저장
			valueCookie.setMaxAge(24*60*60); //쿠키옵션2. MaxAge: 만료시간 설정
			opCookie.setPath("/calc2");
			response.addCookie(valueCookie);
			response.addCookie(opCookie);
			
			response.sendRedirect("calc2.html"); //calc2.html로 경로우회
		}
		
		
		
		
	}

}
