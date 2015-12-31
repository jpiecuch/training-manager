package pl.jakubpiecuch.trainingmanager.web.controllers.api.dictionary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_DICTIONARY_PATH)
@RestController
public class DictionaryController extends AbstractController {

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = {RequestMethod.GET})
    public ResponseEntity dictionary(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        return new ResponseEntity(versionServices.get(version).dictionary().retrieve(id), HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity dictionaries(@PathVariable ApiVersionService.Version version, @RequestParam("id") long[] ids) {
        return new ResponseEntity(versionServices.get(version).dictionary().retrieve(ids), HttpStatus.OK);
    }
}
