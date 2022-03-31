package com.example.studentauth.service.impl;

import com.example.studentauth.dao.ClassGradeDao;
import com.example.studentauth.dto.ClassGradeExecution;
import com.example.studentauth.entity.ClassesGrade;
import com.example.studentauth.enums.StudentStateEnum;
import com.example.studentauth.excptions.ClassGradeOperationException;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.service.ClassGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassGradeServiceImpl implements ClassGradeService {
    @Autowired
    private ClassGradeDao classGradeDao;

    @Override
    public ClassGradeExecution addClassGrade(ClassesGrade classesGrade) throws ClassGradeOperationException {
        //空值判断
        if (classesGrade == null) {
            return new ClassGradeExecution(StudentStateEnum.INNER_ERROR);
        }
        try {
            //添加店铺信息
            int effectedNum = classGradeDao.insertClassesGrade(classesGrade);
            if (effectedNum <= 0) {
                throw new ExcelOperationException("数据表创建失败");
            } else {
                //添加图片时用到的地方没图片就pass
            }
        } catch (Exception e) {
            throw new ExcelOperationException("addExcel ERROR:" + e.getMessage());
        }
        return new ClassGradeExecution(StudentStateEnum.SUCCESS, classesGrade);
    }

    @Override
    public ClassesGrade getCLassGradeByExcelId(long excelId) {
        return classGradeDao.queryClassesGrade(excelId);
    }

    @Override
    public ClassGradeExecution modifyClassGrade(ClassesGrade classesGrade) throws ClassGradeOperationException {
        if (classesGrade == null || classesGrade.getExcelId() == null) {
            return new ClassGradeExecution(StudentStateEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = classGradeDao.updateClassGrade(classesGrade);
                if (effectedNum <= 0) {
                    return new ClassGradeExecution(StudentStateEnum.INNER_ERROR);
                } else {
                    classesGrade = classGradeDao.queryClassesGrade(classesGrade.getExcelId());
                    return new ClassGradeExecution(StudentStateEnum.SUCCESS, classesGrade);
                }
            } catch (Exception e) {
                // TODO: handle exception
                throw new ExcelOperationException("modifyClassGrade error" + e.getMessage());
            }
        }
    }

    @Override
    public ClassGradeExecution deleteClassGradeById(Integer gradeId, long excelId) {
        try {
            int effectedNum = classGradeDao.deleteClassGrade(gradeId, excelId);
            if (effectedNum <= 0) {
                throw new ClassGradeOperationException("学生信息删除失败");
            } else {
                return new ClassGradeExecution(StudentStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ExcelOperationException("deletestudentlist error:" + e.getMessage());
        }
    }

}
