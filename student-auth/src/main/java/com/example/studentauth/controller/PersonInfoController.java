package com.example.studentauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dto.PersonInfoExecution;
import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.enums.PersonInfoStateEnum;
import com.example.studentauth.excptions.PersonInfoOperationException;
import com.example.studentauth.service.PersonInfoService;
import com.example.studentauth.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/exceladmin")
public class PersonInfoController {
    @Resource
    private PersonInfoService personInfoService;

    @RequestMapping(value = "/getpersonmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getPersonManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        long UserId = user.getUserId();
        System.out.println("personInfocontroller " + UserId);
        if (UserId <= 0) {
            Object currentPersonObj = request.getSession().getAttribute("currentPerson");
            if (currentPersonObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/exceladmin/getpersonInfolist");
            } else {
                PersonInfo currentPersonInfo = (PersonInfo) currentPersonObj;
                modelMap.put("redirect", false);
                modelMap.put("userId", currentPersonInfo.getUserId());
            }
        } else {
            PersonInfo currentPerson = new PersonInfo();
            currentPerson.setUserId((long) UserId);
            request.getSession().setAttribute("currentPerson", user);
            modelMap.put("userId", UserId);
            modelMap.put("teacherName", user.getTeacherName());
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo vo = new PersonInfo();
        String name = request.getParameter("userId");
        String password = request.getParameter("password");
        System.out.println(name + "-0000");
        System.out.println(password + "-0000");
        vo.setUserName(name);
        vo.setUserPassword(password);
        PersonInfo personInfo = personInfoService.login(vo);
        if (name == null || "".equals(name)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入账号");
        } else if (personInfo == null || "".trim().equals(personInfo)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "账号不存在请重新输入");
        } else if (personInfo != null & (password == null || "".equals(password))) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        } else if (personInfo != null & !(personInfo.getUserPassword().equals(password))) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "密码错误");
        } else if (personInfo != null & personInfo.getUserPassword().equals(password)) {
            if (personInfo.getEnabkeStatus() == 0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "您已被禁止使用本程序请联系相关人员");
            } else {
                if (personInfo.getUserType() == 1) {
                    modelMap.put("type", false);
                } else {
                    modelMap.put("type", true);
                }
                modelMap.put("success", true);
                request.getSession().setAttribute("personInfo", personInfo);
                modelMap.put("personInfo", personInfo);
            }
        }
        System.out.println("完成");
        return modelMap;
    }

    @RequestMapping("/regist")
    @ResponseBody
    public Map<String, Object> regist(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        PersonInfo personInfo = new PersonInfo();
        String name = request.getParameter("userId");
        String password = request.getParameter("password");
        String personlStr = HttpServletRequestUtil.getString(request, "personStr");

        try {
            personInfo = mapper.readValue(personlStr, PersonInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        System.out.println("为空");
        if (personInfo != null) {
            System.out.println("不为空");
            PersonInfoExecution se = personInfoService.insertPerson(personInfo);
            if (se.getState() == PersonInfoStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                List<PersonInfo> personInfoList = (List<PersonInfo>) request.getSession().getAttribute("personInfoList");
                if (personInfoList == null || personInfoList.size() == 0) {
                    personInfoList = new ArrayList<PersonInfo>();
                }
                personInfoList.add(se.getPersonInfo());
                request.getSession().setAttribute("personInfoList", personInfoList);
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

    @RequestMapping(value = "/getpersonInfolist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getpersonInfoList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user = (PersonInfo) request.getSession().getAttribute("personInfo");
        try {
            PersonInfo personInfo = new PersonInfo();
            PersonInfoExecution se = personInfoService.queryPersonInfoList(0, 300);
            modelMap.put("personList", se.getPersonInfoList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("sucess", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getpersonbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getPersonById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long userId = HttpServletRequestUtil.getLong(request, "userId");
        System.out.println(userId);
        if (userId > -1) {
            try {
                PersonInfo personInfo = personInfoService.getPersonId(userId);
                //List<Area> areaList=areaService.getAreaList();
                modelMap.put("person", personInfo);
                //modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty userId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyPerson", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyPerson(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        PersonInfo personInfo = null;
        //1.接收并转化相应的参数，包括表信息
        String personStr = HttpServletRequestUtil.getString(request, "personStr");
        try {
            personInfo = mapper.readValue(personStr, PersonInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //2.修改表信息
        if (personInfo != null && personInfo.getUserId() != null) {
            PersonInfo owner = new PersonInfo();
            List<PersonInfo> personInfoList = (List<PersonInfo>) request.getSession().getAttribute("personInfoList");
            PersonInfoExecution se = personInfoService.modifyPersonInfo(personInfo);
            try {
                if (se.getState() == PersonInfoStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (PersonInfoOperationException e) {
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

    @RequestMapping(value = "/removeteacher", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removePersonInfo(@RequestParam("userId") String userId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int puserId = Integer.parseInt(userId);
        if (puserId > 0) {
            try {
                PersonInfo currentUser = (PersonInfo) request.getSession().getAttribute("personInfo");
                PersonInfoExecution pe = personInfoService.deletePerson(puserId);
                if (pe.getState() == PersonInfoStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (PersonInfoOperationException e) {
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

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            request.getSession().removeAttribute("personInfo");
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }
}
