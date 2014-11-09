package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseComment<T> extends VersionedEntity {
    
    private String comment;
    private Users user;
    private T commented;

    public BaseComment() {
    }
    
    public BaseComment(Long id) {
        super(id);
    }
    
    @JoinColumn(name = "commented")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public T getCommented() {
        return this.commented;
    }

    @JsonProperty(value = "commented")
    public void setCommented(T commented) {
        this.commented = commented;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JoinColumn(name = "creator")
    @ManyToOne(fetch = FetchType.LAZY)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
