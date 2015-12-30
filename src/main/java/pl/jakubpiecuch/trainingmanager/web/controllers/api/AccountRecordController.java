package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.service.repository.accountrecord.AccountRecordCriteria;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_ACCOUNT_RECORD_PATH)
@RestController
public class AccountRecordController extends AbstractRepositoryController<AccountRecord, AccountRecordCriteria> {

    @Override
    protected AccountRecordCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new AccountRecordCriteria(locale.getLanguage())
                .addDateRangeRestriction(resolveDate(parameters.getFirst("from")), resolveDate(parameters.getFirst("to")))
                .addTypeRestrictions(resolveEnumValues(parameters.get("type"), AccountRecord.Type.class));
    }
}
