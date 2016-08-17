package cn.whut.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import cn.whut.utils.JdbcUtils;

public class Demo1 {

	/** 读写大文本
	 * 
	 create table testclob
	 (
	 	id varchar(40) primary key,
	 	resume text
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
			String sql = "insert into testclob(id,resume) values(?,?)";
			st=conn.prepareStatement(sql);
			st.setString(1, "1");
			
			File file = new File("src/1.txt");
			FileReader reader = new FileReader(file);
			
			st.setCharacterStream(2, reader,(int)file.length());
			
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
			String sql = "select id,resume from testclob where id='1'";
			st=conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
//				String resum = rs.getString("resume");
				Reader reader =  rs.getCharacterStream("resume");
				//reader = rs.getClob("resume").getCharacterStream();
				FileWriter writer = new FileWriter("WebContent/resume1.txt");
				try{
					
					int len=0;
					char buffer[] = new char[1024];
					
					while((len=reader.read(buffer))>0){
						writer.write(buffer, 0, len);
					}
				}finally{
					if(reader!=null){
						reader.close();
					}
					if(writer!=null){
						writer.close();
					}
				}
			}
			
		} finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
}
