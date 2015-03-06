package pl.jakubpiecuch.trainingmanager.web.controllers.api.equipment;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.repository.equipment.EquipmentCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_EQUIPMENT_PATH)
@RestController
public class EquipmentController extends AbstractController {

    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<Equipment> descriptions(@PathVariable ApiVersionService.Version version,
                               @RequestParam(value = "type", required = false) Equipment.Type[] types,
                               @RequestParam(value = "excludeId", required = false) Long[] excludedIds,
                               @RequestParam(value = "firstResult", required = false, defaultValue = "0") Integer firstResult,
                               @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults,
                               Locale locale) {
        return versionServices.get(version).retrieveFromRepository(new EquipmentCriteria(locale.getLanguage()).addTypeRestriction(types).setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults).addExcludedIdRestriction(excludedIds), Repositories.EQUIPMENT);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public Equipment description(@PathVariable ApiVersionService.Version version, @PathVariable long id, Locale locale) {
        return (Equipment) versionServices.get(version).retrieveFromRepository(new EquipmentCriteria(locale.getLanguage()).setIdRestriction(id), Repositories.EQUIPMENT).getResult().get(0);
    }

    @RequestMapping(method = { RequestMethod.POST })
    public long create(@PathVariable ApiVersionService.Version version, HttpServletRequest request, @RequestParam(value = "type") Equipment.Type type) throws IOException {
        HttpInputMessage message = new ServletServerHttpRequest(request);
        Equipment equipment = versionServices.get(version).resolve(message.getBody(), type);
        return versionServices.get(version).storeInRepository(equipment, Repositories.EQUIPMENT);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.PUT })
    public void update(@PathVariable ApiVersionService.Version version, @PathVariable long id, HttpServletRequest request, @RequestParam(value = "type") Equipment.Type type) throws IOException {
        HttpInputMessage message = new ServletServerHttpRequest(request);
        Equipment equipment = versionServices.get(version).resolve(message.getBody(), type);
        equipment.setId(id);
        versionServices.get(version).updateInRepository(equipment, Repositories.EQUIPMENT);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.DELETE })
    public void delete(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        versionServices.get(version).removeFromRepository(id, Repositories.EQUIPMENT);
    }
}
