package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.api.v1.Version1Service;

@Controller
@RequestMapping(value = "/api/{version}")
public class ApiController {

    private Map<ApiVersionService.Version, Version1Service> versionServices;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "config/locale", method = {RequestMethod.GET, RequestMethod.POST}, params = {"lang"})
    public @ResponseBody Object locale(Locale locale) {
        return locale;
    }

    public void setVersionServices(Map<ApiVersionService.Version, Version1Service> versionServices) {
        this.versionServices = versionServices;
    }
}
