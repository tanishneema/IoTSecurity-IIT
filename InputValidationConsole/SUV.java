import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SUV {
    private static int age;
    private static String fname, lname, uId;
    static Scanner sc;

    public static void main(String[] args) {
        try {
            sc = new Scanner(System.in);
            while (true) {
                System.out.print("Enter First name: ");
                fname = sc.nextLine().trim();
                if (fname.indexOf(" ") != -1) {
                    System.out.println("\n----- Incorrect First Name -----\n");
                    continue;
                }
                if (fname.length() < 3) {
                    System.out.println("\n----- Atleast 3 Characters -----\n");
                    continue;
                }
                Pattern p1 = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
                Matcher m1 = p1.matcher(fname);
                if (m1.find()) {
                    System.out.println("\n----- Incorrect First Name -----\n");
                    continue;
                }
                break;
            }
            while (true) {
                System.out.print("Enter Last name: ");
                lname = sc.nextLine().trim();
                if (lname.indexOf(" ") != -1) {
                    System.out.println("\n----- Incorrect Last Name -----\n");
                    continue;
                }
                if (lname.length() < 3) {
                    System.out.println("\n----- Atleast 3 Characters -----\n");
                    continue;
                }
                Pattern p1 = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
                Matcher m1 = p1.matcher(lname);
                if (m1.find()) {
                    System.out.println("\n----- Incorrect Last Name -----\n");
                    continue;
                }
                break;
            }
            while (true) {
                System.out.print("Enter Age: ");
                if (sc.hasNextInt()) {
                    age = sc.nextInt();
                    if (age < 1) {
                        System.out.println("\n----- Wrong Age -----\n");
                        continue;
                    }
                    if (age < 18) {
                        System.out.println("\n----- Under Age -----\n");
                        continue;
                    }
                    if (age > 120) {
                        System.out.println("\n----- Wrong Age -----\n");
                        continue;
                    }
                } else {
                    System.out.println("\n----- Enter Number -----\n");
                    continue;
                }
                break;
            }
            sc.nextLine();
            while (true) {
                System.out.print("Enter User Id: ");
                uId = sc.nextLine().trim().toLowerCase();
                if (uId.indexOf(" ") != -1) {
                    System.out.println("\n----- Incorrect User Id -----\n");
                    continue;
                }
                if (uId.length() < 5) {
                    System.out.println("\n----- Atleast 5 Characters -----\n");
                    continue;
                }
                Pattern p1 = Pattern.compile("[^a-z_]", Pattern.CASE_INSENSITIVE);
                Matcher m1 = p1.matcher(uId);
                if (m1.find()) {
                    System.out.println("\n----- Incorrect User Id -----\n");
                    continue;
                }
                break;
            }
            sc.close();
            System.out.println("\n----- Profile Created Successfully -----\n");
            System.out.println("Name: " + fname + " " + lname);
            System.out.println("Age: " + age);
            System.out.println("User Id: " + uId + "\n");
        } catch (Exception e) {
            System.out.print("\n ----- ERROR -----\n");
            sc.close();
        }
    }
}
