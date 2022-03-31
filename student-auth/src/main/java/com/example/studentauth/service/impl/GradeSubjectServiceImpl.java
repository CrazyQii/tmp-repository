package com.example.studentauth.service.impl;

import com.example.studentauth.dao.GradeSubjectDao;
import com.example.studentauth.dto.GradeSubjectExecution;
import com.example.studentauth.entity.GradeSubject;
import com.example.studentauth.enums.GradeSubjectEnum;
import com.example.studentauth.excptions.GradeSubjectOperationException;
import com.example.studentauth.service.GradeSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeSubjectServiceImpl implements GradeSubjectService {
    @Autowired
    private GradeSubjectDao gradeSubjectDao;

    @Override
    public List<GradeSubject> getGradeSubjectList(long userId) {
        return gradeSubjectDao.queryGradeSubjectList(userId);
    }

    @Override
    public List<GradeSubject> getGradeSubjectListByExcel(long excelId) {
        return gradeSubjectDao.queryGradeSubjectListByExcel(excelId);
    }

    @Override
    public GradeSubjectExecution deleteGradeSubjectById(Integer subjectId, long userId) {
        try {
            int effectedNum = gradeSubjectDao.deleteGradeSubject(subjectId, userId);
            if (effectedNum <= 0) {
                throw new GradeSubjectOperationException("命题与课程目标对应表信息删除失败");
            } else {
                return new GradeSubjectExecution(GradeSubjectEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new GradeSubjectOperationException("deleteGradeSubject error:" + e.getMessage());
        }
    }

    @Override
    public GradeSubjectExecution modifyGradeSubject(GradeSubject gradeSubject) {
        if (gradeSubject == null || gradeSubject.getSubjectId() == null) {
            return new GradeSubjectExecution(GradeSubjectEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = gradeSubjectDao.updateGradeSubject(gradeSubject);
                if (effectedNum <= 0) {
                    return new GradeSubjectExecution(GradeSubjectEnum.INNER_ERROR);
                } else {
                    gradeSubject = gradeSubjectDao.queryBySubjectId(gradeSubject.getSubjectId());
                    return new GradeSubjectExecution(GradeSubjectEnum.SUCCESS, gradeSubject);
                }
            } catch (Exception e) {
                throw new GradeSubjectOperationException("modifyGradeSubject error" + e.getMessage());
            }
        }
    }

    @Override
    public GradeSubject getBySubjectId(int gradeSubjectId) {
        return gradeSubjectDao.queryBySubjectId(gradeSubjectId);
    }

    @Override
    public GradeSubjectExecution InsertGradeSubject(GradeSubject gradeSubject) {
        if (gradeSubject != null) {
            try {
                int effectedNum = gradeSubjectDao.batchInsertGradeSubject(gradeSubject);
                if (effectedNum <= 0) {
                    throw new GradeSubjectOperationException("命题与课程目标对应表创建失败");
                } else {
                    return new GradeSubjectExecution(GradeSubjectEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new GradeSubjectOperationException("bathAddGradeSubject error:" + e.getMessage());
            }
        } else {
            return new GradeSubjectExecution(GradeSubjectEnum.EMPTY_LIST);
        }
    }


}
