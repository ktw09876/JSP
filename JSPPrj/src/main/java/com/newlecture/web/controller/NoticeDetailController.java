package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		String url = "jdbc:oracle:thin:@121.148.39.128:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "test1", "1111");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id); //15행 1번째 물음표에 값을 넣겠다
			
			ResultSet rs = st.executeQuery();
			
			rs.next();
			
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			Date regdate = rs.getDate("REGDATE");	
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");
			
			Notice notice = new Notice(
											id,
											title,
											writerId,
											regdate,
											hit,
											files,
											content					
										);
			
			request.setAttribute("n", notice);
			
			/*
			request.setAttribute("title", title);
			request.setAttribute("writerId", writerId);
			request.setAttribute("regdate", regdate);
			request.setAttribute("hit", hit);
			request.setAttribute("files", files);
			request.setAttribute("content", content);
			*/
			
		    rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//redirect 경로 전환
		
		//forward(controller와 view를 연결)
		request
		.getRequestDispatcher("/notice/detail.jsp")
		.forward(request, response);
		
	}
}
