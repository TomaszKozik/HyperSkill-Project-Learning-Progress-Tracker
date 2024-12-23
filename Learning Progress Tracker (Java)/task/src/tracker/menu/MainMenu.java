package tracker.menu;

public class MainMenu extends Menu {

    public MainMenu(String name) {
        super(name);
    }

    @Override
    MenuItem getMenuItem() {
        return new MenuItem();
    }

}