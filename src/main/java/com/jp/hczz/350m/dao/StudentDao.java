package com.jp.hczz.350m.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.hczz.350m.entity.Student;

public interface StudentDao extends JpaRepository<Student,String>{
}
