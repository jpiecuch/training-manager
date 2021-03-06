package pl.jakubpiecuch.gymhome.domain;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.social.SocialProvider;
import pl.jakubpiecuch.gymhome.web.util.WebUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pl.jakubpiecuch.gymhome.domain.Account.*;

@Entity
@Table(name = "account",
        uniqueConstraints = {@UniqueConstraint(columnNames = {Account.NAME_FIELD_NAME, Account.SOCIAL_TYPE_FIELD_NAME}),
        @UniqueConstraint(columnNames = {Account.EMAIL_FIELD_NAME, Account.SOCIAL_TYPE_FIELD_NAME})}  )
public class Account extends VersionedEntity {

    public static final String NAME_FIELD_NAME = "name";
    public static final String CREDENTIAL_FIELD_NAME = "password";
    public static final String SALT_FIELD_NAME = "salt";
    public static final String STATUS_FIELD_NAME = "status";
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String CONFIG_FIELD_NAME = "config";
    public static final String PROVIDER_FIELD_NAME = "provider";
    public static final String ROLE_FIELD_NAME = "role";
    public static final String SOCIAL_TYPE_FIELD_NAME = "social_type";
    private String name;
    private String credential;
    private String email;
    private String salt;
    private Status status;
    private String config;
    private Provider.Type provider;
    private SocialProvider.SocialType socialType;
    private List<Role> roles = new ArrayList<Role>();
    public Account() {
        super();
    }

    public Account(Long id) {
        super(id);
    }

    @Column(name = NAME_FIELD_NAME)
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9\\-\\.])+$")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = CREDENTIAL_FIELD_NAME)
    @NotNull
    public String getCredential() {
        return credential;
    }

    public void setCredential(String password) {
        this.credential = password;
    }

    @Column(name = SALT_FIELD_NAME)
    @NotNull
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = STATUS_FIELD_NAME)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = EMAIL_FIELD_NAME)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = CONFIG_FIELD_NAME)
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Column(name = PROVIDER_FIELD_NAME)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    public Provider.Type getProvider() {
        return provider;
    }

    public void setProvider(Provider.Type provider) {
        this.provider = provider;
    }

    @Column(name = SOCIAL_TYPE_FIELD_NAME)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    public SocialProvider.SocialType getSocialType() {
        return socialType;
    }

    public void setSocialType(SocialProvider.SocialType socialType) {
        this.socialType = socialType;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_role", joinColumns = {@JoinColumn(name = Role.ACCOUNT_FIELD_NAME)}, inverseJoinColumns = {@JoinColumn(name = ROLE_FIELD_NAME)})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public List<String> getGrantedPermissions() {
        Set<String> authorities = new HashSet<>();
        if (CollectionUtils.isNotEmpty(this.roles)) {
            for (Role role : this.roles) {
                for (String permission : role.getGrantedPermissions()) {
                    authorities.add(Permissions.ROLE_PREFIX + permission);
                }
            }
        }
        return new ArrayList<>(authorities);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Account rhs = (Account) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.name, rhs.name)
                .append(this.credential, rhs.credential)
                .append(this.email, rhs.email)
                .append(this.salt, rhs.salt)
                .append(this.status, rhs.status)
                .append(this.config, rhs.config)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(credential)
                .append(email)
                .append(salt)
                .append(status)
                .append(config)
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", config='").append(config).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public enum Status {
        ACTIVE, RESET_PASSWORD, EXPIRED, CREATED
    }

    public static class Config {
        private String firstName;
        private String lastName;

        protected Config() {
        }

        private Config(Builder builder) {
            this.firstName = builder.firstName;
            this.lastName = builder.lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return WebUtil.toJson(this);
        }

        public static class Builder {
            private String firstName;
            private String lastName;

            public Builder lastName(String lastName) {
                this.lastName = lastName;
                return this;
            }

            public Builder firstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public Config build() {
                return new Config(this);
            }
        }
    }
}
