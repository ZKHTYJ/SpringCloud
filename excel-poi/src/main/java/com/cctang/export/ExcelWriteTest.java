package com.cctang.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/10/18 21:17
 * @description
 */
public class ExcelWriteTest {
    String PATH = "E:\\export\\";

    @Test
    public void test03() throws IOException {
        // 创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        Sheet sheet = workbook.createSheet("熟悉流程");
        // 创建一个行 (1,1)
        Row row1 = sheet.createRow(0);
        // 创建一个单元格
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");

        // 创建第一行第二个单元格
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        // 创建第二行（2，1）
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
    // (2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");

        cell22.setCellValue(time);

    // 生成一张表 （IO流） 03版本 使用xls结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "练习表03.xls");
        workbook.write(fileOutputStream);

        //关闭流
        fileOutputStream.close();
        System.out.println("练习表03.xls 生成完毕！");
    }
    @Test
    public void test07() throws IOException {
        // 创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        Sheet sheet = workbook.createSheet("熟悉流程");
        // 创建一个行 (1,1)
        Row row1 = sheet.createRow(0);
        // 创建一个单元格
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");

        // 创建第一行第二个单元格
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        // 创建第二行（2，1）
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        // (2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");

        cell22.setCellValue(time);

        // 生成一张表 （IO流） 03版本 使用xlsx结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "练习表07.xlsx");
        workbook.write(fileOutputStream);

        //关闭流
        fileOutputStream.close();
        System.out.println("练习表03.xls 生成完毕！");
    }
    @Test
    public void testWrite03BigData() throws IOException {
        // 开始
        Long start = System.currentTimeMillis();
        // 创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        // 创建表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNum = 0; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum<10;cellNum++){
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "testWrite03BigData.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        // 结束
        Long end = System.currentTimeMillis();
        System.out.println( (double) (end-start)/1000);
    }
    @Test
    //耗时较长 10.048s
    public void testWrite07BigData() throws IOException {
        // 开始
        Long start = System.currentTimeMillis();
        // 创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNum = 0; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum<10;cellNum++){
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "testWrite07BigData.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        // 结束
        Long end = System.currentTimeMillis();
        System.out.println( (double) (end-start)/1000);
    }
    @Test
    // testWrite07BigData（）升级版 1.841s
    public void testWrite07BigDataUp() throws IOException {
        // 开始
        Long start = System.currentTimeMillis();
        // 创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();
        // 创建表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNum = 0; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum<10;cellNum++){
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "testWrite07BigDataS.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        //清除临时文件
        ((SXSSFWorkbook)workbook).dispose();
        // 结束
        Long end = System.currentTimeMillis();
        System.out.println( (double) (end-start)/1000);
    }
}
