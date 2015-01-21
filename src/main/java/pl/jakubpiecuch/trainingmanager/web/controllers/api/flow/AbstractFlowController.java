package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowController extends AbstractController {

    protected abstract Flow.Hierarchy getHierarchy();

    public ResponseEntity create(ApiVersionService.Version version, Flow flow) throws Exception {
        return new ResponseEntity(versionServices.get(version).createFlow(getHierarchy(), flow), HttpStatus.CREATED);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public ResponseEntity flow(@PathVariable ApiVersionService.Version version, @PathVariable Long id, @RequestParam(required = false, defaultValue = "false") boolean full) throws Exception {
        return new ResponseEntity(versionServices.get(version).flow(getHierarchy(), id, full), HttpStatus.OK);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM_CHILDREN, method = { RequestMethod.GET })
    public ResponseEntity children(@PathVariable ApiVersionService.Version version, @PathVariable Long id, @RequestParam(required = false, defaultValue = "false") boolean full) throws Exception {
        return new ResponseEntity(versionServices.get(version).children(getHierarchy(), id, full), HttpStatus.OK);
    }
}
