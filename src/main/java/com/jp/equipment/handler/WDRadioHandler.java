package com.jp.equipment.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jp.equipment.model.RadioMsg;
import com.jp.equipment.service.SendKafkaService;
import com.jp.equipment.service.impl.SendKafkaServiceImpl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * 
 * @author fengwei
 *
 */
@Component
public class WDRadioHandler extends SimpleChannelInboundHandler<RadioMsg> {
	private static final Logger log = LoggerFactory.getLogger(WDRadioHandler.class);
	
	@Autowired
	private SendKafkaService sendKafkaService;
 
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	
		log.debug("client connectioned...");
	 }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RadioMsg msg) throws Exception {
		log.debug("从上游厂家接收数据为："+JSON.toJSONString(msg));
		//推送kafaka 实时点位主题
		//sendKafkaService.sendKafka(msg);
		//推送kafaka 数据保存主题
		
	 }
	
	 @Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
 
