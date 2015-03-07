package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class CommonEntity implements Serializable {

    private Long id;

    public CommonEntity() {
    }

    public CommonEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CommonEntity)) {
            return false;
        }
        CommonEntity other = (CommonEntity) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

}
