package cn.itcast.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.itcast.domain.User;

/**
 * JDBC
 * 
 * @author orange
 *
 */
public class Demo1 {
	/*
	 root root create database day14; use day14; create table users( id int
	 primary key, name varchar(40), password varchar(40), email varchar(60),
	 birthday date );
	 insert into users(id,name,password,email,birthday)
	 values(1,'zs','123456','547e@163.com','1989-12-04'); insert into
	 users(id,name,password,email,birthday)
	 values(2,'kl','123456','105e@163.com','1999-10-05'); insert into
	 users(id,name,password,email,birthday)
	 values(3,'kuang','123456','kl@163.com','1990-09-02');
	  
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/day14";// 尽管有默认编码方式UTF-8，最好加上编码格式
		String username = "root";
		String password = "root";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 1.加载驱动
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver");
			// 2.获取数据库链接
			conn = DriverManager.getConnection(url, username, password);
			// 3.获取向数据库发sql语句的statement对象
			st = conn.createStatement();
			// connection.prepareStatement("select * from users");

			// 4，向数据库发送sql，获取数据库返回的结果集
			rs = st.executeQuery("select * from users");
			// st.executeUpdate("");
			// 5.从结果集中获取数据
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setBirthday(rs.getDate("birthday"));
				// System.out.print("id="+rs.getObject("id"));
				// System.out.print(" name="+rs.getObject("name"));
				// System.out.print(" password="+rs.getObject("password"));
				// System.out.print(" email="+rs.getObject("email"));
				// System.out.println(" birthday="+rs.getObject("birthday"));
			}

		} finally {
			// 6.释放资源（释放链接）
//			rs.close();
//			st.close();
//			conn.close();
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}
}
