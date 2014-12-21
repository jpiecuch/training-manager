package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public abstract class AbstractController {

    protected Map<ApiVersionService.Version, ApiVersionService> versionServices;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public void setVersionServices(Map<ApiVersionService.Version, ApiVersionService> versionServices) {
        this.versionServices = versionServices;
    }

}
