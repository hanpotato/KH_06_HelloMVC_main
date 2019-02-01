package com.rclass.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.rclass.board.model.service.BoardService;
import com.rclass.board.model.vo.Board;

import common.rename.MyFileRenamePolicy;

/**
 * Servlet implementation class BoardFormEndServlet
 */
@WebServlet("/board/boardFormEnd")
public class BoardFormEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFormEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "잘못접근! 조심!");
			request.setAttribute("loc", "/board/boardList");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		String root = getServletContext().getRealPath("/upload");
		String filepath=root+File.separator+"board";
		int maxSize = 1024*1024*10;
		MultipartRequest mr = new MultipartRequest(request, filepath, maxSize, "UTF-8", new MyFileRenamePolicy());
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		String fileO = mr.getOriginalFileName("up_file");
		String fileR = mr.getFilesystemName("up_file");
		
		Board bo = new Board();
		bo.setBoardTitle(title);
		bo.setBoardWriter(writer);
		bo.setBoardContent(content);
		bo.setBoardOriginalFilename(fileO);
		bo.setBoardRenamedFilename(fileR);
		
		int iResult = new BoardService().insertBoard(bo);
		String msg="";
		String loc="";
		String view="/views/common/msg.jsp";
		if(iResult>0) {
			msg="자유게시판 등록 성공";
			loc="/board/boardView?boardNo="+iResult;
		}
		else {
			msg="자유게시판 등록 실패";
			loc="/board/boardForm";
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
