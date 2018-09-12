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
            dbTreeObject.setChildren(checkChildren(repository.findOne(dbTreeObject.getId())));
            repository.save(dbTreeObject);
        }
        else {
            dbTreeObject = new DbTreeObject();
            dbTreeObject.setParentId(treeObject.getIntParent());
            dbTreeObject.setText(treeObject.getText());
            dbTreeObject.setChildren(checkChildren(repository.findOne(dbTreeObject.getId())));
            dbTreeObject = repository.save(dbTreeObject);
            treeObject.setId(dbTreeObject.getId());
        }
        if (treeObject.getIntParent() != null) {
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
           // repository.delete(id);
            deepDelete(id);
            dbTreeParentObject.setChildren(checkChildren(dbTreeParentObject));
            repository.save(dbTreeParentObject);
        }
        else {
           // repository.delete(id);
            deepDelete(id);
        }
        return id;
    }

    private List<TreeObject> convertTreeObjects(List<DbTreeObject> dbTreeObjects) {
        List<TreeObject> result = new ArrayList<>();
        for (DbTreeObject dbObj : dbTreeObjects) {
            if (dbObj.getParentId() == null)
                result.add(new TreeObject(dbObj.getId(), dbObj.getText(), "#", dbObj.isChildren()));
            else
                result.add(new TreeObject(dbObj.getId(), dbObj.getText(), dbObj.getParentId().toString(), dbObj.isChildren()));
        }
        return result;
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
        return repository.findAllByParentId(object.getId()).size() != 0;
    }
}
