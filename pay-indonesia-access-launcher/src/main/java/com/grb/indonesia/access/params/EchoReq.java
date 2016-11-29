package  com.grb.indonesia.access.params;

        import org.apache.commons.lang3.builder.ToStringBuilder;
        import io.swagger.annotations.ApiModel;

@ApiModel( description="回音请求参数")
public class EchoReq extends Req{

    private String reqContent;

    public String getReqContent() {
        return reqContent;
    }

    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

}