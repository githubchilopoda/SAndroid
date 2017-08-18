package com.ss.ssframework.exception;

/**
 * Created by 健 on 2017/7/24.
 */

public class IllegalResponseException extends RuntimeException {

    public final static int NO_DATA = 0x00;
    public IllegalResponseException(int code) {
        super(getApiExceptionMessage(code));
    }

    public IllegalResponseException(String msg) {
        super(msg);
    }

    public IllegalResponseException(int code, String msg) {
        super(msg);
    }

    /**
     * 转换错误数据
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;

        }
        return message;
    }
}
