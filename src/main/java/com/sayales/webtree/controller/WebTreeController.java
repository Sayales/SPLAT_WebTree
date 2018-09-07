package com.sayales.webtree.controller;

import com.sayales.webtree.domain.TreeObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */

@Controller
public class WebTreeController {


    ArrayList<TreeObject> treeObjects = new ArrayList<TreeObject>();
    {
        TreeObject obj1 = new TreeObject(1, "Odin");
        treeObjects.add(obj1);
        TreeObject obj2 = new TreeObject(2, "Dva");
        treeObjects.add(obj2);
        TreeObject obj3 = new TreeObject(3, "Tri");
        treeObjects.add(obj3);
        //obj1.getChildren().add(obj2);
        obj2.setParent(1);
        //obj2.getChildren().add(obj3);
        obj3.setParent(2);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/getJSONTree", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeObject> testJSON(){
       return treeObjects;
    }

}
