package com.rclass.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.rclass.notice.model.service.NoticeService;
import com.rclass.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeFormEndServlet
 */
@WebServlet("/notice/noticeFormEnd")
public class NoticeFormEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFormEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 파일형식으로 제대로 전송되었는지 확인!
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "공지사항오류![Error code{001}:form enctype 관리자에게 문의하세요");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		//클라이언트가 보낸 자료를 받자~
		//1.파일전송된 파일을 처리하기 위해 전송된 파일을 저장할 공간 확보~
		//디렉토리경로를 확인! 파일이 저장 low 최상위 드라이브 시작! 절대경로로 확보
		String dir = getServletContext().getRealPath("/");//루트 web~
		dir+="upload/notice";
		System.out.println("파일저장경로 : "+dir);
		//2.저장크기설정
		int maxSize=1024*1024*10;
		//3.파일 업로드 처리할 MultipartRequest객체생성
		
		MultipartRequest mr = new MultipartRequest(request, dir, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		// MultipartRequest 안에 들어갈 값 : request, 저장경로, 파일크기, 문자형식, 중복값 금지
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		String file = mr.getFilesystemName("up_file");
		
		Notice n = new Notice();
		n.setNoticeTitle(title);
		n.setNoticeWriter(writer);
		n.setNoticeContent(content);
		n.setFilepath(file);
		
		int insertResult = new NoticeService().insertNotice(n);
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(insertResult>0) {
			msg="공지사항 등록 성공";
			loc="/notice/noticeView?noticeNo="+insertResult;
		}
		else {
			msg="공지사항 등록 실패";
			loc="/notice/noticeForm";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
