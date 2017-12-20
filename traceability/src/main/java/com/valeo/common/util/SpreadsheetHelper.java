package com.valeo.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SpreadsheetHelper {



	public void writeExcel(List<ClassDesign> listClassDesign, String excelFilePath) throws IOException {
		Workbook workclsInfo = new HSSFWorkbook();
		Sheet sheet = workclsInfo.createSheet();
		
		createHeaderRow (sheet);
		
		int rowCount = 0;
		
		for (ClassDesign aClassDesign : listClassDesign) {
			
			for(String data:aClassDesign.getConstructors())
			{
			Row row = sheet.createRow(++rowCount);
			writeClassDesign(rowCount, aClassDesign.getPackageName(), aClassDesign.getClsName(), "constructor", data, row);
			}
			
			for(String data:aClassDesign.getMethods())
			{
			Row row = sheet.createRow(++rowCount);
			writeClassDesign(rowCount, aClassDesign.getPackageName(),aClassDesign.getClsName(), "method", data, row);
			}
			
			for(String data:aClassDesign.getFields())
			{
			Row row = sheet.createRow(++rowCount);
			writeClassDesign(rowCount, aClassDesign.getPackageName(),aClassDesign.getClsName(), "variable", data, row);
			}
			
		}
		
		try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
			workclsInfo.write(outputStream);
		}		
	}
	
	private void writeClassDesign(int rowCount, String packageName, String className, String memberType, String data, Row row) {
		
		Cell cell = row.createCell(0);
		cell.setCellValue(rowCount);
		
		cell = row.createCell(1);
		cell.setCellValue(packageName);
				
		cell = row.createCell(2);
		cell.setCellValue(className.substring(className.lastIndexOf(".")+1));

		cell = row.createCell(3);
		cell.setCellValue(memberType);
		
		cell = row.createCell(4);
		cell.setCellValue(data);
		
	}
	
	private void createHeaderRow(Sheet sheet) {
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setFontHeightInPoints((short) 16);
		cellStyle.setFont(font);
				
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);

		cell.setCellStyle(cellStyle);
		cell.setCellValue("#ID");
		
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("PackageName");
		
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("ClassName");

		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("MemberType");

		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("MemeberDetails");
		
	}
}
