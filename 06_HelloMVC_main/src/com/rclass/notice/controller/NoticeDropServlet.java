package com.rclass.notice.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDropServlet
 */
@WebServlet("/notice/noticeDrop")
public class NoticeDropServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDropServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		String fileName = request.getParameter("fileName");
		
		int result = new NoticeService().dropNotice(no);
		
		//파일 저장 위치 설정
		String root = getServletContext().getRealPath("/");
		String filePath = root+File.separator+"upload"+File.separator+"notice";
		
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(result>0) {
			//파일 삭제
			File delFile = new File(filePath+"/"+fileName);
			boolean delResult = delFile.delete();
			System.out.println(delResult?fileName+" : 제대로 지워짐":fileName+" : 안 지워짐.");
			msg="공지사항 삭제(완전삭제) 성공";
			loc="/notice/noticeList";
		}
		else {
			msg="공지사항 삭제(완전삭제) 실패";
			loc="/notice/noticeView?noticeNo="+no;
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
