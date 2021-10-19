package com.cctang.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/10/19 20:34
 * @description
 */
@Slf4j
public class Export {
    public static void main(String[] args) {
        ArrayList<String> ListGlobal = new ArrayList<>();
        ListGlobal.add("有功电度");
        ListGlobal.add("峰时电镀");
        ListGlobal.add("平时电度");
        ListGlobal.add("谷时电度");
        String downloadUrl = "";
        // 模拟数据
        List<EmsMeter> meterList = new ArrayList<>();
        EmsMeter meter1 = new EmsMeter();
        EmsMeter meter2 = new EmsMeter();
        EmsMeter meter3 = new EmsMeter();
        EmsMeter meter4 = new EmsMeter();

        meter1.setMeterId(1);
        meter1.setMeterCode("ss");
        meter1.setCompanyId(2);
        meter1.setMeterName("加/光伏");
        meter1.setCreateDate(new Date());
        meterList.add(meter1);

        meter2.setMeterId(1);
        meter2.setMeterCode("ss");
        meter2.setCompanyId(2);
        meter2.setMeterName("加/35KV总");
        meter2.setCreateDate(new Date());
        meterList.add(meter2);

        meter3.setMeterId(10);
        meter3.setMeterCode("sst");
        meter3.setCompanyId(20);
        meter3.setMeterName("加/1号主变");
        meter3.setCreateDate(new Date());
        meterList.add(meter3);

        meter4.setMeterId(1);
        meter4.setMeterCode("ss");
        meter4.setCompanyId(2);
        meter4.setMeterName("加/2号主变");
        meter4.setCreateDate(new Date());
        meterList.add(meter4);

        try {
            // 创建workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 创建sheet
            XSSFSheet sheet = workbook.createSheet("有功电度月报");
            sheet.setDefaultColumnWidth(16);
            // 标题1
            XSSFRow rowTitle0 = sheet.createRow(0);
            rowTitle0.setHeightInPoints(20F);
            XSSFCellStyle title0Style = ExcelUtil.newMeterDataStatisticTitle0Style(workbook);
            XSSFCell cell0 = rowTitle0.createCell(0);
            cell0.setCellValue("有功电度月报");
            cell0.setCellStyle(title0Style);
            // 右边框
            XSSFCell cell012 = rowTitle0.createCell(meterList.size() * 4);
            cell012.setCellStyle(title0Style);
            CellRangeAddress region0 = new CellRangeAddress(rowTitle0.getRowNum(), // first row
                    rowTitle0.getRowNum(), // last row
                    rowTitle0.getFirstCellNum(), // first column
                    meterList.size() * 4 // last column
            );
            sheet.addMergedRegion(region0);
            // 标题: 统计区域：公司名——工厂名
            XSSFRow rowTitle1 = sheet.createRow(1);
            rowTitle1.setHeightInPoints(18F);
            XSSFCellStyle title1Style = ExcelUtil.newMeterDataStatisticTitle1Style(workbook);
            XSSFCell cell1 = rowTitle1.createCell(0);
            cell1.setCellValue(String.format("统计区域：%s——%s", "公司名称", "节能"));
            cell1.setCellStyle(title1Style);
            if(meterList.size()>0){
                CellRangeAddress region1 = new CellRangeAddress(rowTitle1.getRowNum(), // first row
                        rowTitle1.getRowNum(), // last row
                        rowTitle1.getFirstCellNum(), // first column
                        2 // last column
                );
                sheet.addMergedRegion(region1);
            }

            // 标题: 统计时间：yyyy.MM
            XSSFCellStyle title2Style = ExcelUtil.newMeterDataStatisticTitle3Style(workbook);
            XSSFCell cell2 = rowTitle1.createCell(3);
            cell2.setCellValue(String.format("统计月份：%s", "2021-10-19".replaceAll("-", ".")));
            cell2.setCellStyle(title2Style);
            cell2.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
            if (meterList.size() > 0) {
                // 右边框
                XSSFCell cell212 = rowTitle1.createCell(meterList.size() * 4);
                cell212.setCellStyle(title2Style);
                CellRangeAddress region2 = new CellRangeAddress(rowTitle1.getRowNum(), // first row
                        rowTitle1.getRowNum(), // last row
                        3, // first column
                        meterList.size() * 4 // last column
                );
                sheet.addMergedRegion(region2);
            }
            // 列名1
            XSSFRow rowHeader = sheet.createRow(2);

            XSSFCellStyle headerStyle = ExcelUtil.newMeterDataStatisticHeaderStyle(workbook);
            for (int i = 0; i <= meterList.size() * 4; i++) {
                XSSFCell cell = rowHeader.createCell(i);
                cell.setCellStyle(headerStyle);
                if(i == 0){
                    CellRangeAddress address = new CellRangeAddress(2, 3, 0, 0);
                    sheet.addMergedRegion(address);
                }
                if (i > 0 && i % 4 == 0) {
                    System.out.println(i / 4 -1);
                    EmsMeter meter = meterList.get(i / 4 - 1);
                    rowHeader.getCell(i - 3).setCellValue(meter.getMeterName().split("/")[1]);
                    CellRangeAddress region = new CellRangeAddress(rowHeader.getRowNum(), // first row
                            rowHeader.getRowNum(), // last row
                            i - 3, // first column
                            i // last column
                    );
                    sheet.addMergedRegion(region);
                }
            }
            // 列名2
            XSSFRow rowHeaderSon = sheet.createRow(3);
            int columnIdx = 0;
            XSSFCell spaceCell = rowHeaderSon.createCell(columnIdx++);
            spaceCell.setCellStyle(headerStyle);
            for (EmsMeter meter : meterList) {
                for (String header : ListGlobal) {
                    XSSFCell cell = rowHeaderSon.createCell(columnIdx++);
                    cell.setCellValue(header+"(kwh)");
                    cell.setCellStyle(headerStyle);
                }
            }

            // 填充数据


            // 创建excel
            String PATH = "E:\\export\\";
            ExcelUtil.saveExcelToFile(workbook, PATH+"有功月度月报（全厂）"+".xslx");
            downloadUrl = PATH+"有功月度月报全厂）"+".xslx";
            log.debug(downloadUrl);
            System.out.println(downloadUrl);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
