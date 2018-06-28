package org.launchcode.controllers;


import org.launchcode.models.Cheese;

import org.launchcode.models.Menu;

import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    // Request path: /menu
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menus");

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Menu newMenu,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuid}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable Integer menuid, Model model) {
        Menu menu = menuDao.findOne(menuid);
        model.addAttribute("title", "View Menu");
        model.addAttribute(menu);
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuid}", method = RequestMethod.GET)
    public String additem(@PathVariable Integer menuid, Model model) {
        Menu menu = menuDao.findOne(menuid);

        AddMenuItemForm addMenuItemForm = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("title", "Add item to menu: " + menu.getName());
        model.addAttribute("form",addMenuItemForm);
        model.addAttribute("menuId",menuid);
        return "menu/add-item";
    }
    @RequestMapping(value = "add-item/{menuid}", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm itemForm,
                          Errors errors, @PathVariable int menuid, @RequestParam int cheeseId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Item");
            return "menu/add-item/" + menuid;
        }

        Menu menu = menuDao.findOne(menuid);
        System.out.println(menu.getName());

        Cheese cheese = cheeseDao.findOne(cheeseId);
        System.out.println(cheese.getDescription());

        menu.addItem(cheese);

        System.out.println(menu.getCheeses());
        menuDao.save(menu);

        return "redirect:../view/" + menu.getId();
    }



}