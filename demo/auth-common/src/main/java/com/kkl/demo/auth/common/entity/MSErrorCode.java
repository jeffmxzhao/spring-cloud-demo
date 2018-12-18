package com.kkl.demo.auth.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MSErrorCode {

    public static int CODE_VALUE_SUCCESS = 0;

    public static MSErrorCode SUCCESS = new MSErrorCode(0, "成功");
    public static MSErrorCode FAILURE = new MSErrorCode(10000, "数据处理错误");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    public String msg;

    /**
     * 创建一个实例
     *
     * @param errorCode
     * @param msg
     * @return
     */
    public static MSErrorCode newInstance(MSErrorCode errorCode, String msg) {
        return new MSErrorCode(errorCode.getCode(), msg);
    }
}
