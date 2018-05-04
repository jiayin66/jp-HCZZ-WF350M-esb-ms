package com.jp.hczz.350m.test.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.hczz.350m.ApplicationStarter;
import com.jp.hczz.350m.entity.Student;
import com.jp.hczz.350m.proxy.FeignStudentProxy;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApplicationStarter.class)
public class FeignStudentProxyTest extends TestCase{
	
	@Autowired
	private FeignStudentProxy stuProxy;
	
	@Test
	public void testGetStudentById(){
		String id="abcdefg";
		Student student=stuProxy.getStudentById(id);
		assertNotNull(student);
	}
}