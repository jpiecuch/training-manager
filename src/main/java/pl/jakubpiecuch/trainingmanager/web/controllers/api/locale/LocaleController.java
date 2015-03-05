package pl.jakubpiecuch.trainingmanager.web.controllers.api.locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(ApiURI.API_LOCALE_PATH)
@RestController
public class LocaleController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity signIn(@PathVariable ApiVersionService.Version version, HttpServletRequest request, HttpServletResponse response, @RequestBody String locale) {
        versionServices.get(version).locale(request, response, locale);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
