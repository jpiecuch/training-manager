package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.domain.PlanExercise;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

@Controller
@RequestMapping("/api/{version}/resource")
public class ApiResourceController {
    
    private static final String EXTENSION_SEPARATOR = ".";
    private Map<ResourceService.Type, ResourceService> resourceServices;
    
    @RequestMapping(value = "content/**", method = RequestMethod.GET)
    public void resource(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = WebUtil.extractPathFromPattern(request);        
        ResourceService resourceService = resourceServices.get(WebUtil.resolveResourceType(StringUtils.substringAfterLast(path, EXTENSION_SEPARATOR)));
        response.setStatus(resourceService != null && resourceService.read(path, response.getOutputStream()) ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value());
    }
    
    @RequestMapping(value = "names/{type}/**", method = RequestMethod.GET)
    public @ResponseBody List<String> resources(@PathVariable ResourceService.Type type, HttpServletRequest request) {
        String path = WebUtil.extractPathFromPattern(request);
        ResourceService resourceService = resourceServices.get(type);
        return resourceService.resources(path);
    }
    
    @Required
    public void setResourceServices(Map<ResourceService.Type, ResourceService> resourceServices) {
        this.resourceServices = resourceServices;
    }
}
