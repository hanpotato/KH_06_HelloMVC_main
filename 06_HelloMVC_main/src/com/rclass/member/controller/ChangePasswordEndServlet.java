package com.rclass.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.member.model.service.MemberService;
import com.rclass.member.model.vo.Member;

/**
 * Servlet implementation class ChangePasswordEndServlet
 */
@WebServlet(name="ChangePasswordEndServlet", urlPatterns="/changePasswordEnd")
public class ChangePasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService service=new MemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String curentPw=request.getParameter("password");
		String newPw=request.getParameter("password_new");
		String userId=request.getParameter("userId");
		
//		System.out.println(curentPw+" : "+newPw+" : "+userId);
		
		Member m=new Member();
		m.setUserId(userId);
		Member result=service.selectOne(m);
		int insertResult = 0;
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(result!=null) {
			if(result.getPassword().equals(curentPw)) {
				m.setPassword(newPw);
				insertResult = service.updatePassword(m);
				if(insertResult>0) {
					msg="패스워드 수정이 완료되었습니다.";
					loc="/memberUpdate?userId="+userId;
					String script="self.close()";
					request.setAttribute("script", script);
				}
				else {
					msg="패스워드 수정을 실패하였습니다.";
					loc="/views/member/changePassword.jsp?userId="+userId;
				}
			}
			else {
				msg="현재비밀번호가 일치하지 않습니다.";
				loc="/views/member/changePassword.jsp?userId="+userId;
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
		
		
/*		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String password_new = request.getParameter("password_new");
		
//		System.out.println("[userId] "+userId);
//		System.out.println("[password] "+password);
//		System.out.println("[password_new] "+password_new);
		
		Member m = new Member();
		m.setUserId(userId);
		Member resultA = new MemberService().selectOne(m);
		
		String msg="";
		boolean flag=false;
		String view="/views/member/changePassword.jsp";
		if(!(resultA.getPassword().equals(password))) {
			msg="현재 비밀번호가 일치 하지 않습니다.";

		}
		else {
			m.setPassword(password_new);
			int resultB = new MemberService().changePassword(m);
			if(resultB>0) {
				msg="비밀번호 변경 완료.";
			}
			else {
				msg="비밀번호 변경 실패.";
			}
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("flag", flag);
		request.getRequestDispatcher(view).forward(request, response);
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
