package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.saeyan.dto.BoardVO;

import util.DBManager;

public class BoardDAO {
	private BoardDAO() {

	}
	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}


	//DB목록 불러오기
	public List<BoardVO> selectAllBoards(){
		String sql = "select * from bboard order by num desc";

		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;


		try {
			conn = DBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);


			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setPass(rs.getString("pass"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setReadcount(rs.getInt("readcount"));
				vo.setWritedate(rs.getTimestamp("writedate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, stmt, rs);
		}

		return list;
	}

	//DB입력
	public void insertBoard(BoardVO vo) {
		String sql = "insert into bboard("
				+ "num, name, email, pass, title, content) "
				+ "values(board_seq.NEXTVAL, ?, ? ,? ,? ,?)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPass());
			pstmt.setString(4, vo.getTitle());
			pstmt.setString(5, vo.getContent());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	
}
