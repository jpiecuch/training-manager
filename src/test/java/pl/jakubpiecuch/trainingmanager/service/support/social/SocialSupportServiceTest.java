package pl.jakubpiecuch.trainingmanager.service.support.social;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.List;



public class SocialSupportServiceTest {

    private SocialSupportService socialSupportService = new SocialSupportService();

    @Test
    public void testSupported() throws Exception {
        List<SocialProvider> supported = socialSupportService.supported();

        Assert.assertEquals(3, supported.size());

        List<SocialProvider.SocialType> types = Lists.newArrayList(SocialProvider.SocialType.values());
        for(SocialProvider provider : supported) {
            types.remove(provider.getSocialType());
        }

        Assert.assertEquals(0, types.size());
    }
}