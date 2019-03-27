package com.jp.hczz.dsj350m.wsx350m;

import com.jp.common.utils.serialize.SerializeUtil;
import com.jp.hczz.dsj350m.wsx350m.io.LocationOutput;
import com.jp.hczz.dsj350m.wsx350m.model.LocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@EnableBinding(LocationOutput.class)
public class UDPClient {
    private Logger logger = LoggerFactory.getLogger(UDPClient.class);
    @Autowired
    private LocationOutput locationOutput;

    @Value("${portNumber}")
    private int portNumber;//端口号

    @Value("${byteArray}")
    private int byteArray;//读取字节数

    public void UDPClient() {
        DatagramSocket datagramSocket = null;
        try {
            //监视40000端口的内容
            datagramSocket = new DatagramSocket(portNumber);
            logger.info("客户端已启动");
            byte[] buf = new byte[byteArray];
            while (true) {
                //定义接收数据的数据包
                DatagramPacket datagramPacket = new DatagramPacket(buf, 0, buf.length);
                datagramSocket.receive(datagramPacket);
                byte[] bytes = datagramPacket.getData();
                //logger.info("接收信息为遍历数组为："+Arrays.toString(bytes));
                int[] ints = new int[bytes.length];
                for (int i =0;i<bytes.length-1;i++){
                    ints[i] = bytes[i]&0xff;  //按位与，将sign类型转化为int的数字。因为Java中没有
                }
                //logger.info("接收信息转换为int数组为："+Arrays.toString(ints));

                String gpsId = gpsIdInfo(ints);
                BigDecimal longitude = BigDecimal.valueOf(longitudeInfo(bytes));
                BigDecimal latitude = BigDecimal.valueOf(latitudeInfo(bytes));
                Double speed = Double.valueOf(speedInfo(ints));
                int direction = dirInfo(ints);
                long time = timeInfo(bytes);
                //从接收数据包取出数据
                //String data=new String(datagramPacket.getData() , 0 ,datagramPacket.getLength());
                logger.info("接收到消息信息转换为int数组为："+Arrays.toString(ints));
                String gpsType = "radio";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
                String date = simpleDateFormat.format(time);
                String manufacturer = "WSX";
                List<LocationInfo> locationInfos = new ArrayList<>();
                LocationInfo locationInfo = new LocationInfo(gpsId,longitude,latitude, date,gpsType, speed,direction, manufacturer);
                locationInfos.add(locationInfo);
                String message = SerializeUtil.toJson(locationInfos);
                sendMessageInfo(message);
            }
        } catch (SocketException e) {
            logger.error("监听端口异常");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("读写异常");
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }

    private void sendMessageInfo(String message) {
        logger.debug("推送手台点位数据：topcs:{},message:{}", LocationOutput.EXCHANGE_NAME,message);
        Message<String> msg= MessageBuilder.withPayload(message).build();
        boolean  result= locationOutput.outputChannel().send(msg);
        logger.debug("推送手台点位数据是否成功:{}",result);
    }

    public String gpsIdInfo(int[] ints) {
        int[] gpsId = new int[6];
        System.arraycopy(ints, 4, gpsId, 0, 6);
        String getGpsId = gpsid(gpsId);

        return getGpsId;
    }
    public int speedInfo(int[] ints){
        //速度
        int[] speed = new int[1];
        System.arraycopy(ints, 26, speed, 0, 1);
        int getSpeed = speed[0];
        return getSpeed;
    }

    public int dirInfo(int[] ints){
        //方向
        int[] dir = new int[1];
        System.arraycopy(ints, 27, dir, 0, 1);
        int getDir = dir[0];
        return  getDir;
    }


    public Double longitudeInfo(byte[] data) {
        //经度
        byte[] lon = new byte[8];
        System.arraycopy(data, 10, lon, 0, 8);
        double getLongitude = bytes2Double(lon);
        return getLongitude;
    }

    public Double latitudeInfo(byte[] data){
        //纬度
        byte[] lat = new byte[8];
        System.arraycopy(data, 18, lat, 0, 8);
        double getLatitude = bytes2Double(lat);
        return getLatitude;
    }

    public long timeInfo(byte[] data){
        //时间
        byte[] time = new byte[8];
        System.arraycopy(data, 36, time, 0, 8);
        double dTime = bytes2Double(time);
        long ltime = (long) ((dTime - 25570) * 1000 * 3600 * 24 + 57600000);
        return ltime;
    }

    private String gpsid(int[] gpsId) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<gpsId.length;i++){
            if (gpsId[i] !=0 ) {
                String num = null;
                if (gpsId[i] >= 10) {
                    num = "" + gpsId[i];
                } else if (gpsId[i] < 10) {
                    num = "0" + gpsId[i];
                }
                stringBuffer.append(num);
            }
        }
        return String.valueOf(stringBuffer);
    }

    public double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }
}
