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
            System.out.println("****客户端已经启动");
            byte[] buf=new byte[1024];
            while (true){
                //定义接收数据的数据包
                DatagramPacket datagramPacket=new DatagramPacket(buf, 0, buf.length);
                datagramSocket.receive(datagramPacket);

                //从接收数据包取出数据
                String data=new String(datagramPacket.getData() , 0 ,datagramPacket.getLength());
                String str = new String(data.getBytes("ISO-8859-1"),"UTF-8");
                logger.info("接收到消息："+str);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }
}
