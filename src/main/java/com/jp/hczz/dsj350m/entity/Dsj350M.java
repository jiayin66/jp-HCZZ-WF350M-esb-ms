package com.jp.hczz.dsj350m.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "ESB_350M_LOCATION_DATA")
@ApiModel("350m手台信息")
public class Dsj350M {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sys-uuid")
    @GenericGenerator(name = "sys-uuid", strategy = "uuid2")
    @Null(message = "不支持设置id")
    private String id;

    @Column(name = "LONGITUDE")
    @ApiModelProperty("经度")
    private String longitude;

    @Column(name = "LATITUDE")
    @ApiModelProperty("纬度")
    private String latitude;

    @Column(name = "EVENT")
    @ApiModelProperty("事件")
    private String event;

    @Column(name = "GPS_ID")
    @ApiModelProperty("设备id")
    private String gps_id;

    @Column(name = "STATUS")
    @ApiModelProperty("状态信息")
    private int status;

    @Column(name = "SPEED")
    @ApiModelProperty("速度")
    private String speed;

    @Column(name = "DIRECTION")
    @ApiModelProperty("方向")
    private String direction;

    @Column(name = "TIME")
    @ApiModelProperty("时间")
    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getGps_id() {
        return gps_id;
    }

    public void setGps_id(String gps_id) {
        this.gps_id = gps_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    //1@130@273473@0@119.039204120636@36.6573214530945@26@0000@2018-05-08 18:21:09
    public Dsj350M(String msg) {
        String msgs[] = msg.split("@");
        setEvent(msgs[1]);
        setGps_id(msgs[2]);
        setStatus(Integer.parseInt(msgs[3]));
        setLongitude(msgs[4]);
        setLatitude(msgs[5]);
        setSpeed(msgs[6]);

        String directionSt = msgs[7];
        switch (directionSt) {
            case "0000":
                directionSt = "北";
                break;
            case "0001":
                directionSt = "北偏东";
                break;
            case "0010":
                directionSt = "东北";
                break;
            case "0011":
                directionSt = "东偏北";
                break;
            case "0100":
                directionSt = "东";
                break;
            case "0101":
                directionSt = "东偏南";
                break;
            case "0110":
                directionSt = "东南";
                break;
            case "0111":
                directionSt = "南偏东";
                break;
            case "1000":
                directionSt = "南";
                break;
            case "1001":
                directionSt = "南偏西";
                break;
            case "1010":
                directionSt = "西南";
                break;
            case "1011":
                directionSt = "西偏南";
                break;
            case "1100":
                directionSt = "西";
                break;
            case "1101":
                directionSt = "西偏北";
                break;
            case "1110":
                directionSt = "西北";
                break;
            case "1111":
                directionSt = "未知";
                break;
            default:
                directionSt = "未知";
                break;
        }
        setDirection(directionSt);
        if (msgs.length > 8) {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
            try {
                setTime(df.parse(msgs[8]));
            } catch (ParseException ignored) {
                setTime(new Date());
            }
        } else {
            setTime(new Date());
        }
    }
}
