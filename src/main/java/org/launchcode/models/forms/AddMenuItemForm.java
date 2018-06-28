package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.constraints.NotNull;


public class AddMenuItemForm {

    private Menu menu;

    private Iterable<Cheese> cheeses;

    @NotNull
    private int menuId;

    @NotNull
    private int cheeseId;

    public Iterable<Cheese> getCheeses() {
        return cheeses;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getCheeseId() {
        return cheeseId;
    }

    public AddMenuItemForm() {}

    public AddMenuItemForm(Menu menu, Iterable<Cheese> cheeses){
        this.menu = menu;
        this.cheeses = cheeses;
    }
}