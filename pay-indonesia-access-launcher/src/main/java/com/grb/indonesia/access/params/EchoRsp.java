package  com.grb.indonesia.access.params;

        import io.swagger.annotations.ApiModel;
        import io.swagger.annotations.ApiModelProperty;
        import org.apache.commons.lang3.builder.ToStringBuilder;

@ApiModel( description="回音响应参数")
public class EchoRsp extends Rsp{

    public final static EchoRsp DEFAULT(){
        EchoRsp rsp = new EchoRsp();
        rsp.setEcho("I say hi");
        rsp.setCode("0000");
        rsp.setMsg("Success");
        return rsp;
    }

    private String echo;

    @ApiModelProperty(value="回音内容", example = "say hi")
    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }


    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}