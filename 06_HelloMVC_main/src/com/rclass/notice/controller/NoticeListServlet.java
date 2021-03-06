package com.rclass.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.notice.model.service.NoticeService;
import com.rclass.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/notice/noticeList")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int cPage;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (Exception e) {
			cPage=1;
		}
		int numPerPage;
		try {
			numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		} catch (Exception e) {
			numPerPage = 5;
		}
		
		boolean delListFlag;
		if(request.getParameter("delListFlag")!=null) {
			delListFlag = Boolean.parseBoolean(request.getParameter("delListFlag"));
		}
		else {
			delListFlag = true;
		}
		
		int totalContent = 0;
		List<Notice> list = null;
		if(delListFlag) {
			totalContent = new NoticeService().selectListCount();
			list = new NoticeService().selectList(cPage,numPerPage);
		}
		else {
			totalContent = new NoticeService().selectDelListCount();
			list = new NoticeService().selectDelList(cPage,numPerPage);
			delListFlag = false;
		}
		
		int totalPage = (int)Math.ceil((double)totalContent/numPerPage);
		int pageBarSize = 5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		String pageBar="";
		if(pageNo==1) {
			pageBar+="<span>[이전]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/notice/noticeList?cPage="+(pageNo-1)+"&numPerPage="+numPerPage+"'[이전]</a>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<span class='cPage'>"+pageNo+"</span>";
			}
			else {
				pageBar+="<a href='"+request.getContextPath()+"/notice/noticeList?cPage="+pageNo+"&numPerPage="+numPerPage+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/notice/noticeList?cPage="+pageNo+"&numPerPage"+numPerPage+"'>[다음]</a>";
		}
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("numPerPage", numPerPage);
		request.setAttribute("delListFlag", delListFlag);
		request.getRequestDispatcher("/views/notice/noticeList.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
