package com.jp.hczz.dsj350m.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 用于向location服务 发送消息
 */
public class TResLocationData {

    @ApiModelProperty("id")
    private String id;

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


    public TResLocationData() {
    }

    public TResLocationData(String msg) {
        String msgs[] = msg.split("@");
        setId(UUID.randomUUID().toString());
        this.gpsType = "wf350m";
        setGpsId(msgs[2]);
        setLongitude(new BigDecimal(msgs[4]));
        setLatitude(new BigDecimal(msgs[5]));
        String timeSt;
        if (msgs.length > 8) {
            timeSt = msgs[8];
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            timeSt = df.format(new Date());
        }
        String timeStr = timeSt.replaceAll(" ", "T");
        this.time = timeStr + ".0000000+08:00";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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