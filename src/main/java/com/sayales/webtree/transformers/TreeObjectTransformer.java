package com.sayales.webtree.transformers;

import com.sayales.webtree.domain.DbTreeObject;
import com.sayales.webtree.domain.TreeObject;

/**
 * Created by Pavel on 13.09.2018.
 */
public interface TreeObjectTransformer {

    TreeObject toTreeObject(DbTreeObject dbTreeObject);

    DbTreeObject toDbTreeObject(TreeObject treeObject);


}
