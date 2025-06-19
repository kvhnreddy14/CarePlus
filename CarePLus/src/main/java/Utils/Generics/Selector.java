package Utils.Generics;

import Utils.FunctionalInterfaces.Mapper;

import java.util.List;
import java.util.Scanner;

public class Selector {
    public static <T> T selectFromList(Mapper<T, String> label, List<T> items) {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        for (T item : items) {
            System.out.println(i + ". " + label.map(item));
            i++;
        }

        T selectedItem = null;
        while (selectedItem == null) {
            System.out.println("Enter choice (1 to " + items.size() + "): ");
            int choice = sc.nextInt();
            if (choice < 1 || choice > items.size()) {
                System.out.println("Invalid choice. Try again.");
            } else {
                selectedItem = items.get(choice - 1);
            }
        }

        return selectedItem;
    }
}
