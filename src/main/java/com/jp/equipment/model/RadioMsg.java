package com.jp.equipment.model;

import java.util.Arrays;
/**
 *
 * GPGLL 协议
（地理定位信息）
例：$GPGLL,4250.5589,S,14718.5084,E,092204.999,A*2D
字段0：$GPGLL，语句ID，表明该语句为Geographic Position（GLL）地理定位信息
字段1：纬度ddmm.mmmm，度分格式（前导位数不足则补0）
字段2：纬度N（北纬）或S（南纬）
字段3：经度dddmm.mmmm，度分格式（前导位数不足则补0）
字段4：经度E（东经）或W（西经）
字段5：UTC时间，hhmmss.sss格式
字段6：状态，A=定位，V=未定位
字段7：校验值（$与*之间的数异或后的值）
 * @author fengwei
 *String gpsId,double longitudes,double latitudes,int dspeed,int  dir,String time
 */

//{"gpsid":"72021316","lon":114.28513333333333,"lat":30.4334,"speed":0, "alt":0, "acc":0,"
//dir":0,"date":"2016-12-31 11:36:09"}
public class RadioMsg {
	//编号
	String gpsid;
	//经度
	double lon;
	//纬度
	double lat;
	//速度
	short speed;
	//方向
	short  alt;
	//高程
	short  acc;
	//精度
	short  dir;
	//时间
	String time;
	//时间
	String beiYong;
	
	
	
	public RadioMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public String getGpsid() {
		return gpsid;
	}
	public RadioMsg(String gpsid, double lon, double lat, short speed, short alt, short acc,
			short dir, String time, String beiYong) {
		super();
		this.gpsid = gpsid;
		this.lon = lon;
		this.lat = lat;
		this.speed = speed;
		this.alt = alt;
		this.acc = acc;
		this.dir = dir;
		this.time = time;
		this.beiYong = beiYong;
	}

	public void setGpsid(String gpsid) {
		this.gpsid = gpsid;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public short getSpeed() {
		return speed;
	}
	public void setSpeed(short speed) {
		this.speed = speed;
	}
	public short getAlt() {
		return alt;
	}
	public void setAlt(short alt) {
		this.alt = alt;
	}
	public short getAcc() {
		return acc;
	}
	public void setAcc(short acc) {
		this.acc = acc;
	}
	public short getDir() {
		return dir;
	}
	public void setDir(short dir) {
		this.dir = dir;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBeiYong() {
		return beiYong;
	}
	public void setBeiYong(String beiYong) {
		this.beiYong = beiYong;
	}
	

	 

	 
 
	

}
