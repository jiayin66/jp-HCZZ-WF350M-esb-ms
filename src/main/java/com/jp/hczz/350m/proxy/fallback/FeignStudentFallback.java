package com.jp.hczz.350m.proxy.fallback;

import org.springframework.stereotype.Component;

import com.jp.hczz.350m.proxy.FeignStudentProxy;
import com.jp.hczz.350m.entity.Student;

@Component
public class FeignStudentFallback implements FeignStudentProxy{
	
	@Override
	public Student getStudentById(String id){
		//打印日志
		return null;
	}
}
