package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role extends CommonEntity implements RepoObject {
    private static final long serialVersionUID = 1L;
    public static final String NAME_FIELD_NAME = "name";
    public static final String GRANTED_PERMISSIONS_FIELD_NAME = "grantedPermissions";
    private static final String PERMISSIONS_SEPARATOR = ";";
    public static final String ADMIN_ROLE = "ADMIN";

    private String name;
    private String permissions;
    private Boolean modifiable;

    public Role() {
    }

    public Role(Long id) {
        super(id);
    }

    @Column(name = NAME_FIELD_NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "permissions")
    protected String getPermissions() {
        return permissions;
    }

    protected void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Transient
    public String[] getGrantedPermissions() {
        return ADMIN_ROLE.equals(name) ? Permissions.ALL : StringUtils.splitByWholeSeparatorPreserveAllTokens(permissions, PERMISSIONS_SEPARATOR);
    }

    public void setGrantedPermissions(String[] grantedPermissions) {
        Assert.isTrue(!ADMIN_ROLE.equals(name));
        this.permissions = StringUtils.join(grantedPermissions, PERMISSIONS_SEPARATOR);
    }


    @Column(name = "modifiable")
    public Boolean getModifiable() {
        return modifiable;
    }

    public void setModifiable(Boolean modifiable) {
        this.modifiable = modifiable;
    }
}
