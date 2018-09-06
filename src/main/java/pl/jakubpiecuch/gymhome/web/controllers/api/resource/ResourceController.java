package pl.jakubpiecuch.gymhome.web.controllers.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.resource.ResourceService;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractController;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;

import java.io.IOException;

@RestController
@RequestMapping(ApiURI.API_RESOURCE_PATH)
public class ResourceController extends AbstractController {

    @RequestMapping(value = ApiURI.KEY_PATH_PARAM, method = RequestMethod.GET)
    public ResponseEntity resource(@PathVariable ApiVersionService.Version version, @PathVariable ResourceService.Type type, @PathVariable String key) throws IOException {
        return versionServices.get(version).resources(type).resource(key);
    }
}
