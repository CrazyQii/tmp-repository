package com.example.studentauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dto.GradeSubjectExecution;
import com.example.studentauth.entity.Excel;
import com.example.studentauth.entity.GradeSubject;
import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.enums.GradeSubjectEnum;
import com.example.studentauth.excptions.GradeSubjectOperationException;
import com.example.studentauth.service.ExcelService;
import com.example.studentauth.service.GradeSubjectService;
import com.example.studentauth.util.ExportWordUtils;
import com.example.studentauth.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/exceladmin")
public class GradeSubjectController {
    @Autowired
    GradeSubjectService gradeSubjectService;
    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/getgradesubjectbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getGradeSubjectById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        int SubjectId = HttpServletRequestUtil.getInt(request, "subjectId");
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        excellist = excelService.getExcelListByuser(user.getUserId());

        if (SubjectId > -1) {
            try {
                GradeSubject gradeSubject = gradeSubjectService.getBySubjectId(SubjectId);
                modelMap.put("excellist", excellist);
                modelMap.put("gradeSubject", gradeSubject);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty gradeSubjectId");
        }
        return modelMap;
    }

    @RequestMapping("/downGradeSubejctWord")
    @ResponseBody
    public void exportWord(@RequestParam("excelId") String excelId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int score = 0;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>(9);
        int ExcelId = Integer.parseInt(excelId);
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        List<GradeSubject> list = null;
        if (ExcelId > 0) {
            System.out.println("下载项进入成功" + ExcelId);
            list = gradeSubjectService.getGradeSubjectListByExcel(ExcelId);
        }
        Excel excel = new Excel();
        excel = excelService.getByExcelId(ExcelId);
        map.put("term", excel.getTerm());
        map.put("num", list.size());
        map.put("classname", excel.getClassesName());
        map.put("class", excel.getCourse());
        List<Map<String, String>> jobs = new ArrayList<>();
        Map<String, String> job;
        for (GradeSubject gradeSubject : list) {
            job = new HashMap<>();
            job.put("name", gradeSubject.getSubjectName());
            job.put("score", gradeSubject.getSubjectDesc());
            job.put("address", gradeSubject.getSubjectScore().toString());
            score += gradeSubject.getSubjectScore();
            jobs.add(job);
        }
        map.put("allscore", score);
        map.put("jobs", jobs);
        InputStream is = this.getClass().getResourceAsStream("/static/ter1.docx");
        ExportWordUtils.exportWord(is, "D:/test", excel.getClassesName() + excel.getExcelName() + "附件1.docx", map, request, response);
    }

    @RequestMapping(value = "/getgradesubjectlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getgradesubjectlist(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        excellist = excelService.getExcelListByuser(user.getUserId());
        List<GradeSubject> list = null;
        System.out.println("getgradesubjectlist " + user.getUserId());
        if (user != null && user.getUserId() > 0) {
            list = gradeSubjectService.getGradeSubjectList(user.getUserId());
            for (GradeSubject gradeSubject : list) {
                Excel excel = excelService.getByExcelId(gradeSubject.getExcelId());
                System.out.println(excel.getExcelName() + "GradeSubject的excel");
                gradeSubject.setExcelName(excel.getExcelName());
                System.out.println(gradeSubject.getSubjectId() + "GradeSubjectControllerid");
            }
            modelMap.put("excellist", excellist);
            modelMap.put("GradeSubjectList", list);
            modelMap.put("user", user);
            modelMap.put("success", true);
            return modelMap;
        } else {
            GradeSubjectEnum ps = GradeSubjectEnum.INNER_ERROR;
            modelMap.put("success", false);
            return modelMap;
        }
    }

    /*@RequestMapping(value = "/getgradesubjectlist",method=RequestMethod.GET)
    @ResponseBody
    private Result<List<GradeSubject>> getgradesubjectlist(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<String,Object>();
        Excel currentExcel=(Excel)request.getSession().getAttribute("currentExcel");
        PersonInfo user=new PersonInfo();
        user=(PersonInfo) request.getSession().getAttribute("personInfo");
        List<GradeSubject> list=null;
        System.out.println(currentExcel.getExcelId());
        if(user!=null&&user.getUserId()>0) {
            list=gradeSubjectService.getGradeSubjectList(user.getUserId());
            for(GradeSubject gradeSubject:list) {
                System.out.println(gradeSubject.getSubjectName());
            }
            modelMap.put("GradeSubjectList",list);
            modelMap.put("user",user);
            modelMap.put("success",true);
            return new Result<List<GradeSubject>>(true,list);
        }else {
            GradeSubjectEnum ps=GradeSubjectEnum.INNER_ERROR;
            return new Result<List<GradeSubject>>(false,ps.getState(),ps.getStateInfo());
        }
    }*/
    @RequestMapping(value = "/getgradesubjectinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excelList = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        System.out.println("zxcasdasd " + user.getUserName());
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

    @RequestMapping(value = "/addgradesubject", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addGradeSubject(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        GradeSubject gradeSubject = new GradeSubject();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        //gradeSubject.setExcelId(currentExcel.getExcelId());
        System.out.println("addgradesubject " + user.getUserId());
        String gradeSubjectStr = HttpServletRequestUtil.getString(request, "gradeSubjectStr");
        try {
            gradeSubject = mapper.readValue(gradeSubjectStr, GradeSubject.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        gradeSubject.setUserId(user.getUserId());
        System.out.println(gradeSubject.getSubjectName());
        if (gradeSubject != null) {
            try {
                GradeSubjectExecution pe = gradeSubjectService.InsertGradeSubject(gradeSubject);
                if (pe.getState() == GradeSubjectEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (GradeSubjectOperationException e) {
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

    @RequestMapping(value = "/removegradesubject", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeGradeSubject(@RequestParam("subjectId") String subjectId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int GradesubjectId = Integer.parseInt(subjectId);
        if (GradesubjectId > 0) {
            try {
                PersonInfo currentUser = (PersonInfo) request.getSession().getAttribute("personInfo");
                GradeSubjectExecution pe = gradeSubjectService.deleteGradeSubjectById(GradesubjectId, currentUser.getUserId());
                if (pe.getState() == GradeSubjectEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (GradeSubjectOperationException e) {
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

    @RequestMapping(value = "/modifyGradeSubject", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyGradeSubject(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        GradeSubject gradeSubject = null;
        String gradeSubjectStr = HttpServletRequestUtil.getString(request, "gradeSubjectStr");
        try {
            gradeSubject = mapper.readValue(gradeSubjectStr, GradeSubject.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (gradeSubject != null && gradeSubject.getSubjectId() > 0) {
            GradeSubjectExecution se = gradeSubjectService.modifyGradeSubject(gradeSubject);
            try {
                if (se.getState() == GradeSubjectEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (GradeSubjectOperationException e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入课程目标表信息");
            return modelMap;
        }
    }
}
