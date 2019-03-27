package com.jp.hczz.dsj350m.wsx350m;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

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
                logger.info("接收到的信息长度为：" + datagramPacket.getData().length);
                logger.info("接收到的信息报文为：" + datagramPacket.getData());
                byte[] data = new byte[56];
                System.arraycopy(datagramPacket.getData(), 0, data, 0, 56);
                String getData = transcoding(data);

                //从接收数据包取出数据
                //String data=new String(datagramPacket.getData() , 0 ,datagramPacket.getLength());
                logger.info("接收到消息：" + getData);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }

    public String transcoding(byte[] data) {
        //设备号
        byte[] gpsId = new byte[6];
        System.arraycopy(data, 4, gpsId, 0, 6);
        String getGpsId = new String(gpsId);
        //经度
        byte[] lon = new byte[8];
        System.arraycopy(data, 10, lon, 0, 8);
        double getLongitude = bytes2Double(lon);
        //纬度
        byte[] lat = new byte[8];
        System.arraycopy(data, 18, lat, 0, 8);
        double getLatitude = bytes2Double(lat);
        //速度
        byte[] speed = new byte[1];
        System.arraycopy(data, 26, speed, 0, 1);
        int getSpeed = bytes2Int(speed);
        //方向
        byte[] dir = new byte[1];
        System.arraycopy(data, 27, dir, 0, 1);
        int getDir = bytes2Int(dir);

        //时间
        byte[] time = new byte[8];
        System.arraycopy(data, 36, time, 0, 8);
        double dTime = bytes2Double(time);
        long ltime = (long) ((double) (dTime - 25570) * 1000 * 3600 * 24 + 57600000);

        return "设备号为：" + getGpsId + "经度为：" + getLongitude + "纬度为：" + getLatitude + "速度为：" + getSpeed + "方向为：" + getDir + "时间为：" + dTime + "," + ltime;
    }

    public double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    public int bytes2Int(byte[] bytes) {
        int num = bytes[3] & 0xFF;
        num |= ((bytes[2] << 8) & 0xFF00);
        num |= ((bytes[1] << 16) & 0xFF0000);
        num |= ((bytes[0] << 24) & 0xFF0000);
        return num;
    }
}
