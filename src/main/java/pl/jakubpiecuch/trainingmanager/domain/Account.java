package pl.jakubpiecuch.trainingmanager.domain;

import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account extends VersionedEntity {
    public enum Status { ACTIVE, RESET_PASSWORD, EXPIRED, CREATED }

    private String name;
    private String password;
    private String email;
    private String salt;
    private Status status;
    private String config;

    public Account() {
    }

    public Account(Long id) {
        this.setId(id);
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
            try {
                return WebUtil.toJson(this);
            } catch (Exception e) {
                return "";
            }
        }
    }
}
