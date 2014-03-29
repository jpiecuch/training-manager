package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface UsersDao extends CoreDao {
    Users findById(Long id);
    Users findByName(String name);
}
