package com.example.studentauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentauth.dto.ReachDescExecution;
import com.example.studentauth.entity.Excel;
import com.example.studentauth.entity.ReachDesc;
import com.example.studentauth.enums.ReachDescEnum;
import com.example.studentauth.excptions.ReachDescOperationException;
import com.example.studentauth.service.ReachDescService;
import com.example.studentauth.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/exceladmin")
public class ReachDescController {
    @Autowired
    private ReachDescService reachDescService;

    @RequestMapping(value = "/getreachdesc", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getReachDescById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        ReachDesc list = null;
        if (currentExcel != null && currentExcel.getExcelId() > 0) {
            try {
                list = reachDescService.getReachDescList(currentExcel.getExcelId());
                modelMap.put("reachDesc", list);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty reachdescId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addreachdesc", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addReachDesc(@RequestBody ReachDesc reachDesc, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Excel currentExcel = (Excel) request.getSession().getAttribute("currentExcel");
        reachDesc.setExcelId(currentExcel.getExcelId());
        if (reachDesc != null) {
            try {
                ReachDescExecution pe = reachDescService.InsertReachDesc(reachDesc);
                if (pe.getState() == ReachDescEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ReachDescOperationException e) {
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

    @RequestMapping(value = "/modifyreachdesc", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyReachDesc(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        ReachDesc reachDesc = null;
        String reachDescStr = HttpServletRequestUtil.getString(request, "reachDescStr");
        try {
            reachDesc = mapper.readValue(reachDescStr, ReachDesc.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (reachDesc != null && reachDesc.getWordId() > 0) {
            ReachDescExecution se = reachDescService.modifyReachDesc(reachDesc);
            try {
                if (se.getState() == ReachDescEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ReachDescOperationException e) {
                // TODO: handle exception
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "课程的持续改进信息");
            return modelMap;
        }
    }
}
