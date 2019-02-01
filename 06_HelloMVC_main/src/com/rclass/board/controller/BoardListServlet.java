package com.rclass.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rclass.board.model.service.BoardService;
import com.rclass.board.model.vo.Board;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
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
			numPerPage=5;
		}
		int totalContent = new BoardService().selectListCount();
		List<Board> list = new BoardService().selectList(cPage,numPerPage);
		
		int totalPage = (int)Math.ceil((double)totalContent/numPerPage);
		int pageBarSize = 5;
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo+pageBarSize-1;
		String pageBar="";
		if(pageNo==1) {
			pageBar+="<span>[이전]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/board/boardList?cPage="+(cPage-1)+"&numPerPage="+numPerPage+"'>[이전]</a>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<span class='cPage'>"+pageNo+"</span>";
			}
			else {
				pageBar+="<a href='"+request.getContextPath()+"/board/boardList?cPage="+pageNo+"&numPerPage="+numPerPage+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/board/boardList?cPage="+pageNo+"&numPerPage"+numPerPage+"'>[다음]</a>";
		}
		
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("numPerPage", numPerPage);
		request.getRequestDispatcher("/views/board/boardList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
