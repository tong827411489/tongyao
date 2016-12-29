package com.Interface.run;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Interface.cases.LoginAPI;
import com.Interface.excel.ExcelUtil;

public class LoginRunCase {
	
	@Test
	public void LoginApi() throws IOException{
		LoginAPI la = new LoginAPI();
		la.Login();
		String[] result = ExcelUtil.getInstance().readExcelColumnData(4, "TestResult");
		for (int i = 0; i < result.length; i++) {
			Assert.assertEquals(result[i], "OK");
		}
	}
}
