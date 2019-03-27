package com.jp.hczz.dsj350m.wsx350m;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

@Component
public class UDPClient {
    private Logger logger = LoggerFactory.getLogger(UDPClient.class);

    //public static void main(String[] args) throws IOException {
    public void UDPClient() {
        DatagramSocket datagramSocket = null;
        try {
            //监视40000端口的内容
            datagramSocket = new DatagramSocket(40000);
            logger.info("客户端已启动");
            byte[] buf = new byte[56];
            while (true) {
                //定义接收数据的数据包
                DatagramPacket datagramPacket = new DatagramPacket(buf, 0, buf.length);
                datagramSocket.receive(datagramPacket);
                byte[] bytes = datagramPacket.getData();

                logger.info("接收信息为遍历数组为："+Arrays.toString(bytes));
                int[] ints = new int[bytes.length];
                for (int i =0;i<bytes.length-1;i++){
                    ints[i] = bytes[i]&0xff;  //按位与，将sign类型转化为int的数字。因为Java中没有
                }
                logger.info("接收信息转换为int数组为："+Arrays.toString(ints));

                String getData1 = transcoding(bytes);
                String getData2 = transcodingInt(ints);
                //从接收数据包取出数据
                String getData = getData1+getData2;
                //String data=new String(datagramPacket.getData() , 0 ,datagramPacket.getLength());
                logger.info("接收到消息：" + getData);
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

    private String transcodingInt(int[] ints) {
        int[] gpsId = new int[6];
        System.arraycopy(ints, 4, gpsId, 0, 6);
        String getGpsId = gpsid(gpsId);
        logger.info("接收到设备号消息：" + getGpsId);
        //速度
        int[] speed = new int[1];
        System.arraycopy(ints, 26, speed, 0, 1);
        /*int getSpeed = 0;
        if (!getChars(speed).equals("") || getChars(speed) != null){
            getSpeed = Integer.parseInt(getChars(speed));
        }*/
        String getSpeed = String.valueOf(speed);
        logger.info("接收到速度消息：" + getSpeed);
        //方向
        int[] dir = new int[1];
        System.arraycopy(ints, 27, dir, 0, 1);
        String getDir = String.valueOf(dir);
        logger.info("接收到方向消息：" + getDir);
        return "，设备号为："+getGpsId + "，速度为：" + getSpeed + "，方向为："+ getDir;
    }


    public String transcoding(byte[] data) {
        //设备号
        /*byte[] gpsId = new byte[6];
        System.arraycopy(data, 4, gpsId, 0, 6);


        byte[] gpsId1 = new byte[1];
        System.arraycopy(data, 4, gpsId1, 0, 1);
        String getGpsId1 = new String(gpsId1);
        byte[] gpsId2 = new byte[1];
        System.arraycopy(data, 5, gpsId2, 0, 1);
        String getGpsId2 = new String(gpsId2);
        byte[] gpsId3 = new byte[1];
        System.arraycopy(data, 6, gpsId3, 0, 1);
        String getGpsId3 = new String(gpsId3);
        byte[] gpsId4 = new byte[1];
        System.arraycopy(data, 7, gpsId4, 0, 1);
        String getGpsId4 = new String(gpsId4);
        byte[] gpsId5 = new byte[1];
        System.arraycopy(data, 8, gpsId5, 0, 1);
        String getGpsId5 = new String(gpsId5);
        byte[] gpsId6 = new byte[1];
        System.arraycopy(data, 9, gpsId6, 0, 1);
        String getGpsId6 = new String(gpsId6);
        String getGpsId = getGpsId1 + getGpsId2 + getGpsId3 + getGpsId4 + getGpsId5 + getGpsId6;
        logger.info("接收到设备号消息：" + getGpsId);*/
        //经度
        byte[] lon = new byte[8];
        System.arraycopy(data, 10, lon, 0, 8);
        double getLongitude = bytes2Double(lon);
        logger.info("接收到经度消息：" + getLongitude);
        //纬度
        byte[] lat = new byte[8];
        System.arraycopy(data, 18, lat, 0, 8);
        double getLatitude = bytes2Double(lat);
        logger.info("接收到纬度消息：" + getLatitude);

        //时间
        byte[] time = new byte[8];
        System.arraycopy(data, 36, time, 0, 8);
        double dTime = bytes2Double(time);
        long ltime = (long) ((double) (dTime - 25570) * 1000 * 3600 * 24 + 57600000);
        logger.info("接收到时间消息：" + ltime);

        return "经度为：" + getLongitude + "，纬度为：" + getLatitude +  "，时间为：" + dTime + "," + ltime;
    }

    private String gpsid(int[] gpsId) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<gpsId.length;i++){
            int[] gpsIds = new int[1];
            System.arraycopy(gpsId, i, gpsIds, 0, 1);

            String getId = String.valueOf(gpsIds);
            logger.info("设备号第"+i+"个字节转换为："+getId);
            String num = String.valueOf(Long.parseLong(getId, 16));
            logger.info("设备号第"+i+"个字节转换为10进制："+num);
            stringBuffer.append(num);
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
