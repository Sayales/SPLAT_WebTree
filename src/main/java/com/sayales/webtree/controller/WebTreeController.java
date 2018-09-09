package com.sayales.webtree.controller;

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

    @RequestMapping(value = "/updateElement", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public TreeObject updateElement(@RequestBody TreeObject treeObject) throws IOException {
        return service.save(treeObject);
    }

    @RequestMapping(value = "/deleteElement", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String deleteElement(@RequestBody List<String> treeObject) throws IOException {
        String response = "";
        for (String s : treeObject) {
            response = ";" + service.delete(Integer.valueOf(s));
        }
        return response;
    }

  /*  @RequestMapping(value = "/addElement", method = RequestMethod.POST, consumes =  "application/json")
    @ResponseBody
    public TreeObject addElement(@RequestBody TreeObject treeObject) throws IOException {
        return service.save(treeObject);
    }
*/
}
