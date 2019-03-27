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
        String getGpsId = getChars(gpsId);
        logger.info("接收到设备号消息：" + getGpsId);
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
        //速度
        byte[] speed = new byte[1];
        System.arraycopy(data, 26, speed, 0, 1);
        int getSpeed = Integer.parseInt(getChars(speed));
        logger.info("接收到速度消息：" + getSpeed);
        //方向
        byte[] dir = new byte[1];
        System.arraycopy(data, 27, dir, 0, 1);
        int getDir = Integer.parseInt(getChars(dir));
        logger.info("接收到方向消息：" + getDir);
        //时间
        byte[] time = new byte[8];
        System.arraycopy(data, 36, time, 0, 8);
        double dTime = bytes2Double(time);
        long ltime = (long) ((double) (dTime - 25570) * 1000 * 3600 * 24 + 57600000);
        logger.info("接收到时间消息：" + ltime);

        return "设备号为：" + getGpsId + "经度为：" + getLongitude + "纬度为：" + getLatitude + "速度为：" + getSpeed + "方向为：" + getDir + "时间为：" + dTime + "," + ltime;
    }

    public double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }


    public String getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return String.valueOf(cb.array());
    }
}
