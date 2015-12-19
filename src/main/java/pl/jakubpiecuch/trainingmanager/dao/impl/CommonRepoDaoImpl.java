package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

public class CommonRepoDaoImpl<E extends CommonEntity, C extends Criteria> extends AbstractRepoDao<E, C> implements RepoDao<E, C> {

}
