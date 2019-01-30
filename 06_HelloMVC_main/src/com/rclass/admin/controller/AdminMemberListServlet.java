package com.rclass.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.admin.model.service.AdminService;
import com.rclass.member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberListServlet
 */
@WebServlet("/admin/memberList")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		int result = -1;
		
		try {
			if(result<0) {
				throw new MyServletException("에러가 발생하였습니다");
			}
		}
		catch(MyServletException e) {
			request.setAttribute("e", e);
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request,response);
		}
*/
		

		Member loginMember=(Member)request.getSession(false).getAttribute("loginMember");
		if(loginMember==null||!"admin".equals(loginMember.getUserId())) {
			request.setAttribute("msg", "잘못된 경로로 이동하셨습니다");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		//페이징처리 시작~ 두둥!
		int cPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}
		//NumberFormatException는 값이 null이 올때 넘어가는것.
		catch (NumberFormatException e) {
			cPage=1;
		}
		int numPerPage;
		try {
			numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		}
		catch (NumberFormatException e) {
			numPerPage=5;
		}
		//총 페이지의 갯수 구하기
		int totalContent = new AdminService().selectMemberCount();
		
		int totalPage = (int)Math.ceil((double)totalContent/numPerPage);
		
		//보여줄 자료가져오기!
		List<Member> list=new AdminService().selectMemberList(cPage,numPerPage);
		
		//pageBar구성
		int pageBarSize=5;//bar에 출력할 페이지 수
		String pageBar="";//bar를 만든 소스코드(html)
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1; //시작지점
		//시작지점 1, 6, 11, 16
		//기준 : cPage 1~5 : 1 / cPage 6~10 : 6
		int pageEnd=pageNo+pageBarSize-1;

		//페이지에 출력해줄 소스코드 작성
		//[이전] 코드작성
		if(pageNo==1) {
			pageBar+="<span>[이전]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+(pageNo-1)+"&numPerPage="+numPerPage+"'>[이전]</a>";
		}
		//페이지연결 숫자 소스작성
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<span class='cPage'>"+pageNo+"</span>";
			}
			else
			{
				pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+pageNo+"&numPerPage="+numPerPage+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		//[다음] 코드작성
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+pageNo+"&numPerPage"+numPerPage+"'>[다음]</a>";
		}
		
		//view를 선택해서 자료를 전송!
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("numPerPage", numPerPage);
		request.getRequestDispatcher("/views/admin/memberList.jsp").forward(request, response);
		
		
		
//		// 모든 회원의 정보를 출력해주는 서블릿
//		
//		List<Member> list= new AdminService().selectMemberList();
//		
//		//view를 선택해서 자료를 전송!
//		request.setAttribute("list", list);
//		request.getRequestDispatcher("/views/admin/memberList.jsp").forward(request, response);
//		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}
}
