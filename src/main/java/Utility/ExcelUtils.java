package Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static Logger logger = LogManager.getLogger(ExcelUtils.class);
	public static int getRowCount(String xlfile, String xlsheet) throws IOException {
		try (FileInputStream fis = new FileInputStream(xlfile);
			 XSSFWorkbook wb = new XSSFWorkbook(fis)) {
			 XSSFSheet ws = wb.getSheet(xlsheet);
			 int rowcount = ws.getLastRowNum();
			 return rowcount;
		} catch (IOException e) {
			logger.error("Failed to read Excel file", e);
			throw e;
		}
	}

	public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {
		try (FileInputStream fis = new FileInputStream(xlfile);
			 XSSFWorkbook wb = new XSSFWorkbook(fis)) {
			 XSSFSheet ws = wb.getSheet(xlsheet);
			 XSSFRow row = ws.getRow(rownum);
			 int cellCount = row.getLastCellNum();
		  return cellCount;
		} catch (IOException e) {
			logger.error("Failed to read Excel file", e);
			throw e;
		}
	}

	public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
	 try (  FileInputStream fis = new FileInputStream(xlfile);
			XSSFWorkbook wb = new XSSFWorkbook(fis)) {
			XSSFSheet ws = wb.getSheet(xlsheet);
			XSSFRow row = ws.getRow(rownum);
			XSSFCell cell = row.getCell(colnum);
			String data;
			try {
				DataFormatter formatter = new DataFormatter();
				data = formatter.formatCellValue(cell);
				return data;
			} catch (Exception e) {
				data = "";
			}
			return data;
		} catch (IOException e) {
			logger.error("Failed to read Excel file", e);
			throw e;
		}
	}

	public static void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data)
			throws IOException {
		try (FileInputStream fis = new FileInputStream(xlfile);
			 XSSFWorkbook wb = new XSSFWorkbook(fis);
			 FileOutputStream fo = new FileOutputStream(xlfile)) {
			 XSSFSheet ws = wb.getSheet(xlsheet);
			 XSSFRow row = ws.getRow(rownum);
			 XSSFCell cell = row.createCell(colnum);
			 cell.setCellValue(data);
			 wb.write(fo);
		} catch (IOException e) {
			 logger.error("Failed to write data to Excel file", e);
			throw e;
		}
	}

	public static void fillGreenColor(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
	   try (FileInputStream fis = new FileInputStream(xlfile);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			FileOutputStream fo = new FileOutputStream(xlfile)) {
		    XSSFSheet ws = wb.getSheet(xlsheet);
			XSSFRow row = ws.getRow(rownum);
			XSSFCell cell = row.getCell(colnum);
			CellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			wb.write(fo);
		} catch (IOException e) {
			logger.error("Failed to write data to Excel file", e);
			throw e;
		}
	}

	public static void fillRedColor(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
		try (FileInputStream fis = new FileInputStream(xlfile);
			 XSSFWorkbook wb = new XSSFWorkbook(fis);
			 FileOutputStream fo = new FileOutputStream(xlfile)) {
			 XSSFSheet ws = wb.getSheet(xlsheet);
			 XSSFRow row = ws.getRow(rownum);
			 XSSFCell cell = row.getCell(colnum);
			 CellStyle style = wb.createCellStyle();
			 style.setFillForegroundColor(IndexedColors.RED.getIndex());
			 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			 cell.setCellStyle(style);
			 wb.write(fo);
		} catch (IOException e) {
			logger.error("Failed to write data to Excel file", e);
			throw e;
		}
	}
}
