package com.sayales.webtree.service;

import com.sayales.webtree.domain.DbTreeObject;
import com.sayales.webtree.domain.TreeObject;
import com.sayales.webtree.repository.DbTreeObjectJpaRepository;
import com.sayales.webtree.transformers.TreeObjectTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 06.09.2018.
 */

@Service
public class TreeObjectServiceImpl implements TreeObjectService {

    @Autowired
    private DbTreeObjectJpaRepository repository;

    @Autowired
    private TreeObjectTransformer transformer;

    @Override
    public TreeObject save(TreeObject treeObject) {
        DbTreeObject dbTreeObject =  repository.findOne(treeObject.getId());
        if (dbTreeObject != null) {
            dbTreeObject.setText(treeObject.getText());
            dbTreeObject.setParentId(treeObject.getIntParent());
            dbTreeObject.setChildren(checkChildren(dbTreeObject));
            repository.save(dbTreeObject);
            treeObject.setChildren(dbTreeObject.isChildren());
        }
        else {
            dbTreeObject = new DbTreeObject();
            dbTreeObject.setParentId(treeObject.getIntParent());
            dbTreeObject.setText(treeObject.getText());
            dbTreeObject = repository.save(dbTreeObject);
            dbTreeObject.setChildren(checkChildren(dbTreeObject));
            repository.save(dbTreeObject);
            treeObject.setId(dbTreeObject.getId());
            treeObject.setChildren(dbTreeObject.isChildren());
        }
        if (dbTreeObject.getParentId() != null) {
            DbTreeObject dbTreeParentObject = repository.findOne(treeObject.getIntParent());
            dbTreeParentObject.setChildren(checkChildren(dbTreeParentObject));
            repository.save(dbTreeParentObject);
        }
        return treeObject;
    }

    @Override
    public List<TreeObject> getAll() {
       return convertTreeObjects(repository.findAll());
    }

    @Override
    public List<TreeObject> getAllByParentId(Integer parentId) {
        return convertTreeObjects(repository.findAllByParentId(parentId));
    }


    @Override
    public int delete(int id) {
        if (repository.findOne(id).getParentId() != null) {
            DbTreeObject dbTreeParentObject = repository.findOne(repository.findOne(id).getParentId());
            deepDelete(id);
            dbTreeParentObject.setChildren(checkChildren(dbTreeParentObject));
            repository.save(dbTreeParentObject);
        }
        else {
            deepDelete(id);
        }
        return id;
    }

    private List<TreeObject> convertTreeObjects(List<DbTreeObject> dbTreeObjects) {
        return dbTreeObjects.stream().
                map(dbObj -> transformer.toTreeObject(dbObj)).collect(Collectors.toList());
    }

    private int deepDelete(int id) {
        List<DbTreeObject> children = repository.findAllByParentId(id);
        repository.delete(id);
        for (DbTreeObject child : children) {
            if (child.isChildren()) {
                deepDelete(child.getId());
                repository.delete(child.getId());
            }
            else
                repository.delete(child.getId());
        }
        return id;
    }

    private boolean checkChildren(DbTreeObject object) {
        List<DbTreeObject> children = repository.findAllByParentId(object.getId());
        return repository.findAllByParentId(object.getId()).size() != 0;
    }
}
