package pl.jakubpiecuch.trainingmanager.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.api.v1.Version1Service;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService.Social;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/{version}")
public class ApiController {

    private Map<ApiVersionService.Version, Version1Service> versionServices;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "config/locale", method = {RequestMethod.GET, RequestMethod.POST}, params = {"lang"})
    public @ResponseBody Object locale(Locale locale) {
        return locale;
    }

    @RequestMapping(value = "dictionary/equipment/type", method = RequestMethod.GET)
    public @ResponseBody Object equipmentTypes(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).equipmentTypes();
    }
    
    @RequestMapping(value = "dictionary/language/{lang}", method = RequestMethod.GET)
    public @ResponseBody Object languages(@PathVariable ApiVersionService.Version version, @PathVariable String lang) throws Exception {
        return versionServices.get(version).language(lang);
    }

    @RequestMapping(value = "dictionary/exercise/first/{first}/max/{max}/partymuscles/{partyMuscles}", method = RequestMethod.GET)
    public @ResponseBody Object exercisesPartyMuscles(@PathVariable ApiVersionService.Version version, @PathVariable int first, @PathVariable int max, @PathVariable Exercise.PartyMuscles partyMuscles) {
        return versionServices.get(version).exercises(first, max, new Exercise.PartyMuscles[] { partyMuscles });
    }

    @RequestMapping(value = "dictionary/exercise/first/{first}/max/{max}", method = RequestMethod.GET)
    public @ResponseBody Object exercises(@PathVariable ApiVersionService.Version version, @PathVariable int first, @PathVariable int max) {
        return versionServices.get(version).exercises(first, max, null);
    }

    @RequestMapping(value = "dictionary/exercise/{id}/comment/first/{first}/max/{max}", method = RequestMethod.GET)
    public @ResponseBody Object comments(@PathVariable ApiVersionService.Version version, @PathVariable long id, @PathVariable int first, @PathVariable int max) {
        return versionServices.get(version).comments(id, first, max);
    }

    @RequestMapping(value = "dictionary/exercise/comment", method = RequestMethod.POST)
    public @ResponseBody Object comment(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception {
        return versionServices.get(version).comment(request);
    }

    @RequestMapping(value = "dictionary/exercise", method = RequestMethod.POST)
    public @ResponseBody void exercise(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception{
       versionServices.get(version).exercise(request);
    }
    
    @RequestMapping(value = "dictionary/social", method = RequestMethod.GET)
    public @ResponseBody Object socials(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).availableSocials();
    }

    @RequestMapping(value = "day/exercise", method = RequestMethod.POST)
    public @ResponseBody Object saveDay(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception{
        return versionServices.get(version).saveDay(request);
    }
    
    @RequestMapping(value = "day/exercise/{id}/progress", method = RequestMethod.GET)
    public @ResponseBody Object exerciseProgress(@PathVariable ApiVersionService.Version version, @PathVariable final long id) {
        return versionServices.get(version).exerciseProgress(id);
    }
    
    @RequestMapping(value = "day/exercise/{date}", method = RequestMethod.GET)
    public @ResponseBody Object dayExercises(@PathVariable ApiVersionService.Version version, @PathVariable Date date) {
        return versionServices.get(version).dayExercises(date);
    }

    @RequestMapping(value = "day/exercise/social/{type}/{id}", method = RequestMethod.POST)
    public @ResponseBody void socialPost(@PathVariable ApiVersionService.Version version, @PathVariable Social.Type type, @PathVariable Long id) {
        versionServices.get(version).socialPost(type, id);
    }

    @RequestMapping(value = "dictionary/equipment", method = RequestMethod.GET)
    public @ResponseBody Object availableEquipment(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).equimpentSet();
    }
    
    @RequestMapping(value = "dictionary/equipment/{type}", method = RequestMethod.GET)
    public @ResponseBody Object equipment(@PathVariable ApiVersionService.Version version, @PathVariable Equipment.Type type) {
        return versionServices.get(version).equipment(type);
    }

    @RequestMapping(value = "dictionary/plan/{id}", method = RequestMethod.GET)
    public @ResponseBody Object plan(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        return versionServices.get(version).plan(id);
    }
    
    @RequestMapping(value = "dictionary/partymuscles", method = RequestMethod.GET)
    public @ResponseBody Object partyMuscles(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).partyMuscles();
    }

    @RequestMapping(value = "calendar/event/start/{start}/end/{end}", method = RequestMethod.GET)
    public @ResponseBody Object calendarEvents(@PathVariable ApiVersionService.Version version, @PathVariable Date start, @PathVariable Date end, Locale locale) {
        return versionServices.get(version).events(start, end, locale);
    }

    @RequestMapping(value = "calendar/event/move", method = RequestMethod.POST)
    public @ResponseBody void moveEvent(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception {
        versionServices.get(version).moveEvent(request);
    }

    @RequestMapping(value = "calendar/event/remove", method = RequestMethod.POST)
    public @ResponseBody void removeEvent(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception{
        versionServices.get(version).removeEvent(request);
    }
    
    @RequestMapping(value = "calendar/event/create", method = RequestMethod.POST)
    public @ResponseBody Object createEvent(@PathVariable ApiVersionService.Version version, HttpServletRequest request) throws Exception {
        return versionServices.get(version).createEvent(request);
    }

    public void setVersionServices(Map<ApiVersionService.Version, Version1Service> versionServices) {
        this.versionServices = versionServices;
    }
}
