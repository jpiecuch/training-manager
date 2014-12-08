package pl.jakubpiecuch.trainingmanager.service.social;

import pl.jakubpiecuch.trainingmanager.service.SupportService;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
public class SocialSupportService implements SupportService<SocialProvider> {

    @Override
    public List<SocialProvider> supported() {
        List<SocialProvider> result = new ArrayList<SocialProvider>();
        result.add(new SocialProvider.Builder().id("facebook").scope("email,read_friendlists,publish_actions").build());
        result.add(new SocialProvider.Builder().id("twitter").scope("publish_stream").build());
        result.add(new SocialProvider.Builder().id("google").scope("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile").build());
        return result;
    }
}
