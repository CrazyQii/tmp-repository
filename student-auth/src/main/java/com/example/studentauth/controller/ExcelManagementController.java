package com.example.studentauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dto.ExcelExecution;
import com.example.studentauth.entity.Area;
import com.example.studentauth.entity.Excel;
import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.enums.ExcelStateEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.service.AreaService;
import com.example.studentauth.service.CourseService;
import com.example.studentauth.service.ExcelService;
import com.example.studentauth.service.majorService;
import com.example.studentauth.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exceladmin")
public class ExcelManagementController {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private majorService MajorService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/getexcelmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getExcelManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long excelId = HttpServletRequestUtil.getLong(request, "excelId");
        if (excelId <= 0) {
            Object currentExcelObj = request.getSession().getAttribute("currentExcel");
            if (currentExcelObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/exceladmin/login");
            } else {
                Excel currentExcel = (Excel) currentExcelObj;
                modelMap.put("redirect", false);
                modelMap.put("excelId", currentExcel.getExcelId());
            }
        } else {
            Excel currentExcel = new Excel();
            currentExcel.setExcelId((int) excelId);
            request.getSession().setAttribute("currentExcel", currentExcel);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getexcelbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getExcelById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long excelId = HttpServletRequestUtil.getLong(request, "excelId");
        System.out.println(excelId);
        if (excelId > -1) {
            try {
                Excel excel = excelService.getByExcelId(excelId);
                //List<Area> areaList=areaService.getAreaList();
                modelMap.put("excel", excel);
                //modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty excelId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getexcellist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getExcelList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        System.out.println("(fromEXcelManageController)用户id" + user.getUserId());
        try {
            Excel excelConditionExcel = new Excel();
            excelConditionExcel.setOwner(user);
            ExcelExecution se = excelService.getExcelList(excelConditionExcel, 0, 300);
            modelMap.put("excelList", se.getExcelList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("sucess", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getexcelinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registerExcel", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerExcel(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Excel excel = null;
        //1.接收并转化相应的参数，包括表信息
        String excelStr = HttpServletRequestUtil.getString(request, "excelStr");
        try {
            excel = mapper.readValue(excelStr, Excel.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //2.注册表
        if (excel != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("personInfo");
            excel.setOwner(owner);
            ExcelExecution se = excelService.addExcel(excel);
            if (se.getState() == ExcelStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
                @SuppressWarnings("unchecked")
                List<Excel> excelList = (List<Excel>) request.getSession().getAttribute("excelList");
                if (excelList == null || excelList.size() == 0) {
                    excelList = new ArrayList<Excel>();
                }
                excelList.add(se.getExcel());
                request.getSession().setAttribute("excelList", excelList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入成绩信息");
            return modelMap;
        }

    }

    @RequestMapping(value = "/modifyExcel", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyExcel(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        Excel excel = null;
        //1.接收并转化相应的参数，包括表信息
        String excelStr = HttpServletRequestUtil.getString(request, "excelStr");
        try {
            excel = mapper.readValue(excelStr, Excel.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //2.修改表信息
        if (excel != null && excel.getExcelId() != null) {
            PersonInfo owner = new PersonInfo();
            //Session TODO
            owner.setUserId(1L);
            excel.setOwner(owner);
            ExcelExecution se = excelService.modifyExcel(excel);
            try {
                if (se.getState() == ExcelStateEnum.SUCCESS.getState()) {
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
            modelMap.put("errMsg", "请输入表单Id");
            return modelMap;
        }

    }

    @RequestMapping(value = "/delexcelbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> removeExcelById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long excelId = HttpServletRequestUtil.getLong(request, "excelId");
        System.out.println(excelId);
        if (excelId > -1) {
            try {
                excelService.delExcelById(excelId);
                modelMap.put("success", true);
            } catch (Exception e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty excelId");
        }
        return modelMap;
    }
}
