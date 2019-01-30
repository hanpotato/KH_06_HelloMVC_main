package com.rclass.member.model.service;

import static common.JDBCTemplate.getConnection;

import java.sql.Connection;

import com.rclass.member.model.dao.MemberDao;
import com.rclass.member.model.vo.Member;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.rollback;

public class MemberService {

	public Member selectOne(Member m) {
		Connection conn = getConnection();
		Member result = new MemberDao().selectOne(conn, m);
		close(conn);
		return result;
	}
	
	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().insertMember(conn, m);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int updateMember(Member m) {
		Connection conn =  getConnection();
		int result = new MemberDao().updateMember(conn,m);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int deleteMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn,m);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int updatePassword(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().updatePassword(conn,m);
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
