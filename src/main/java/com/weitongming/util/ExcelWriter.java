package com.weitongming.util;

import org.apache.poi.ss.usermodel.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author weitongming
 * @date 2020年08月10日 15:50
 */

public class ExcelWriter {


    /**
     * 生成Excel并写入数据信息
     * @param dataList 数据列表
     * @return 写入数据后的工作簿对象
     */
    public static void writeRow(List<List<String>> dataList,Sheet sheet){


        //构建每行的数据内容
        int rowNum = 1;
        for (Iterator<List<String>> it = dataList.iterator(); it.hasNext(); ) {
            List<String> rowData = it.next();
            if (rowData == null) {
                continue;
            }
            //输出行数据
            Row row = sheet.createRow(rowNum++);
            convertDataToRow(rowData,row);
        }
    }

    /**
     * 生成sheet表，并写入第一行数据（列头）
     * @return 已经写入列头的Sheet
     */
    public static Sheet buildDataSheet(  Workbook workbook ,List<String> header) {
        Sheet sheet = workbook.createSheet();
        // 设置列头宽度
        for (int i=0; i<header.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < header.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 设置第一行列头的样式
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * 将数据转换成行
     * @param data 源数据
     * @param row 行对象
     * @return
     */
    private static void convertDataToRow(List<String> data, Row row){
        int cellNum = 0;
        Cell cell;
        for (String current:data) {
            cell = row.createCell(cellNum++);
            cell.setCellValue(null == current ? "" : current);
        }
    }
}