package org.tydd.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("接口返回对象")
public class ResponseVo<T> implements Serializable {

    private final static String SUCCESS_FLAG = "1";

    private final static String FAIL_FLAG = "2";

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("状态说明")
    private String message;

    @ApiModelProperty("数据明细")
    private T data;

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
