package com.rclass.notice.controller;

import java.io.File;
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
 * Servlet implementation class NoticeUpdateEndServlet
 */
@WebServlet("/notice/noticeUpdateEnd")
public class NoticeUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "잘못왔어");
			request.setAttribute("loc", "/notice/noticeList");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		String root = getServletContext().getRealPath("/");
		String filePath = root+File.separator+"upload"+File.separator+"notice"; // separator 은 슬러시('/')과 같다. 정확성이 더 높다.
		//dir+="upload/notice";
		
		int maxSize=1024*1024*10;
		
		MultipartRequest mr = new MultipartRequest(request, filePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		int no = Integer.parseInt(mr.getParameter("no"));
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		String fileName = mr.getFilesystemName("up_file");
		File f = mr.getFile("up_file");
		
		if(f!=null&&f.length()>0) {
			File delFile=new File(filePath+"/"+mr.getParameter("old_file"));
			boolean resul = delFile.delete();
			System.out.println(resul?"제대로 지워짐":"안지워졌어!");
		}
		else {
			fileName = mr.getParameter("old_file");
		}

		Notice n = new Notice();
		n.setNoticeNo(no);
		n.setNoticeTitle(title);
		n.setNoticeWriter(writer);
		n.setNoticeContent(content);
		n.setFilepath(fileName);
		
//		
//		// 기존 파일 수정 안했을때 다시 값을 받아오기. (내가 생각한것
//		if (mr.getFilesystemName("up_file")==null) {
//			Notice n1 = new NoticeService().selectOne(no);
//			n.setFilepath(n1.getFilepath());
//		}
//		
		
		int updateResult = new NoticeService().updateNotice(n);
		
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(updateResult>0) {
			msg="공지사항 수정 성공";
			loc="/notice/noticeView?noticeNo="+no;
		}
		else {
			msg="공지사항 수정 실패";
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
