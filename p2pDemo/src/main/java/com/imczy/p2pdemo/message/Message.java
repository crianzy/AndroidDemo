package com.imczy.p2pdemo.message;

import java.nio.ByteBuffer;

/**
 * Created by chenzhiyong on 15/10/12.
 */
public abstract class Message {


    public static int version = 1;

    public static final int CLIENT_MESSAGE_MIN_LENGTH = 22;
    public static final int SERVER_MESSAGE_MIN_LENGTH = 6;

    public static final int CMD_HEARTBEAT = 0x00;//心跳包
    public static final int CMD_LOGIN = 0x01;//登录
    public static final int CMD_RELOGIN = 0x02;//服务器端下发命令, 表示要重新登陆
    public static final int CMD_LOGIN_SUCC = 0x03;//服务器端下发命令, 表示要登录成功
    public static final int CMD_CHAT = 0x10;//普通弹幕信息
    public static final int CMD_POSITION = 0x20;//地理位置包
    public static final int CMD_LOGOUT = 0xff;//登出

    public static final int CMD_CHAT_P2P = 0x11;// 我想 p2p
    public static final int CMD_CHAT_RECEIVE_P2P = 0x12;//别人想 p2p

    public static final int CMD_I_HAD_CALL_A = 0x13;//别人想 p2p
    public static final int CMD_B_HAD_CALL_YOU = 0x14;//别人想 p2p


    protected int minLength = 0;
    protected byte[] data;

    public int getCmd() {
        byte b = data[2];
        return b & 0xff;
    }

    public int getContentLength() {
        return (int) ByteBuffer.wrap(data, minLength - 3, 2).getChar();
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public static void setVersion(int v) {
        if (v < 1 || v > 255) {
            return;
        }
        version = v;
    }

    public static int getVersion() {
        return version;
    }


    public abstract String getContent();
}
