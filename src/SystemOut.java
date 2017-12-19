public class SystemOut {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void centerPrint(String text, int len) {
        int before = (len - text.length()) / 2;
        int rest = len - before - text.length();
        for (int i = 0; i < before; i++) {
            System.out.print("-");
        }
        System.out.print(text);
        for (int i = 0; i < rest; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }
}
