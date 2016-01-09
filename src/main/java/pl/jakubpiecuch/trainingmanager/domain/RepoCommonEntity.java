package pl.jakubpiecuch.trainingmanager.domain;

import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

public class RepoCommonEntity extends CommonEntity implements RepoObject {

    public RepoCommonEntity() {
    }

    public RepoCommonEntity(Long id) {
        super(id);
    }
}
