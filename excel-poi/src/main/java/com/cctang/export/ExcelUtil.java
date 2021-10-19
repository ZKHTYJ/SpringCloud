package com.cctang.export;

import cn.hutool.core.text.csv.CsvWriter;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;

import java.awt.Color;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/10/19 20:35
 * @description
 */
public class ExcelUtil {
    public static Workbook readExcelWithFilePath(String filePath) {
        File file = new File(filePath);
        InputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream = new FileInputStream(file);
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            //CommonUtil.printErrorLog(e);
        }
        return workbook;
    }

    public static Workbook doTestWrite(Workbook workbook) {
        try {
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            //得到单元格样式
            CellStyle cellStyle = cell.getCellStyle();
            System.out.println("行数：" + rowLength + ",列数：" + colLength);
            for (int i = 0; i < rowLength; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    //Excel数据Cell有不同的类型，当我们试图从一个数字类型的Cell读取出一个字符串时就有可能报异常：
                    //Cannot get a STRING value from a NUMERIC cell
                    //将所有的需要读的Cell表格设置为String格式
                    if (cell != null)
                        cell.setCellType(CellType.STRING);

                    //对Excel进行修改
                    if (i > 0 && j == 1)
                        cell.setCellValue("1000");
                    System.out.print(cell.getStringCellValue() + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

//    public static String saveExcelWithFilePath(Workbook workbook, String filePath, String fileName) {
//        filePath = filePath + File.separator + UUID.randomUUID() + System.currentTimeMillis() + fileName;
//        File file = new File(filePath);
//        try {
//            if (!file.getParentFile().exists()) {//判断文件夹是否创建，没有创建则创建新文件夹
//                file.getParentFile().mkdirs();
//            }
//            OutputStream out = new FileOutputStream(file);
//            workbook.write(out);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return file.getAbsolutePath();
//    }



    /**
     * @param picByte    图片的base64编码
     * @param workbook   操作的excel对象
     * @param sheetIndex 操作的excel对应的sheet(from 0)
     * @param startCol   插入图片起点(左上角)列数
     * @param startRow   插入图片起点(左上角)行数
     * @param colLength  插入图片宽度列数
     */
    public static Workbook addPictureToExcel(byte[] picByte, Workbook workbook, int sheetIndex,
                                             int startCol, int startRow, double colLength
    ) {

        Sheet sheet = workbook.getSheetAt(sheetIndex);

        int pictureIdx = workbook.addPicture(picByte, Workbook.PICTURE_TYPE_JPEG);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setCol1(startCol);  //列
        anchor.setRow1(startRow);  //行
        // 插入图片
        Picture pict = drawing.createPicture(anchor, pictureIdx);

        double col = colLength;
        double row = col * pict.getImageDimension().getWidth() / pict.getImageDimension().getHeight();

        pict.resize(col, row);

        return workbook;
    }

    /**
     * Add data to workbook
     */
    public static Workbook exportToExcelFile(LinkedList<List<String>> data, List<String> header) throws IOException {

        // System.err.println(header.toString());
        // for(List<String> item: data){
        //     System.err.println(item.toString());
        // }

        // Create XLSX workbook and write data to it
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            // write header to workbook
            Sheet sheet = workbook.createSheet();
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (String col : header) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(col);
            }

            // write data to workbook
            for (List<String> dataRow : data) {
                row = sheet.createRow(rowNum++);
                cellNum = 0;
                for (String cellData : dataRow) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellValue(cellData);
                }
            }

        } catch (Exception e) {
            //CommonUtil.printErrorLog(e);
        }

        return workbook;
    }

    /**
     * Append the Base64 Image workbook
     */
    public static Workbook appendBase64ImageToExcelFile(String base64Img, Workbook workbook) throws IOException {

        Sheet sheet = workbook.getSheetAt(0);
        int imageStartRowNumber = sheet.getLastRowNum() + 2;
        int imageStartColNumber = 0;
        double maxColLength = sheet.getRow(0).getLastCellNum();

        String[] arr = base64Img.split("base64,");
        // default image to JPEG foratt
        int imgType = Workbook.PICTURE_TYPE_JPEG;
        // Only support other PNG format, others will be ignore to default value
        if (arr[0].contains("png")) {
            imgType = Workbook.PICTURE_TYPE_PNG;
        }

        byte[] picByte = Base64.decodeBase64(arr[1]);

        workbook = ExcelUtil.addPictureToExcel(picByte, workbook, 0, imageStartColNumber, imageStartRowNumber, maxColLength, imgType);

        return workbook;
    }

    /**
     * Save workbook to file
     */
    public static int saveExcelToFile(Workbook workbook, String filePath) throws IOException {
        int rv = 1;
        File file = new File(filePath);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            //CommonUtil.printErrorLog(e);
            rv = 0;
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return rv;
    }

    /**
     * Create the absolute path for the file
     */
    public static String buildAbsoluteExcelFileNameAndPath(String filePath, String fileName) throws IOException {

        // Creating the file
        Path path = Paths.get(filePath);
        Path pathDir = Files.createDirectories(path);

        String fileAllPath = String.format("%s/%s%s", pathDir.toAbsolutePath().toString(), fileName, "xlsx");

        return fileAllPath;
    }

    /**
     * @param picByte    图片的base64编码
     * @param workbook   操作的excel对象
     * @param sheetIndex 操作的excel对应的sheet(from 0)
     * @param startCol   插入图片起点(左上角)列数
     * @param startRow   插入图片起点(左上角)行数
     * @param colLength  插入图片宽度列数
     * @param imgType    图片类型
     */
    public static Workbook addPictureToExcel(byte[] picByte, Workbook workbook, int sheetIndex,
                                             int startCol, int startRow, double colLength, int imgType) {

        Sheet sheet = workbook.getSheetAt(sheetIndex);

        int pictureIdx = workbook.addPicture(picByte, imgType);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setCol1(startCol);  //列
        anchor.setRow1(startRow);  //行
        // 插入图片
        Picture pict = drawing.createPicture(anchor, pictureIdx);

        double col = colLength;
        double row = col * pict.getImageDimension().getWidth() / pict.getImageDimension().getHeight();

        pict.resize(col, row);

        return workbook;
    }

    /**
     * Export data to file
     */
    public static String exportToExcel(String path, String fileName, LinkedList<List<String>> data, List<String> header, String base64Img) throws IOException {

        // Create file
        String filePath = ExcelUtil.buildAbsoluteExcelFileNameAndPath(path, fileName);

        // Export table data to the file
        Workbook workbook = ExcelUtil.exportToExcelFile(data, header);

        // Append image to the file
        workbook = ExcelUtil.appendBase64ImageToExcelFile(base64Img, workbook);

        // Save workbook to file
        int rv = ExcelUtil.saveExcelToFile(workbook, filePath);
        if (rv == 0) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * Export data to file with totals in the last row
     */
    public static String exportToExcel(String path, String fileName, LinkedList<List<String>> data, List<String> header,
                                       int mergeCellStartIndex, int mergeCellEndIndex, List<String> total) throws IOException {

        // Create file
        String filePath = ExcelUtil.buildAbsoluteExcelFileNameAndPath(path, fileName);

        // Export table data to the workbook
        Workbook workbook = ExcelUtil.exportToExcelFile(data, header);

        // Append the last total summary row to the workbook
        workbook = addSummaryToExcel(workbook, mergeCellStartIndex, mergeCellEndIndex, total);

        // Save workbook to file
        int rv = ExcelUtil.saveExcelToFile(workbook, filePath);
        if (rv == 0) {
            filePath = null;
        }

        return filePath;
    }

    public static Workbook addSummaryToExcel(Workbook workbook, int mergeCellStartIndex, int mergeCellEndIndex, List<String> total) {

        Sheet sheet = workbook.getSheetAt(0);
        int rowIndex = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowIndex);
        int cellNum = 0;

        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, mergeCellStartIndex, mergeCellEndIndex));
        Boolean firstCell = true;
        for (String cellData : total) {
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(cellData);
            CellStyle style = workbook.createCellStyle();
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.RIGHT);
            if (firstCell) {
                style.setAlignment(HorizontalAlignment.CENTER);
                cell.setCellValue(total.get(0));
                cell.setCellStyle(style);
                while (cellNum <= mergeCellEndIndex) {
                    cell = row.createCell(cellNum++);
                    cell.setCellStyle(style);
                }
                firstCell = false;
            } else {
                cell.setCellStyle(style);

                // check column size
                int currentColumnSize = sheet.getColumnWidth(cellNum - 1) / 256;
                if (currentColumnSize < cell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length) {
                    sheet.setColumnWidth(cellNum - 1, (cell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length) * 256);
                }
            }
        }

        return workbook;
    }

    /**
     * Export data to file with table name and totals in the last row
     */
//    public static String exportToExcelWithTableName(String path, String fileName, LinkedList<List<String>> data, List<String> header,
//                                                    String tableName, String dateRange,
//                                                    int mergeCellStartIndex, int mergeCellEndIndex, List<String> total) throws IOException {
//
//        // Create file
//        String filePath = ExcelUtil.buildAbsoluteExcelFileNameAndPath(path, fileName);
//
//        // Create workbook
//        Workbook workbook = new XSSFWorkbook();
//        // Create table name and header style
//        CellStyle tableNameAndHeaderStyle = workbook.createCellStyle();
//        tableNameAndHeaderStyle.setBorderTop(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setBorderLeft(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setBorderRight(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setBorderBottom(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
//        tableNameAndHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        tableNameAndHeaderStyle.setFillForegroundColor(Global.SAP_EXPORT_TABLE_HEADER_BG_COLOR);
//        tableNameAndHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        // create the font for headers
//        Font font = workbook.createFont();
//        font.setColor(IndexedColors.WHITE.getIndex());
//        tableNameAndHeaderStyle.setFont(font);
//        // create the table name and header
//        workbook = ExcelUtil.createWorkbookWithTableNameAsFirstRow(workbook, tableName, dateRange, header, tableNameAndHeaderStyle);
//        // create the cell style for the remaining table
//        CellStyle tableStyle = workbook.createCellStyle();
//        tableStyle.setBorderTop(BorderStyle.THIN);
//        tableStyle.setBorderLeft(BorderStyle.THIN);
//        tableStyle.setBorderRight(BorderStyle.THIN);
//        tableStyle.setBorderBottom(BorderStyle.THIN);
//        tableStyle.setAlignment(HorizontalAlignment.RIGHT);
//        tableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        // Export table data to the workbook
//        workbook = ExcelUtil.appendToExcelFile(workbook, data, tableStyle);
//
//        // Append the last total summary row to the workbook
//        workbook = addSummaryToExcel(workbook, mergeCellStartIndex, mergeCellEndIndex, total);
//
//        // Save workbook to file
//        int rv = ExcelUtil.saveExcelToFile(workbook, filePath);
//        if (rv == 0) {
//            filePath = null;
//        }
//
//        workbook.close();
//
//        return filePath;
//    }

    /**
     * Append data to workbook
     */
    public static Workbook appendToExcelFile(Workbook workbook, LinkedList<List<String>> data, List<String> header) throws IOException {

        // Create XLSX workbook and write data to it
        if (workbook == null) {
            throw new RuntimeException("Workbook is null");
        }

        try {
            // write header to workbook
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            CellStyle style = workbook.createCellStyle();
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);

            for (String col : header) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellStyle(style);
                cell.setCellValue(col);
            }

            // write data to workbook
            for (List<String> dataRow : data) {
                row = sheet.createRow(rowNum++);

                cellNum = 0;
                for (String cellData : dataRow) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellStyle(style);
                    cell.setCellValue(cellData);
                }
            }

            // adjusting the size
            for (int i = 0; i < header.size(); i++) {
                sheet.autoSizeColumn(i);
            }


        } catch (Exception e) {
            //CommonUtil.printErrorLog(e);
        }

        return workbook;
    }


    /**
     * Append data to workbook
     */
    public static Workbook appendToExcelFile(Workbook workbook, LinkedList<List<String>> data, CellStyle tableStyle) throws IOException {

        // Create XLSX workbook and write data to it
        if (workbook == null) {
            throw new RuntimeException("Workbook is null");
        }

        try {
            // write header to workbook
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;
            int cellNum = 0;
            CellStyle style = tableStyle != null ? tableStyle : null;
            List<Integer> maxCellLengthPerColumn = new ArrayList<>();
            // write data to workbook
            for (List<String> dataRow : data) {
                Row row = sheet.createRow(rowNum++);

                cellNum = 0;
                for (String cellData : dataRow) {

                    Cell cell = row.createCell(cellNum);
                    cell.setCellStyle(style);
                    cell.setCellValue(cellData);

                    // keep track of the maximum length per column
                    if (maxCellLengthPerColumn.size() <= cellNum) {
                        maxCellLengthPerColumn.add(cell.getStringCellValue().getBytes().length);
                    } else if (maxCellLengthPerColumn.get(cellNum)
                            .compareTo(cell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length) < 0) {
                        maxCellLengthPerColumn.set(cellNum, cell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length);
                    }
                    cellNum++;
                }
            }

            int numColumns = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
            Row headerRow = sheet.getRow(1);
            // // adjusting the size
            for (int i = 0; i < numColumns; i++) {
                // check the cell size for the header row
                if (maxCellLengthPerColumn.get(i).compareTo(
                        headerRow.getCell(i).getStringCellValue().getBytes(StandardCharsets.UTF_8).length) < 0) {
                    maxCellLengthPerColumn.set(i, headerRow.getCell(i).getStringCellValue().getBytes(StandardCharsets.UTF_8).length);
                }

                // set column size
                if (maxCellLengthPerColumn.get(i).compareTo(255) > 0)
                    sheet.setColumnWidth(i, 255 * 256);
                else
                    sheet.setColumnWidth(i, maxCellLengthPerColumn.get(i) * 256);
            }

        } catch (Exception e) {
            //CommonUtil.printErrorLog(e);
        }

        return workbook;
    }

    public static Workbook createWorkbookWithTableNameAsFirstRow(Workbook workbook, String tableName, String dateRange, List<String> headers, CellStyle cellStyle) {

        if (cellStyle == null) {
            throw new RuntimeException("Missing Cell Style");
        }
        int mergeLength = headers.size();
        try {
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);
            CellStyle style = cellStyle != null ? cellStyle : null;

            CellStyle tableNameStyle = workbook.createCellStyle();
            tableNameStyle.cloneStyleFrom(style);
            tableNameStyle.setWrapText(true);
            // write table name
            for (int i = 0; i < mergeLength; i++) {
                Cell cell = row.createCell(i);
                if (i == 0) {
                    // enable line break
                    if (dateRange == "")
                        cell.setCellValue(tableName);
                    else
                        cell.setCellValue(tableName + "\n" + dateRange);
                }
                cell.setCellStyle(tableNameStyle);
            }
            // expand the row height
            sheet.getRow(0).setHeight((short) (sheet.getRow(0).getHeight() * 2));
            // merge cells
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, mergeLength - 1));

            // write header to workbook
            row = sheet.createRow(1);
            int cellNum = 0;
            for (String col : headers) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellStyle(style);
                cell.setCellValue(col);
            }

        } catch (Exception e) {
            //CommonUtil.printErrorLog(e);
        }

        return workbook;
    }


    /**
     * Export data to file with table name but no totals in the last row
     */
//    public static String exportToExcelWithTableNameNoTotals(String path, String fileName, LinkedList<List<String>> data, List<String> header,
//                                                            String tableName) throws IOException {
//
//        // Create file
//        String filePath = ExcelUtil.buildAbsoluteExcelFileNameAndPath(path, fileName);
//
//        // Create workbook
//        Workbook workbook = new XSSFWorkbook();
//        // Create table name and header style
//        CellStyle tableNameAndHeaderStyle = workbook.createCellStyle();
//        tableNameAndHeaderStyle.setBorderTop(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setBorderLeft(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setBorderRight(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setBorderBottom(BorderStyle.THIN);
//        tableNameAndHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
//        tableNameAndHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        tableNameAndHeaderStyle.setFillForegroundColor(Global.SAP_EXPORT_TABLE_HEADER_BG_COLOR);
//        tableNameAndHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        // create the font for headers
//        Font font = workbook.createFont();
//        font.setColor(IndexedColors.WHITE.getIndex());
//        tableNameAndHeaderStyle.setFont(font);
//        // create the table name and header
//        workbook = ExcelUtil.createWorkbookWithTableNameAsFirstRow(workbook, tableName, "", header, tableNameAndHeaderStyle);
//        // create the cell style for the remaining table
//        CellStyle tableStyle = workbook.createCellStyle();
//        tableStyle.setBorderTop(BorderStyle.THIN);
//        tableStyle.setBorderLeft(BorderStyle.THIN);
//        tableStyle.setBorderRight(BorderStyle.THIN);
//        tableStyle.setBorderBottom(BorderStyle.THIN);
//        tableStyle.setAlignment(HorizontalAlignment.RIGHT);
//        tableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        // Export table data to the workbook
//        workbook = ExcelUtil.appendToExcelFile(workbook, data, tableStyle);
//
//        // Save workbook to file
//        int rv = ExcelUtil.saveExcelToFile(workbook, filePath);
//        if (rv == 0) {
//            filePath = null;
//        }
//
//        workbook.close();
//
//        return filePath;
//    }

    /**
     * 设置表格样式：数据内容
     * 数据内容
     * 行居中
     * 列居中
     * 加粗
     * 文字自适应
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newDataStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    /**
     * 设置表格样式：列名
     * 数据内容
     * 行居中
     * 列居中
     * 加粗
     * 文字自适应
     * 宋体
     * 12号
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newMeterDataStatisticHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(true);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //style.setFillForegroundColor(new XSSFColor((IndexedColorMap) new java.awt.Color(148, 193,234)));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    /**
     * 设置表格样式：标题
     * 数据内容
     * 行居中
     * 列居中
     * 加粗
     * 文字自适应
     * 宋体
     * 16号
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newMeterDataStatisticTitle0Style(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(true);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //style.setFillForegroundColor(IndexedColors);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    /**
     * 设置表格样式：标题
     * 数据内容
     * 行居左
     * 列居中
     * 加粗
     * 文字自适应
     * 宋体
     * 14号
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newMeterDataStatisticTitle1Style(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(true);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //style.setFillForegroundColor(new XSSFColor((IndexedColorMap) new java.awt.Color(148, 193,234)));

        return style;
    }

    /**
     * 设置表格样式：标题
     * 数据内容
     * 行居右
     * 列居中
     * 加粗
     * 文字自适应
     * 宋体
     * 11号
     *
     * @param workbook
     * @return
     */
//    public static XSSFCellStyle newMeterDataStatisticTitle2Style(XSSFWorkbook workbook) {
//        XSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.RIGHT);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        XSSFFont font = workbook.createFont();
//        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 11);
//        font.setBold(true);
//        style.setFont(font);
//        style.setWrapText(true);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFillForegroundColor(new XSSFColor((IndexedColorMap) new java.awt.Color(148, 193,234)));
//        style.setBorderRight(BorderStyle.THIN);
//        return style;
//    }

    /**
     * 设置表格样式：标题
     * 数据内容
     * 行居右
     * 列居中
     * 加粗
     * 文字自适应
     * 宋体
     * 14号
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newMeterDataStatisticTitle3Style(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(true);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //style.setFillForegroundColor(new XSSFColor((IndexedColorMap) new java.awt.Color(148, 193,234)));
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    /**
     * 设置表格样式：标题
     * 数据内容
     * 行居中
     * 列居中
     * 加粗
     * 文字自适应
     * 宋体
     * 14号
     *
     * @param workbook
     * @return
     */
//    public static XSSFCellStyle newMeterDataStatisticTitle4Style(XSSFWorkbook workbook) {
//        XSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.RIGHT);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        XSSFFont font = workbook.createFont();
//        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 14);
//        font.setBold(true);
//        style.setFont(font);
//        style.setWrapText(true);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFillForegroundColor(new XSSFColor((IndexedColorMap) new java.awt.Color(148, 193,234)));
//        style.setBorderRight(BorderStyle.THIN);
//        return style;
//    }
}
