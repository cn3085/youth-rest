package org.youth.api.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import org.youth.api.dto.ExcelTitleDTO;

@Component("BasicExcelDownloadView")
public class BasicExcelDownloadView extends AbstractXlsView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileName = model.get("fileName").toString();
		response.setHeader("Content-Disposition", "attachment; filename = " + fileName+ ".xls");
		
		/* --------폰트 및 스타일 지정-------- */
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("맑은고딕");
		
		//th 스타일
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(font);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.SOLID_FOREGROUND);
		
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		titleStyle.setBorderTop(CellStyle.BORDER_THIN);
		titleStyle.setBorderRight(CellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		
		//td 스타일
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);
		/* --------폰트 및 스타일 지정 끝------- */
		
		String sheetName = "sheet1";
		
		List<ExcelTitleDTO> titleList = (List<ExcelTitleDTO>)model.get("titleList");
		List<HashMap<String, Object>> bodyList = (List<HashMap<String, Object>>)model.get("bodyList");
		
		Sheet sheet = workbook.createSheet(sheetName);
		Row row = null;
		Cell cell = null;
		int rowCount = 0;
		int cellCount = 0;
		
		row = sheet.createRow(rowCount++);
		row.setHeight((short)(37.5*20)); // 20 : 1
		
		for(int i = 0; i < titleList.size(); i ++){
			ExcelTitleDTO title = titleList.get(i);
			cell = row.createCell(cellCount++);
			cell.setCellValue(title.getColumnName());
			cell.setCellStyle(titleStyle);
			sheet.setColumnWidth(i, title.getColumnSize() * 256);
		}
		
		for(HashMap<String, Object> data : bodyList) {
			row = sheet.createRow(rowCount++);
			row.setHeight((short)(37.5*20)); // 20 : 1
			
			cellCount = 0;
			for(ExcelTitleDTO title : titleList) {
				String value = String.valueOf(data.get(title.getColumnCode()) != null ? data.get(title.getColumnCode()):"");
				cell = row.createCell(cellCount++);
				cell.setCellValue(value);
				cell.setCellStyle(cellStyle);
			}
		}
		
	}

}
