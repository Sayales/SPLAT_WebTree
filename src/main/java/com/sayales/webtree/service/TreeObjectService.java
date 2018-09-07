package com.sayales.webtree.service;

import com.sayales.webtree.domain.TreeObject;
import com.sun.istack.internal.Nullable;

/**
 * Created by Pavel on 06.09.2018.
 */
public interface TreeObjectService {

    String getValue();

    void save(TreeObject treeObject);

    @Nullable
    TreeObject getParent();

}
