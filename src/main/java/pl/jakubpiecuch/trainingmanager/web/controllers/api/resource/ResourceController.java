package pl.jakubpiecuch.trainingmanager.web.controllers.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(ApiURI.API_RESOURCE_PATH)
public class ResourceController extends AbstractController {

    @RequestMapping(value = ApiURI.KEY_PATH_PARAM, method = RequestMethod.GET)
    public ResponseEntity resource(@PathVariable ApiVersionService.Version version, @PathVariable ResourceService.Type type, @PathVariable String key, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return versionServices.get(version).resource(type, key);
    }
}
