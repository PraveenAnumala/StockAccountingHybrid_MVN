package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	
			Workbook wb;
			
			public ExcelFileUtil() throws Exception{
				FileInputStream fi=new FileInputStream("C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\TestInput\\InputSheet.xlsx");
				wb=new XSSFWorkbook(fi);
			}
			
			//count no of rows in a sheet
			public int rowCount(String sheetname)
			{
				return wb.getSheet(sheetname).getLastRowNum();
			}
			
			//count no of column in row
			public int colCount(String sheetname){
				return wb.getSheet(sheetname).getRow(0).getLastCellNum();
			}
			
			public String getData(String sheetname,int row, int column){
				String data="";
				
				if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC){			
					int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
					data=String.valueOf(celldata);
				}else {
					data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
			    }
		
				return data;
			}
			
			public void setData(String sheetname,int row,int column, String status) throws Exception{
				
				Sheet sh=wb.getSheet(sheetname);
				Row rownum=sh.getRow(row);
				Cell cell= rownum.createCell(column);
				cell.setCellValue(status);	
				
				if (status.equalsIgnoreCase("Pass"))
				{
					
					//Create a cell style
					CellStyle style=wb.createCellStyle();
					//Create a font
					Font font = wb.createFont();
					//Apply color to the text
					font.setColor(IndexedColors.GREEN.getIndex());
					//Apply bold to the text
					font.setBold(true);
					style.setFont(font);
					
					rownum.getCell(column).setCellStyle(style);
					
				}
				else if(status.equalsIgnoreCase("Fail")){
					//Create s cell style
					CellStyle style= wb.createCellStyle();
					//create a font
					Font font=wb.createFont();
					//Apply color to the text
					font.setColor(IndexedColors.RED.getIndex());
					//Apply bold to the text
					font.setBold(true);
					style.setFont(font);
					
					rownum.getCell(column).setCellStyle(style);
				}
				else if(status.equalsIgnoreCase("Not Executed")){
					//Create s cell style
					CellStyle style= wb.createCellStyle();
					//create a font
					Font font=wb.createFont();
					//Apply color to the text
					font.setColor(IndexedColors.BLUE.getIndex());
					//Apply bold to the text
					font.setBold(true);
					style.setFont(font);
					
					rownum.getCell(column).setCellStyle(style);
					
				}
				
				FileOutputStream fos=new FileOutputStream("C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\TestOutput\\Hybrid.xlsx");
				wb.write(fos);
				fos.close();
				
			}	

}
