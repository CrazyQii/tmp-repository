package com.example.studentauth.service;

import com.example.studentauth.dto.StudentExecution;
import com.example.studentauth.entity.Student;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.ReachPointOperationException;
import com.example.studentauth.excptions.StudentOperationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    /**
     * 查询表单的评价信息
     *
     * @param excelId
     * @return
     */
    List<Student> queryStudentList(long userId);

    /**
     * 批量添加
     *
     * @param reachPointList
     * @return
     * @throws ReachPointOperationException
     */
    StudentExecution batchAddStudent(List<Student> studentList) throws ReachPointOperationException;

    /**
     * 删除
     *
     * @param pointId
     * @param excelId
     * @return
     * @throws ReachPointOperationException
     */
    StudentExecution deleteStudent(int studentId, long userId) throws ReachPointOperationException;

    /**
     * 导入学生信息
     *
     * @param userExcel
     * @param excelId
     * @param userId
     * @throws IOException
     * @throws InvalidFormatException
     */
    void importExcel(MultipartFile userExcel, int excelId, long userId) throws IOException, InvalidFormatException;

    /**
     * 通过pointId查询
     *
     * @param reachPoint
     * @return
     * @throws ExcelOperationException
     */
    Student getStudent(int exeStudentId) throws StudentOperationException;

    /**
     * 修改学生信息系
     *
     * @param student
     * @return
     * @throws StudentOperationException
     */
    StudentExecution modifyStudent(Student student) throws StudentOperationException;

    /**
     * 查询
     */
    Student getStudentByExcelId(long excelId);

    /**
     * 通过excel查询
     *
     * @param excelId
     * @return
     */
    List<Student> getStudentListByExcel(long excelId);
}
