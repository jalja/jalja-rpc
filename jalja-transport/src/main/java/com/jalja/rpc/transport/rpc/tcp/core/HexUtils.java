package com.jalja.rpc.transport.rpc.tcp.core;


import org.apache.commons.lang3.RandomUtils;

/**
 * @author XL
 * @title: HexUtils
 * @projectName jalja-rpc
 * @date 2020/7/822:01
 * @description: TODO
 */
public class HexUtils {
   public static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f'};

    /**
     * 字节转hex
     * @param bytes
     * @return
     */
   public static String bytesToHex(byte[] bytes) {
        char[] resultCharArray = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            resultCharArray[index++] = hexDigits[b>>>4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    /**
     * 生产一个唯一的标识(15位)
     * @return
     */
    public static String uuid() {
        long time=System.nanoTime();
        String uuid=String.valueOf(time);
        if(uuid.length()>14){
            uuid=uuid.substring(1);
        }
        uuid=RandomUtils.nextInt(0,9)+uuid;
        return uuid;
    }
}
