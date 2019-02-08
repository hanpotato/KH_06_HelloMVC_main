package com.rclass.board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.rclass.board.model.dao.BoardDao;
import com.rclass.board.model.vo.Board;
import com.rclass.board.model.vo.BoardComment;


public class BoardService {

	public BoardDao dao = new BoardDao();
	
	public int selectListCount() {
		Connection conn = getConnection();
		int totalCount = dao.selectListCount(conn);
		if(totalCount>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return totalCount;
		
	}
	
	public List<Board> selectList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Board> list = dao.selectList(conn,cPage,numPerPage);
		close(conn);
		return list;
	}
	
	public Board selectOne(int boardNo, boolean hasRead) {
		Connection conn = getConnection();
		Board bo = dao.selectOne(conn,boardNo);
		if(bo!=null&&!hasRead) {
			int result=dao.increReadCount(conn,boardNo);
			if(result>0) {
				commit(conn);
			}
			else {
				rollback(conn);
			}
		}
		close(conn);
		return bo;
	}
	
	public int insertBoard(Board bo) {
		Connection conn = getConnection();
		int iResult = dao.insertBoard(conn,bo);
		if(iResult>0) {
			iResult = dao.selectSeq(conn);
			commit(conn);
		}
		else {
			rollback(conn);
		}
		return iResult;
	}
	
	public int insertComment(BoardComment comment) {
		Connection conn = getConnection();
		int result = dao.insertComment(conn,comment);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public List<BoardComment> selectCommentAll(int no) {
		Connection conn = getConnection();
		List<BoardComment> list = dao.selectCommentAll(conn, no);
		close(conn);
		return list;
	}
}
