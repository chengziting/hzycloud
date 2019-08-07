package com.huy.cloud.web.controller;

import com.huy.cloud.model.MenuModel;
import com.huy.cloud.repository.MenuPersistenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10191042 on 2019-08-05.
 */
@Controller
public class MenuController {
    @Autowired
    private MenuPersistenceRepo menuRepo;
    @RequestMapping("/menu/list")
    @ResponseBody
    public List<MenuModel> getMenu(){
        List<MenuModel> list = menuRepo.getMenuList();
        return list;
    }
}
