package com.huy.cloud.repository;

import com.huy.cloud.model.MenuModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10191042 on 2019-08-07.
 */
@Component
public class MenuPersistenceRepo {
    private int  currentId = 1001;
    private static final String MENU_ID_PREFIX = "menu_";


    private List<MenuModel> menuList = new ArrayList<>();

    public MenuPersistenceRepo(){
        try {
            menuList = loadMenuFromFile();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<MenuModel> loadMenuFromFile() throws DocumentException, IOException {
        List<MenuModel> list = new ArrayList<>();
        SAXReader reader = new SAXReader();
        File menuFile = new ClassPathResource("menu-list.xml").getFile();
        Document doc = reader.read(menuFile);
        Element root = doc.getRootElement();

        List<Element> items = root.elements();

        for (Element it : items){
            MenuModel mm = new MenuModel();
            String id = it.elementText("id");
            String name = it.elementText("name");
            String iconPath = it.elementText("iconPath");
            BigDecimal price = BigDecimal.valueOf(Double.valueOf(it.elementText("price")));
            BigDecimal vipPrice = BigDecimal.valueOf(Double.valueOf(it.elementText("vipPrice")));
            String description = it.elementText("description");
            int appraise = Integer.valueOf(it.elementText("appraise"));

            currentId = Integer.valueOf(id.replace(MENU_ID_PREFIX,""));

            mm.setId(id);
            mm.setName(name);
            mm.setIconPath(iconPath);
            mm.setPrice(price);
            mm.setVipPrice(vipPrice);
            mm.setDescription(description);
            mm.setAppraise(appraise);

            list.add(mm);

        }


        return list;
    }


    public List<MenuModel> getMenuList(){
        return menuList;
    }

}
