package com.example.studentauth.service;

import com.example.studentauth.dto.PersonInfoExecution;
import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.excptions.PersonInfoOperationException;

public interface PersonInfoService {
    public PersonInfo login(PersonInfo vo) throws Exception;

    PersonInfoExecution queryPersonInfoList(int pageIndex, int pageSize);

    PersonInfoExecution insertPerson(PersonInfo personInfo) throws PersonInfoOperationException;

    PersonInfoExecution deletePerson(long userId) throws PersonInfoOperationException;

    PersonInfo getPersonId(long userId) throws PersonInfoOperationException;

    PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) throws PersonInfoOperationException;
}
