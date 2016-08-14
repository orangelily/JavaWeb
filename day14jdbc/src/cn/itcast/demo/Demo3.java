package cn.itcast.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.itcast.domain.User;
import cn.itcast.utils.JdbcUtils;

/**
 * JDBC
 * 
 * @author orange
 *
 */
public class Demo3 {
	
	@Test
	public void insert() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "insert into users(id,name,password,email,birthday) values(4,'lisa','342363','orangef@163.com','1992-03-02')";
			int num = st.executeUpdate(sql);
			if (num>0) {
				System.out.println("添加成功");
			}
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	public void update() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	@Test
	public void delete() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "delete from users where id = 4";
			int num = st.executeUpdate(sql);
			if (num>0) {
//				user.setId(rs.getInt("id"));
//				user.setName(rs.getString("name"));
//				user.setPassword(rs.getString("password"));
//				user.setEmail(rs.getString("email"));
//				user.setBirthday(rs.getDate("birthday"));
				System.out.println("删除成功");
				this.getAll();
			}
			
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	@Test
	public void find() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "select id,name,password,email,birthday from users where id = 3";
			rs = st.executeQuery(sql);
			User user = new User();
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setBirthday(rs.getDate("birthday"));
			}
			System.out.println(user);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	@Test
	public void getAll() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "select id,name,password,email,birthday from users";
			rs = st.executeQuery(sql);
			List<User> list =new ArrayList<>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setBirthday(rs.getDate("birthday"));
				list.add(user);				
			}
			System.out.println(list.size());
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
}
