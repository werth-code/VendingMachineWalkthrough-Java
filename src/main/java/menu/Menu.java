package menu;

import java.util.ArrayList;

public class Menu {
    private ArrayList<String> options;

    public Menu(ArrayList<String> options) {
        this.options = options;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
