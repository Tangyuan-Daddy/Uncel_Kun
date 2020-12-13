package org.tydd.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.common
 * @Description
 * @Date 2020/12/1
 */
@Data
public class ResponseVo implements Serializable {

    private int code;

    private String message;

    private Object data;

    public static ResponseVo getSuccess(Object data) {
        ResponseVo vo = new ResponseVo();
        vo.setCode(1);
        vo.setMessage("success");
        vo.setData(data);
        return vo;
    }

    public static ResponseVo getSuccess() {
        ResponseVo vo = new ResponseVo();
        vo.setCode(1);
        vo.setMessage("success");
        return vo;
    }
}
