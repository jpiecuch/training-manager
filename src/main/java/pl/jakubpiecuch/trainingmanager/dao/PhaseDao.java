package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Phase;

import java.util.List;

public interface PhaseDao {
    List<Phase> findAll();
}
