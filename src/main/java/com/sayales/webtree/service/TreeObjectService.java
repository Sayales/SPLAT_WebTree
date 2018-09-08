package com.sayales.webtree.service;

import com.sayales.webtree.domain.TreeObject;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */
public interface TreeObjectService {

    String getValue();

    void save(TreeObject treeObject);

    List<TreeObject> getAll();

    TreeObject get(int id);

    @Nullable
    TreeObject getParent(int id);

    int delete(int id);

}
