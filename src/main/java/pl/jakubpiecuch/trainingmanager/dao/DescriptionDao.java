package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Description.PartyMuscles;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;


public interface DescriptionDao extends RepoDao<Description, DescriptionCriteria>  {
    Description findById(Long id);
    PageResult<Description> findPage(int firstResult, int maxResult, PartyMuscles[] partyMuscles);
    Long count();
}
