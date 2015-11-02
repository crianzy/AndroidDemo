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

public final class ReceiverMessage extends Message {
    public static final String TAG = "ReceiverMessage";

    public ReceiverMessage(byte[] data) {
        this.data = data;
        minLength = SERVER_MESSAGE_MIN_LENGTH;
    }

    public boolean isRightFomat() {
        if (data == null || data.length < ReceiverMessage.SERVER_MESSAGE_MIN_LENGTH) {
            return false;
        }
        int cmd = getCmd();
        if (cmd != CMD_CHAT && cmd != CMD_CHAT_RECEIVE_P2P) {
            return false;
        }
        int dataLen = getContentLength();


        LogUtil.d(TAG, "dataLen = " + dataLen + " , data.length = " + data.length);
        LogUtil.d(TAG, "dataLen + CLIENT_MESSAGE_MIN_LENGTH = " + (dataLen + SERVER_MESSAGE_MIN_LENGTH));
        if (data.length != dataLen + SERVER_MESSAGE_MIN_LENGTH) {
            LogUtil.d(TAG, "格式错误");
            return false;
        }

        LogUtil.d(TAG, "格式正确");

        return true;
    }

    public String getContent() {
        String content = "";
        if (!isRightFomat()) {
            return content;
        }
        content = new String(data, SERVER_MESSAGE_MIN_LENGTH - 1, getContentLength());
        return content;
    }

}
