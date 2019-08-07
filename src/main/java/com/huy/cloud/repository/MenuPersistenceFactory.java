package com.huy.cloud.repository;

import com.huy.cloud.model.MenuModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10191042 on 2019-08-07.
 */
@Component
public class MenuPersistenceFactory {
    private static int  currentId = 1000;
    private static final String MENU_ID_PREFIX = "menu_";


    private List<MenuModel> menuList = new ArrayList<>();

    private MenuPersistenceFactory(){
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

            int tmpId = Integer.valueOf(id.replace(MENU_ID_PREFIX,""));
            if(tmpId >= currentId){
                currentId = tmpId;
            }

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

    private void saveMenuListToFile() throws IOException {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("menu");
        for (MenuModel mm : menuList){
            Element item = root.addElement("item");
            Element id = item.addElement("id");
            id.setText(mm.getId());

            Element name = item.addElement("name");
            name.setText(mm.getName());

            Element price = item.addElement("price");
            price.setText(mm.getPrice().toString());

            Element vipPrice = item.addElement("vipPrice");
            vipPrice.setText(mm.getVipPrice().toString());

            Element iconPath = item.addElement("iconPath");
            iconPath.setText(mm.getIconPath());

            Element desc = item.addElement("description");
            desc.setText(mm.getDescription());

            Element appraise = item.addElement("appraise");
            appraise.setText(String.valueOf(mm.getAppraise()));
        }

        //实例化输出格式对象
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置输出编码
        format.setEncoding("UTF-8");

        File file = new ClassPathResource("menu-list.xml").getFile();
        //生成XMLWriter对象，构造函数中的参数为需要输出的文件流和格式
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        //开始写入，write方法中包含上面创建的Document对象
        writer.write(doc);
    }


    public List<MenuModel> getMenuList(){
        return menuList;
    }

    public synchronized void addMenu(MenuModel menu){
        if(
                menu == null
                || StringUtils.isEmpty(menu.getName())
                || StringUtils.isEmpty(menu.getIconPath())
                || StringUtils.isEmpty(menu.getPrice())
                || StringUtils.isEmpty(menu.getVipPrice())
                ) return;
        try {
            currentId++;
            MenuModel mm = new MenuModel();
            mm.setId(MENU_ID_PREFIX + currentId);
            mm.setName(menu.getName());
            mm.setDescription(menu.getDescription());
            mm.setAppraise(menu.getAppraise());
            mm.setPrice(menu.getPrice());
            mm.setVipPrice(menu.getVipPrice());
            mm.setIconPath(menu.getIconPath());
            menuList.add(mm);

            saveMenuListToFile();

        }catch (Exception ex){
            currentId--;
        }
    }



}
