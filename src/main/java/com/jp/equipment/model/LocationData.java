package com.jp.equipment.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.jp.equipment.service.impl.SendKafkaServiceImpl;
import com.jp.mq.model.BaseEvent;



/**
 * 用于向location服务 发送消息
 */
public class LocationData  extends BaseEvent implements Serializable{
	private static final Logger log = LoggerFactory.getLogger(LocationData.class);
	private static BigDecimal bigDecimal = new BigDecimal(100);


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("设备标示")
    private String gpsId;

    @ApiModelProperty("350m手台所处的纬度")
    private BigDecimal latitude;

    @ApiModelProperty("350m手台所处的经度")
    private BigDecimal longitude;

    @ApiModelProperty("350m手台所处位置的时间节点")
    private String time;

    @ApiModelProperty("数据类型")
    private String gpsType;
    @ApiModelProperty("厂家来源")
    private String manufacturer;

   

    public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public LocationData() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    /**
     * {
  "radioCode": "71821451",
  "radioNS": "3723.2475",
  "radioTime": "161229.487",
  "radionEW": "12158.3416"
	}
     * @param radioMsg
     */
   public LocationData(RadioMsg radioMsg,String manufacturer) {
    	super();
    	this.gpsId=radioMsg.getGpsid();
    	this.gpsType="radio";
    	String lat = radioMsg.getLat()+"";
    	String lon = radioMsg.getLon()+"";
    	
    	if(!StringUtils.isEmpty(lon)) {
    		this.longitude=new BigDecimal(lon);
    	}
    	if(!StringUtils.isEmpty(lat)) {
    		this.latitude=new BigDecimal(lat);
    	}

    	String radioTime = radioMsg.getTime();
    	Date finalDate=null;
    	if(StringUtils.isEmpty(radioTime)) {
    		finalDate=new Date();
    	}else {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    		try {
    		 finalDate = sdf.parse(radioTime);
    		} catch (Exception e) {
				log.error("时间解析出错，解析前的时间格式：{}",radioTime);
				finalDate=new Date();
			}	
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
		this.time=sdf1.format(finalDate);	
		this.manufacturer=manufacturer;
	}
   }
	

    public String getGpsId() {
        return this.gpsId;
    }

    public void setGpsId(String gpsId) {
        this.gpsId = gpsId;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGpsType() {
        return gpsType;
    }

    public void setGpsType(String gpsType) {
        this.gpsType = gpsType;
    }
}