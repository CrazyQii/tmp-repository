package com.example.studentauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dao.StudentDao;
import com.example.studentauth.dto.AnalysisExecution;
import com.example.studentauth.entity.*;
import com.example.studentauth.enums.AnalysisEnum;
import com.example.studentauth.excptions.AnalysisOperationException;
import com.example.studentauth.service.AnalysisService;
import com.example.studentauth.service.ClassGradeService;
import com.example.studentauth.service.ExcelService;
import com.example.studentauth.service.ReachPointService;
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
public class AnalysisController {
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ReachPointService reachPointService;
    @Autowired
    private ClassGradeService classGradeService;

    @RequestMapping(value = "/getanalysisbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getAnalysisById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        excellist = excelService.getExcelListByuser(user.getUserId());
        int analysisId = HttpServletRequestUtil.getInt(request, "analysisId");
        if (analysisId > -1) {
            try {
                Analysis analysis = analysisService.getByAnalysisId(analysisId);
                modelMap.put("excellist", excellist);
                modelMap.put("analysis", analysis);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty analysisId");
        }
        return modelMap;
    }

    @RequestMapping("/downAnalysisWord")
    @ResponseBody
    public void exportWord(@RequestParam("excelId") String excelId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int score = 0;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>(9);
        int ExcelId = Integer.parseInt(excelId);
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        Analysis list = null;
        ClassesGrade classList = null;
        ReachPoint reachList = null;
        int countlA = 0, countlB = 0, countlC = 0, countlD = 0, countlE = 0, countAA = 0, countAB = 0, countAC = 0, countAD = 0, countAE = 0, count = 0;
        double maxlA = 0, maxlB = 0, minlA = 0, minlB = 0, prolA = 0, prolB = 0, prolC = 0, prolD = 0, prolE = 0, proAA = 0, proAB = 0, proAC = 0, proAD = 0, proAE = 0;
        System.out.println("excelId为 " + ExcelId);
        if (ExcelId > 0) {
            System.out.println("下载项进入成功" + ExcelId);
            list = analysisService.getAnalysisByExcelId(ExcelId);
            classList = classGradeService.getCLassGradeByExcelId(ExcelId);
            reachList = reachPointService.getReachPointByExcelId(ExcelId);
            count = classList.getStudentCount();
            maxlA = studentDao.querymaxlast(ExcelId);
            minlA = studentDao.queryminlast(ExcelId);
            maxlB = studentDao.querymaxall(ExcelId);
            minlB = studentDao.queryminall(ExcelId);
            countlA = studentDao.queryCountLastbest(ExcelId);
            countlB = studentDao.queryCountLast2(ExcelId);
            countlC = studentDao.queryCountLast3(ExcelId);
            countlD = studentDao.queryCountLast4(ExcelId);
            countlE = studentDao.queryCountLast5(ExcelId);
            countAA = studentDao.queryCountallbest(ExcelId);
            countAB = studentDao.queryCountall2(ExcelId);
            countAC = studentDao.queryCountall3(ExcelId);
            countAD = studentDao.queryCountall4(ExcelId);
            countAE = studentDao.queryCountall5(ExcelId);
            prolA = countlA * 1.0 / count * 100;
            prolB = countlB * 1.0 / count * 100;
            prolC = countlC * 1.0 / count * 100;
            prolD = countlD * 1.0 / count * 100;
            prolE = countlE * 1.0 / count * 100;
            proAA = countAA * 1.0 / count * 100;
            proAB = countAB * 1.0 / count * 100;
            proAC = countAC * 1.0 / count * 100;
            proAD = countAD * 1.0 / count * 100;
            proAE = countAE * 1.0 / count * 100;
        }
        Excel excel = new Excel();
        double num = 0, fallone = 0, fnum = 0, allone = 0;
        excel = excelService.getByExcelId(ExcelId);
        map.put("term", excel.getTerm());
        map.put("classname", excel.getClassesName());
        map.put("classN", excel.getExcelName());
        map.put("teacher", user.getTeacherName());
        map.put("count", classList.getStudentCount());
        map.put("work", reachList.getWorkGrade());
        map.put("class", reachList.getClassGrade());
        map.put("exp", reachList.getExpGrade());
        map.put("last", reachList.getLastGrade());
        num = reachList.getWorkGrade() + reachList.getClassGrade() + reachList.getExpGrade() + reachList.getLastGrade();
        map.put("all", num);
        map.put("fclass", classList.getAvgClassGrade());
        map.put("fwork", classList.getAvgWorkGrade());
        map.put("fexp", classList.getAvgExpGrade());
        map.put("flast", classList.getAvgLastGrade());
        fnum = classList.getAvgClassGrade() + classList.getAvgWorkGrade() + classList.getAvgExpGrade() + classList.getAvgLastGrade();
        map.put("fall", fnum);
        map.put("pclass", classList.getClassPoint());
        map.put("pwork", classList.getWorkPoint());
        map.put("pexp", classList.getExpPoint());
        map.put("plast", classList.getLastPoint());
        map.put("pall", classList.getAllPoint());
        map.put("maxl", maxlA);
        map.put("minl", minlA);
        map.put("maxA", maxlB);
        map.put("minA", minlB);
        map.put("countlA", countlA);
        map.put("countlB", countlB);
        map.put("countlC", countlC);
        map.put("countlD", countlD);
        map.put("countlE", countlE);
        map.put("countAA", countAA);
        map.put("countAB", countAB);
        map.put("countAC", countAC);
        map.put("countAD", countAD);
        map.put("countAE", countAE);
        System.out.println(countlA + " " + count + " " + prolA);
        map.put("prolA", prolA);
        map.put("prolB", prolB);
        map.put("prolC", prolC);
        map.put("prolD", prolD);
        map.put("prolE", prolE);
        map.put("proAA", proAA);
        map.put("proAB", proAB);
        map.put("proAC", proAC);
        map.put("proAD", proAD);
        map.put("proAE", proAE);
        map.put("fallOne", classList.getAvgLastGrade() * 2);
        map.put("fallTwo", classList.getAvgAllGrade());
        map.put("assessmentAna", list.getAssessmentAnalysis());
        map.put("termAna", list.getTermAnalysis());
        map.put("courseAna", list.getCourseAnalysis());
        map.put("graduationAna", list.getGraduationAnalysis());
        map.put("edcationAna", list.getEdcationAnalysis());
        InputStream is = this.getClass().getResourceAsStream("/static/ter4.docx");
        ExportWordUtils.exportWord(is, "D:/test", excel.getClassesName() + excel.getExcelName() + "附件5.docx", map, request, response);
    }

    @RequestMapping(value = "/getanalinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getAnalysisInt(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excelList = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        System.out.println("analysis " + user.getUserName());
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

    @RequestMapping(value = "/getanalysislist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getAnalysisList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        List<Analysis> list = null;
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (user != null && user.getUserId() > 0) {
            //记得修改
            excellist = excelService.getExcelListByuser(user.getUserId());
            list = analysisService.getAnalysisList(user.getUserId());
            //excellist=excelService.getExcelListByuser(152200);
            for (Analysis analysis : list) {
                Excel excel = excelService.getByExcelId(analysis.getExcelId());
                System.out.println(excel.getExcelName() + "AnalysisController的excel");
                analysis.setExcelName(excel.getExcelName());
                System.out.println(analysis.getAnalysisId() + "AnalysisControllerid");
            }
            modelMap.put("excellist", excellist);
            modelMap.put("AnalysisList", list);
            modelMap.put("user", user);
            modelMap.put("success", true);
            return modelMap;
        } else {
            AnalysisEnum ps = AnalysisEnum.INNER_ERROR;
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty excelId from analysis");
            return modelMap;
        }
    }


    @RequestMapping(value = "/addanalysis", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addAnalysis(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("到添加了");
        Analysis analysis = new Analysis();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        String analysisStr = HttpServletRequestUtil.getString(request, "analysisStr");
        try {
            analysis = mapper.readValue(analysisStr, Analysis.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        analysis.setUserId(user.getUserId());
        List<Analysis> analysislist = new ArrayList<Analysis>();
        if (analysis != null) {
            analysislist.add(analysis);
            try {
                AnalysisExecution pe = analysisService.InsertAnalysis(analysis);
                if (pe.getState() == AnalysisEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (AnalysisOperationException e) {
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

    @RequestMapping(value = "/modifyAnalysis", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyAnalysis(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        Analysis analysis = null;
        String analysisStr = HttpServletRequestUtil.getString(request, "analysisStr");
        try {
            analysis = mapper.readValue(analysisStr, Analysis.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (analysis != null && analysis.getAnalysisId() > 0) {
            AnalysisExecution se = analysisService.modifyAnalysis(analysis);
            try {
                if (se.getState() == AnalysisEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (AnalysisOperationException e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入成绩分析信息");
            return modelMap;
        }
    }

    @RequestMapping(value = "/removeanalysis", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeAnalysis(@RequestParam("analysisId") String analysisId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int panalysisId = Integer.parseInt(analysisId);
        if (panalysisId > 0) {
            try {
                Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
                PersonInfo user = new PersonInfo();
                user = (PersonInfo) request.getSession().getAttribute("personInfo");
                AnalysisExecution pe = analysisService.deleteAnalysisById(panalysisId, user.getUserId());
                if (pe.getState() == AnalysisEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (AnalysisOperationException e) {
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
}
