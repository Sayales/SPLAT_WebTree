package com.sayales.webtree.repository;

import com.sayales.webtree.domain.DbTreeObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Pavel on 08.09.2018.
 */

@Repository
@Transactional
public interface DbTreeObjectJpaRepository extends JpaRepository<DbTreeObject, Integer> {
    void deleteByParentId(int parentId);
}
