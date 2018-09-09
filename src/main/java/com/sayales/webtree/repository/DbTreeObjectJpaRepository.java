package com.sayales.webtree.repository;

import com.sayales.webtree.domain.DbTreeObject;
import com.sun.istack.internal.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by Pavel on 08.09.2018.
 */

@Repository
@Transactional
public interface DbTreeObjectJpaRepository extends JpaRepository<DbTreeObject, Integer> {

    void deleteByParentId(int parentId);

    List<DbTreeObject> findAllByParentId(@Nullable  Integer ParentId);

    DbTreeObject findOne(@Nullable Integer id);

}
