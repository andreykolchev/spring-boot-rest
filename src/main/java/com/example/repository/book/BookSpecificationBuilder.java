package com.example.repository.book;

import com.example.model.dto.search.SearchItem;
import com.example.model.entity.BookEntity;
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
public class BookSpecificationBuilder {

    public static final String TITLE = "title";
    public static final String AUTHORS = "authors";
    public static final String AUTHOR_NAME = "authorName";
    public static final String AUTHOR_FULL_NAME = "fullName";

    public Specification<BookEntity> build(List<SearchItem> searchItems, List<Sort.Order> orderList) {
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
                                               Root<BookEntity> root,
                                               List<Sort.Order> orderList,
                                               CriteriaBuilder cb) {
        switch (item.getKey()) {
            case TITLE:
                orderList.add(new Sort.Order(Sort.Direction.valueOf(item.getSort().toUpperCase()), TITLE));
                return cb.like(root.get(TITLE), "%" + item.getValue() + "%");
            case AUTHOR_NAME:
                orderList.add(new Sort.Order(Sort.Direction.valueOf(item.getSort().toUpperCase()), AUTHOR_FULL_NAME));
                return cb.equal(root.join(AUTHORS).get(AUTHOR_FULL_NAME), item.getValue());
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
