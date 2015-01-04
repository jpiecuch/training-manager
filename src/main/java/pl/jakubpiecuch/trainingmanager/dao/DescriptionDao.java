package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Description.PartyMuscles;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import java.util.List;

public interface DescriptionDao extends CoreDao {
    List<Description> findAll();
    List<Description> findByPartyMuscles(PartyMuscles pms);
    Description findById(Long id);
    PageResult<Description> findPage(int firstResult, int maxResult, PartyMuscles[] partyMuscles);
    PageResult<Description> findByCriteria(DescriptionCriteria criteria);
    Long count();
}
