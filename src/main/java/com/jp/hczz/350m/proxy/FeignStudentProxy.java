package com.jp.hczz.350m.proxy;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jp.hczz.350m.entity.Student;
import com.jp.hczz.350m.proxy.fallback.FeignStudentFallback;

/**
 * Feign远程调用，调用jp-birm-student-ms微服务
 * @author shuibaoqin
 *
 */
@FeignClient(value="jp-birm-student-ms",path="/student",fallback=FeignStudentFallback.class)
public interface FeignStudentProxy {
	
	/**
	 * 获取一个学生id，远程调用
	 */
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable String id);
}
