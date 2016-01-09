package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.RepoCommonEntity;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

public class CommonRepoDaoImpl<E extends RepoCommonEntity, C extends Criteria> extends AbstractRepoDao<E, C> implements RepoDao<E, C> {

}
