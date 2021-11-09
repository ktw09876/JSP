package com.newlecture.web.controller.admin.notice;

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

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet {
	//url이 없으면 404에러(url 오류)
	//url은 있지만 처리할 수 있는 메서드가 없으면 405에러(메서드 오류)
	//url도 있고 메서드도 있지만 권한이 없으면 403에러(보안 오류)
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		
		switch(cmd) {
		case "일괄공개":
			for(String openId : openIds)
				System.out.printf("open id : %s\n", openId);
			break;
		case "일괄삭제":
			NoticeService service = new NoticeService();
			int[] ids = new int[delIds.length];
			for(int i=0; i<delIds.length; i++)
				ids[i]= Integer.parseInt(delIds[i]);
			int result = service.deleteNoticeAll(ids); //선택한 것을 잘 삭제했는지 확인을 위해 결과값을 반환받는다
			break;
		}
		
		response.sendRedirect("list"); //다른 페이지로 이동한다

		
	}
	
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
		.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp")
		.forward(request, response);
		
	}
}
