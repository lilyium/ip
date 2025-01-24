import java.util.Scanner;

public class Baron {
    private static String horizontalLine = "____________________________________________________________";
    private static String hello = horizontalLine + "\n" +
            "Hello! I'm Baron.\n" +
            "What can I do for you?\n" +
            horizontalLine;

    private static String bye = "Bye. Hope to see you again soon!\n" +
            horizontalLine;


    public static void main(String[] args) {
        System.out.println(hello);
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        while (!input.equals("bye")) {
            System.out.println(horizontalLine + "\n" + input + "\n" + horizontalLine);
            input = s.nextLine();
        }

        System.out.println(bye);
    }
}
