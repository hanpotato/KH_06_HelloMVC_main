package com.rclass.board.model.service;

import java.sql.Connection;
import java.util.List;

import com.rclass.board.model.dao.BoardDao;
import com.rclass.board.model.vo.Board;

import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.rollback;
import static common.JDBCTemplate.close;


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
	
	public Board selectOne(int boardNo) {
		Connection conn = getConnection();
		Board bo = dao.selectOne(conn,boardNo);
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
}
