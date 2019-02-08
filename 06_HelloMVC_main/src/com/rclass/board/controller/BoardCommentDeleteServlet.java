package com.rclass.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.board.model.service.BoardService;
import com.rclass.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardCommentDeleteServlet
 */
@WebServlet("/board/boardCommentDelete")
public class BoardCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int delNo = Integer.parseInt(request.getParameter("delNo"));
		
		BoardComment bc = new BoardComment();
		bc.setBoardCommentNo(delNo);
		
		int result = new BoardService().deleteBoard(bc);
		
		String msg = "";
		String loc = "";
		String view = "/views/common/msg.jsp";
		if(result>0) {
			msg = "댓글 삭제 성공";
			loc = "/board/boardView?boardNo="+boardNo;
		}
		else {
			msg = "댓글 삭제 실패";
			loc = "/board/boardView?boardNo="+boardNo;
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
