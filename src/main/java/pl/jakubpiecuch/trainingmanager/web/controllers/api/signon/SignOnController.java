package pl.jakubpiecuch.trainingmanager.web.controllers.api.signon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/{version}/signon")
@Controller
public class SignOnController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public @ResponseBody
    ResponseEntity signOn(@PathVariable ApiVersionService.Version version, HttpServletRequest request, @RequestBody Registration registration) throws Exception {
        versionServices.get(version).signOn(registration, request.getLocale());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
