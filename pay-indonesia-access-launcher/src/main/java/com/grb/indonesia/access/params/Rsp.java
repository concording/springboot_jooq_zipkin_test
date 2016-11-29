package  com.grb.indonesia.access.params;

        import io.swagger.annotations.ApiModelProperty;

public class Rsp {
    private String code;
    private String msg;

    @ApiModelProperty(value="交易响应码", required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ApiModelProperty(value="交易响应信息", required = true)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}