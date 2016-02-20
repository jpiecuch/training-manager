package pl.jakubpiecuch.trainingmanager.domain;

import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

public class RepoCommonEntity extends CommonEntity implements RepoObject {

    public RepoCommonEntity() {
        //jackson needs default public constructor
    }

    public RepoCommonEntity(Long id) {
        super(id);
    }
}
