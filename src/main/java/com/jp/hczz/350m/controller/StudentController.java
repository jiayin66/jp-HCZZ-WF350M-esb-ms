package com.jp.hczz.350m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jp.common.model.JPPageImpl;
import com.jp.common.model.Paging;

import com.jp.hczz.350m.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.jp.hczz.350m.service.StudentService;

@RestController
@RequestMapping("/students")
@Api("学生接口")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * 向数据库中保存一条学生数据
	 * @param student
	 * @return
	 */
	@ApiOperation("向数据库中保存一条学生数据")
	@PostMapping("/")
	public Student saveStudent(@Validated @RequestBody Student student){
		return studentService.saveStudent(student);
	}
	
	@ApiOperation("删除数据库中指定id对应的学生数据")
	@DeleteMapping("/{id}")
	public void deleteStudent(
			@ApiParam("删除数据库中指定id对应的学生数据") @PathVariable String id){
		studentService.deleteStudent(id);
	}
	
	@ApiOperation("更新数据库指定id对应的学生数据")
	@PutMapping("/{id}")
	public Student updateStudent(
			@ApiParam("学生id") @PathVariable String id,
			@ApiParam("更新学生数据") @Validated @RequestBody Student student){
		return studentService.updateStudent(id, student);
	}
	
	@ApiOperation("根据id查找对应的学生数据")
	@GetMapping("/{id}")
	public Student getStudentById(
			@ApiParam("学生id") @PathVariable String id){
		return studentService.getStudentById(id);
	}
	
	@ApiOperation("分页查找学生数据")
	@GetMapping("/")
	public JPPageImpl<Student> fetchStudentPage(Paging paging){
		return new JPPageImpl<>(studentService.fetchStudentPage(paging));
	}
}
