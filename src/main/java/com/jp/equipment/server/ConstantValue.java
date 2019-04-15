package com.jp.equipment.server;
import io.netty.buffer.Unpooled;
/**
 * 
 * @author fengwei
 *标志头	2	Unsigned short	0xAAAA	报文头标志
 */
public class ConstantValue {
	public final static short MSG_FLAG=Unpooled.wrappedBuffer(new byte[]{(byte) 0xAA,(byte) 0xAA}).readShort();
	public final static short MSG_TYPE=Unpooled.wrappedBuffer(new byte[]{(byte) 0xCC,(byte) 0xCC}).readShort();
}
