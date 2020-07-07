package com.jalja.rpc.common.utils;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * @author XL
 * @title: NetUtils
 * @projectName jalja-rpc
 * @date 2020/7/317:22
 * @description: TODO
 */
public class NetUtils {


    public static int scoreAddr(NetworkInterface iface, InetAddress addr) {
        int score = 0;
        if (addr instanceof Inet4Address) {
            score += 300;
        }
        try {
            if (!iface.isLoopback() && !addr.isLoopbackAddress()) {
                score += 100;
                if (iface.isUp()) {
                    score += 100;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return score;
    }

    public static InetAddress getCurrentIp() {
        int bestScore = -1;
        InetAddress bestAddr = null;

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> addrs = iface.getInetAddresses();

                while (addrs.hasMoreElements()) {
                    InetAddress addr = (InetAddress) addrs.nextElement();
                    int score = scoreAddr(iface, addr);
                    if (score >= bestScore) {
                        bestScore = score;
                        bestAddr = addr;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bestAddr;
    }
    /***
     * 测试主机Host的port端口是否被使用 true 被占用  false：没被占用
     * @param host
     * @param port
     */
    public static boolean isPortUsing(String host,int port) {
        boolean flag = false;
        try {
            InetAddress Address = InetAddress.getByName(host);
            new Socket(Address,port);
            flag = true;
        } catch (IOException e) {
            flag = false;
        }
        return flag;
    }
    public static void main(String[] args) {
        String ip = NetUtils.getCurrentIp().getHostAddress();
        System.out.println(isPortUsing(ip,8081));
    }

}
