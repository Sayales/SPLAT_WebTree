package com.sayales.webtree.domain;

import com.sayales.webtree.controller.WebTreeController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Pavel on 06.09.2018.
 */


public class TreeObject {


    private int id;

    private String text;

    private String parent;

    private boolean children;

    public TreeObject() {
    }

    public TreeObject(int id, String text, String parent, boolean children) {
        this.id = id;
        this.text = text;
        this.parent = parent;
        this.children = children;
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
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

    public Integer getIntParent() {
        if (parent.equals("#"))
            return null;
        else
            return Integer.parseInt(parent);
    }


    public void setParent(String parentID) {
        this.parent = parentID;
    }


    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }
}


