package Composite;

import java.util.ArrayList;

public class Menu extends MenuComponent {

    private String name;
    private String description;
    private ArrayList<MenuComponent> menuComponents = new ArrayList<>();

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }

    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void print() {
        System.out.print("\n" + getName());
        System.out.println(", " + getDescription());
        System.out.println("---------------------");

        for (Object component : menuComponents) {
            MenuComponent menuComponent =
                    (MenuComponent) component;
            menuComponent.print();
        }

    }
}
