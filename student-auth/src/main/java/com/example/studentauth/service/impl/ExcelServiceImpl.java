package com.example.studentauth.service.impl;

import com.example.studentauth.dao.ExcelDao;
import com.example.studentauth.dto.ExcelExecution;
import com.example.studentauth.entity.Excel;
import com.example.studentauth.enums.ExcelStateEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.service.ExcelService;
import com.example.studentauth.util.PageCalcuator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelDao excelDao;

    @Override
    @Transactional
    public ExcelExecution addExcel(Excel excel) throws ExcelOperationException {
        //空值判断
        if (excel == null) {
            return new ExcelExecution(ExcelStateEnum.NULL_SHOP);
        }
        try {
            //给表信息赋值初始值
            excel.setCreateTime(new Date());
            excel.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = excelDao.insertExcel(excel);
            if (effectedNum <= 0) {
                throw new ExcelOperationException("数据表创建失败");
            } else {
                //添加图片时用到的地方没图片就pass
            }
        } catch (Exception e) {
            throw new ExcelOperationException("addExcel ERROR:" + e.getMessage());
        }
		/*if(excel.getArea()==null) {
			return new ExcelExecution(ExcelStateEnum.NULL_AREA);
		}
		if(excel.getOwner()==null) {
			return new ExcelExecution(ExcelStateEnum.NULL_Person);
		}*/
        return new ExcelExecution(ExcelStateEnum.CHECK, excel);
    }

    @Override
    public Excel getByExcelId(long excelId) {
        return excelDao.queryByExcelId(excelId);
    }

    @Override
    public ExcelExecution modifyExcel(Excel excel) throws ExcelOperationException {
        if (excel == null || excel.getExcelId() == null) {
            return new ExcelExecution(ExcelStateEnum.NULL_SHOP);
        } else {
            try {
                excel.setLastEditTime(new Date());
                int effectedNum = excelDao.updateExcel(excel);
                if (effectedNum <= 0) {
                    return new ExcelExecution(ExcelStateEnum.INNER_ERROR);
                } else {
                    excel = excelDao.queryByExcelId(excel.getExcelId());
                    return new ExcelExecution(ExcelStateEnum.SUCCESS, excel);
                }
            } catch (Exception e) {
                throw new ExcelOperationException("modifyExcel error" + e.getMessage());
            }
        }
    }

    @Override
    public ExcelExecution getExcelList(Excel excelCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalcuator.calculateRowIndex(pageIndex, pageSize);
        List<Excel> excelList = excelDao.queryExcelList(excelCondition, rowIndex, pageSize);
        int count = excelDao.queryExcelCount(excelCondition);
        ExcelExecution se = new ExcelExecution();
        if (excelList != null) {
            se.setExcelList(excelList);
            se.setCount(count);
        } else {
            se.setState(ExcelStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public List<Excel> getExcelListByuser(long userId) {
        List<Excel> excelList = excelDao.queryExcelListByUser(userId);
        return excelList;
    }

    @Override
    public void delExcelById(long excelId) {
        Excel excel = excelDao.queryByExcelId(excelId);
        if (excel != null) {
            excelDao.deleteExcel(excel);
        }
    }

}
