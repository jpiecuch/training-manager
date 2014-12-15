package pl.jakubpiecuch.trainingmanager.web.controllers.api.signin;

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

@RequestMapping("api/{version}/signin")
@Controller
public class SignInController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public @ResponseBody
    Response signIn(@PathVariable ApiVersionService.Version version, NativeWebRequest request, HttpServletResponse response) throws Exception {
        return versionServices.get(version).signIn(request).updateHttpStatus(response);
    }

    @RequestMapping(method = { RequestMethod.GET })
    public @ResponseBody
    Response signed(@PathVariable ApiVersionService.Version version, HttpServletResponse response) throws Exception {
        return versionServices.get(version).signed().updateHttpStatus(response);
    }
}
