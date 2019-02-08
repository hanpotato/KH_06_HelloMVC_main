package com.rclass.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.board.model.service.BoardService;
import com.rclass.board.model.vo.Board;
import com.rclass.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardViewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int boardNo = Integer.parseInt(request.getParameter("boardNo"));

		Cookie[] cookies = request.getCookies();
		String boardCookieVal = "";
		boolean hasRead = false;

		// 원하는 쿠키가 없다면? 읽지 않았다.
		if (cookies != null) {
			output: for (Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				if ("boardCookie".equals(name)) {
					boardCookieVal = value;
					if (value.contains("|" + boardNo + "|")) {
						hasRead = true;
						break output;
					}
				}
			}
		}
		
		// boardCookie를 세팅
		if(!hasRead) {
			Cookie c = new Cookie("boardCookie", boardCookieVal+"|"+boardNo+"|");
			c.setMaxAge(-1);//-1 : 브라우저를 닫거나 session을 닫았을때 쿠키값을 지운다.
			response.addCookie(c);
		}

		Board bo = new BoardService().selectOne(boardNo,hasRead);
		if(bo!=null) {
			List<BoardComment> comments = new BoardService().selectCommentAll(boardNo);
			request.setAttribute("comments", comments);
		}

		request.setAttribute("bo", bo);
		request.getRequestDispatcher("/views/board/boardView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
