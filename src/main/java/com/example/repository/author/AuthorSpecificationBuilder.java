package com.example.repository.author;

import com.example.model.dto.search.SearchItem;
import com.example.model.entity.AuthorEntity;
import com.example.model.error.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AuthorSpecificationBuilder {

    public static final String FULL_NAME = "fullName";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String DATE_OF_BIRTH_FROM = "dateOfBirthFrom";
    public static final String DATE_OF_BIRTH_TO = "dateOfBirthTo";


    public Specification<AuthorEntity> build(List<SearchItem> searchItems, List<Sort.Order> orderList) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.and(
                    searchItems.stream()
                            .map(searchItem -> getPredicateBySearchItem(searchItem, root, orderList, criteriaBuilder))
                            .toArray(Predicate[]::new)
            );
        };
    }

    private Predicate getPredicateBySearchItem(SearchItem item,
                                               Root<AuthorEntity> root,
                                               List<Sort.Order> orderList,
                                               CriteriaBuilder cb) {
        switch (item.getKey()) {
            case FULL_NAME:
                orderList.add(new Sort.Order(Sort.Direction.valueOf(item.getSort().toUpperCase()), FULL_NAME));
                return cb.like(root.get(FULL_NAME), "%" + item.getValue() + "%");
            case DATE_OF_BIRTH_FROM:
                orderList.add(new Sort.Order(Sort.Direction.valueOf(item.getSort().toUpperCase()), DATE_OF_BIRTH));
                return cb.greaterThanOrEqualTo(root.get(DATE_OF_BIRTH), parseDateParam(item.getValue()));
            case DATE_OF_BIRTH_TO:
                orderList.add(new Sort.Order(Sort.Direction.valueOf(item.getSort().toUpperCase()), DATE_OF_BIRTH));
                return cb.lessThanOrEqualTo(root.get(DATE_OF_BIRTH), parseDateParam(item.getValue()));
            default:
                throw new ErrorException("Invalid search key: " + item.getKey());
        }
    }

    private Date parseDateParam(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            log.warn(e.getLocalizedMessage());
            throw new ErrorException("Invalid search value: " + dateStr);
        }
    }
}
