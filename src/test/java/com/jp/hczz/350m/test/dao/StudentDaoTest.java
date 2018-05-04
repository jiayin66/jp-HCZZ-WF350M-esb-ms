package com.jp.hczz.350m.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.hczz.350m.ApplicationStarter;
import com.jp.hczz.350m.entity.Student;
import com.jp.hczz.350m.dao.StudentDao;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApplicationStarter.class)
public class StudentDaoTest extends TestCase{
	
	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void testFindStudent(){
		String id="123";
		Student stu=studentDao.findOne(id);
		assertNotNull(stu);
	}
}