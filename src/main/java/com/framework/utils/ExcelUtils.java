package com.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";

    public static List<Map<String, String>> readExcelData(String fileName, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(TEST_DATA_PATH + fileName);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file '" + fileName + "'");
            }

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet '" + sheetName + "'");
            }

            // Create column mapping
            Map<Integer, String> columnMap = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    columnMap.put(i, getCellValueAsString(cell));
                }
            }

            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        String columnName = columnMap.get(j);
                        String cellValue = cell != null ? getCellValueAsString(cell) : "";
                        rowData.put(columnName, cellValue);
                    }
                    dataList.add(rowData);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
        
        return dataList;
    }

    public static void writeExcelData(String fileName, String sheetName, List<Map<String, String>> data) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            
            if (!data.isEmpty()) {
                // Create header row
                Row headerRow = sheet.createRow(0);
                Map<String, String> firstRow = data.get(0);
                int colIndex = 0;
                for (String columnName : firstRow.keySet()) {
                    Cell cell = headerRow.createCell(colIndex++);
                    cell.setCellValue(columnName);
                }

                // Create data rows
                int rowIndex = 1;
                for (Map<String, String> rowData : data) {
                    Row row = sheet.createRow(rowIndex++);
                    colIndex = 0;
                    for (String columnName : firstRow.keySet()) {
                        Cell cell = row.createCell(colIndex++);
                        cell.setCellValue(rowData.getOrDefault(columnName, ""));
                    }
                }
            }

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(TEST_DATA_PATH + fileName)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing Excel file: " + e.getMessage(), e);
        }
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public static String getCellValue(String fileName, String sheetName, int rowNum, int colNum) {
        try (FileInputStream fis = new FileInputStream(TEST_DATA_PATH + fileName);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            
            return getCellValueAsString(cell);
        } catch (IOException e) {
            throw new RuntimeException("Error reading cell value: " + e.getMessage(), e);
        }
    }

    public static void setCellValue(String fileName, String sheetName, int rowNum, int colNum, String value) {
        try (FileInputStream fis = new FileInputStream(TEST_DATA_PATH + fileName);
             Workbook workbook = new XSSFWorkbook(fis);
             FileOutputStream fos = new FileOutputStream(TEST_DATA_PATH + fileName)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            
            cell.setCellValue(value);
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Error setting cell value: " + e.getMessage(), e);
        }
    }
}
