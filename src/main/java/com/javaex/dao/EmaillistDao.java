package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaex.vo.EmaillistVo;

@Repository
public class EmaillistDao {

	public void insert(EmaillistVo vo) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " insert into emaillist "+
						   " values (seq_emaillist_no.nextval, ?, ?, ?) ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getLastName());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());

			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 저장완료");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}

	public List<EmaillistVo> getList() {
		
				// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<EmaillistVo> eList = new ArrayList<EmaillistVo>();

				try {
				    // 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
				    // 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");
				    
					// 3. SQL문 준비 / 바인딩 / 실행
				    String query = " select no, last_name, first_name, email "+ 
				    		       " from emaillist ";
				    
					pstmt = conn.prepareStatement(query);
				    rs = pstmt.executeQuery();
								
				    // 4.결과처리
				    while(rs.next()) {
				    	EmaillistVo vo = new EmaillistVo();
				    	int no = rs.getInt("no");
				    	String lastName = rs.getString("last_name");
				    	String firstName = rs.getString("first_name");
				    	String email = rs.getString("email");
				    	
				    	vo.setNo(no);
				    	vo.setLastName(lastName);
				    	vo.setFirstName(firstName);
				    	vo.setEmail(email);
				    	eList.add(vo);
				    					    					    								    	
				    }		    
				    	
				} catch (ClassNotFoundException e) {
				    System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
				    System.out.println("error:" + e);
				} finally {
				   
				    // 5. 자원정리
				    try {
				        if (rs != null) {
				            rs.close();
				        }                
				        if (pstmt != null) {
				            pstmt.close();
				        }
				        if (conn != null) {
				            conn.close();
				        }
				    } catch (SQLException e) {
				        System.out.println("error:" + e);
				    }

				}
				return eList;
	}
}
