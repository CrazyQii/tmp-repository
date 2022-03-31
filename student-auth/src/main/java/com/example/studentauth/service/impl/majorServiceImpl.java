package com.example.studentauth.service.impl;

import com.example.studentauth.dao.ExcelMajorDao;
import com.example.studentauth.entity.major;
import com.example.studentauth.service.majorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class majorServiceImpl implements majorService {
    @Autowired
    private ExcelMajorDao excelMajorDao;

    @Override
    public List<major> getMajorList() {
        // TODO Auto-generated method stub
        return excelMajorDao.queryExcelMajor();
    }
}
