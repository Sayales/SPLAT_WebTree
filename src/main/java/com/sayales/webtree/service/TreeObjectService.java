package com.sayales.webtree.service;

import com.sayales.webtree.domain.TreeObject;

import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */
public interface TreeObjectService {



    TreeObject save(TreeObject treeObject);

    List<TreeObject> getAll();

    List<TreeObject> getAllByParentId(Integer parentId);


    int delete(int id);

}
