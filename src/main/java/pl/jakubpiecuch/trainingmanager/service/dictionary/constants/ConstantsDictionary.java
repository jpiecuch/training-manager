package pl.jakubpiecuch.trainingmanager.service.dictionary.constants;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.dictionary.Dictionary;

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
    private static final long EXCERCISE_TYPE_ID = 3;
    private static final long EXCERCISE_LEVEL_ID = 4;
    private static final long EXCERCISE_MECHANICS_ID = 5;
    private static final long EXERCISE_FORCE_ID = 6;

    private static final long[] MAP_IDS = new long[] {GOAL_MAP_ID, EXERCISE_MUSCLES_MAP_ID};

    private static final Map<Long, List> MAP = new HashMap<Long, List>() {
        {
            put(GOAL_MAP_ID, Arrays.asList(Plan.Goal.values()));
            put(EXERCISE_MUSCLES_MAP_ID, Arrays.asList(Description.PartyMuscles.values()));
            put(EXCERCISE_TYPE_ID, Arrays.asList(Description.Type.values()));
            put(EXCERCISE_LEVEL_ID, Arrays.asList(Description.Level.values()));
            put(EXCERCISE_MECHANICS_ID, Arrays.asList(Description.Mechanics.values()));
            put(EXERCISE_FORCE_ID, Arrays.asList(Description.Force.values()));
        }
    };

    @Override
    public List<Object> retrieve(long id) {
        return MAP.get(id);
    }
}
