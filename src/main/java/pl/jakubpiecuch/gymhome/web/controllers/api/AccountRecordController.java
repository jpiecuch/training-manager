package pl.jakubpiecuch.gymhome.web.controllers.api;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.gymhome.domain.AccountRecord;
import pl.jakubpiecuch.gymhome.service.repository.accountrecord.AccountRecordCriteria;
import pl.jakubpiecuch.gymhome.service.repository.accountrecord.AccountRecordDto;
import pl.jakubpiecuch.gymhome.web.util.AuthenticatedUserUtil;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_ACCOUNT_RECORD_PATH)
@RestController
public class AccountRecordController extends AbstractRepositoryController<AccountRecordDto, AccountRecordCriteria> {

    @Override
    protected AccountRecordCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new AccountRecordCriteria(locale.getLanguage()).setAccountIdRestriction(AuthenticatedUserUtil.getAuthenticatedUserDetails().getId())
                .addDateRangeRestriction(resolveDate(parameters.getFirst("from")), resolveDate(parameters.getFirst("to")))
                .addTypeRestrictions(resolveEnumValues(parameters.get("type"), AccountRecord.Type.class));
    }
}
