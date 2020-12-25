package org.tydd.common;

import lombok.Data;

import java.io.Serializable;

import static org.tydd.common.ResponseStatusConstant.FAIL_STATUS;
import static org.tydd.common.ResponseStatusConstant.SUCCESS_STATUS;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.common
 * @Description
 * @Date 2020/12/1
 */
@Data
public class ResponseVo implements Serializable {

    private final static String SUCCESS_FLAG = "1";

    private final static String FAIL_FLAG = "2";

    private int code;

    private String message;

    private Object data;

    public static ResponseVo success(Object data) {
        ResponseVo vo = success();
        vo.setData(data);
        return vo;
    }

    public static ResponseVo success() {
        ResponseVo vo = new ResponseVo();
        vo.setCode(SUCCESS_STATUS);
        vo.setMessage("success");
        return vo;
    }

    public static ResponseVo fail() {
        ResponseVo vo = new ResponseVo();
        vo.setCode(FAIL_STATUS);
        vo.setMessage("fail");
        return vo;
    }
}
