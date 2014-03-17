/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zols.web.config.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.zols.datastore.DataStore;
import com.zols.datastore.domain.BaseObject;
import com.zols.datastore.domain.Entity;
import com.zols.datastore.domain.NameLabel;
import com.zols.datastore.util.DynamicBeanGenerator;
import com.zols.templatemanager.TemplateStorageManager;
import com.zols.templatemanager.domain.Template;
import com.zols.templatemanager.domain.TemplateStorage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CoreController {

    @Autowired
    private DataStore dataStore;

    @Autowired
    private DynamicBeanGenerator dynamicBeanGenerator;

    @Autowired
    private TemplateStorageManager templateStorageManager;

    @RequestMapping(value = "/controlpanel", method = GET)
    @ApiIgnore
    public String controlPanel() {
        return "controlpanel";
    }

    @RequestMapping(value = "/master/{name}", method = GET)
    @ResponseBody
    public List master(@PathVariable(value = "name") String name) throws IOException {
        List masterList = null;
        if (name.equals("attributeType")) {
            masterList = dataStore.list(Entity.class);
            if (masterList == null) {
                masterList = new ArrayList(5);
            }
            Entity entity = null;

            entity = new Entity();
            entity.setName("Double");
            entity.setLabel("Double");
            masterList.add(0, entity);
            
            entity = new Entity();
            entity.setName("Time");
            entity.setLabel("Time");
            masterList.add(0, entity);

            entity = new Entity();
            entity.setName("Date");
            entity.setLabel("Date");
            masterList.add(0, entity);

            entity = new Entity();
            entity.setName("RichText");
            entity.setLabel("Rich Text");
            masterList.add(0, entity);
            
            entity = new Entity();
            entity.setName("Boolean");
            entity.setLabel("Boolean");
            masterList.add(0, entity);

            entity = new Entity();
            entity.setName("Float");
            entity.setLabel("Float");
            masterList.add(0, entity);

            entity = new Entity();
            entity.setName("Integer");
            entity.setLabel("Integer");
            masterList.add(0, entity);

            entity = new Entity();
            entity.setName("String");
            entity.setLabel("String");
            masterList.add(0, entity);
        } else if (name.equals("template")) {
            masterList = dataStore.list(Template.class);
        } else if (name.equals("templatePath")) {
            masterList = templateStorageManager.templatePaths();
        } else if (name.equals("templateStorageType")) {
            masterList = new ArrayList(2);

            NameLabel nameLabel = null;

            nameLabel = new NameLabel();
            nameLabel.setName(TemplateStorage.FILE_SYSTEM);
            nameLabel.setLabel("File System");
            masterList.add(nameLabel);

            nameLabel = new NameLabel();
            nameLabel.setName(TemplateStorage.FTP);
            nameLabel.setLabel("FTP");
            masterList.add(nameLabel);

        } else {
            Class<? extends BaseObject> clazz = dynamicBeanGenerator.getBeanClass(name);
            masterList = dataStore.list(clazz);
        }
        return masterList;
    }

    @RequestMapping(value = "/master/all/{name}", method = GET)
    @ResponseBody
    public Map<String, List> masterAll(@PathVariable(value = "name") String name) throws IOException {
        Map<String, List> map = new HashMap<String, List>(1);
        map.put(name, master(name));
        return map;
    }
}
