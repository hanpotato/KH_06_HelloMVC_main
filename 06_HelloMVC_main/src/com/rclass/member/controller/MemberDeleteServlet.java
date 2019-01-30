package com.rclass.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rclass.member.model.service.MemberService;
import com.rclass.member.model.vo.Member;

/**
 * Servlet implementation class DelMemberServlet
 */
@WebServlet("/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId=request.getParameter("userId");
		Member m = new Member();
		m.setUserId(userId);
		
		int result = new MemberService().deleteMember(m);
		
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(result>0) {
			msg="회원탈회 성공";
			//접속자에 대한 세션삭제
			request.getSession(false).invalidate();
		}
		else {
			msg="회원탈퇴 실패";
			loc="/memberUpdate";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
//		HttpSession session = request.getSession();
//		String userId = ((Member)session.getAttribute("loginMember")).getUserId();
//		
//		Member m = new Member();
//		m.setUserId(userId);
//		
//		System.out.println(m.toString());
//		
//		int result = new MemberService().deleteMember(m);
//		
//		String view="/views/common/msg.jsp";
//		String loc="";
//		String msg="";
//		if(result>0) {
//			msg="회원탈퇴 성공.";
//		}
//		else {
//			msg="회원탈퇴 실패.";
//		}
//		
//		request.setAttribute("msg", msg);
//		request.setAttribute("loc", loc);
//		
//		request.getRequestDispatcher(view).forward(request, response);
//		session.invalidate();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
