package com.grb.indonesia.access.rest.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grb.indonesia.access.rest.AbstractCtl;

/**
 * Home redirection to swagger api documentation
 */
@Controller
public class SwaggerUiCtl extends AbstractCtl{
    @RequestMapping(value = "/swaggerUi")
    public String index() {
        this.getLogger().info("{}", "swaggerUi");
        return "redirect:swagger-ui.html";
    }
}