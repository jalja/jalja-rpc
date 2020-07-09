package com.jalja.rpc.common;

import java.nio.charset.Charset;

/**
 * @Auther: XL
 * @Date: 2019/12/10 11:27
 * @Description:
 */
public final class Charsets {
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    public static final Charset GBK = Charset.forName("GBK");
    public static final Charset GB2312 = Charset.forName("GB2312");

    private Charsets() {
    }
}
