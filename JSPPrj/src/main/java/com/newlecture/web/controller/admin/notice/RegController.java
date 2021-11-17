package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig(
		fileSizeThreshold =1024*1024, //1. 전송하는 데이터가 1mb를 넘어서면  
		maxFileSize = 1024*1024*50, //파일 하나의 크기가 50mb
		maxRequestSize = 1024*1024*50*5 //파일을 여러 개 첨부할 경우 전체 파일 크기는 250mb를 초과할 수 없다.
)
@WebServlet("/admin/board/notice/reg") //url mapping
public class RegController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request
		.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp") //뷰 페이지 경로 지정
		.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		
		System.out.print("title: ");
		System.out.println(title);
		
		
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		
		Collection<Part> parts	 = request.getParts();
		StringBuilder builder = new StringBuilder();
		
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue; //file이 아니면 넘어가고 file이면 코드진행 -> 저장
			if(p.getSize() == 0) continue; //file크기가 0이어도 코드진행 -> 파일을 하나만 첨부해도 등록할 수 있다
			
			Part filePart = p; //다중 파일 첨부
//			Part filePart = request.getPart("file"); //단일 파일 첨부 input태그에서 name속성으로 보낸 file을 받을 때
			String fileName = filePart.getSubmittedFileName(); //파일이름
			builder.append(fileName); //사용자가 전달한 파일명을
			builder.append(","); //","로 구분
			
			InputStream fis = filePart.getInputStream(); //파일을 저장
			
			String realPath = request.getServletContext().getRealPath("/upload"); //지정한 경로의 절대(물리)경로를 얻어준다
			System.out.println(realPath);
			
			File path = new File(realPath);
			if(!path.exists())
				path.mkdirs();
			
			String filePath = 	realPath +File.separator + fileName; //File.separator -> 현재 시스템이 가지고 있는 경로 지정 방법
			FileOutputStream fos = new FileOutputStream(filePath); 
			
			byte[] buf = new byte[1024];
			int size = 0;
			while((size=fis.read(buf)) != -1) //파일을 1024byte읽는다, 읽은 파일 데이터가 -1이 아니라면
				fos.write(buf,0 ,size); //buf(1024byte) 쓴다, 0부터 size길이만큼 반복해서 저장
			
			fos.close();
			fis.close(); 
		}
		
		builder.delete(builder.length()-1, builder.length()); //delete는 마지막을 포함하지 않는다. builder에 포함된 문자열 중 마지막 문자열을 삭제
		
		boolean pub = false;
		if(isOpen != null)
			pub = true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec");
		notice.setFiles(builder.toString());
		
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice); //데이터베이스에 저장
		
		response.sendRedirect("list");
	}
}
