package com.jp.equipment.handler;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jp.equipment.model.RadioMsg;
import com.jp.equipment.server.ConstantValue;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/** 
 * <pre> 
 * 自己定义的协议 
 *  数据包格式 
 * +——-----——+——-----——+——----——+ 
 * |协议开始标志|  长度          |标志及数据 | 
 * +——-----——+——-----——+——----——+ 
 * 1.协议开始标志 ，16进制表示为0xAB 0xAA 
 * 2.传输数据的长度 ，int类型 
 * 3.要传输的数据,长度不应该超过2048，防止socket流的攻击 
 * </pre> 
 */
/**
 * 20190401 对接南宁的云图（350M数据）
 * 
 * @author fengwei
 *
 */
@Component
public class RadioMsgDecoder extends ByteToMessageDecoder {
	private static final Logger log = LoggerFactory.getLogger(RadioMsgDecoder.class);
	
	private static final String CHARSET= "GBK";
	//private static final String CHARSET= "ASCII";
	
	
	public final int BASE_LENGTH = 10;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		// 可读长度必须大于基本长度
		if (buffer.readableBytes() >= BASE_LENGTH) {
			// 防止socket字节流攻击
			// 防止，客户端传来的数据过大
			// 因为，太大的数据，是不合理的
			if (buffer.readableBytes() > 2048) {
				buffer.skipBytes(buffer.readableBytes());
			}		
			// 记录包头开始的index
			int beginReader;
			
			while (true) {
				// 获取包头开始的index
				beginReader = buffer.readerIndex();
				// 标记包头开始的index
				buffer.markReaderIndex();
				// 读到了协议的开始标志，结束while循环 0xCCCC	定位数据命令字
				if ((buffer.readShort() == ConstantValue.MSG_FLAG) && buffer.readShort() == ConstantValue.MSG_TYPE) {
					break;
				}
		
				// 未读到包头，略过一个字节
				// 每次略过，一个字节，去读取，包头信息的开始标记
				buffer.resetReaderIndex();
				buffer.readByte();

				// 当略过，一个字节之后，
				// 数据包的长度，又变得不满足
				// 此时，应该结束。等待后面的数据到达
				if (buffer.readableBytes() < BASE_LENGTH) {
					return;
				}
			}
			//版本号	2
			buffer.skipBytes(2);
			//网络字节序
			// 消息的长度(小端读取) 
			// int length = buffer.readBytes(4).order(ByteOrder.LITTLE_ENDIAN).readInt();
			 int length = buffer.readInt();
			
			// 判断请求数据包数据是否到齐
			//其中，包体长度只包括“数据体长度”，不包括“包头”和“包尾部分”。
			if (buffer.readableBytes() < 51 ) {
				// 还原读指针
				buffer.readerIndex(beginReader);
				return;
			}
		
			//开始读取全部完整数据 //手台
			byte[] dataForGps = new byte[20];
			buffer.readBytes(dataForGps);
			String gpsid  = new String(dataForGps, CHARSET);
			if(!StringUtils.isEmpty(gpsid)) {
				gpsid=gpsid.trim();
			}
			
			//经度
			double  lon=buffer.readBytes(8).order(ByteOrder.LITTLE_ENDIAN).readDouble();
			
			//纬度
			double lat = buffer.readBytes(8).order(ByteOrder.LITTLE_ENDIAN).readDouble();
			
			//速度
			  short speed = buffer.readBytes(2).order(ByteOrder.LITTLE_ENDIAN).readShort();
			  
			//方向
			  short alt = buffer.readBytes(2).order(ByteOrder.LITTLE_ENDIAN).readShort();
			  
			//高程
			  short acc = buffer.readBytes(2).order(ByteOrder.LITTLE_ENDIAN).readShort();
			  
			//精度
			  short dir = buffer.readBytes(2).order(ByteOrder.LITTLE_ENDIAN).readShort();
			  
			//年月日
			  String timeStr = buffer.readBytes(2).order(ByteOrder.LITTLE_ENDIAN).readShort()+"";
			  
			  for(int i=0;i<5;i++) {
				  String subTime = buffer.readByte()+"";
				if(subTime.length()==1) {
					timeStr=timeStr+"0"+subTime;
				}else if(subTime.length()==2){
					timeStr=timeStr+subTime;
				}else {
					log.info("收到异常数据月日时分秒中的第{}个",i+1);
				}
		  
			  }
	  
			String beiYong="";
			if(length>51) {
				byte[] dataN = new byte[length-51];
				buffer.readBytes(dataN);
				beiYong = new String(dataN, CHARSET);
			}
			RadioMsg radioMsg =new RadioMsg(gpsid, lon, lat, speed, alt, acc,dir, timeStr,beiYong);
			System.out.println(JSON.toJSONString(radioMsg));
			out.add(radioMsg);
		}
	}

}