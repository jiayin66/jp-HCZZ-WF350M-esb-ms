package com.jp.hczz.350m.service;

import org.springframework.data.domain.Page;
import com.jp.common.model.Paging;

import com.jp.hczz.350m.entity.Student;

public interface StudentService {
	
	/**
	 * 保存一条学生记录
	 * @param student
	 * @return
	 */
	Student saveStudent(Student student);
	/**
	 * 根据id删除一条学生记录
	 * @param id
	 */
	void deleteStudent(String id);
	/**
	 * 根据id修改对应的学生数据
	 * @param id
	 * @param student
	 * @return
	 */
	Student updateStudent(String id,Student student);
	/**
	 * 根据id查找对应的学生数据
	 * @param id
	 * @return
	 */
	Student getStudentById(String id);
	/**
	 * 分页查询满足条件的
	 * @param paging
	 * @return
	 */
	Page<Student> fetchStudentPage(Paging paging);
}
