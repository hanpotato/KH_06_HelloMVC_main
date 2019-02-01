package com.rclass.notice.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.rclass.notice.model.dao.NoticeDao;
import com.rclass.notice.model.vo.Notice;

public class NoticeService {
	
	private NoticeDao dao = new NoticeDao();

	public List<Notice> selectList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Notice> list = dao.selectList(conn,cPage,numPerPage);
		close(conn);
		return list;
	}
	
	public List<Notice> selectDelList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Notice> list = dao.selectDelList(conn,cPage,numPerPage);
		close(conn);
		return list;
	}
	
	public int selectListCount() {
		Connection conn = getConnection();
		int result = dao.selectListCount(conn);
		close(conn);
		return result;
	}
	
	public int selectDelListCount() {
		Connection conn = getConnection();
		int result = dao.selectDelListCount(conn);
		close(conn);
		return result;
	}
	
	public Notice selectOne(int no) {
		Connection conn = getConnection();
		Notice n = dao.selectOne(conn,no);
		close(conn);
		return n;
	}
	
	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int insertResult = dao.insertNotice(conn,n);
		if(insertResult>0) {
			insertResult = dao.selectSeq(conn);
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return insertResult;
	}
	
	public int updateNotice(Notice n) {
		Connection conn = getConnection();
		int updateResult = dao.updateNotice(conn,n);
		if(updateResult>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return updateResult;
	}
	
	public int deleteNotice(int no) {
		Connection conn = getConnection();
		int result = dao.deleteNotice(conn,no);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int restoreNotice(int no) {
		Connection conn = getConnection();
		int result = dao.restoreNotice(conn,no);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int dropNotice(int no) {
		Connection conn = getConnection();
		int result = dao.dropNotice(conn,no);
		if(result>0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}
