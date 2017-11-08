
package com.generic;
import java.io.File;
import java.io.FileInputStream;
/*import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;*/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *  
 *
 */
public class ExcelFileOperation {
	
	public ExcelFileOperation()
	{
		
	}
	
	/**
	 * @Method : Fn_Setup_GetTestUserDetailsFromExcelOperations
	 * @Description : This method is used to get User Details FromExcel
	 * @param sAction:Action to be performed
	 * @param sFilePath: File path of the xlsx
	 * @param sAutomationId: AUtomation id for the specific user(its key to fetch data)
	 * @return user details as a string
	 *  
	 */
	public String Fn_Setup_GetTestUserDetailsFromExcelOperations(String sAction,String sFilePath, String sAutomationId)
	{
		String sReturnValue = null;
		try 
		{
			// Specify the path of file
			File objFile=new File(sFilePath);			
			// load file
			FileInputStream objFileInputStream;
			objFileInputStream = new FileInputStream(objFile);			
			// Load workbook
			XSSFWorkbook objWorkBook=new XSSFWorkbook(objFileInputStream);
			// Load sheet- Here we are loading first sheet only
			XSSFSheet objSheet= objWorkBook.getSheetAt(0);
			int iRowCount = objSheet.getLastRowNum();
			
			switch (sAction.toLowerCase()){
				case "getlogindetails":
					for(int iCounter=1;iCounter<iRowCount;iCounter++)
					{	
						// getRow() specify which row we want to read.
						// and getCell() specify which column to read.
						// getStringCellValue() specify that we are reading String data.
						//RSHANK15 - Added trim for Automation ID comparison
						if( sAutomationId.equals(objSheet.getRow(iCounter).getCell(0).getStringCellValue().trim()))
						{
							String sUserName = objSheet.getRow(iCounter).getCell(1).getStringCellValue();
							String sPassword = objSheet.getRow(iCounter).getCell(2).getStringCellValue();
							String sGroup = objSheet.getRow(iCounter).getCell(3).getStringCellValue();
							String sRole = objSheet.getRow(iCounter).getCell(4).getStringCellValue();
							sReturnValue = sUserName+"~"+sPassword+"~"+sGroup+"~"+sRole;
							break;
						}
					}	
					break;
				case "getuserid":
					for(int iCounter=1;iCounter<iRowCount;iCounter++)
					{	
						// getRow() specify which row we want to read.
						// and getCell() specify which column to read.
						// getStringCellValue() specify that we are reading String data.
						if( sAutomationId.equals(objSheet.getRow(iCounter).getCell(0).getStringCellValue()))
						{
							String sUserName = objSheet.getRow(iCounter).getCell(1).getStringCellValue();
							sReturnValue = sUserName;
							break;
						}
					}		
					break;
				case "getname":
					for(int iCounter=1;iCounter<iRowCount;iCounter++)
					{	
						// getRow() specify which row we want to read.
						// and getCell() specify which column to read.
						// getStringCellValue() specify that we are reading String data.
						if( sAutomationId.equals(objSheet.getRow(iCounter).getCell(0).getStringCellValue()))
						{
							String sUsername = objSheet.getRow(iCounter).getCell(5).getStringCellValue();
							sReturnValue = sUsername;
							break;
						}
					}
					break;
				case "getgroup":
					for(int iCounter=1;iCounter<iRowCount;iCounter++)
					{	
						// getRow() specify which row we want to read.
						// and getCell() specify which column to read.
						// getStringCellValue() specify that we are reading String data.
						if( sAutomationId.equals(objSheet.getRow(iCounter).getCell(0).getStringCellValue()))
						{
							String sGroup = objSheet.getRow(iCounter).getCell(1).getStringCellValue();
							sReturnValue = sGroup;
							break;
						}
					}	
					break;
				case "getrole":
					for(int iCounter=1;iCounter<iRowCount;iCounter++)
					{	
						// getRow() specify which row we want to read.
						// and getCell() specify which column to read.
						// getStringCellValue() specify that we are reading String data.
						if( sAutomationId.equals(objSheet.getRow(iCounter).getCell(0).getStringCellValue()))
						{
							String sRole = objSheet.getRow(iCounter).getCell(1).getStringCellValue();
							sReturnValue = sRole;
							break;
						}
					}	
					break;
				default:
					sReturnValue = "Invalid AutomationID";
					break;
				}
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return sReturnValue;

	}
}
