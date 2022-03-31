package com.example.studentauth.service.impl;

import com.example.studentauth.dao.PersonInfoDao;
import com.example.studentauth.dto.PersonInfoExecution;
import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.enums.PersonInfoStateEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.PersonInfoOperationException;
import com.example.studentauth.service.PersonInfoService;
import com.example.studentauth.util.PageCalcuator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo login(PersonInfo vo) throws Exception {
        PersonInfo personInfo = new PersonInfo();
        return personInfoDao.findLogin(vo);
    }

    @Override
    public PersonInfoExecution queryPersonInfoList(int pageIndex, int pageSize) {
        int rowIndex = PageCalcuator.calculateRowIndex(pageIndex, pageSize);
        List<PersonInfo> personInfoList = personInfoDao.queryPersonInfoList(rowIndex, pageSize);
        int count = personInfoDao.queryPersonCount();
        PersonInfoExecution se = new PersonInfoExecution();
        if (personInfoList != null) {
            se.setPersonInfoList(personInfoList);
            se.setCount(count);
        } else {
            se.setState(PersonInfoStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public PersonInfoExecution insertPerson(PersonInfo personInfo) throws PersonInfoOperationException {
        if (personInfo == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY_LIST);
        }
        try {
            personInfo.setCreateTime(new Date());
            personInfo.setLastEditTime(new Date());
            int effectedNum = personInfoDao.insertPersonInfo(personInfo);
            if (effectedNum < 0) {
                throw new ExcelOperationException("用户创建失败");
            }
        } catch (Exception e) {
            throw new ExcelOperationException("addPersonINfo ERROR:" + e.getMessage());
        }
        return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
    }

    @Override
    public PersonInfoExecution deletePerson(long userId) throws PersonInfoOperationException {
        try {
            int effectedNum = personInfoDao.deletePersonInfo(userId);
            if (effectedNum <= 0) {
                throw new PersonInfoOperationException("用户信息删除失败");
            } else {
                return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new PersonInfoOperationException("deletePersonInfo error:" + e.getMessage());
        }
    }

    @Override
    public PersonInfo getPersonId(long userId) throws PersonInfoOperationException {
        return personInfoDao.queryByPersonInfoId(userId);
    }

    @Override
    public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) throws PersonInfoOperationException {
        if (personInfo == null || personInfo.getUserId() == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = personInfoDao.updatePersonInfo(personInfo);
                if (effectedNum < 0) {
                    return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
                } else {
                    personInfo = personInfoDao.queryByPersonInfoId(personInfo.getUserId());
                    return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
                }
            } catch (Exception e) {
                throw new PersonInfoOperationException("modifyPersonInfo error" + e.getMessage());
            }
        }
    }

}
