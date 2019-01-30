package com.rclass.notice.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

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
	
	public int selectListCount() {
		Connection conn = getConnection();
		int result = dao.selectListCount(conn);
		close(conn);
		return result;
	}
	
	public Notice selectOne(int no) {
		Connection conn = getConnection();
		Notice n = dao.selectOne(conn,no);
		close(conn);
		return n;
	}
}
