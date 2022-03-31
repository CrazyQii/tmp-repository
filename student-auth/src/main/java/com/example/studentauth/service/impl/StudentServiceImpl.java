package com.example.studentauth.service.impl;

import com.example.studentauth.dao.StudentDao;
import com.example.studentauth.dto.StudentExecution;
import com.example.studentauth.entity.ClassesGrade;
import com.example.studentauth.entity.Student;
import com.example.studentauth.enums.StudentStateEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.ReachPointOperationException;
import com.example.studentauth.excptions.StudentOperationException;
import com.example.studentauth.service.ClassGradeService;
import com.example.studentauth.service.StudentService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ClassGradeService classGradeService;

    @Override
    public List<Student> queryStudentList(long userId) {
        return studentDao.queryStudentList(userId);
    }

    @Override
    public List<Student> getStudentListByExcel(long excelId) {
        return studentDao.queryStudentListByExcel(excelId);
    }

    @Override
    @Transactional
    public StudentExecution batchAddStudent(List<Student> studentList) throws ReachPointOperationException {
        if (studentList != null && studentList.size() > 0) {
            try {
                int effectedNum = studentDao.batchInsertStudent(studentList);
                if (effectedNum <= 0) {
                    throw new StudentOperationException("学生成绩创建失败");
                } else {
                    return new StudentExecution(StudentStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new StudentOperationException("bathAddStudent error:" + e.getMessage());
            }
        } else {
            return new StudentExecution(StudentStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public StudentExecution deleteStudent(int studentId, long excelId) throws StudentOperationException {
        try {
            int effectedNum = studentDao.deleteStudent(studentId, excelId);
            if (effectedNum <= 0) {
                throw new StudentOperationException("学生成绩删除失败");
            } else {
                return new StudentExecution(StudentStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new StudentOperationException("deleteStudent error:" + e.getMessage());
        }
    }

    @Override
    public void importExcel(MultipartFile userExcel, int excelId, long userId) throws IOException, InvalidFormatException {

        //获取输入流
        InputStream inputStream = userExcel.getInputStream();
        //创建读取工作簿
        Workbook workbook = WorkbookFactory.create(inputStream);
        //获取工作表
        Sheet sheet = workbook.getSheetAt(0);
        //获取总行
        int rows = sheet.getPhysicalNumberOfRows();
        List<Student> list = new ArrayList<Student>();
        if (rows >= 2) {
            //获取单元格
            System.out.println("IIIIIIIIIIIIIIIII " + excelId);
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                Student student = new Student();
                try {
                    String id = row.getCell(1).getStringCellValue();
                    student.setStudentId(Integer.parseInt(id));
                } catch (IllegalStateException e) {
                    int id = (int) row.getCell(1).getNumericCellValue();
                    student.setStudentId(id);
                }
                student.setExcelId(excelId);
                String studentname = row.getCell(2).getStringCellValue();
                student.setStudentName(studentname);//Double.parseDouble(String)
                Double classGrade = row.getCell(3).getNumericCellValue();
                student.setClassGrade(classGrade);
                Double workGrade = row.getCell(4).getNumericCellValue();
                student.setWorkGrade(workGrade);
                Double expGrade = row.getCell(5).getNumericCellValue();
                student.setExpGrade(expGrade);
                Double lastGrade = row.getCell(6).getNumericCellValue();
                student.setLastGrade(lastGrade);
                Double AllGrade = row.getCell(7).getNumericCellValue();
                student.setAllGrade(AllGrade);
                student.setUserId(userId);
                //想数据库中添加新对象
                list.add(student);
                ClassesGrade classesGrade = new ClassesGrade();
                classesGrade = classGradeService.getCLassGradeByExcelId(excelId);
                double avgClassGrade = 0, avgWorkGrade = 0, avgLastGrade = 0, avgAllGrade = 0, avgExpGrade = 0;
                int count;
                if (classesGrade == null) {
                    classesGrade = new ClassesGrade();
                    classesGrade.setAvgClassGrade(student.getClassGrade());
                    classesGrade.setAvgWorkGrade(student.getWorkGrade());
                    classesGrade.setAvgLastGrade(student.getLastGrade());
                    classesGrade.setAvgExpGrade(student.getExpGrade());
                    classesGrade.setAvgAllGrade(student.getAllGrade());
                    classesGrade.setStudentCount(1);
                    classesGrade.setExcelId(student.getExcelId());
                    classesGrade.setUserId(userId);
                    classGradeService.addClassGrade(classesGrade);
                } else {
                    count = classesGrade.getStudentCount();
                    avgClassGrade = classesGrade.getAvgClassGrade() * count;
                    avgWorkGrade = classesGrade.getAvgWorkGrade() * count;
                    avgLastGrade = classesGrade.getAvgLastGrade() * count;
                    avgAllGrade = classesGrade.getAvgAllGrade() * count;
                    avgExpGrade = classesGrade.getAvgExpGrade() * count;
                    count++;
                    avgClassGrade += student.getClassGrade();
                    avgWorkGrade += student.getWorkGrade();
                    avgLastGrade += student.getLastGrade();
                    avgAllGrade += student.getExpGrade();
                    avgExpGrade += student.getAllGrade();
                    classesGrade.setAvgClassGrade(avgClassGrade / count);
                    classesGrade.setAvgWorkGrade(avgWorkGrade / count);
                    classesGrade.setAvgLastGrade(avgLastGrade / count);
                    classesGrade.setAvgExpGrade(avgExpGrade / count);
                    classesGrade.setAvgAllGrade(avgAllGrade / count);
                    classesGrade.setStudentCount(count);
                    classGradeService.modifyClassGrade(classesGrade);
                }
            }
            studentDao.batchInsertStudent(list);
        }/*else {
        	return new StudentExecution(StudentStateEnum.EMPTY_LIST);
        }*/
        inputStream.close();
    }

    @Override
    public Student getStudent(int exeStudentId) throws StudentOperationException {
        return studentDao.queryByexestudentId(exeStudentId);
    }

    @Override
    public Student getStudentByExcelId(long excelId) {
        return studentDao.queryStudentByExcelId(excelId);
    }

    @Override
    public StudentExecution modifyStudent(Student student) throws StudentOperationException {
        if (student == null || student.getExeStudentId() == null) {
            return new StudentExecution(StudentStateEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = studentDao.updateStudent(student);
                if (effectedNum <= 0) {
                    return new StudentExecution(StudentStateEnum.INNER_ERROR);
                } else {
                    student = studentDao.queryByexestudentId(student.getExeStudentId());
                    return new StudentExecution(StudentStateEnum.SUCCESS, student);
                }
            } catch (Exception e) {
                throw new ExcelOperationException("modifyStudent error" + e.getMessage());
            }
        }
    }

}

