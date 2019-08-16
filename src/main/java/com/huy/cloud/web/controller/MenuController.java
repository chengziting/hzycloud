package com.huy.cloud.web.controller;

import com.huy.cloud.model.MenuModel;
import com.huy.cloud.repository.MenuPersistenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 10191042 on 2019-08-05.
 */
@Controller
public class MenuController {
    @Autowired
    private MenuPersistenceFactory menuRepo;
    @RequestMapping("/menu/list")
//    @ResponseBody
    public String getMenu(Model model){
        List<MenuModel> list = menuRepo.getMenuList();
        model.addAttribute("list",list);
        return "/menu/list";
    }

    @RequestMapping("/api/menu/list")
    @ResponseBody
    public List<MenuModel> getMenuJson(){
        List<MenuModel> list = menuRepo.getMenuList();

        return list;
    }

    @RequestMapping("/menu/add")
    public String add(){
        return "/menu/add";
    }

    @PostMapping("/menu/post")
    public String post(HttpServletRequest request,
                       HttpServletResponse response,
                       MenuModel model){
        if(model != null){
            menuRepo.addMenu(model);
        }

        return "redirect:/menu/list";

    }


}
