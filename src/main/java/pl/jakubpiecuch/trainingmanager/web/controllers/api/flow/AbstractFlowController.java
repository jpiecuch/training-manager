package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowController extends AbstractController {

    protected abstract Flow.Hierarchy getHierarchy();

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public ResponseEntity phase(@PathVariable ApiVersionService.Version version, @PathVariable Long id) throws Exception {
        return new ResponseEntity(versionServices.get(version).flow(getHierarchy(), id), HttpStatus.OK);
    }
}
