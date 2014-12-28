package pl.jakubpiecuch.trainingmanager.web.controllers.api.language;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(ApiURI.API_LANGUAGE_PATH)
@RestController
@Controller
public class LanguageController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.GET })
    public ResponseEntity langs(@PathVariable ApiVersionService.Version version) throws Exception {
        return new ResponseEntity(versionServices.get(version).languages(), HttpStatus.OK);
    }

    @RequestMapping(value = ApiURI.KEY_PATH_PARAM, method = { RequestMethod.GET })
    public ResponseEntity lang(@PathVariable ApiVersionService.Version version, @PathVariable String key) throws Exception {
        return new ResponseEntity(versionServices.get(version).language(key), HttpStatus.OK);
    }

}
