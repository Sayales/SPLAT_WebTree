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
            dbTreeObject.setChildren(treeObject.isChildren());
            repository.save(dbTreeObject);
        }
        else {
            dbTreeObject = new DbTreeObject();
            dbTreeObject.setParentId(treeObject.getIntParent());
            dbTreeObject.setText(treeObject.getText());
            dbTreeObject.setChildren(treeObject.isChildren());
            dbTreeObject = repository.save(dbTreeObject);
            treeObject.setId(dbTreeObject.getId());
        }
        DbTreeObject dbTreeParentObject = repository.findOne(treeObject.getIntParent());
        dbTreeParentObject.setChildren(checkChildren(dbTreeParentObject));
        repository.save(dbTreeParentObject);
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
        DbTreeObject dbTreeParentObject = repository.findOne(repository.findOne(id).getParentId());
        repository.deleteByParentId(id);
        repository.delete(id);
        dbTreeParentObject.setChildren(checkChildren(dbTreeParentObject));
        repository.save(dbTreeParentObject);
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

    private boolean checkChildren(DbTreeObject object) {
        return repository.findAllByParentId(object.getId()).size() != 0;
    }
}
