package pl.jakubpiecuch.trainingmanager.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class VersionedEntity extends CommonEntity {
    
    private Date created;
    private Date updated;
    private int version;

    public VersionedEntity() {
    }
    
    public VersionedEntity(Long id) {
        super(id);
    }
    
    
    @Column(name = "created", insertable = false, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "updated", insertable = false, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    @Version
    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }
}
