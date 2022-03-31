package com.example.studentauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dto.ReachPointExecution;
import com.example.studentauth.entity.ClassesGrade;
import com.example.studentauth.entity.Excel;
import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.entity.ReachPoint;
import com.example.studentauth.enums.ReachPointStateEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.ReachPointOperationException;
import com.example.studentauth.service.ClassGradeService;
import com.example.studentauth.service.ExcelService;
import com.example.studentauth.service.ReachPointService;
import com.example.studentauth.service.StudentService;
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
public class ReachPointManagementController {
    @Autowired
    private ReachPointService reachPointService;
    @Autowired
    private ClassGradeService classGradeService;
    @Autowired
    private StudentService StudentService;
    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/getreachpointbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getReachPointById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        excellist = excelService.getExcelListByuser(user.getUserId());
        int reachPointId = HttpServletRequestUtil.getInt(request, "reachPointId");
        if (reachPointId > -1) {
            try {
                ReachPoint reachPoint = reachPointService.getReachPoint(reachPointId);
                modelMap.put("excellist", excellist);
                modelMap.put("reachPoint", reachPoint);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty reachpointId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getreachpointinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Excel> excelList = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        System.out.println("reachpoint " + user.getUserName());
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

    @RequestMapping(value = "/getreachpointlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getReachPointList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        List<Excel> excellist = new ArrayList<Excel>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        List<ReachPoint> list = null;
        if (user != null && user.getUserId() > 0) {
            list = reachPointService.queryReachPointList(user.getUserId());
            excellist = excelService.getExcelListByuser(user.getUserId());
            //list=reachPointService.queryReachPointList(152200);
            for (ReachPoint reachPoint : list) {
                Excel excel = excelService.getByExcelId(reachPoint.getExcelId());
                System.out.println(excel.getExcelName() + "ReachPointManagement的excel");
                reachPoint.setExcelName(excel.getExcelName());
                System.out.println(reachPoint.getPointIdOne());
            }
            modelMap.put("excellist", excellist);
            modelMap.put("ReachPointList", list);
            modelMap.put("user", user);
            modelMap.put("success", true);
            return modelMap;
        } else {
            ReachPointStateEnum ps = ReachPointStateEnum.INNER_ERROR;
            modelMap.put("success", false);
            return modelMap;
        }
    }

    @RequestMapping("/downReachPointWord1")
    @ResponseBody
    public void exportWord(@RequestParam("excelId") String excelId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int score = 0;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>(9);
        int ExcelId = Integer.parseInt(excelId);
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        ReachPoint list = null;
        ClassesGrade classList = null;
        System.out.println("excelId为 " + ExcelId);
        if (ExcelId > 0) {
            System.out.println("下载项进入成功" + ExcelId);
            list = reachPointService.getReachPointByExcelId(ExcelId);
            classList = classGradeService.getCLassGradeByExcelId(ExcelId);
        }
        Excel excel = new Excel();
        double num = 0, fallone = 0, fnum = 0, allone = 0;
        excel = excelService.getByExcelId(ExcelId);
        map.put("term", excel.getTerm());
        map.put("classname", excel.getClassesName());
        map.put("class", excel.getExcelName());
        map.put("classscore", excel.getClassGrade());
        map.put("classnumber", excel.getCourse());
        map.put("major", excel.getMajor());
        map.put("pointGraduation", list.getPointGraduation());
        map.put("pointOne", list.getPointIdOne());
        map.put("pointTwo", list.getPointIdTwo());
        map.put("reachOne", list.getReachScoreOne());
        map.put("reachTwo", list.getReachScoreTwo());
        map.put("targetone", list.getTeachTargetOne());
        map.put("targettwo", list.getTeachTargetTwo());
        map.put("work", list.getWorkGrade());
        map.put("class", list.getClassGrade());
        map.put("exp", list.getExpGrade());
        map.put("last", list.getLastGrade());
        num = list.getWorkGrade() + list.getClassGrade() + list.getExpGrade() + list.getLastGrade();
        allone = list.getWorkGrade() + list.getClassGrade() + list.getExpGrade();
        map.put("all", num);
        map.put("allone", allone);
        map.put("fclass", classList.getAvgClassGrade());
        map.put("fwork", classList.getAvgWorkGrade());
        map.put("fexp", classList.getAvgExpGrade());
        map.put("flast", classList.getAvgLastGrade());
        fnum = classList.getAvgClassGrade() + classList.getAvgWorkGrade() + classList.getAvgExpGrade() + classList.getAvgLastGrade();
        fallone = classList.getAvgClassGrade() + classList.getAvgWorkGrade() + classList.getAvgExpGrade();
        map.put("fall", fnum);
        map.put("fallone", fallone);
        map.put("freachOne", list.getFinallReachScoreOne());
        map.put("freachTwo", list.getFinallReachScoreTwo());
        InputStream is = this.getClass().getResourceAsStream("/static/ter2.docx");
        ExportWordUtils.exportWord(is, "D:/test", excel.getClassesName() + excel.getExcelName() + "附件3.docx", map, request, response);
    }

    @RequestMapping("/downReachPointWord2")
    @ResponseBody
    public void exportWord2(@RequestParam("excelId") String excelId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int score = 0;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>(9);
        int ExcelId = Integer.parseInt(excelId);
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        ReachPoint list = null;
        ClassesGrade classList = null;
        System.out.println("excelId为 " + ExcelId);
        if (ExcelId > 0) {
            System.out.println("下载项进入成功" + ExcelId);
            list = reachPointService.getReachPointByExcelId(ExcelId);
            classList = classGradeService.getCLassGradeByExcelId(ExcelId);
        }
        Excel excel = new Excel();
        excel = excelService.getByExcelId(ExcelId);
        map.put("term", excel.getTerm());
        map.put("classname", excel.getClassesName());
        map.put("classN", excel.getExcelName());
        map.put("classscore", excel.getClassGrade());
        map.put("classnumber", excel.getCourse());
        map.put("major", excel.getMajor());
        map.put("pointGraduation", list.getPointGraduation());
        map.put("pointOne", list.getPointIdOne());
        map.put("pointTwo", list.getPointIdTwo());
        map.put("pointdescOne", list.getPointDescOne());
        map.put("pointdescTwo", list.getPointDescTwo());
        map.put("reachOne", list.getReachScoreOne());
        map.put("reachTwo", list.getReachScoreTwo());
        map.put("freachOne", list.getFinallReachScoreOne());
        map.put("freachTwo", list.getFinallReachScoreTwo());
        map.put("targetone", list.getTeachTargetOne());
        map.put("targettwo", list.getTeachTargetTwo());
        map.put("wayone", list.getReachWayOne());
        map.put("waytwo", list.getReachWayTwo());
        map.put("basisone", list.getEvaluationBasisOne());
        map.put("basistwo", list.getEvaluationBasisTwo());
        map.put("record", list.getReachRecord());
        map.put("change", list.getReachChange());
        map.put("resources", list.getReachResources());
        InputStream is = this.getClass().getResourceAsStream("/static/ter3.docx");
        ExportWordUtils.exportWord(is, "D:/test", excel.getClassesName() + excel.getExcelName() + "附件4.docx", map, request, response);
    }

    @RequestMapping(value = "/addreachpoints", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addReachPoints(HttpServletRequest request) {
        System.out.println("到达目的地");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        ReachPoint reachPoint = new ReachPoint();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
		/*for(ReachPoint pc:reachPointList) {
			pc.setExcelId(currentExcel.getExcelId());
		}*/
        String reachPointStr = HttpServletRequestUtil.getString(request, "reachPointStr");
        try {
            reachPoint = mapper.readValue(reachPointStr, ReachPoint.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        reachPoint.setUserId(user.getUserId());
        List<ReachPoint> reachPointlist = new ArrayList<ReachPoint>();
        if (reachPoint != null) {
            reachPointlist.add(reachPoint);
            try {
                ReachPointExecution pe = reachPointService.batchAddReachPoint(reachPointlist);
                if (pe.getState() == ReachPointStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ReachPointOperationException e) {
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

    @RequestMapping(value = "/removereachpoints", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeReachPoint(@RequestParam("reachPointId") String reachPointId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pointId = Integer.parseInt(reachPointId);
        if (pointId > 0) {
            try {
                PersonInfo currentUser = (PersonInfo) request.getSession().getAttribute("personInfo");
                ReachPointExecution pe = reachPointService.deleteReachPoint(pointId, currentUser.getUserId());
                if (pe.getState() == ReachPointStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ReachPointOperationException e) {
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

    @RequestMapping(value = "/modifyReachPoint", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyReachPoint(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        ReachPoint reachPoint = null;
        String reachPointStr = HttpServletRequestUtil.getString(request, "reachPointStr");
        try {
            reachPoint = mapper.readValue(reachPointStr, ReachPoint.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (reachPoint != null && reachPoint.getReachPointId() > 0) {
            double classGrade = 0, workGrade = 0, expGrade = 0, lastGrade = 0, allGrade = 0, finallScore = 0;
            int allScore = 0;
            ClassesGrade cg = classGradeService.getCLassGradeByExcelId(reachPoint.getExcelId());
            if (reachPoint.getClassGrade() != 0) {
                classGrade += cg.getAvgClassGrade();
            }
            if (reachPoint.getWorkGrade() != 0) {
                workGrade += cg.getAvgWorkGrade();
            }
            if (reachPoint.getExpGrade() != 0) {
                expGrade += cg.getAvgExpGrade();
            }
            if (reachPoint.getLastGrade() != 0) {
                lastGrade += cg.getAvgLastGrade();
            }
            allGrade = classGrade + workGrade + expGrade + lastGrade;
			/*double ss=reachPoint.getReachScore();
			reachPoint.setAllGrade(reachPoint.getClassGrade()+reachPoint.getWorkGrade()+reachPoint.getExpGrade()+reachPoint.getLastGrade());
			finallScore=(ss*allGrade)/reachPoint.getAllGrade();
			reachPoint.setFinallReachScore(finallScore);*/
            ReachPointExecution se = reachPointService.modifyReachPoint(reachPoint);
            try {
                if (se.getState() == ReachPointStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ExcelOperationException e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入表Id");
            return modelMap;
        }
    }
}
