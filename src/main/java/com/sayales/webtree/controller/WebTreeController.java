package com.sayales.webtree.controller;

import com.sayales.webtree.domain.CodeResponse;
import com.sayales.webtree.domain.TreeObject;
import com.sayales.webtree.service.TreeObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Pavel on 06.09.2018.
 */

@Controller
public class WebTreeController {

    public static int ID =1000;


   @Autowired
    TreeObjectService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/getJSONTree", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeObject> testJSON(){
       return service.getAll();
    }

    @RequestMapping(value = "/moveCategory", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public CodeResponse moveCategory(@RequestBody TreeObject treeObject) throws IOException {
        service.save(treeObject);
        return new CodeResponse("success");
    }

    @RequestMapping(value = "/deleteElement", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public CodeResponse deleteElement(@RequestBody List<String> treeObject) throws IOException {
        for (String s : treeObject) {
            service.delete(Integer.valueOf(s));
        }
        return new CodeResponse("success");
    }

    @RequestMapping(value = "/addElement", method = RequestMethod.POST, consumes =  "application/json")
    @ResponseBody
    public TreeObject addElement(@RequestBody TreeObject treeObject) throws IOException {
        ID++;
        treeObject.setId(ID);
        return service.save(treeObject);
    }

}
