package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.api.v1.Version1Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/{version}/dictionary")
public class ApiDictionaryController {

    private Map<ApiVersionService.Version, Version1Service> versionServices;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "equipment/type", method = RequestMethod.GET)
    public @ResponseBody Object equipmentTypes(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).equipmentTypes();
    }
    
    @RequestMapping(value = "language/{lang}", method = RequestMethod.GET)
    public @ResponseBody Object languages(@PathVariable ApiVersionService.Version version, @PathVariable String lang) throws Exception {
        return versionServices.get(version).language(lang);
    }

    @RequestMapping(value = "exercise/first/{first}/max/{max}/partymuscles/{partyMuscles}", method = RequestMethod.GET)
    public @ResponseBody Object exercisesPartyMuscles(@PathVariable ApiVersionService.Version version, @PathVariable int first, @PathVariable int max, @PathVariable Exercise.PartyMuscles partyMuscles) {
        return versionServices.get(version).exercises(first, max, new Exercise.PartyMuscles[] { partyMuscles });
    }

    @RequestMapping(value = "exercise/first/{first}/max/{max}", method = RequestMethod.GET)
    public @ResponseBody Object exercises(@PathVariable ApiVersionService.Version version, @PathVariable int first, @PathVariable int max) {
        return versionServices.get(version).exercises(first, max, null);
    }

    @RequestMapping(value = "exercise/{id}/comment/first/{first}/max/{max}", method = RequestMethod.GET)
    public @ResponseBody Object comments(@PathVariable ApiVersionService.Version version, @PathVariable long id, @PathVariable int first, @PathVariable int max) {
        return versionServices.get(version).comments(id, first, max);
    }

    @RequestMapping(value = "exercise/comment", method = RequestMethod.POST)
    public @ResponseBody Object comment(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception {
        return versionServices.get(version).comment(request);
    }

    @RequestMapping(value = "exercise", method = RequestMethod.POST)
    public @ResponseBody void exercise(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception{
       versionServices.get(version).exercise(request);
    }
    
    @RequestMapping(value = "social", method = RequestMethod.GET)
    public @ResponseBody Object socials(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).availableSocials();
    }

    @RequestMapping(value = "equipment", method = RequestMethod.GET)
    public @ResponseBody Object availableEquipment(@PathVariable ApiVersionService.Version version, @RequestParam Integer[] type) {
        return versionServices.get(version).equipments(type);
    }

    @RequestMapping(value = "plan/{id}", method = RequestMethod.GET)
    public @ResponseBody Object plan(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        return versionServices.get(version).plan(id);
    }
    
    @RequestMapping(value = "partymuscles", method = RequestMethod.GET)
    public @ResponseBody Object partyMuscles(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).partyMuscles();
    }

    public void setVersionServices(Map<ApiVersionService.Version, Version1Service> versionServices) {
        this.versionServices = versionServices;
    }
}
