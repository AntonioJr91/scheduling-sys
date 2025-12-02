package utils;

import java.util.Scanner;

public class PauseUI {

  public static void pause() {
    Scanner sc = Input.sc;
    System.out.print("Press any key to continue...");
    sc.nextLine();
  }

}
