package com.example.studentauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "exceladmin", method = {RequestMethod.GET})
public class ExcelAdminController {

    @RequestMapping(value = "/exceloperation")
    public String excelOperation() {
        return "exceloperation";
    }

    @RequestMapping(value = "/excellist")
    public String excelList() {
        return "excellist";
    }

    @RequestMapping(value = "/excelmanagement")
    public String excelManagement() {
        return "excelmanagement";
    }

    @RequestMapping(value = "/reachpointmanagement", method = RequestMethod.GET)
    private String reachPointManage() {
        return "reachpointmanagement";
    }

    @RequestMapping(value = "/reachpointupdate", method = RequestMethod.GET)
    private String updateReachPointManage() {
        return "reachpointupdate";
    }

    @RequestMapping(value = "/studentmanagement", method = RequestMethod.GET)
    private String updateStudentManage() {
        return "studentmanagement";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String loginManage() {
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String indexManage() {
        return "index";
    }

    @RequestMapping(value = "/indexs", method = RequestMethod.GET)
    private String indexsManage() {
        return "indexs";
    }

    @RequestMapping(value = "/gradesubject", method = RequestMethod.GET)
    private String gradesubjectManage() {
        return "gradesubject";
    }

    @RequestMapping(value = "/newgradesubject", method = RequestMethod.GET)
    private String newgradesubjectManage() {
        return "newgradesubject";
    }

    @RequestMapping(value = "/gradesubjectupdate", method = RequestMethod.GET)
    private String gradesubjectupdateManage() {
        return "gradesubjectupdate";
    }

    @RequestMapping(value = "/newgradesubjectupdate", method = RequestMethod.GET)
    private String newgradesubjectupdateManage() {
        return "newgradesubjectupdate";
    }

    @RequestMapping(value = "/reachdescupdate", method = RequestMethod.GET)
    private String reachdescupdateManage() {
        return "reachdescupdate";
    }

    @RequestMapping(value = "/testlist", method = RequestMethod.GET)
    private String testManage() {
        return "testlist";
    }

    @RequestMapping(value = "/testinput", method = RequestMethod.GET)
    private String testinput() {
        return "testinput";
    }

    @RequestMapping(value = "/personInfolist", method = RequestMethod.GET)
    private String personList() {
        return "personInfolist";
    }

    @RequestMapping(value = "/personregister", method = RequestMethod.GET)
    private String personregister() {
        return "personregister";
    }

    @RequestMapping(value = "/newreachpointmanagement", method = RequestMethod.GET)
    private String newreachpointmanagement() {
        return "newreachpointmanagement";
    }

    @RequestMapping(value = "/newreachpointupdate", method = RequestMethod.GET)
    private String newreachpointupdate() {
        return "newreachpointupdate";
    }

    @RequestMapping(value = "/newanalysis", method = RequestMethod.GET)
    private String newanalysis() {
        return "newanalysis";
    }

    @RequestMapping(value = "/newanalysisupdate", method = RequestMethod.GET)
    private String newanalysisupdate() {
        return "newanalysisupdate";
    }

    @RequestMapping(value = "/newstudentmanagement", method = RequestMethod.GET)
    private String newstudentmanagement() {
        return "newstudentmanagement";
    }

    @RequestMapping(value = "/newstudentupdate", method = RequestMethod.GET)
    private String newstudentupdate() {
        return "newstudentupdate";
    }

    @RequestMapping(value = "/importexcel", method = RequestMethod.GET)
    private String importexcel() {
        return "importexcel";
    }
	/*@RequestMapping(value = "/getpersonmanagementinfo",method=RequestMethod.GET)
	private String personmanagementinfo() {
		return "personInfolist";
	}*/
}
