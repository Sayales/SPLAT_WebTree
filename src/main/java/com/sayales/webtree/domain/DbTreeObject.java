package com.sayales.webtree.domain;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

/**
 * Created by Pavel on 08.09.2018.
 */

@Entity
@Table(name = "db_tree_objects")
public class DbTreeObject {


    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "text_value")
    private String text;

    @Column(name = "children")
    private boolean children;

    public DbTreeObject() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }
}
