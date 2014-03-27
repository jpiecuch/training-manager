package pl.jakubpiecuch.trainingmanager.dao.administration;

import pl.jakubpiecuch.trainingmanager.domain.administration.Users;

public interface UsersDao {
    Users findById(Long id);
    Users findByName(String name);
}
