package com.sayales.webtree.service;

import com.sayales.webtree.domain.DbTreeObject;
import com.sayales.webtree.domain.TreeObject;
import com.sayales.webtree.repository.DbTreeObjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */

@Service
public class TreeObjectServiceImpl implements TreeObjectService {

    @Autowired
    private DbTreeObjectJpaRepository repository;

    @Override
    public TreeObject save(TreeObject treeObject) {
        DbTreeObject dbTreeObject =  repository.findOne(treeObject.getId());
        if (dbTreeObject != null) {
            dbTreeObject.setText(treeObject.getText());
            dbTreeObject.setParentId(treeObject.getIntParent());
            repository.save(dbTreeObject);
        }
        else {
            dbTreeObject = new DbTreeObject();
            dbTreeObject.setParentId(treeObject.getIntParent());
            dbTreeObject.setText(treeObject.getText());
            dbTreeObject = repository.save(dbTreeObject);
            treeObject.setId(dbTreeObject.getId());
        }
        return treeObject;
    }

    @Override
    public List<TreeObject> getAll() {
        List<DbTreeObject> dbAll = repository.findAll();
        List<TreeObject> result = new ArrayList<>();
        for (DbTreeObject dbObj : dbAll) {
            if (dbObj.getParentId() == null)
                result.add(new TreeObject(dbObj.getId(), dbObj.getText(), "#"));
            else
                result.add(new TreeObject(dbObj.getId(), dbObj.getText(), dbObj.getParentId().toString()));
        }
        return result;
    }



    @Override
    public int delete(int id) {
        repository.deleteByParentId(id);
        repository.delete(id);
        return id;
    }
}
