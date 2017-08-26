package pl.jakubpiecuch.gymhome.service.repository.accountrecord;

import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.AccountRecord;
import pl.jakubpiecuch.gymhome.service.converter.AbstractConverter;
import pl.jakubpiecuch.gymhome.service.user.authentication.AuthenticationService;

/**
 * Created by Rico on 2014-12-31.
 */
public class AccountRecordConverter extends AbstractConverter<AccountRecordDto, AccountRecord> {

    private AuthenticationService authenticationService;

    @Override
    protected AccountRecord convertFrom(AccountRecordDto dto, AccountRecord entity) {
        entity.setAccount(entity.getId() == null ? new Account(authenticationService.signed().getId()) : entity.getAccount());
        entity.setDate(entity.getId() == null ? dto.getDate() : entity.getDate());
        entity.setType(dto.getType());
        entity.setValue(dto.getValue());
        return entity;
    }

    @Override
    protected AccountRecordDto convertTo(AccountRecord entity) {
        AccountRecordDto dto = new AccountRecordDto();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        dto.setType(entity.getType());
        dto.setDate(entity.getDate());
        dto.setAccountId(entity.getAccount().getId());
        return dto;
    }

    @Override
    protected AccountRecord getEmpty() {
        return new AccountRecord();
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
