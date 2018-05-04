package com.jp.hczz.350m.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jp.common.exception.JPExObjectNotFoundException;
import com.jp.common.model.Paging;
import com.jp.common.utils.page.PagingUtils;

import com.jp.hczz.350m.dao.StudentDao;
import com.jp.hczz.350m.entity.Student;
import com.jp.hczz.350m.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentDao studentDao;
	
	@Override
	public Student saveStudent(Student student) {
		return studentDao.save(student);
	}

	@Override
	public void deleteStudent(String id) {
		if(studentDao.exists(id)){
			studentDao.delete(id);
		}else{
			throw new JPExObjectNotFoundException("未找到指定的学生信息");
		}
	}

	@Override
	public Student updateStudent(String id, Student student) {
		if(studentDao.exists(id)){
			student.setId(id);
			return studentDao.save(student);
		}else{
			throw new JPExObjectNotFoundException("未找到指定的学生信息");
		}
	}

	@Override
	public Student getStudentById(String id) {
		Student student=studentDao.findOne(id);
		if(student!=null){
			return student;
		}else{
			throw new JPExObjectNotFoundException("未找到指定的学生信息");
		}
	}

	@Override
	public Page<Student> fetchStudentPage(Paging paging) {
		return studentDao.findAll(PagingUtils.getPageable(paging));
	}
	
}