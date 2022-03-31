package com.example.studentauth.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dto.StudentExecution;
import com.example.studentauth.entity.*;
import com.example.studentauth.enums.ReachPointStateEnum;
import com.example.studentauth.enums.StudentStateEnum;
import com.example.studentauth.excptions.StudentOperationException;
import com.example.studentauth.service.*;
import com.example.studentauth.util.ExportWordUtils;
import com.example.studentauth.util.HttpServletRequestUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/exceladmin")
public class StudentManagementController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private ReachPointService reachPointService;
    @Autowired
    GradeSubjectService gradeSubjectService;
    @Autowired
    private ClassGradeService classGradeService;

    @RequestMapping(value = "/downloadstudentsexcel")
    @ResponseBody
    private void excelDown(HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        String webPath = request.getServletContext().getRealPath("/");
        List<Student> list = null;
        if (currentExcel != null && currentExcel.getExcelId() > 0) {
            list = studentService.queryStudentList(currentExcel.getExcelId());
        } else {
            ReachPointStateEnum ps = ReachPointStateEnum.INNER_ERROR;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("成绩表单");
        HSSFRow row = sheet.createRow(0);
        CellRangeAddress region = new CellRangeAddress(0, 6, 0, 4);
        sheet.addMergedRegion(region);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("学生姓名");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("课堂表现");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("作业考核");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("实验考核");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("期末考试");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("总评成绩");
        cell.setCellStyle(style);
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Student student = list.get(i);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(student.getStudentId());
            row.createCell(2).setCellValue(student.getStudentName());
            row.createCell(3).setCellValue(student.getClassGrade());
            row.createCell(4).setCellValue(student.getWorkGrade());
            row.createCell(5).setCellValue(student.getExpGrade());
            row.createCell(6).setCellValue(student.getLastGrade());
            row.createCell(7).setCellValue(student.getAllGrade());
        }
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            String fileName = "成绩表单.xls";
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            wb.write(out);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
    }

    @RequestMapping("/downExe")
    @ResponseBody
    public void exportExe(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>(9);
        TemplateExportParams params = new TemplateExportParams("/static/成绩表单.xls");
        try {
            Workbook book = ExcelExportUtil.exportExcel(params, map);
            //下载方法
            ExportWordUtils.export(response, book, "上传模板");
        } catch (Exception e) {
            System.out.println("导出模板Excel，失败:" + e);
        }

    }

    private static final String TEMPLATE_FILE_NAME = "static/template.docx";

    @RequestMapping("/downWord")
    @ResponseBody
    public void exportWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>(9);
        Excel excel = new Excel();
        excel.setExcelId(20);
        excel = excelService.getByExcelId(excel.getExcelId());
        System.out.println("excelName=" + excel.getExcelName() + " class=" + excel.getCourse());
        map.put("term", excel.getTerm());
        map.put("classname", excel.getClassesName());
        map.put("class", excel.getCourse());
        map.put("3", "four");
        map.put("4", "five");
        map.put("5", "six");
        List<GradeSubject> list = null;
        list = gradeSubjectService.getGradeSubjectList(excel.getExcelId());
        List<Map<String, String>> jobs = new ArrayList<>();
        Map<String, String> job;
        for (GradeSubject gradeSubject : list) {
            job = new HashMap<>();
            job.put("name", gradeSubject.getSubjectName());
            job.put("score", gradeSubject.getSubjectDesc());
            job.put("address", gradeSubject.getSubjectScore().toString());
            jobs.add(job);
        }
        map.put("jobs", jobs);
        InputStream is = this.getClass().getResourceAsStream("/static/ter1.docx");
        ExportWordUtils.exportWord(is, "D:/test", "aaa.docx", map, request, response);
    }

    @RequestMapping("/downstudentexe")
    @ResponseBody
    public void exportExe(@RequestParam("excelId") String excelId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int score = 0;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>(9);
        int ExcelId = Integer.parseInt(excelId);
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        List<Student> list = null;
        ClassesGrade classList = null;
        ReachPoint Pointlist = null;
        if (ExcelId > 0) {
            System.out.println("下载项进入成功" + ExcelId);
            list = studentService.getStudentListByExcel(ExcelId);
            classList = classGradeService.getCLassGradeByExcelId(ExcelId);
            Pointlist = reachPointService.getReachPointByExcelId(ExcelId);
        }

        Excel excel = new Excel();
        excel = excelService.getByExcelId(ExcelId);
        map.put("term", excel.getTerm());
        map.put("classname", excel.getClassesName());
        map.put("classN", excel.getExcelName());
        map.put("classscore", excel.getClassGrade());
        map.put("classnumber", excel.getCourse());
        map.put("major", excel.getMajor());
        map.put("work", Pointlist.getWorkGrade());
        map.put("class", Pointlist.getClassGrade());
        map.put("exp", Pointlist.getExpGrade());
        map.put("last", Pointlist.getLastGrade());
        int all = Pointlist.getWorkGrade() + Pointlist.getClassGrade() + Pointlist.getExpGrade() + Pointlist.getLastGrade();
        map.put("all", all);
        map.put("flcass", String.valueOf(classList.getAvgClassGrade()));
        map.put("fwork", String.valueOf(classList.getAvgWorkGrade()));
        map.put("fexp", String.valueOf(classList.getAvgExpGrade()));
        map.put("flast", String.valueOf(classList.getAvgLastGrade()));
        map.put("fall", String.valueOf(classList.getAvgAllGrade()));
        map.put("point1", String.valueOf(Pointlist.getFinallReachScoreOne()));
        map.put("point2", String.valueOf(Pointlist.getFinallReachScoreTwo()));
        double cpoint1 = Pointlist.getFinallReachScoreOne() / Pointlist.getReachScoreOne();
        double cpoint2 = Pointlist.getFinallReachScoreTwo() / Pointlist.getReachScoreTwo();
        map.put("cpoint1", String.valueOf(cpoint1));
        map.put("cpoint2", String.valueOf(cpoint2));
        map.put("pointOne", String.valueOf(Pointlist.getPointIdOne()));
        map.put("pointTwo", String.valueOf(Pointlist.getPointIdTwo()));
        double fpoint = classList.getAvgAllGrade() / 100;
        map.put("fpoint", String.valueOf(fpoint));
        List<Student> studentlist = null;

        List<Map<String, String>> jobs = new ArrayList<>();
        Map<String, String> job;
        int num = 1;
        for (Student student : list) {
            job = new HashMap<>();
            String snum = Integer.toString(num);
            job.put("num", snum);
            num++;
            job.put("studentnum", Integer.toString(student.getStudentId()));
            job.put("studentname", student.getStudentName());
            job.put("sclass", String.valueOf(student.getClassGrade()));
            job.put("swork", String.valueOf(student.getWorkGrade()));
            job.put("sexp", String.valueOf(student.getExpGrade()));
            job.put("slast", String.valueOf(student.getLastGrade()));
            double sall = student.getClassGrade() + student.getWorkGrade() + student.getExpGrade() + student.getLastGrade();
            job.put("sall", String.valueOf(sall));
            jobs.add(job);
        }
        map.put("jobs", jobs);
        try {
            // 此处需要使用绝对路径 - 注意修改
            TemplateExportParams params = new TemplateExportParams("D:\\Workspace\\tmp-repository\\student-auth\\src\\main\\resources\\static\\ter5.xls");
            Workbook book = ExcelExportUtil.exportExcel(params, map);
            //下载方法
            ExportWordUtils.export(response, book, excel.getClassesName() + excel.getExcelName() + "附件2");
        } catch (Exception e) {
            System.out.println("导出模板Excel，失败:" + e);
        }
		/*InputStream is = this.getClass().getResourceAsStream("/static/ter5.xlsx");
//		ExportWordUtils.exportExcel(is,"F:/test","mmm.xls",map,request,response);*/
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile userExcel, @RequestParam("excelId") String excelId, HttpServletRequest request, HttpSession session) throws IOException, InvalidFormatException {
        //Excel currentExcel=(Excel)request.getSession().getAttribute("currentExcel");
        //System.out.println("excelId"+currentExcel.getExcelId());
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int excelId2 = Integer.valueOf(excelId);
        ObjectMapper mapper = new ObjectMapper();
        if (userExcel == null) {
            session.setAttribute("excelName", "未上传文件，上传失败！");
            return null;
        }
        try {
            String userExcelFileName = userExcel.getOriginalFilename();
            if (!userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
                session.setAttribute("excelName", "文件格式不正确！请使用.xls或.xlsx后缀的文档，导入失败！");
                return null;
            }
            PersonInfo user = new PersonInfo();
            user = (PersonInfo) request.getSession().getAttribute("personInfo");
            //导入
            studentService.importExcel(userExcel, excelId2, user.getUserId());
            session.setAttribute("excelName", "导入成功！");
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getstudentlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getStudentList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        List<Student> list = null;
        if (user != null && user.getUserId() > 0) {
            list = studentService.queryStudentList(user.getUserId());
            excellist = excelService.getExcelListByuser(user.getUserId());
            //list=reachPointService.queryReachPointList(152200);
            for (Student student : list) {
                Excel excel = excelService.getByExcelId(student.getExcelId());
                System.out.println(excel.getExcelName() + "studentManagement的excel");
                student.setExcelName(excel.getExcelName());
                System.out.println(student.getExeStudentId());
            }
            modelMap.put("excellist", excellist);
            modelMap.put("StudentList", list);
            modelMap.put("user", user);
            modelMap.put("success", true);
            return modelMap;
        } else {
            StudentStateEnum ps = StudentStateEnum.INNER_ERROR;
            modelMap.put("success", false);
            return modelMap;
        }
    }


    @RequestMapping(value = "/addstudents", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addStudents(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        ObjectMapper mapper = new ObjectMapper();
        Student student = new Student();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        String studentStr = HttpServletRequestUtil.getString(request, "studentStr");
        try {
            student = mapper.readValue(studentStr, Student.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        student.setUserId(user.getUserId());
        List<Student> studentlist = new ArrayList<Student>();
        if (student != null) {
            double number = 0;
            int count;
            number += student.getClassGrade();
            number += student.getWorkGrade();
            number += student.getLastGrade();
            number += student.getExpGrade();
            student.setAllGrade(Math.ceil(number));
            studentlist.add(student);
            ClassesGrade classesGrade = new ClassesGrade();
            classesGrade = classGradeService.getCLassGradeByExcelId(student.getExcelId());
            ReachPoint reachPoint = new ReachPoint();
            reachPoint = reachPointService.getReachPointByExcelId(student.getExcelId());
            double avgClassGrade = 0, avgWorkGrade = 0, avgLastGrade = 0, avgAllGrade = 0, avgExpGrade = 0, reachScoreOne = 0, reachScoreTwo = 0;
            double classPoint = 0, workPoint = 0, expPoint = 0, lastPoint = 0, allPoint = 0, finallReachScoreOne = 0, finallReachScoreTwo = 0;
            reachScoreOne = reachPoint.getReachScoreOne();
            reachScoreTwo = reachPoint.getReachScoreTwo();
            if (classesGrade == null) {
                classesGrade = new ClassesGrade();
                System.out.println("student.getClassGrade() +" + student.getClassGrade());
                reachScoreOne = reachPoint.getReachScoreOne();
                reachScoreTwo = reachPoint.getReachScoreTwo();
                classesGrade.setAvgClassGrade(student.getClassGrade());
                classesGrade.setAvgWorkGrade(student.getWorkGrade());
                classesGrade.setAvgLastGrade(student.getLastGrade());
                classesGrade.setAvgExpGrade(student.getExpGrade());
                classesGrade.setAvgAllGrade(student.getAllGrade());
                classesGrade.setStudentCount(1);
                classesGrade.setExcelId(student.getExcelId());
                classesGrade.setUserId(user.getUserId());
                classPoint = classesGrade.getAvgClassGrade() / (reachPoint.getClassGrade());
                workPoint = classesGrade.getAvgWorkGrade() / (reachPoint.getWorkGrade());
                expPoint = classesGrade.getAvgExpGrade() / (reachPoint.getExpGrade());
                lastPoint = classesGrade.getAvgLastGrade() / (reachPoint.getLastGrade());
                allPoint = classesGrade.getAvgAllGrade() / (100);
                classesGrade.setClassPoint(classPoint);
                classesGrade.setWorkPoint(workPoint);
                classesGrade.setExpPoint(expPoint);
                classesGrade.setLastPoint(lastPoint);
                classesGrade.setAllPoint(allPoint);
                finallReachScoreOne = (classesGrade.getAvgClassGrade() + classesGrade.getAvgWorkGrade() + classesGrade.getAvgExpGrade()) * reachScoreOne / (reachPoint.getClassGrade() + reachPoint.getWorkGrade() + reachPoint.getExpGrade());
                finallReachScoreTwo = (classesGrade.getAvgLastGrade()) * reachScoreTwo / reachPoint.getLastGrade();
                reachPoint.setFinallReachScoreOne(finallReachScoreOne);
                reachPoint.setFinallReachScoreTwo(finallReachScoreTwo);
                classGradeService.addClassGrade(classesGrade);
                reachPointService.modifyReachPointForScore(reachPoint);
                System.out.println("classesGrade.getGradeId() +" + classesGrade.getAvgClassGrade());
            } else {
                count = classesGrade.getStudentCount();
                avgClassGrade = classesGrade.getAvgClassGrade() * count;
                avgWorkGrade = classesGrade.getAvgWorkGrade() * count;
                avgLastGrade = classesGrade.getAvgLastGrade() * count;
                avgAllGrade = classesGrade.getAvgAllGrade() * count;
                avgExpGrade = classesGrade.getAvgExpGrade() * count;
                count++;
                avgClassGrade += student.getClassGrade();
                avgWorkGrade += student.getWorkGrade();
                avgLastGrade += student.getLastGrade();
                avgAllGrade += student.getExpGrade();
                avgExpGrade += student.getAllGrade();
                classesGrade.setAvgClassGrade(avgClassGrade / count);
                classesGrade.setAvgWorkGrade(avgWorkGrade / count);
                classesGrade.setAvgLastGrade(avgLastGrade / count);
                classesGrade.setAvgExpGrade(avgExpGrade / count);
                classesGrade.setAvgAllGrade(avgAllGrade / count);
                classesGrade.setStudentCount(count);
                classPoint = classesGrade.getAvgClassGrade() / (reachPoint.getClassGrade());
                workPoint = classesGrade.getAvgWorkGrade() / (reachPoint.getWorkGrade());
                expPoint = classesGrade.getAvgExpGrade() / (reachPoint.getExpGrade());
                lastPoint = classesGrade.getAvgLastGrade() / (reachPoint.getLastGrade());
                allPoint = classesGrade.getAvgAllGrade() / (100);
                classesGrade.setClassPoint(classPoint);
                classesGrade.setWorkPoint(workPoint);
                classesGrade.setExpPoint(expPoint);
                classesGrade.setLastPoint(lastPoint);
                classesGrade.setAllPoint(allPoint);
                System.out.println("classesGrade.getScore() +" + reachPoint.getFinallReachScoreOne() + " " + reachPoint.getFinallReachScoreTwo());
                finallReachScoreOne = (classesGrade.getAvgClassGrade() + classesGrade.getAvgWorkGrade() + classesGrade.getAvgExpGrade()) * reachScoreOne / (reachPoint.getClassGrade() + reachPoint.getWorkGrade() + reachPoint.getExpGrade());
                finallReachScoreTwo = (classesGrade.getAvgLastGrade()) * reachScoreTwo / reachPoint.getLastGrade();
                reachPoint.setFinallReachScoreOne(finallReachScoreOne);
                reachPoint.setFinallReachScoreTwo(finallReachScoreTwo);
                classGradeService.modifyClassGrade(classesGrade);
                reachPointService.modifyReachPointForScore(reachPoint);
                System.out.println("classesGrade.getGradeId() +" + classesGrade.getAvgClassGrade());
                System.out.println("classesGrade.getfinallyScore() +" + reachPoint.getFinallReachScoreOne() + " " + reachPoint.getFinallReachScoreTwo());
            }

            try {
                StudentExecution pe = studentService.batchAddStudent(studentlist);
                if (pe.getState() == StudentStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (StudentOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少写入一种信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getstudentinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excelList = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        System.out.println("student " + user.getUserName());
        try {
            //excelList=excelService.getExcelListByuser(user.getUserId());
            excelList = excelService.getExcelListByuser(user.getUserId());
            for (Excel ss : excelList) {
                System.out.println(ss.getExcelName());
            }
            modelMap.put("excelList", excelList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getstudentinitinfo2", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList2(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excelList = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        //System.out.println("student "+user.getUserName());
        try {
            //excelList=excelService.getExcelListByuser(user.getUserId());
            excelList = excelService.getExcelListByuser(152200L);
            for (Excel ss : excelList) {
                System.out.println(ss.getExcelName());
            }
            modelMap.put("excelList", excelList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getstudentbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getStudentById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        excellist = excelService.getExcelListByuser(user.getUserId());
        int exeStudentId = HttpServletRequestUtil.getInt(request, "exeStudentId");
        if (exeStudentId > -1) {
            try {
                Student student = studentService.getStudent(exeStudentId);
                modelMap.put("excellist", excellist);
                modelMap.put("student", student);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty exeStudentId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removestudents", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeReachPoint(@RequestParam("exeStudentId") String exeStudentId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int studentId2 = Integer.parseInt(exeStudentId);
        ClassesGrade classesGrade = new ClassesGrade();
        ReachPoint reachPoint = new ReachPoint();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        int count = 0;
        double avgClassGrade = 0, avgWorkGrade = 0, avgLastGrade = 0, avgAllGrade = 0, avgExpGrade = 0, reachScoreOne = 0, reachScoreTwo = 0;
        double classPoint = 0, workPoint = 0, expPoint = 0, lastPoint = 0, allPoint = 0, finallReachScoreOne = 0, finallReachScoreTwo = 0;
        if (studentId2 > 0) {
            try {
                //Excel currentExcel=(Excel)request.getSession().getAttribute("currentExcel");
                Student student = studentService.getStudent(studentId2);
                long excelId = student.getExcelId();
                reachPoint = reachPointService.getReachPointByExcelId(student.getExcelId());
                classesGrade = classGradeService.getCLassGradeByExcelId(excelId);
                count = classesGrade.getStudentCount();
                avgClassGrade = ((classesGrade.getAvgClassGrade()) * count - student.getClassGrade()) / (count - 1);
                avgWorkGrade = ((classesGrade.getAvgWorkGrade()) * count - student.getWorkGrade()) / (count - 1);
                avgExpGrade = ((classesGrade.getAvgExpGrade()) * count - student.getExpGrade()) / (count - 1);
                avgLastGrade = ((classesGrade.getAvgLastGrade()) * count - student.getLastGrade()) / (count - 1);
                avgAllGrade = ((classesGrade.getAvgAllGrade()) * count - student.getAllGrade()) / (count - 1);
                count--;
                classesGrade.setAvgClassGrade(avgClassGrade);
                classesGrade.setAvgWorkGrade(avgWorkGrade);
                classesGrade.setAvgExpGrade(avgExpGrade);
                classesGrade.setAvgLastGrade(avgLastGrade);
                classesGrade.setAvgAllGrade(avgAllGrade);
                classesGrade.setStudentCount(count);
                classPoint = classesGrade.getAvgClassGrade() / (reachPoint.getClassGrade());
                workPoint = classesGrade.getAvgWorkGrade() / (reachPoint.getWorkGrade());
                expPoint = classesGrade.getAvgExpGrade() / (reachPoint.getExpGrade());
                lastPoint = classesGrade.getAvgLastGrade() / (reachPoint.getLastGrade());
                allPoint = classesGrade.getAvgAllGrade() / (100);
                classesGrade.setClassPoint(classPoint);
                classesGrade.setWorkPoint(workPoint);
                classesGrade.setExpPoint(expPoint);
                classesGrade.setLastPoint(lastPoint);
                classesGrade.setAllPoint(allPoint);
                finallReachScoreOne = (classesGrade.getAvgClassGrade() + classesGrade.getAvgWorkGrade() + classesGrade.getAvgExpGrade()) * reachScoreOne / (reachPoint.getClassGrade() + reachPoint.getWorkGrade() + reachPoint.getExpGrade());
                finallReachScoreTwo = (classesGrade.getAvgLastGrade()) * reachScoreTwo / reachPoint.getLastGrade();

                classGradeService.modifyClassGrade(classesGrade);
                reachPointService.modifyReachPointForScore(reachPoint);
                System.out.println("classesGrade.getGradeId() +" + classesGrade.getAvgClassGrade());
                System.out.println("classesGrade.getfinallyScore() +" + reachPoint.getFinallReachScoreOne() + " " + reachPoint.getFinallReachScoreTwo());
                StudentExecution pe = studentService.deleteStudent(studentId2, user.getUserId());
                if (pe.getState() == StudentStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (StudentOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyStudent", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyStudent(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        Student student = null;
        String studentStr = HttpServletRequestUtil.getString(request, "studentStr");
        try {
            student = mapper.readValue(studentStr, Student.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (student != null && student.getExeStudentId() > 0) {
            StudentExecution se = studentService.modifyStudent(student);
            try {
                if (se.getState() == StudentStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (StudentOperationException e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入学生成绩");
            return modelMap;
        }
    }
}
