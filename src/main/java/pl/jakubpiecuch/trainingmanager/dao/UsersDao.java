package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface UsersDao extends CoreDao {
    Users findByUniques(Long id, String name, String email);
}
