package com.Interface.run;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Interface.cases.LoginAPI;
import com.Interface.cases.LoginAPI1;
import com.Interface.excel.ExcelUtil;

public class LoginRunCase1 {
	
	@Test
	public void LoginApi() throws IOException{
		LoginAPI1 la = new LoginAPI1();
		la.Login();
		String[] result = ExcelUtil.getInstance().readExcelColumnData(0, "TestResult");
		for (int i = 0; i < result.length; i++) {
			Assert.assertEquals(result[i], "OK");
		}
	}
}
