package tracker.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;


public abstract class Menu implements MenuItemInterface {
    private final String name;
    private final List<MenuItem> menuItemList = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    public void addItem(MenuItem menuItem) {
        menuItemList.add(menuItem);
    }

    @Override
    public void display() {
        final String join = menuItemList.stream().filter(MenuItem::isDisplayed).map(MenuItem::getName).collect(Collectors.joining(", "));
        System.out.print("[" + name + "] Enter action (" + join + "): ");
    }

    @Override
    public void execute() {
        String choice = new Scanner(System.in).nextLine();
        try {
            menuItemList.stream().filter(menuItem -> menuItem.getName().equals(choice)).findFirst().get().execute();
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("invalid input");
        }
    }

    public void run() {
        while (true) {
            execute();
        }
    }

    abstract MenuItem getMenuItem();
}
