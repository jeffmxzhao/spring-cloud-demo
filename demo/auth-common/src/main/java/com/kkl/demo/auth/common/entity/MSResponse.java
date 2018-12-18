package com.kkl.demo.auth.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MSResponse<T> {

    /**
     * 返回码
     */
    @Getter
    @Setter
    private int code;

    /**
     * 返回提示信息
     */
    @Getter
    @Setter
    private String msg;

    /**
     * 返回的数据
     */
    @Getter
    @Setter
    private T data;

    public MSResponse(MSErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.data = data;
    }

    public MSResponse(MSErrorCode errorCode) {
        this(errorCode, null);
    }

    /**
     * 检查是否是调用成功的响应
     * @param responseEntity
     * @return
     */
    public static boolean isSuccess(MSResponse<?> responseEntity) {
        return (responseEntity!=null &&
                responseEntity.getCode()==MSErrorCode.CODE_VALUE_SUCCESS &&
                responseEntity.getData()!=null);
    }

}
