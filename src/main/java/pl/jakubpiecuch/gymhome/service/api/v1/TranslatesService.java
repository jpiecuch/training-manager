package pl.jakubpiecuch.gymhome.service.api.v1;

import java.util.List;
import java.util.Map;

/**
 * Created by jakub on 29.12.2015.
 */
public interface TranslatesService {
    List<String> languages();
    Map<String, String> translates(String language);
}
