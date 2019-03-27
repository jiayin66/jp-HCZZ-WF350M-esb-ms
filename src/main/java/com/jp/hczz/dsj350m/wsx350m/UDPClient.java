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
    public void UDPClient(){
        DatagramSocket datagramSocket=null;
        try {
            //监视40000端口的内容
            datagramSocket=new DatagramSocket(40000);
            logger.info("客户端已启动");
            byte[] buf=new byte[1024];
            while (true){
                //定义接收数据的数据包
                DatagramPacket datagramPacket=new DatagramPacket(buf, 0, buf.length);
                datagramSocket.receive(datagramPacket);
                String getData = null;
                if (datagramPacket.getData().length%56 ==0){
                    for (int i=0;i<datagramPacket.getData().length/56;i++){
                        byte[] data = new byte[56];
                        System.arraycopy(datagramPacket.getData(), 56*i, data, 0, 56);
                        getData = transcoding(data);
                    }
                }else{
                    logger.debug("");
                }


                //从接收数据包取出数据
                //String data=new String(datagramPacket.getData() , 0 ,datagramPacket.getLength());
                logger.info("接收到消息："+getData);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }

    public String transcoding(byte[] data){
        //设备号
        byte[] gpsId = new byte[6];
        System.arraycopy(data, 4, gpsId, 0, 6);
        String getGpsId = new String(gpsId);
        //经度
        byte[] lon = new byte[8];
        System.arraycopy(data, 10, lon, 0, 8);
        double  getLongitude = Double.parseDouble(String.valueOf(lon));
        //纬度
        byte[] lat = new byte[8];
        System.arraycopy(data, 18, lat, 0, 8);
        double getLatitude = Double.parseDouble(String.valueOf(lat));
        //速度
        byte[] speed = new byte[1];
        System.arraycopy(data, 26, speed, 0, 1);
        double getSpeed = Double.parseDouble(String.valueOf(speed));
        //方向
        byte[] dir = new byte[1];
        System.arraycopy(data, 27, dir, 0, 1);
        int getDir = Integer.parseInt(String.valueOf(dir));
        //星数
        byte[] state = new byte[4];
        System.arraycopy(data, 28, state, 0, 4);

        //消息类型
        byte[] nMsgtype = new byte[2];
        System.arraycopy(data, 32, nMsgtype, 0, 2);

        //高度
        byte[] height = new byte[2];
        System.arraycopy(data, 34, height, 0, 2);

        //时间
        byte[] time = new byte[8];
        System.arraycopy(data, 36, time, 0, 8);
        double dTime = Double.parseDouble(String.valueOf(time));
        long ltime = (long) ((double)(dTime -25570)*1000*3600*24+57600000);
        //场强
        byte[] dbm = new byte[8];
        System.arraycopy(data, 44, dbm, 0, 8);
        double getDbm = Double.parseDouble(String.valueOf(dbm));
        //基站ip
        byte[] ip = new byte[4];
        System.arraycopy(data, 52, ip, 0, 4);
        String getIp = new String(ip);

        return "设备号为："+getGpsId +"经度为："+ getLongitude +"纬度为："+ getLatitude + "速度为："+ getSpeed +"方向为："+ getDir + "时间为："+dTime+","+ltime + "场强为："+getDbm + "ip为："+getIp;
    }
}
