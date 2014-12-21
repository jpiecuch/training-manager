package pl.jakubpiecuch.trainingmanager.web.controllers.api.signout;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/{version}/signout")
@Controller
public class SignOutController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public @ResponseBody
    ResponseEntity signOut(@PathVariable ApiVersionService.Version version, HttpServletResponse response) throws Exception {
        versionServices.get(version).signOut();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
