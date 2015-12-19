package pl.jakubpiecuch.trainingmanager.service.repository;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.Query;
import org.hibernate.Session;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;

import java.util.*;

/**
 * Created by Rico on 2015-01-02.
 */
public abstract class Criteria<T extends Criteria> {

    protected final String alias;
    protected final String entity;
    protected final String restrictionFormat;
    protected final String lang;

    protected List<String> restrictions = new ArrayList<String>();
    protected Map<String, Object> params = new HashMap<String, Object>();
    protected Long id;
    protected Integer firstResult;
    protected Integer maxResults;
    protected String order;
    private List<Long> excludedIds = new ArrayList<Long>();
    private List<String> joins = new ArrayList<String>();
    public Criteria(String alias, String entity, String lang) {
        this.alias = alias;
        this.entity = entity;
        this.lang = lang;
        this.restrictionFormat = alias + ".%s %s (:%s) ";
    }

    protected void collection(Collection collection, String property, String clause) {
        if (CollectionUtils.isNotEmpty(collection)) {
            restrictions.add(String.format(restrictionFormat, property, clause, property));
            params.put(property, collection);
        }
    }

    protected void validateProperty(String property) {
        if (!ArrayUtils.contains(getValidFields(), property)) {
            throw new IllegalArgumentException();
        }
    }

    protected T addJoin(String join) {
        this.joins.add(join);
        return (T) this;
    }

    protected abstract String[] getValidFields();

    protected abstract void appendRestrictions();

    public Query query(Session session) {
        StringBuilder sb = new StringBuilder("SELECT " + alias + ", over(count(*)) FROM " + entity + " " + alias + " ");

        for (String join : joins) {
            sb.append(join).append(" ");
        }
        if (this.id != null) {
            restrictions.add(" " + alias + ".id = :id ");
            params.put("id", this.id);
        }

        appendRestrictions();

        if (this.id == null) {
            collection(this.excludedIds, "id", "NOT IN");
        }

        if (!restrictions.isEmpty()) {
            sb.append(" WHERE ");
        }

        for (int i = 0; i < restrictions.size(); i++) {
            String restriction = restrictions.get(i);
            sb.append((i > 0 ? " AND " : "") + restriction);
        }

        if (StringUtils.isNotEmpty(order)) {
            sb.append(" ORDER BY " + order + " ");
        }

        Query query = session.createQuery(sb.toString());
        if (this.id == null && this.firstResult != null) {
            query.setFirstResult(this.firstResult);
        }
        if (this.id == null && this.maxResults != null) {
            query.setMaxResults(this.maxResults);
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof List) {
                query.setParameterList(entry.getKey(), (Collection) entry.getValue());
            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }


        return query;
    }

    public T setIdRestriction(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setFirstResultRestriction(Integer firstResult) {
        this.firstResult = firstResult;
        return (T) this;
    }

    public T setMaxResultsRestriction(Integer maxResults) {
        this.maxResults = maxResults;
        return (T) this;
    }

    public T setOrderBy(String property, OrderMode mode, Map<String, OrderResolver> orderResolverMap) {
        validateProperty(property);
        OrderResolver orderResolver = MapUtils.isNotEmpty(orderResolverMap) ? orderResolverMap.get(entity + "-" + property) : null;
        this.order = orderResolver != null ? orderResolver.resolve(lang, alias, property, mode) : (property + " " + mode);
        return (T) this;
    }

    public T addExcludedIdRestriction(Long... ids) {
        if (ArrayUtils.isNotEmpty(ids)) {
            this.excludedIds.addAll(Arrays.asList(ids));
        }
        return (T) this;
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
        Criteria rhs = (Criteria) obj;
        return new EqualsBuilder()
                .append(this.alias, rhs.alias)
                .append(this.entity, rhs.entity)
                .append(this.restrictionFormat, rhs.restrictionFormat)
                .append(this.lang, rhs.lang)
                .append(this.restrictions, rhs.restrictions)
                .append(this.params, rhs.params)
                .append(this.excludedIds, rhs.excludedIds)
                .append(this.joins, rhs.joins)
                .append(this.id, rhs.id)
                .append(this.firstResult, rhs.firstResult)
                .append(this.maxResults, rhs.maxResults)
                .append(this.order, rhs.order)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(alias)
                .append(entity)
                .append(restrictionFormat)
                .append(lang)
                .append(restrictions)
                .append(params)
                .append(excludedIds)
                .append(joins)
                .append(id)
                .append(firstResult)
                .append(maxResults)
                .append(order)
                .toHashCode();
    }

    public enum OrderMode {
        ASC, DESC
    }


}
