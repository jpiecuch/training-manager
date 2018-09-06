package pl.jakubpiecuch.gymhome.web.controllers.api.language;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractController;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;

import java.util.List;
import java.util.Map;

@RequestMapping(ApiURI.API_LANGUAGE_PATH)
@RestController
public class LanguageController extends AbstractController {

    @RequestMapping(method = {RequestMethod.GET})
    public List<String> langs(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).translates().languages();
    }

    @RequestMapping(value = ApiURI.KEY_PATH_PARAM, method = {RequestMethod.GET})
    public Map<String, String> lang(@PathVariable ApiVersionService.Version version, @PathVariable String key) {
        return versionServices.get(version).translates().translates(key);
    }

}
