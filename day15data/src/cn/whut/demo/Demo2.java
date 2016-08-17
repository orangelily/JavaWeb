package cn.whut.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import cn.whut.utils.JdbcUtils;

public class Demo2 {
	/** 读写二进制
	 * 流文件
	 * 
	 create table testblob
	 (
	 	id varchar(40) primary key,
	 	image blob
	 );
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void insert() throws SQLException, FileNotFoundException {
		Connection conn = null;
		PreparedStatement st =null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into testblob(id,image) values(?,?)";
			st=conn.prepareStatement(sql);
			st.setString(1, "1");
			File file =new File("src/1.jpg");
			FileInputStream in = new FileInputStream(file);
			st.setBinaryStream(2,in, (int)file.length());
			
			int num = st.executeUpdate();
			if (num>0) {
				System.out.println("insert success");
			}
		} finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	@Test
	public void read() throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement st =null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select id,image from testblob where id = '1'";
			st=conn.prepareStatement(sql);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				InputStream in= rs.getBinaryStream("image");
				OutputStream out =new FileOutputStream("WebContent/1.jpg");
				
				try {
					int len = 0;
					byte buffer[] = new byte[1024];
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
				} finally {
					if (in != null)
						in.close();
					if (out != null)
						out.close();
				}

			}
		} finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
}
