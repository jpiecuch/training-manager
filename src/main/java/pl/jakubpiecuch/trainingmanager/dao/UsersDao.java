package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface UsersDao {
    Users findById(Long id);
    Users findByName(String name);
}
