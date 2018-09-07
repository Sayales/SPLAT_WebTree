package com.sayales.webtree.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */


public class TreeObject {

    private int id;

    private String text;

    private int parent;

    public TreeObject(int id) {
        this.id = id;
    }

    public TreeObject(int id, String value) {
        this.id = id;
        this.text = value;
        parent = -1;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public String getParent() { //cuz of json format for jstree
        return (parent == -1) ? "#" : String.valueOf(parent);
    }

    public int getIntParent() {
        return parent;
    }

    public void setParent(int parentID) {
        this.parent = parentID;
    }

   


}


