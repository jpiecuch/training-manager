package pl.jakubpiecuch.trainingmanager.web.controllers.api.signout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/{version}/signout")
@Controller
public class SignOutController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public @ResponseBody
    Response signOut(@PathVariable ApiVersionService.Version version, HttpServletResponse response) throws Exception {
        return versionServices.get(version).signOut().updateHttpStatus(response);
    }
}
