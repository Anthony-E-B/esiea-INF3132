package INF3132.ui;

import java.util.Scanner;

enum InputType {
    ITEM,
    BACK,
    INVALID
}

public class Menu {
    public Menu parent;
    public MenuItem[] items;

    protected String name;

	private Scanner scanner;

    public Menu(String name) {
        this(name, null);
    }

    public Menu(String name, MenuItem[] items) {
        this(name, items, null);
    }

    public Menu(String name, MenuItem[] items, Menu parent) {
        this.name = name;
        this.items = items;
        this.parent = parent;
        this.scanner = new Scanner(System.in);
    }

    protected void displayItem(String activator, String title) {
        System.out.println(String.format("\t%s:\t%s", activator, title));
    }

    protected void displayItems() {
        System.out.println();
        System.out.println(String.format("MENU : %s", getName()));

        if (parent != null) {
            System.out.println();
            displayItem("B", "Retour");
        }

        System.out.println("");
        for (int i = 0; i < items.length; i++) {
            displayItem(Integer.toString(i+1), items[i].getLabel());
        }

        System.out.println("");
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public void setItems(MenuItem[] items) {
        this.items = items;
    }

    protected InputType determineInputType(String userInput) {
        String input = userInput.trim().toUpperCase();

        // If the input is an int belonging to N*
        if (input.matches("[1-9]\\d*")) {
            // Check if it is in bounds (the 0 case is handled by the regex)
            if (Integer.parseInt(input) > items.length) {
                return InputType.INVALID;
            }
            return InputType.ITEM;
        } else if (parent != null && input.equals("B")) {
            return InputType.BACK;
        } else {
            return InputType.INVALID;
        }
    }

    public void prompt() {
        InputType it = null;
        String userInput = null;

        do {
            if (it == InputType.INVALID) {
                String message = "Entrée %s invalide, veuillez entrer un numéro de menu";
                if (parent != null) message += ", ou B pour retourner au menu parent.";
                else message += ".";
                System.out.println(String.format(message, userInput));
            }

            displayItems();
            userInput = scanner.next();
            it = determineInputType(userInput);
        } while (it == InputType.INVALID);

        switch (it) {
        case ITEM:
            int userChoice = Integer.parseInt(userInput);
            items[userChoice - 1].trigger();
            break;
        case BACK:
            parent.prompt();
            break;
        default: break;
        }
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

