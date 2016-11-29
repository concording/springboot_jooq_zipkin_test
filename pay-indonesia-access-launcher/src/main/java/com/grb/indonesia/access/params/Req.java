package  com.grb.indonesia.access.params;

        import io.swagger.annotations.ApiModelProperty;

public class Req {

    private String reqNo;

    private int pageNo;
    private int pageSize;

    private String token;

    @ApiModelProperty(value="请求序号")
    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    @ApiModelProperty(value="分页页码", example = "1")
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @ApiModelProperty(value="分页单页数据条数", example = "15")
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value="访问token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}