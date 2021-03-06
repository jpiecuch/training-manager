package pl.jakubpiecuch.gymhome.service.dictionary.constants;

import org.apache.commons.lang3.ArrayUtils;
import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.domain.Equipment;
import pl.jakubpiecuch.gymhome.domain.EquipmentDescriptor;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.service.dictionary.Dictionary;
import pl.jakubpiecuch.gymhome.service.support.SupportService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2015-01-01.
 */
public class ConstantsDictionary implements Dictionary {

    private static final long GOAL_MAP_ID = 1;
    private static final long EXERCISE_MUSCLES_MAP_ID = 2;
    private static final long EXERCISE_TYPE_ID = 3;
    private static final long EXERCISE_LEVEL_ID = 4;
    private static final long EXERCISE_MECHANICS_ID = 5;
    private static final long EXERCISE_FORCE_ID = 6;
    private static final long LANGS_ID = 7;
    private static final long EQUIPMENT_TYPE_ID = 8;
    private static final long BARBELL_TYPE_ID = 9;
    private static final long SOCIALS_ID = 10;
    private static final long EXERCISE_SETS_ID = 11;
    private static final long EXERCISE_LATERAL_ID = 12;

    private static final long[] SECURED_IDS = new long[]{
            GOAL_MAP_ID, EXERCISE_MUSCLES_MAP_ID, EXERCISE_TYPE_ID, EXERCISE_LEVEL_ID,
            EQUIPMENT_TYPE_ID, EXERCISE_MECHANICS_ID, EXERCISE_FORCE_ID, EQUIPMENT_TYPE_ID,
            BARBELL_TYPE_ID, EXERCISE_SETS_ID
    };
    protected Map<Long, List> map = new HashMap<>();
    private SupportService socialSupportService;
    private Dictionary securedDictionary;
    private String[] langs;

    @Override
    public List retrieve(long id) {
        return ArrayUtils.contains(SECURED_IDS, id) ? securedDictionary.retrieve(id) : map.get(id);
    }

    @Override
    public Map<Long, List> retrieve(long[] ids) {
        return securedDictionary.retrieve(ids);
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public void setSocialSupportService(SupportService socialSupportService) {
        this.socialSupportService = socialSupportService;
    }

    public void setSecuredDictionary(Dictionary securedDictionary) {
        this.securedDictionary = securedDictionary;
    }

    @PostConstruct
    protected void afterPropertiesSet() {
        map.put(LANGS_ID, Arrays.asList(langs));
        map.put(SOCIALS_ID, socialSupportService.supported());
        map.put(GOAL_MAP_ID, Arrays.asList(Plan.Goal.values()));
        map.put(EXERCISE_MUSCLES_MAP_ID, Arrays.asList(Description.Muscles.values()));
        map.put(EXERCISE_TYPE_ID, Arrays.asList(Description.Type.values()));
        map.put(EXERCISE_LEVEL_ID, Arrays.asList(Description.Level.values()));
        map.put(EXERCISE_MECHANICS_ID, Arrays.asList(Description.Mechanics.values()));
        map.put(EXERCISE_FORCE_ID, Arrays.asList(Description.Force.values()));
        map.put(EQUIPMENT_TYPE_ID, Arrays.asList(Equipment.Type.values()));
        map.put(BARBELL_TYPE_ID, Arrays.asList(EquipmentDescriptor.BarbellConfig.Type.values()));
        map.put(EXERCISE_SETS_ID, Arrays.asList(Description.Sets.values()));
        map.put(EXERCISE_LATERAL_ID, Arrays.asList(Description.Lateral.values()));
    }
}
