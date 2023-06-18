import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class fuzzyLogic {
    public static void main(String[] args) {
        validI();
        invalidI();
        System.out.println("\n");
        validE();
        invalidE();
    }

    private static void validI() {
        System.out.println("Valid Test Cases");
        String[] meterNo2 = { "112233", "123456", "987654", "999999", "100000" };
        for (String x : meterNo2)
            testInput(x);
    }

    private static void validE() {
        System.out.println("Valid Test Cases");
        String[] mail = { "abc@gmail.com", "x@ab.cc", "a@aaaa.ccc" };
        for (String x : mail)
            testEmail(x);
    }

    private static void invalidE() {
        System.out.println("\nInvalid Test Cases");
        String[] mail = { "@gmail.com", "x@a.cc", "a@abcd.", "a@abcd.defg" };
        for (String x : mail)
            testEmail(x);
    }

    private static void testEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches() && email.length() >= 7) {
            System.out.println("Valid Email: " + email);
        } else {
            System.out.println("Invalid Email: " + email);
        }
    }

    private static void invalidI() {
        System.out.println("\nInvalid Test Cases");
        String[] meterNo2 = { "12345", "123456789", "1000000", "000000", "0", "-12", "99999", "011111", "ABC" };
        for (String x : meterNo2)
            testInput(x);
    }

    private static void testInput(String x) {
        try {
            int number = Integer.parseInt(x);
            if (number >= 100000 && number <= 999999) {
                System.out.println("Valid Number: " + x);
            } else {
                System.out.println("Invalid Number: " + x);
            }
        } catch (Exception e) {
            System.out.println("Invalid Number: " + x);
        }
    }
}