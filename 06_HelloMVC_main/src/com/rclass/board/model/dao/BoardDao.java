package com.rclass.board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.rclass.board.model.vo.Board;

public class BoardDao {

	private Properties prop = new Properties();
	
	public BoardDao() {
		try {
			String fileName = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
			prop.load(new FileReader(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int selectListCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		String sql = prop.getProperty("selectListCount");
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return totalCount;
	}
	
	public List<Board> selectList(Connection conn, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = prop.getProperty("selectList");
		List<Board> list = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board bo = new Board();
				bo.setBoardNo(rs.getInt("board_no"));
				bo.setBoardTitle(rs.getString("board_title"));
				bo.setBoardWriter(rs.getString("board_writer"));
				bo.setBoardContent(rs.getString("board_content"));
				bo.setBoardOriginalFilename(rs.getString("board_original_filename"));
				bo.setBoardRenamedFilename(rs.getString("board_renamed_filename"));
				bo.setBoardDate(rs.getDate("board_date"));
				bo.setBoardReadcount(rs.getInt("board_readcount"));
				list.add(bo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}
	
	public Board selectOne(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = prop.getProperty("selectOne");
		Board bo = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				bo = new Board();
				bo.setBoardNo(rs.getInt("board_no"));
				bo.setBoardTitle(rs.getString("board_title"));
				bo.setBoardWriter(rs.getString("board_writer"));
				bo.setBoardContent(rs.getString("board_content"));
				bo.setBoardOriginalFilename(rs.getString("board_original_filename"));
				bo.setBoardRenamedFilename(rs.getString("board_renamed_filename"));
				bo.setBoardDate(rs.getDate("board_date"));
				bo.setBoardReadcount(rs.getInt("board_readcount"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return bo;
	}
	
	public int insertBoard(Connection conn, Board bo) {
		PreparedStatement pstmt = null;
		int iResult = 0;
		String sql = prop.getProperty("insertBoard");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bo.getBoardTitle());
			pstmt.setString(2, bo.getBoardWriter());
			pstmt.setString(3, bo.getBoardContent());
			pstmt.setString(4, bo.getBoardOriginalFilename());
			pstmt.setString(5, bo.getBoardRenamedFilename());
			iResult = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return iResult;
	}
	
	public int selectSeq(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int iResult = 0;
		String sql = prop.getProperty("selectSeq");
		try {
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				iResult = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return iResult;
	}
}
