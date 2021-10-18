package com.cctang.export;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.hssf.record.DVALRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/10/18 22:25
 * @description
 */
public class ExcelReadTest {
    String PATH = "E:\\export\\";

    @Test
    public void testRead03() throws IOException {
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(PATH + "练习表03.xls");
        // 创建一个工作簿
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        // 得到表
        Sheet sheet = workbook.getSheetAt(0);
        //得到行
        Row row = sheet.getRow(0);
        //得到列
        Cell cell = row.getCell(0);
        // 读取值的时候注意类型
        System.out.println(cell.getStringCellValue());
        //得到列
        Cell cell1 = row.getCell(1);
        System.out.println(cell1.getNumericCellValue());
        fileInputStream.close();
    }
    @Test
    public void testRead07() throws IOException {
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(PATH + "练习表07.xlsx");
        // 创建一个工作簿
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        // 得到表
        Sheet sheet = workbook.getSheetAt(0);
        //得到行
        Row row = sheet.getRow(0);
        //得到列
        Cell cell = row.getCell(0);
        // 读取值的时候注意类型
        System.out.println(cell.getStringCellValue());
        //得到列
        Cell cell1 = row.getCell(1);
        System.out.println(cell1.getNumericCellValue());
        fileInputStream.close();
    }
    @Test
    public void testCellType() throws IOException {
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(PATH + "明细表.xls");
        // 创建一个工作簿
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        // 得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取标题内容
        Row rowTitle = sheet.getRow(0);
        if (rowTitle!=null){
            //多个列 返回列个数
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0;cellNum< cellCount;cellNum++){
                Cell cell = rowTitle.getCell(cellNum);
                if(cell!=null){
                    CellType cellType = cell.getCellType();
                    switch (cellType){
                        case STRING:
                            System.out.println(cell.getStringCellValue()+"\n");
                            break;
                        case BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            System.out.println(cell.getCellFormula());
                            break;
                        case NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                    }
                }
            }
        }
        // 获取表中内容
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount;rowNum++){
            Row rowData = sheet.getRow(rowNum);
            if(rowData!=null){
                //读取列
                int cellCount = rowTitle.getPhysicalNumberOfCells();
                for (int cellNum = 0;cellNum< cellCount;cellNum++){
                    System.out.println("["+(rowNum+1)+"-"+(cellNum+1)+"]");
                    Cell cell = rowData.getCell(cellNum);
                    //匹配数据类型
                    if(cell!=null){
                        CellType cellType = cell.getCellType();
                        String cellValue =null;
                        switch (cellType){
                            case STRING:
                                System.out.println("【String】");
                                cellValue = cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                System.out.println("【BOOLEAN】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case NUMERIC:
                                if(HSSFDateUtil.isCellDateFormatted(cell)){// 日期
                                    System.out.println("【日期】");
                                    Date dateCellValue = cell.getDateCellValue();
                                     cellValue = new DateTime(dateCellValue).toString("yyy-MM-dd");
                                    System.out.println(dateCellValue);
                                }else{
                                    System.out.println("【转换为字符串输出】");
                                   cell.setCellType(CellType.STRING);
                                   cellValue=cell.toString();
                                }
                                break;
                            case BLANK:
                                System.out.println("【BLANK】");
                                break;
                            case ERROR:
                                System.out.println("【error】");
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }
        fileInputStream.close();
    }
    @Test
    public void testFormula() throws IOException {
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(PATH + "公式.xls");
        // 创建一个工作簿
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        // 得到表
        Sheet sheet = workbook.getSheetAt(0);
        //得到行
        Row row = sheet.getRow(5);
        //得到列
        Cell cell = row.getCell(0);
        // 拿到计算公式
        FormulaEvaluator FormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook)workbook);
        // 输出单元格内容
        CellType cellType = cell.getCellType();
        switch (cellType){
            case FORMULA: //公式
                String cellFormula = cell.getCellFormula();
                System.out.println(cellFormula);
                // 计算
                CellValue evaluate = FormulaEvaluator.evaluate(cell);
                String cellValue = evaluate.formatAsString();
                System.out.println(cellValue);
                break;
        }
    }
}
