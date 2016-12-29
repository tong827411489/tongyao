package com.Interface.cases;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.Interface.api.HttpRequester;
import com.Interface.api.MobileApiTools;
import com.Interface.excel.ExcelUtil;

public class LoginAPI {
	
	private static Logger logger = Logger.getLogger(LoginAPI.class);
	
	private final String FILE_PATH = "D:/Login.xlsx";
	private final String SHEET_NAME = "Login";
	private final int TITLE_LINE_INDEX = 4;
	
	private final String RESULT_CODE = "ResultCode";
	private final String TEST_RESULT = "TestResult";
	private final String RUNNING_TIME = "RunningTime";
	private final String ACTUAL_RESULT = "ActualResult";
	private final String RUN = "Run";
	
	//需要检查的参数
	private final String ACCOUNT = "account";
	private final String PASSWORD = "password";
	
	public LoginAPI(){
		try {
			logger.info(LoginAPI.class);
			ExcelUtil.getInstance().setFilePath(FILE_PATH);
			ExcelUtil.getInstance().setSheetName(SHEET_NAME);
			
			logger.info("初始化: " + ExcelUtil.getInstance().getFilePath() + ", " + ExcelUtil.getInstance().getSheetName());
			MobileApiTools.initializeData(TITLE_LINE_INDEX, RUN, "N", 1);
			MobileApiTools.initializeData(TITLE_LINE_INDEX, ACTUAL_RESULT, "", 1);
			MobileApiTools.initializeData(TITLE_LINE_INDEX, RESULT_CODE, "", 1);
			MobileApiTools.initializeData(TITLE_LINE_INDEX, TEST_RESULT, "NT", 2);
			MobileApiTools.initializeData(TITLE_LINE_INDEX, RUNNING_TIME, "", 1);
			
			logger.info(ExcelUtil.getInstance().getFilePath() + ", " + ExcelUtil.getInstance().getSheetName() + "初始化完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Login() throws IOException{
		String url = "";
		String method = "";
		String act = "";
		List<Map<String, String>> data = null;
		boolean flag = false;
		
		url = ExcelUtil.getInstance().readExcelCell(0,1);
		method = ExcelUtil.getInstance().readExcelCell(2,1);
		act = ExcelUtil.getInstance().readExcelCell(1,1);
		flag = MobileApiTools.isArgEquals(3, 1, TITLE_LINE_INDEX);
		
		if (url.equals("") || act.equals("") ||method.equals("") || !flag) {
			logger.debug("请检查 Excel 中 Interface、Act、Method、ArgName 是否设置正确...");
			System.exit(-1);
		}
		data = ExcelUtil.getInstance().readExcelAllData(4);
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				Map<String, String> map = data.get(i);
				if (!(map.get("CaseID").equals(""))) {
					String account = map.get(ACCOUNT);
					String password = map.get(PASSWORD);
					String state = map.get("State");
					String expectedResult = map.get("ExpecteResult");
					
					String param = "account="+account+"&"+"password="+password;
					Map<String, String> result = HttpRequester.sendPost(method, url, param);
					String code = result.get("map");
					String rsTmp = result.get("result");
					
					
					String actualResult = rsTmp;
					String testResult = MobileApiTools.assertResult(expectedResult, actualResult);
					
					//写入run列
					MobileApiTools.writeResult(TITLE_LINE_INDEX, TITLE_LINE_INDEX+1+i, RUN, "Y");
					
					//写入httpcode
					MobileApiTools.writeResult(TITLE_LINE_INDEX, TITLE_LINE_INDEX+1+i, RESULT_CODE, code);
					
					//设置单元格格式
					if (Integer.parseInt(code) == 200) {
						ExcelUtil.getInstance().setCellbackGroundColor(TITLE_LINE_INDEX, RESULT_CODE, TITLE_LINE_INDEX+1+i, 1);
					}else{
						ExcelUtil.getInstance().setCellbackGroundColor(TITLE_LINE_INDEX, RESULT_CODE, TITLE_LINE_INDEX+1+i, 0);
					}
					 // 写入实际结果
					MobileApiTools.writeResult(TITLE_LINE_INDEX, TITLE_LINE_INDEX+1+i, ACTUAL_RESULT, actualResult);
					// 写入测试通过与否
		            MobileApiTools.writeResult(TITLE_LINE_INDEX, TITLE_LINE_INDEX
		            		+ 1 + i, TEST_RESULT, testResult);
		            
		            if (testResult.equals("OK")) {
						ExcelUtil.getInstance().setCellbackGroundColor(TITLE_LINE_INDEX, TEST_RESULT, TITLE_LINE_INDEX+1+i, 1);
					}else{
						ExcelUtil.getInstance().setCellbackGroundColor(TITLE_LINE_INDEX, TEST_RESULT, TITLE_LINE_INDEX+1+i, 0);
					}
		            // 写入执行时间
		            MobileApiTools.writeResult(TITLE_LINE_INDEX, TITLE_LINE_INDEX+1+i, RUNNING_TIME, MobileApiTools.getData());
		            logger.info("CaseID: " + map.get("CaseID") + ", CaseName: " + map.get("CaseName") + ", ExpectedResult: " +
		            		map.get("ExpectedResult") + ", ActualResult: " + actualResult + ", ResultCode: " + code +
		            		", TestResult: " + testResult);
				}
			}	
		}
	}
}
