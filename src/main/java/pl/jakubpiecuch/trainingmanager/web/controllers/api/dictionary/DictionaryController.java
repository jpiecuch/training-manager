package pl.jakubpiecuch.trainingmanager.web.controllers.api.dictionary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_DICTIONARY_PATH)
@RestController
public class DictionaryController extends AbstractController {

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public ResponseEntity flow(@PathVariable ApiVersionService.Version version, @PathVariable Long id) throws Exception {
        return new ResponseEntity(versionServices.get(version).dictionary(id), HttpStatus.OK);
    }
}
