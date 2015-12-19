package pl.jakubpiecuch.trainingmanager.web.controllers.api.language;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

@RequestMapping(ApiURI.API_LANGUAGE_PATH)
@RestController
public class LanguageController extends AbstractController {

    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity langs(@PathVariable ApiVersionService.Version version) {
        return new ResponseEntity(versionServices.get(version).languages(), HttpStatus.OK);
    }

    @RequestMapping(value = ApiURI.KEY_PATH_PARAM, method = {RequestMethod.GET})
    public ResponseEntity lang(@PathVariable ApiVersionService.Version version, @PathVariable String key) {
        return new ResponseEntity(versionServices.get(version).language(key), HttpStatus.OK);
    }

}
