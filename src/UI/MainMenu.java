package UI;

import java.util.List;
import java.util.Scanner;

import utils.Input;

public class MainMenu {
  private static final Scanner sc = Input.sc;

  public static void menu(String header, List<String> options, List<Runnable> actions) {
    if (options.size() != actions.size()) {
      throw new IllegalArgumentException("Options and actions must have same size");
    }

    while (true) {
      System.out.printf("\n----- %s -----\n", header);

      for (int i = 0; i < options.size(); i++) {
        System.out.printf("%d - %s\n", i + 1, options.get(i));
      }
      System.out.println("0 - Exit");

      int choose = sc.nextInt();
      sc.nextLine();

      if (choose == 0) {
        System.out.println("Program closed.");
        return;
      }

      if (choose > 0 && choose <= actions.size()) {
        actions.get(choose - 1).run();
      } else {
        System.out.println("Invalid option!");
      }
    }

  }
}
