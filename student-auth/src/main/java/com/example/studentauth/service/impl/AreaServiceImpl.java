package com.example.studentauth.service.impl;

import com.example.studentauth.dao.AreaDao;
import com.example.studentauth.entity.Area;
import com.example.studentauth.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        // TODO Auto-generated method stub
        return areaDao.queryArea();
    }

}
