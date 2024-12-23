package tracker.menu;

public class MenuItem implements MenuItemInterface {
    private String name;
    private Action action;
    private boolean isDisplayed;

    public MenuItem() {
    }

    public MenuItem(String name, Action action, boolean isDisplayed) {
        this.name = name;
        this.action = action;
        this.isDisplayed = isDisplayed;
    }

    @Override
    public void display() {
        System.out.print(name);
    }

    @Override
    public void execute() {
        if (action != null) {
            action.execute();
        } else {
            System.out.println("Invalid option.");
        }
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }
}