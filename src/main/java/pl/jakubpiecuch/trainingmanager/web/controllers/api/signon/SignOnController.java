package pl.jakubpiecuch.trainingmanager.web.controllers.api.signon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/{version}/signon")
@Controller
public class SignOnController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public @ResponseBody
    Response signOn(@PathVariable ApiVersionService.Version version, HttpServletResponse response, HttpServletRequest request) throws Exception {
        return versionServices.get(version).signOn(request).updateHttpStatus(response);
    }
}
