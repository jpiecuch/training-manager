package pl.jakubpiecuch.trainingmanager.service.repository.role;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.repository.AbstractRepository;

import java.util.Arrays;

/**
 * Created by Rico on 2015-01-02.
 */
public class RoleRepository extends AbstractRepository<Role, RoleCriteria> {

    @Override
    public long create(Role element) {
        element.setModifiable(true);
        return super.create(element);
    }

    @Override
    public void update(Role element) {
        Role role = (Role) dao.findById(element.getId());
        Assert.isTrue(role.getModifiable());
        element.setModifiable(true);
        super.update(element);
    }

    @Override
    public void delete(long id) {
        Role role = (Role) dao.findById(id);
        Assert.isTrue(role.getModifiable());
        super.delete(id);
    }

}
