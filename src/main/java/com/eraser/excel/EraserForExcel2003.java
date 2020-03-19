package com.eraser.excel;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.format.*;
import jxl.write.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @user eric
 * @date 2020-03-17 9:06
 */
public class EraserForExcel2003 {

    private static final String FILENAME = "防疫提报记录信息.xls";

    private static final String ENCODE = "UTF-8";

    private static final String HEAD1 = "content-disposition";

    private static final String HEAD2 = "attachment;filename=";

    private static final Integer WIDTH = 20;


    public static <T> void downloadExcel(List<T> list) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpServletRequest request = requestAttributes.getRequest();
        try {
            //写到服务器上
            String path = request.getSession().getServletContext().getRealPath("") + "/" + FILENAME;
            File file = new File(path);
            //创建写Excel对象
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            //工作表
            WritableSheet sheet = workbook.createSheet("防疫提报记录信息", 0);
            //设置字体
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat cellFormat = new WritableCellFormat();
            //设置背景颜色
            cellFormat.setBackground(Colour.WHITE);
            //设置边框
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            //设置文字居中对齐方式
            cellFormat.setAlignment(Alignment.CENTRE);
            //设置垂直居中
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            //分别给1、5、6列设置不同的宽度
            sheet.setColumnView(0, 15);
            sheet.setColumnView(4, 60);
            sheet.setColumnView(5, 35);
            //给sheet电子版中的所有列设置默认的列的宽度
            sheet.getSettings().setDefaultColumnWidth(WIDTH);
            //设置自动换行
            cellFormat.setWrap(true);
            Label label0 = new Label(0, 0, "ID", cellFormat);
            Label label1 = new Label(1, 0, "姓名", cellFormat);
            Label label2 = new Label(2, 0, "身份证号码", cellFormat);
            Label label3 = new Label(3, 0, "手机号", cellFormat);
            Label label4 = new Label(4, 0, "小区", cellFormat);
            Label label5 = new Label(5, 0, "楼座", cellFormat);
            Label label6 = new Label(6, 0, "单元", cellFormat);
            Label label7 = new Label(7, 0, "房间", cellFormat);
            Label label8 = new Label(8, 0, "身份类型", cellFormat);
            Label label9 = new Label(9, 0, "体温", cellFormat);
            Label label10 = new Label(10, 0, "是否异常", cellFormat);
            Label label11 = new Label(11, 0, "测量时间", cellFormat);
            Label label12 = new Label(12, 0, "出行轨迹", cellFormat);
            Label label13 = new Label(13, 0, "是否隔离", cellFormat);
            Label label14 = new Label(14, 0, "隔离原因", cellFormat);
            Label label15 = new Label(15, 0, "隔离开始时间", cellFormat);
            Label label16 = new Label(16, 0, "隔离结束时间", cellFormat);
            Label label17 = new Label(17, 0, "提报人", cellFormat);
            Label label18 = new Label(18, 0, "是否删除", cellFormat);
            Label label19 = new Label(19, 0, "创建用户", cellFormat);
            Label label20 = new Label(20, 0, "创建时间", cellFormat);
            Label label21 = new Label(21, 0, "更新用户", cellFormat);
            Label label22 = new Label(22, 0, "更新时间", cellFormat);
            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            sheet.addCell(label4);
            sheet.addCell(label5);
            sheet.addCell(label6);
            sheet.addCell(label7);
            sheet.addCell(label8);
            sheet.addCell(label9);
            sheet.addCell(label10);
            sheet.addCell(label11);
            sheet.addCell(label12);
            sheet.addCell(label13);
            sheet.addCell(label14);
            sheet.addCell(label15);
            sheet.addCell(label16);
            sheet.addCell(label17);
            sheet.addCell(label18);
            sheet.addCell(label19);
            sheet.addCell(label20);
            sheet.addCell(label21);
            sheet.addCell(label22);


            //给第二行设置背景，字体颜色，对齐方式等
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            //设置文字居中对齐方式
            cellFormat2.setAlignment(Alignment.CENTRE);
            //设置垂直居中
            cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat2.setBackground(Colour.WHITE);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(true);

            int element = 0;
            int up = 1;
            Method method;
            String column = "";
            for (T model : list) {
                Field[] fields = model.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i].getName();
                    String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    method = model.getClass().getMethod("get" + methodName);
                    Object invoke = method.invoke(model);
                    column = invoke.toString();
                    if (methodName.equalsIgnoreCase("measure_time") || methodName.equalsIgnoreCase("isolate_start_time") || methodName.equalsIgnoreCase("isolate_end_time")
                            || methodName.equalsIgnoreCase("create_time") || methodName.equalsIgnoreCase("update_time")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        column = format.format(invoke);
                    }
                    Label labelCell = new Label(element, up, column, cellFormat);
                    sheet.addCell(labelCell);
                    element++;
                }
                element = 0;
                up++;
            }
            //开始执行写入操作
            workbook.write();
            //释放资源
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //下载操作
        OutputStream out;
        try {
            response.addHeader(HEAD1, HEAD2 + URLEncoder.encode(FILENAME, ENCODE));
            //下载
            out = response.getOutputStream();
            String path = request.getSession().getServletContext().getRealPath("") + "/" + FILENAME;
            //读文件
            InputStream in = new FileInputStream(path);
            byte[] b = new byte[4096];
            int size = in.read(b);
            while (size > 0) {
                out.write(b, 0, size);
                size = in.read(b);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
