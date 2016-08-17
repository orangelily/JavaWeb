package cn.whut.junit;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.Test;

import cn.whut.demo.Demo1;

public class Demo1Test {

	@Test
	public void test1() throws FileNotFoundException, SQLException {
		Demo1 demo1 = new Demo1();
		demo1.insert();
	}
}
