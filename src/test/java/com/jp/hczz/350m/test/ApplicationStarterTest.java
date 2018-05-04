package com.jp.hczz.350m.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.hczz.350m.ApplicationStarter;
import com.jp.hczz.350m.entity.Student;
import com.jp.hczz.350m.service.StudentService;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApplicationStarter.class)
public class ApplicationStarterTest extends TestCase{
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * 测试业务层：根据id查找指定学生
	 */
	@Test
	public void testGet(){
		String testId="testStu1";
		Student student=studentService.getStudentById(testId);
		assertNotNull(student);
	}
}
