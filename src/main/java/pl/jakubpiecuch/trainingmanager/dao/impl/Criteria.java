package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.Query;
import org.hibernate.Session;
import pl.jakubpiecuch.trainingmanager.dao.EmptyPageResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

import java.util.*;

/**
 * Created by Rico on 2015-01-02.
 */
public abstract class Criteria<T extends Criteria> {

    private static final String PREFIX_FORMAT =  "SELECT %s FROM %s %s ";
    private static final String COUNT_PREFIX_FORMAT =  "SELECT COUNT(%s) FROM %s %s ";
    private static final String RESTRICTIONS_FORMAT =  " %s.%s = :%s ";
    private static final String FETCH = "FETCH";
    public static final String LEFT_JOIN_FETCH = "LEFT JOIN FETCH ";

    protected final String alias;
    protected final String entity;
    protected final String restrictionFormat;
    protected final String lang;

    protected List<String> restrictions = new ArrayList<>();
    protected Map<String, Object> params = new HashMap<>();
    protected Long id;
    protected Integer firstResult;
    protected Integer maxResults;
    protected String order;
    private List<Long> excludedIds = new ArrayList<>();
    private List<String> joins = new ArrayList<>();
    private Map<String, OrderResolver> orderResolverMap = new HashMap<>();

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

    protected void appendFromToRestrictions(String field, Date from, Date to) {
        if (this.id == null) {
            if (from != null) {
                restrictions.add(" " + alias + "."+field+" >= :from ");
                params.put("from", from);
            }
            if (to != null) {
                restrictions.add(" " + alias + "."+field+" <= :to ");
                params.put("to", to);
            }
        }
    }

    protected void appendCurrentUserRestriction(String field) {
        restrictions.add(" " + alias + "."+field+" = :accountId ");
        params.put("accountId", AuthenticatedUserUtil.getUser().getId());
    }

    protected abstract String[] getValidFields();

    protected abstract void appendRestrictions();

    long count(Session session) {
        boolean isSearchById = this.id != null;
        StringBuilder sb = createCommonQuery(COUNT_PREFIX_FORMAT, isSearchById);

        Query query = session.createQuery(sb.toString());
        fillQueryWithParameters(query);

        return (long) query.uniqueResult();
    }

    PageResult page(Session session) {
        boolean isSearchById = this.id != null;
        StringBuilder sb = createCommonQuery(PREFIX_FORMAT, isSearchById);

        if (StringUtils.isNotEmpty(order)) {
            sb.append(" ORDER BY " + order + " ");
        }

        Query query = session.createQuery(sb.toString());
        fillQueryWithParameters(query);
        fillQueryWithPageRestrictions(query, isSearchById);

        final long count = count(session);
        final List list  = query.list();

        return count > 0 ? new PageResult<Object>() {
            @Override
            public List getResult() {
                return list;
            }

            @Override
            public long getCount() {
                return count;
            }
        } : new EmptyPageResult<>();
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

    public T setOrderBy(String property, OrderMode mode) {
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

    T appendOrderResolvers(Map<String, OrderResolver> orderResolverMap) {
        this.orderResolverMap.putAll(orderResolverMap);
        return (T) this;
    }

    private StringBuilder createCommonQuery(String prefix, boolean isSearchById) {
        StringBuilder sb = new StringBuilder(String.format(prefix, alias, entity, alias));

        boolean isCount = COUNT_PREFIX_FORMAT == prefix;

        for (String join : this.joins) {
            sb.append(isCount ? StringUtils.remove(join, FETCH) : join).append(" ");
        }

        if (isSearchById) {
            restrictions.add(String.format(RESTRICTIONS_FORMAT, alias, CommonEntity.ID_FIELD_NAME, CommonEntity.ID_FIELD_NAME));
            params.put(CommonEntity.ID_FIELD_NAME, this.id);
        }

        appendRestrictions();

        if (!isSearchById) {
            collection(this.excludedIds, CommonEntity.ID_FIELD_NAME, "NOT IN");
        }

        if (!restrictions.isEmpty()) {
            sb.append(" WHERE ");
        }

        for (int i = 0; i < restrictions.size(); i++) {
            String restriction = restrictions.get(i);
            sb.append((i > 0 ? " AND " : "") + restriction);
        }
        return sb;
    }

    private void fillQueryWithParameters(Query query) {
        for (Map.Entry<String, Object> entry : this.params.entrySet()) {
            if (entry.getValue() instanceof List) {
                query.setParameterList(entry.getKey(), (Collection) entry.getValue());
            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    private void fillQueryWithPageRestrictions(Query query, boolean isSearchById) {
        if (!isSearchById) {
            query.setFirstResult(ObjectUtils.firstNonNull(this.firstResult, 0));
            query.setMaxResults(ObjectUtils.firstNonNull(this.maxResults, Integer.MAX_VALUE));
        }
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
