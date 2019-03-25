/*
package com.jp.hczz.dsj350m.wsx350m;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
@Component
public class UDPClient {
    private static final int TIMEOUT = 5000;//设置接收数据超时时间
    private static final int MAXSUM = 5;//设置重发数据的次数
    private Logger logger = LoggerFactory.getLogger(UDPClient.class);
    public void UDPClient() throws IOException {
        */
/*
         * 向服务器端发送数据
         *//*

        //1.定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("http://59.48.5.102");
        int port = 40000;
        byte[] data = "0xAA0xBBgis123456".getBytes();
        logger.info("启动tcp服务" + address + ":" + port);
        try {
            //定义用来发送数据的DatagramPacket实例
            //官方：创建一个数据包实例，将指定长度的数据发送给指定端口的指定主键上。
            //2.创建数据报，包含发送的数据信息
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            //3.创建DatagramSocket对象
            DatagramSocket socket = new DatagramSocket();

            //定义用来接收数据的DatagramPacket实例
            //没有设置端口号，所以是接收数据，要是设置了端口号和主机就是发送数据
            DatagramPacket dp_receive = new DatagramPacket(data, data.length);

            //数据发向本地3000端口
            socket.setSoTimeout(TIMEOUT);
            int tries = 0;
            boolean receivedResponse = false;
            //直到接收到数据，或者重发次数达到预定值，则退出循环
            while(!receivedResponse && tries < MAXSUM){
                //4.向服务器端发送数据报
                socket.send(packet);

                try {
                    //接收从服务端发送来的数据
                    socket.receive(dp_receive);
                    //如果接收的数据不是来自目标地址就抛出异常
                    if(!dp_receive.getAddress().equals(address)){
                        throw new IOException("Received packet from an umknown source");
                    }
                    //如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
                    receivedResponse = true;
                } catch (InterruptedIOException e) {
                    //如果接收数据时阻塞超时，重发并减少一次重发次数
                    tries += 1;
                    System.out.println("Time out," + (MAXSUM - tries) + " more tries..." );
//                  e.printStackTrace();
                }
            }

            if(receivedResponse){
                //如果收到数据，则打印出来
                System.out.println("client received data from server：");
                */
/*
                 * 接收服务器端响应的数据
                 *//*


                String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
                        " from " + dp_receive.getAddress().getHostAddress() + ":" +
                        dp_receive.getPort();
                logger.debug("接收到惟实信服务器发送的消息：" + str_receive);

                //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
                //所以这里要将dp_receive的内部消息长度重新置为1024
                dp_receive.setLength(1024);
            }else{
                //如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
                System.out.println("No response -- give up.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
