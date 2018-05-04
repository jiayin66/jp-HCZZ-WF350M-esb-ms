package com.jp.hczz.350m.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 学生实体
 * @author shuibaoqin
 *
 */
@Entity
@Table(name="tb_student")
@ApiModel("学生信息")
public class Student {
	
	@Id
	@Column(name="STU_ID")
	@GeneratedValue(generator="sys-uuid")
	@GenericGenerator(name="sys-uuid",strategy="uuid2")
	@Null(message="不支持为学生设置id")
	@ApiModelProperty("学生信息id")
	private String id;
	
	@Column(name="STU_NAME")
	@ApiModelProperty("学生名称")
	@Size(max=100,message="name长度错误，最短：0，最长：100")
	private String name;
	
	@Column(name="STU_SEX")
	@ApiModelProperty("学生性别")
	@Size(max=3,message="name长度错误，最短：0，最长：3")
	private String sex;
	
	@Column(name="STU_SCORE")
	@ApiModelProperty("学生分数")
	@Min(value=0,message="score不能低于0")
	private Long score;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
}
