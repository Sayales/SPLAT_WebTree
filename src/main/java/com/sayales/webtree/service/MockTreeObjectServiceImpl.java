package com.sayales.webtree.service;

import com.sayales.webtree.domain.TreeObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 08.09.2018.
 */

@Service
public class MockTreeObjectServiceImpl implements TreeObjectService {


    List<TreeObject> treeObjects = new ArrayList<TreeObject>();
    {
        TreeObject obj1 = new TreeObject(1, "Odin");
        treeObjects.add(obj1);
        TreeObject obj2 = new TreeObject(2, "Dva");
        treeObjects.add(obj2);
        TreeObject obj3 = new TreeObject(3, "Tri");
        treeObjects.add(obj3);
        //obj1.getChildren().add(obj2);
        obj2.setParent("1");
        //obj2.getChildren().add(obj3);
        obj3.setParent("2");
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public TreeObject save(TreeObject treeObject) {
        boolean isPresent = false;
        for (int i = 0; i < treeObjects.size(); i++) {
            if (treeObjects.get(i).getId() == treeObject.getId()){
                treeObjects.remove(i);
                treeObjects.add(i,treeObject);
                isPresent = true;
            }
        }
        if (!isPresent) {
            treeObjects.add(treeObject);
        }
        System.out.println("Objects update: " + treeObjects);
        return treeObject;
    }

    @Override
    public List<TreeObject> getAll() {
        return treeObjects;
    }

    @Override
    public TreeObject get(int id) {
        return null;
    }

    @Override
    public TreeObject getParent(int id) {
        return null;
    }

    @Override
    public int delete(int id) {
        treeObjects.removeIf(elem -> (elem.getId() == id || elem.getIntParent() == id));
        return id;
    }
}
