package pl.jakubpiecuch.trainingmanager.service.support.social;

import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.service.support.SupportService;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
public class SocialSupportService implements SupportService<SocialProvider> {

    @Override
    public List<SocialProvider> supported() {
        return Lists.newArrayList(
            new SocialProvider.Builder().id(SocialProvider.SocialType.FACEBOOK.getProviderId())
                    .scope("email,read_friendlists,publish_actions").build(),
            new SocialProvider.Builder().id(SocialProvider.SocialType.TWITTER.getProviderId())
                    .scope("publish_stream").build(),
            new SocialProvider.Builder().id(SocialProvider.SocialType.GOOGLE.getProviderId())
                    .scope("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile").build());
    }
}
