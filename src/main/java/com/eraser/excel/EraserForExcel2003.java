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

import static java.awt.image.ImageObserver.WIDTH;

/**
 * @user eric
 * @date 2020-03-17 9:06
 * @TODO 适用于在Web情况下导出excel文件到本地的文件管理系统，此工具类并未采用poi的方式编写，所以对.xlsx格式的excel文件提供的支持并不完善
 */
public class EraserForExcel2003 {

    /**
     * 用于在web端导出excel文件
     * @param data 包含数据对象的集合，可以是任何对象类型
     * @param title 包含excel表头的数据集合，注意，渲染到导出的excel中的表头的数据等同于声明此数组时嵌入数据的顺序
     * @param filename 提供一个导出文件的文件名
     * @param <T> 数据对象泛型
     */
    public static <T> void downloadExcel(List<T> data, List<String> title,String filename) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpServletRequest request = requestAttributes.getRequest();
        try {
            //写到服务器上
            String path = request.getSession().getServletContext().getRealPath("") + "/" + filename;
            File file = new File(path);
            //创建写Excel对象
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            //工作表
            WritableSheet sheet = workbook.createSheet(filename, 0);
            //设置字体 todo 可配置
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat cellFormat = new WritableCellFormat();
            //设置背景颜色 todo 可配置
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
            sheet.getSettings().setDefaultColumnWidth(20);
            //设置自动换行
            cellFormat.setWrap(true);
            /**
             * 说明：传入参数data为数据列表，title为表头数据，表头数据的渲染跟插入的顺序有关
             */
            for (int i = 0; i < data.size(); i++) {
                Label label = new Label(i, 0, title.get(i), cellFormat);
                sheet.addCell(label);
            }


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
            for (T model : data) {
                Field[] fields = model.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i].getName();
                    String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    method = model.getClass().getMethod("get" + methodName);
                    Object invoke = method.invoke(model);
                    column = invoke.toString();
                    //todo 
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
            response.addHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            //下载
            out = response.getOutputStream();
            String path = request.getSession().getServletContext().getRealPath("") + "/" + filename;
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
