package com.crawler.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class ExcelImportUtil {

	public static List<LinkedHashMap<String, String>> parseExcel(InputStream fis,
			String fileName) throws Exception {
		List<LinkedHashMap<String, String>> data = new ArrayList<LinkedHashMap<String, String>>();
		Workbook book = null;
		String suffix = fileName.substring(fileName.indexOf(".") + 1,
				fileName.length());
		if (StringUtils.equals(suffix, "xlsx")) {
			book = new XSSFWorkbook(fis);
		} else {
			book = new HSSFWorkbook(fis);

		}
		try {
			Sheet sheet = book.getSheetAt(0);
			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			// 除去表头和第一行
			// ComnDao dao = SysBeans.getComnDao();
			for (int i = firstRow; i < lastRow + 1; i++) {
				LinkedHashMap map = new LinkedHashMap();

				Row row = sheet.getRow(i);
				int firstCell = row.getFirstCellNum();
				int lastCell = row.getLastCellNum();

				for (int j = firstCell; j < lastCell; j++) {

					Cell cell2 = sheet.getRow(firstRow).getCell(j);
					String key = cell2.getStringCellValue();

					Cell cell = row.getCell(j);

					// if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// }
					// String val = cell.getStringCellValue();

					String val = parseCellVal(cell);
					if (i == firstRow) {
						break;
					} else {
						map.put(key, val);

					}
				}
				if (i != firstRow) {
					data.add(map);
				}
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (book != null) {
				book.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	private static String parseCellVal(Cell cell) {
		String result = new String();
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC :// 数字类型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
					SimpleDateFormat sdf = null;
					if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
							.getBuiltinFormat("h:mm")) {
						sdf = new SimpleDateFormat("HH:mm");
					} else {// 日期
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					Date date = cell.getDateCellValue();
					result = sdf.format(date);
				} else if (cell.getCellStyle().getDataFormat() == 58) {
					// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					double value = cell.getNumericCellValue();
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(value);
					result = sdf.format(date);
				} else {
					double value = cell.getNumericCellValue();
					CellStyle style = cell.getCellStyle();
					DecimalFormat format = new DecimalFormat();
					String temp = style.getDataFormatString();
					// 单元格设置成常规
					if (temp.equals("General")) {
						format.applyPattern("#");
					}
					result = format.format(value);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING :// String类型
				result = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_BLANK :
				result = "";
			default :
				result = "";
				break;
		}
		return result;
	}
}
