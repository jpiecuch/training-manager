package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.api.v1.Version1Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public abstract class AbstractController {

    protected Map<ApiVersionService.Version, Version1Service> versionServices;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Required
    public void setVersionServices(Map<ApiVersionService.Version, Version1Service> versionServices) {
        this.versionServices = versionServices;
    }

}
