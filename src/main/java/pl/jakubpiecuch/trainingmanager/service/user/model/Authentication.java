package pl.jakubpiecuch.trainingmanager.service.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authentication {
    public static final String BEAN_NAME = "authentication";
    public static final String USERNAME_FIELD = "username";
    public static final String CREDENTIAL_FIELD = "password";
    public static final String FIRST_NAME_FIELD = "firstName";
    public static final String LAST_NAME_FIELD = "lastName";
    public static final String EMAIL_FIELD = "email";
    public static final String PROVIDER_FIELD = "provider";
    public static final String SOCIAL_FIELD = "social";


    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Provider.Type provider;
    private SocialProvider.SocialType social;
    private List<String> authorities;

    public Authentication() {
        //jackson requires default public constructor
    }

    public Authentication(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.username = account.getName();
        this.authorities = account.getGrantedPermissions();
        Account.Config config = WebUtil.fromJson(account.getConfig(), Account.Config.class);
        this.firstName = config.getFirstName();
        this.lastName = config.getLastName();
    }

    public SocialProvider.SocialType getSocial() {
        return social;
    }

    public void setSocial(SocialProvider.SocialType social) {
        this.social = social;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Provider.Type getProvider() {
        return provider;
    }

    public void setProvider(Provider.Type provider) {
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
