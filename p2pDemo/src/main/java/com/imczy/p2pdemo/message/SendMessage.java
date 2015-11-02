/*
 *Copyright 2014 DDPush
 *Author: AndyKwok(in English) GuoZhengzhu(in Chinese)
 *Email: ddpush@126.com
 *

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
package com.imczy.p2pdemo.message;


import com.imczy.common_util.log.LogUtil;

import java.nio.ByteBuffer;


public final class SendMessage extends Message {
    public static final String TAG = "SendMessage";

    public SendMessage(byte[] data) {
        this.data = data;
        this.minLength = CLIENT_MESSAGE_MIN_LENGTH;
    }

    public boolean checkFormat() {
        if (data == null || data.length < SendMessage.CLIENT_MESSAGE_MIN_LENGTH) {
            return false;
        }
        int cmd = getCmd();
        if (cmd != CMD_HEARTBEAT
                && cmd != CMD_LOGIN
                && cmd != CMD_CHAT_P2P
                && cmd != CMD_CHAT
                && cmd != CMD_POSITION
                && cmd != CMD_LOGOUT) {
            return false;
        }
        //TODO 需要检查 内容格式
        int dataLen = getContentLength();
        LogUtil.d(TAG, "dataLen = " + dataLen);
        LogUtil.d(TAG, "data.length != dataLen + CLIENT_MESSAGE_MIN_LENGTH = " + (data.length != dataLen + CLIENT_MESSAGE_MIN_LENGTH));
        if (data.length != dataLen + CLIENT_MESSAGE_MIN_LENGTH - 1) {
            return false;
        }

        return true;
    }

    public static SendMessage newMessage(int appId, int msgType, byte[] uuid, String contentStr) {
        if (contentStr == null) {
            contentStr = "";
        }

        byte[] contentBytes = contentStr.getBytes();
        byte[] data = new byte[SendMessage.CLIENT_MESSAGE_MIN_LENGTH + contentBytes.length];
        ByteBuffer.wrap(data).
                put((byte) SendMessage.version).
                put((byte) appId).
                put((byte) msgType).
                put(uuid).
                putShort((short) contentBytes.length).
                put(contentBytes).
                put((byte) '\n');
        SendMessage message = new SendMessage(data);
        return message;
    }
    public static SendMessage newMessage(int appId, int msgType, byte[] uuid, byte[] content) {
        byte[] contentBytes = content;
        byte[] data = new byte[SendMessage.CLIENT_MESSAGE_MIN_LENGTH + contentBytes.length];
        ByteBuffer.wrap(data).
                put((byte) SendMessage.version).
                put((byte) appId).
                put((byte) msgType).
                put(uuid).
                putShort((short) contentBytes.length).
                put(contentBytes).
                put((byte) '\n');
        SendMessage message = new SendMessage(data);
        return message;
    }

    public String getContent() {
        String content = "";
        if (!checkFormat()) {
            return content;
        }
        content = new String(data, CLIENT_MESSAGE_MIN_LENGTH - 1, getContentLength());
        return content;
    }

}
