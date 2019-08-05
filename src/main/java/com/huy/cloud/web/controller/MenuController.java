package com.huy.cloud.web.controller;

import com.huy.cloud.web.vm.MenuModel;
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
    @RequestMapping("/menu/list")
    @ResponseBody
    public List<MenuModel> getMenu(){
        List<MenuModel> list = new ArrayList<>();
        int i=0;
        while (i<20){
            MenuModel model = new MenuModel();
            model.setId("menu_"+(i+1));
            model.setName("红烧臭鳜鱼");
            model.setPrice(new BigDecimal(199.00));
            model.setDescription("红烧臭鳜鱼");
            model.setIconPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565004455821&di=282b8ad6ce6d481dc9e52a046a542f9c&imgtype=0&src=http%3A%2F%2Fdimg01.c-ctrip.com%2Fimages%2Ffd%2Ftg%2Fg1%2FM05%2F3E%2F6A%2FCghzflVeqXWAKJvdAAGzEMJFSzY637_R_600_10000.jpg");
            list.add(model);
            i++;
        }

        return list;
    }
}
