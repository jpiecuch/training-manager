package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "role")
public class Role extends CommonEntity implements RepoObject {
    public static final String NAME_FIELD_NAME = "name";
    public static final String GRANTED_PERMISSIONS_FIELD_NAME = "grantedPermissions";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";
    public static final String ACCOUNT_FIELD_NAME = "account";
    private static final long serialVersionUID = 1L;
    private static final String PERMISSIONS_SEPARATOR = ";";
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
        return ADMIN_ROLE.equals(name) ? Permissions.ALL : USER_ROLE.equals(name) ? Permissions.USER_ROLE_PERMISSIONS : StringUtils.splitByWholeSeparatorPreserveAllTokens(permissions, PERMISSIONS_SEPARATOR);
    }

    public void setGrantedPermissions(String[] grantedPermissions) {
        Assert.isTrue(!ADMIN_ROLE.equals(name) && !USER_ROLE.equals(name));
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
