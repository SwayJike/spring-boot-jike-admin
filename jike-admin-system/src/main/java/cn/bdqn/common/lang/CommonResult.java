package cn.bdqn.common.lang;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @title:CommonResult
 * 设计为泛型解决 swagger2 字段显示说明的问题
 * @Author SwayJike
 * @Date:2021/12/15 19:17
 * @Version 1.0
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * Http状态系列
     */
    @ApiModelProperty(value = "Http状态系列")
    private HttpStatus.Series series;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T data;

    public CommonResult(HttpStatus status){
        this.code = status.value();
        this.series = status.series();
        this.message = status.getReasonPhrase();
    }

    public CommonResult(HttpStatus status, T data){
        this(status);
        this.data = data;
    }

    public static CommonResult success(){
        CommonResult result = new CommonResult(HttpStatus.OK);
        return result;
    }

    public static CommonResult success(Object data){
        return success().setData(data);
    }

    public static CommonResult success(HttpStatus status){
        CommonResult result = new CommonResult(status);
        return result;
    }

    public static CommonResult success(HttpStatus status, Object data){
        return success(status).setData(data);
    }

    public static CommonResult failure(){
        CommonResult result = new CommonResult(HttpStatus.BAD_REQUEST);
        return result;
    }

    public static CommonResult failure(HttpStatus status){
        CommonResult result = new CommonResult(status);
        return result;
    }

    public static CommonResult failure(HttpStatus status, Object data){
        return failure(status).setData(data);
    }

    /**
     * 清除 message 前端通过判断当前有没有信息,控制前端弹出框信息.
     * @return
     */
    public CommonResult clearMessage(){
        return this.setMessage(null);
    }

}
