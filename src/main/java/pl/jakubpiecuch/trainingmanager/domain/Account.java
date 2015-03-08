package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account extends VersionedEntity {
    public enum Status {
        ACTIVE, RESET_PASSWORD, EXPIRED, CREATED
    }

    private String name;
    private String password;
    private String email;
    private String salt;
    private Status status;
    private String config;

    public Account() {
        super();
    }

    public Account(Long id) {
        super(id);
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "config")
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
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

        @Override
        public String toString() {
            return WebUtil.toJson(this);
        }
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
                .append(this.password, rhs.password)
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
                .append(password)
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
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", config='").append(config).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
