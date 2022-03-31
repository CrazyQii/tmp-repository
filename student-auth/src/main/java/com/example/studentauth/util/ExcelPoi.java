package com.example.studentauth.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelPoi<T> {
    //private static Logger log = Logger.getLogger(ExcelPoi.class);

    /**
     * 导入数据
     *
     * @param file
     * @param fileName
     * @param clz
     * @throws Exception
     */
    public List<T> importObjectList(InputStream file, String fileName, Class<T> clz) throws Exception {
        List<T> list = new ArrayList<>();
        Workbook workbook = null;
        if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook(file);
        } else if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(file);
        }
        file.close();
        if (workbook != null) {
            // Map<String, Integer> titleMap = new HashMap<>();
            Map<Object, Integer> titleMap = new HashMap<>();
            for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                Row row = sheet.getRow(0);
                for (int columnNum = 0; columnNum < row.getLastCellNum(); columnNum++) {

                    //String value = getCellValue(row.getCell(columnNum));
                    Object value = getCellValue(row.getCell(columnNum));
                    titleMap.put(value, columnNum);
                }
            }
            for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                Field[] publicFields = clz.getFields();
                Field[] declaredFields = clz.getDeclaredFields();
                int countRow = sheet.getLastRowNum() + 1;
                for (int rowNum = 1; rowNum < countRow; rowNum++) {
                    T record = clz.newInstance();
                    Row row = sheet.getRow(rowNum);
                    setObject(row, publicFields, record, titleMap);
                    setObject(row, declaredFields, record, titleMap);
                    list.add(record);
                }
            }
            //workbook.close();
        }
        return list;
    }

    /**
     * 获取一条数据为对象赋值
     *
     * @param row
     * @param fields
     * @param object
     * @param titleMap
     */
    public static void setObject(Row row, Field[] fields, Object object, Map<Object, Integer> titleMap) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(com.example.studentauth.util.ExcelTitle.class)) {
                com.example.studentauth.util.ExcelTitle excelTitle = field.getAnnotation(com.example.studentauth.util.ExcelTitle.class);
                String title = excelTitle.title();
                Integer index = titleMap.get(title);
                if (index != null) {
                    Object value = getCellValue(row.getCell(index));
                    MyObjectUtils.setFieldValueByName(object, field, value);
                }
            }
        }
    }


    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    //原本Sting型
    public static Object getCellValue(Cell cell) {
        //String cellValue = "";

        Object cellValue = new Object();
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                Date tempValue = cell.getDateCellValue();
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                cellValue = simpleFormat.format(tempValue);
            } else {
//cellValue = String.valueOf(cell.getNumericCellValue());
                long longVal = Math.round(cell.getNumericCellValue());
                Double doubleVal = cell.getNumericCellValue();
                int intval = (int) cell.getNumericCellValue();
                if (Double.parseDouble(longVal + ".0") == doubleVal) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    //cellValue = String.valueOf(cell.getStringCellValue());
                    cellValue = Integer.parseInt(String.valueOf(longVal));
                } else if (intval > 0 && intval < 9) {
                    cellValue = Integer.parseInt(String.valueOf(longVal));
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    //cellValue = cell.getNumericCellValue();
                    //cellValue = doubleVal;
                }
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = String.valueOf(cell.getStringCellValue());//获取数据
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else {
            cellValue = "";
        }
        System.out.println("cellValue" + cellValue);
        return cellValue;
    }


}
