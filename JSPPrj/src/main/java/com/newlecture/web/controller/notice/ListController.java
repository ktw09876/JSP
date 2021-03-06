package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@WebServlet("/notice/list")
public class ListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list?f=title&q=a
		
		String field_ = request.getParameter("f"); 
		String query_ = request.getParameter("q"); //일단 사용자가 입력한 값을 임시변수에 담아두고
		String page_ =  request.getParameter("p");
		
		String field = "title"; //기본값
		if(field_ != null && !field_.equals("")) //사용자가 입력한 값이 null이 아니면
			field = field_; //그 값을 field에 담아서 전달
		
		String query = ""; //기본값
		if(query_ != null && !query_.equals("")) //사용자가 입력한 값이 null이 아니면
			query = query_; //그 값을 query에 담아서 전달
		
		int page = 1; //기본값
		if(page_ != null && !page_.equals("")) //사용자가 입력한 값이 null이 아니면
			page = Integer.parseInt(page_); //그 값을 page에 담아서 전달
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);
		
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		request
		.getRequestDispatcher("/WEB-INF/view/notice/list.jsp")
		.forward(request, response);
		
	}
}
