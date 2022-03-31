package com.example.studentauth.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

public class ExportWordUtils {


    //模板地址
    @Value("${fileTemplateUrl}")
    private String fileTemplateUrl = "/static/ter5.xls";
    //文件存储地址
    private static String fileGoalUrl = "D:/test";

    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     *
     * @param is       word模板文件流
     * @param temDir   生成临时文件存放地址
     * @param fileName 文件名
     * @param params   替换的参数
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void exportWord(InputStream is, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(is, "模板不能为空");
        Assert.notNull(temDir, "临时文件路径不能为空 ");
        Assert.notNull(fileName, "导出文件名不能为空 ");
        Assert.isTrue(fileName.endsWith(".docx"), "word导出请使用docx格式");
        if (!temDir.endsWith("/")) {
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }

            MyXWPFDocument doc = new MyXWPFDocument(is);
            WordExportUtil.exportWord07(doc, params);

            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            delFileWord(temDir, fileName);//这一步看具体需求，要不要删
        }
    }

    public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
        ServletOutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            workbook.write(outStream);
        } finally {
            outStream.close();
        }

    }

    public static void exportExcel(InputStream is, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(is, "模板不能为空");
        Assert.notNull(temDir, "临时文件路径不能为空 ");
        Assert.notNull(fileName, "导出文件名不能为空 ");
        //Assert.isTrue(fileName.endsWith(".docx"), "word导出请使用docx格式");
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }

            //MyXWPFDocument doc = new MyXWPFDocument(is);
            TemplateExportParams doc = new TemplateExportParams(fileName);
            Workbook workbook = ExcelExportUtil.exportExcel(doc, params);
            File savefile = new File(fileGoalUrl);
            if (!savefile.exists()) {
                //logger.info("按模板导出Excel数据时存储文件目录不存在,为您创建文件夹!");
                savefile.mkdirs();
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            /*response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Access-Control-Allow-Headers", "Content-Disposition");*/
            workbook.write(response.getOutputStream());
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            workbook.write(fos);
            // 设置强制下载不打开
            //response.setContentType("application/force-download");
            // 设置文件名
            /*OutputStream out = response.getOutputStream();
            workbook.write(out);*/
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            delFileWord(temDir, fileName);//这一步看具体需求，要不要删
        }
    }

    /**
     * 删除零时生成的文件
     */
    public static void delFileWord(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        File file1 = new File(filePath);
        file.delete();
        file1.delete();
    }
}

