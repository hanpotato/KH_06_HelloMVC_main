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
 * Servlet implementation class MemberUpdateEndServlet
 */
@WebServlet(name="MemberUpdateEndServlet",urlPatterns="/memberUpdateEnd")
public class MemberUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId=request.getParameter("userId");
//		String password=request.getParameter("password");
		String userName=request.getParameter("userName");
		int age=Integer.parseInt(request.getParameter("age"));
		String email=request.getParameter("email");
		String gender=request.getParameter("gender");
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		String[] hobby=request.getParameterValues("hobby");
		String hobbys=String.join(",", hobby);
		Member m=new Member();
		m.setUserId(userId);
//		m.setPassword(password);
		m.setUserName(userName);
		m.setAge(age);
		m.setEmail(email);
		m.setGender(gender);
		m.setPhone(phone);
		m.setAddress(address);
		m.setHobby(hobbys);
		
		int result = new MemberService().updateMember(m);
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(result>0) {
			msg="회원정보수정을 완료하였습니다.";
		}
		else {
			msg="회원정보수정을 실패하였습니다.";
//			loc="/views/member/memberView.jsp";
			loc="/memberUpdate?userId="+userId;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		request.getRequestDispatcher(view).forward(request, response);
		
		
//		HttpSession session = request.getSession();
//		String userId = ((Member)session.getAttribute("loginMember")).getUserId();
//		
//		Member m = new Member();
//		m.setUserId(userId);
//		m.setPassword(request.getParameter("password"));
//		m.setUserName(request.getParameter("userName"));
//		m.setAge(Integer.parseInt(request.getParameter("age")));
//		m.setEmail(request.getParameter("email"));
//		m.setPhone(request.getParameter("phone"));
//		m.setGender(request.getParameter("gender"));
//		m.setAddress(request.getParameter("address"));
//		
//		String[] hobbys=request.getParameterValues("hobby");
//		String hobby=String.join(",", hobbys);
//		m.setHobby(hobby);
//		
//		int result = new MemberService().updateMember(m);
//		
//		String views = "/views/common/msg.jsp";
//		String loc = "/";
//		String msg = "";
//		
//		if(result > 0) {
//			msg = "회원수정 성공.";
//		}
//		else {
//			msg = "회원수정 실패.";
//		}
//		
//		request.setAttribute("msg", msg);
//		request.setAttribute("loc", loc);
//		
//		request.getRequestDispatcher(views).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
