package pl.jakubpiecuch.trainingmanager.web.controllers.api.equipment;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.equipment.EquipmentCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractRepositoryController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_EQUIPMENT_PATH)
public class EquipmentController extends AbstractRepositoryController<Equipment, EquipmentCriteria> {

    @Override
    protected EquipmentCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new EquipmentCriteria(locale.getLanguage()).addTypeRestriction(resolveEnumValues(parameters.get("type"), Equipment.Type.class));
    }
}
