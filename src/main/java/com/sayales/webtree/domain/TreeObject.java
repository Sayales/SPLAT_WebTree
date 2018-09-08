package com.sayales.webtree.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */


public class TreeObject {

    private int id;

    private String text;

    private String parent;

    public TreeObject() {
    }

    public TreeObject(int id, String text, String parent) {
        this.id = id;
        this.text = text;
        this.parent = parent;
    }

    public TreeObject(int id) {
        this.id = id;
    }

    public TreeObject(int id, String value) {
        this.id = id;
        this.text = value;
        parent = "#";
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
        return parent;
    }


    public void setParent(String parentID) {
        this.parent = parentID;
    }

   


}


