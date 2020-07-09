package com.jalja.rpc.transport.rpc.tcp.core;


import com.jalja.rpc.common.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.AttributeKey;

/**
 * @Auther: XL
 * @Date: 2019/12/3 23:10
 * @Description:
 */
public interface NettyConstant {
      AttributeKey<String> SESSION = AttributeKey.valueOf("SESSION");
      /**
       * 报文分隔符
       */
      ByteBuf DE_LIMITER= Unpooled.buffer().writeBytes("$_&_$".getBytes());
      /**
       * 报文分隔符
       */
      byte[] DE_LIMITERS="$_&_$".getBytes(Charsets.US_ASCII);
      /**
       * 客户端的心跳数据
       */
      byte [] HEART_BEAT="HEART.BEAT".getBytes(Charsets.US_ASCII);

      static enum NettyType{
            SERVER,
            CLIENT;
      }
}
