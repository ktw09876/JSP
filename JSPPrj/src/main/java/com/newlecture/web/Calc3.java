package com.newlecture.web;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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


@WebServlet("/calc3")
public class Calc3 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		
		String value = request.getParameter("value"); //1. 사용자가 입력한 값 저장
		String operator = request.getParameter("operator"); //2. 사용자가 입력한 계산식 저장
		String dot = request.getParameter("dot"); //3. 사용자가 입력한 점 저장
		
		String exp = "";
		if(cookies != null)
			for(Cookie c : cookies)
				if(c.getName().equals("exp")) {
					exp =c.getValue();
					break;
				}
		
		if(operator != null && operator.equals("=")) { //만약 값이 null이 아니고 "="가 오면
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				exp =String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(operator != null && operator.equals("C")) { //만약 값이 null이 아니고 "C"가 오면
			exp = "";
		}else {		
			exp += (value == null)? "" : value;
			exp += (operator == null)? "" : operator;
			exp += (dot == null)? "" : dot;
		}
		
		Cookie expCookie = new Cookie("exp", exp); //Cookie로 저장
		if(operator != null && operator.equals("C")) //만약 값이 null이 아니고 "C"가 오면
			expCookie.setMaxAge(0);
		
		expCookie.setPath("/");
		response.addCookie(expCookie);
		response.sendRedirect("calcpage");
		
		
	}

}
