package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

@MappedSuperclass
public class BaseComment<T extends CommonEntity> extends VersionedEntity {

    private String comment;
    private Account account;
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
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BaseComment rhs = (BaseComment) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.comment, rhs.comment)
                .append(this.account, rhs.account)
                .append(this.commented, rhs.commented)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(comment)
                .append(account)
                .append(commented)
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseComment{");
        sb.append("comment='").append(comment).append('\'');
        sb.append(", account=").append(account);
        sb.append(", commented=").append(commented);
        sb.append('}');
        return sb.toString();
    }
}
