package pl.jakubpiecuch.gymhome.domain;

import pl.jakubpiecuch.gymhome.service.repository.RepoObject;

public class RepoCommonEntity extends CommonEntity implements RepoObject {

    public RepoCommonEntity() {
        //jackson needs default public constructor
    }

    public RepoCommonEntity(Long id) {
        super(id);
    }
}
