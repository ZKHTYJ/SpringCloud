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
 * @date 2021/10/19 21:20
 * @description
 */
@Slf4j
public class ExportMonth {
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

        ArrayList<DateMonth> ListDateMonths = new ArrayList<>();
            DateMonth dateMonth = new DateMonth();
            DateMonth dateMonth1 = new DateMonth();
            DateMonth dateMonth2 = new DateMonth();

            dateMonth.setName("当月");
            dateMonth1.setName("上年同月");
            dateMonth2.setName("增幅");
            ListDateMonths.add(dateMonth);
            ListDateMonths.add(dateMonth1);
            ListDateMonths.add(dateMonth2);

        try {
            // 创建workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 创建sheet
            XSSFSheet sheet = workbook.createSheet("基础数据采集总汇表");
            sheet.setDefaultColumnWidth(16);
           // 表头
            XSSFRow rowTitle0 = sheet.createRow(0);
            rowTitle0.setHeightInPoints(20F);
            XSSFCellStyle title0Style = ExcelUtil.newMeterDataStatisticTitle0Style(workbook);
            XSSFCell cell0 = rowTitle0.createCell(0);
            cell0.setCellValue("基础数据采集总汇表（月度）");
            cell0.setCellStyle(title0Style);
            // 右边框
            XSSFCell cell012 = rowTitle0.createCell(meterList.size() * ListGlobal.size());
            cell012.setCellStyle(title0Style);
            CellRangeAddress region0 = new CellRangeAddress(rowTitle0.getRowNum(), // first row
                    rowTitle0.getRowNum(), // last row
                    rowTitle0.getFirstCellNum(), // first column
                    meterList.size() * ListGlobal.size() // last column
            );
            sheet.addMergedRegion(region0);

            // 标题: 统计月份
            XSSFRow rowTitle1 = sheet.createRow(1);
            rowTitle1.setHeightInPoints(18F);
            //XSSFCellStyle title1Style = ExcelUtil.newMeterDataStatisticTitle1Style(workbook);
            XSSFCell cell1 = rowTitle1.createCell(0);
            cell1.setCellValue(String.format("统计月份：%s", "2021-10-19".replaceAll("-", ".")));
            //cell1.setCellStyle(title1Style);
            cell1.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
            if (meterList.size() > 0) {
                // 右边框
                XSSFCell cell212 = rowTitle1.createCell(meterList.size() * ListGlobal.size());
                //cell212.setCellStyle(title1Style);
                CellRangeAddress region2 = new CellRangeAddress(rowTitle1.getRowNum(), // first row
                        rowTitle1.getRowNum(), // last row
                        0, // first column
                        meterList.size() * ListGlobal.size() // last column
                );
                sheet.addMergedRegion(region2);
            }

            // 列名1
            XSSFRow rowHeader = sheet.createRow(2);
            XSSFCellStyle headerStyle = ExcelUtil.newMeterDataStatisticHeaderStyle(workbook);
            for (int i = 0; i<=meterList.size()*ListGlobal.size();i++){
                XSSFCell cell = rowHeader.createCell(i);
                cell.setCellStyle(headerStyle);
                if(i == 0){
                    CellRangeAddress address = new CellRangeAddress(2, 3, 0, 0);
                    sheet.addMergedRegion(address);
                }
                if (i>0 && i % ListGlobal.size() == 0) {
                    System.out.println(i/4 -1);
                    DateMonth month = ListDateMonths.get(i / ListGlobal.size() - 1);
                    rowHeader.getCell(i-3).setCellValue(month.getName());
                    CellRangeAddress cellAddresses = new CellRangeAddress(rowHeader.getRowNum(), rowHeader.getRowNum(), i - 3, i);
                    sheet.addMergedRegion(cellAddresses);

                }
            } // 列名2
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

            // 设置左边列名


            // 创建excel
            String PATH = "E:\\export\\";
            ExcelUtil.saveExcelToFile(workbook, PATH+"基础数据采集总汇表（月度）1"+".xslx");
            downloadUrl = PATH+"基础数据采集总汇表1（月度）"+".xslx";
            log.debug(downloadUrl);
            System.out.println(downloadUrl);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
