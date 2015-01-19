package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Description.Muscles;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;


public interface DescriptionDao extends RepoDao<Description, DescriptionCriteria>  {
    Description findById(Long id);
    Long count();
}
