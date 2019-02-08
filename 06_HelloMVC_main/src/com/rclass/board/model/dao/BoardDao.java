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
import com.rclass.board.model.vo.BoardComment;

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
	
	public int increReadCount(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("increReadCount");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
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
	
	public int insertComment(Connection conn, BoardComment comment) {
		System.out.println(comment.toString());
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertComment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getBoardCommentLevel());
			pstmt.setString(2, comment.getBoardCommentWriter());
			pstmt.setString(3, comment.getBoardCommentContent());
			pstmt.setInt(4, comment.getBoardRef());
			pstmt.setString(5, comment.getBoardCommentRef()==0?null:String.valueOf(comment.getBoardCommentRef()));
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public List<BoardComment> selectCommentAll(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardComment> list = new ArrayList();
		String sql = prop.getProperty("selectCommentAll");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardComment bc = new BoardComment();
				bc.setBoardCommentNo(rs.getInt("board_comment_no"));
				bc.setBoardCommentLevel(rs.getInt("board_comment_level"));
				bc.setBoardCommentWriter(rs.getString("board_comment_writer"));
				bc.setBoardCommentContent(rs.getString("board_comment_content"));
				bc.setBoardRef(rs.getInt("board_ref"));
				bc.setBoardCommentRef(rs.getInt("board_comment_ref"));
				bc.setBoardCommentDate(rs.getDate("board_comment_date"));
				list.add(bc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}
	
	public int deleteBoard(Connection conn, BoardComment bc) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteBoard");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getBoardCommentNo());
			pstmt.setInt(2, bc.getBoardCommentNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
}
