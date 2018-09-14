package com.sayales.webtree.transformers;

import com.sayales.webtree.domain.DbTreeObject;
import com.sayales.webtree.domain.TreeObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 13.09.2018.
 */



@Component
public class TreeObjectTransformerImpl implements TreeObjectTransformer {

    @Override
    public TreeObject toTreeObject(DbTreeObject dbTreeObject) {
        TreeObject treeObject = new TreeObject();
        treeObject.setId(dbTreeObject.getId());
        treeObject.setText(dbTreeObject.getText());
        treeObject.setChildren(dbTreeObject.isChildren());
        Integer parent = dbTreeObject.getParentId();
        String stringParent;
        if (parent == null)
            stringParent = "#";
        else
            stringParent = parent.toString();
        treeObject.setParent(stringParent);
        return treeObject;
    }

    @Override
    public DbTreeObject toDbTreeObject(TreeObject treeObject) {
        DbTreeObject dbTreeObject = new DbTreeObject();
        dbTreeObject.setText(treeObject.getText());
        dbTreeObject.setChildren(treeObject.isChildren());
        dbTreeObject.setParentId(treeObject.getIntParent());
        return dbTreeObject;
    }
}
