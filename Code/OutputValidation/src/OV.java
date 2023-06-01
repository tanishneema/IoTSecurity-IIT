
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.regex.*;
import java.util.*;
import java.math.BigDecimal;
import java.util.Calendar;

public class OV {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            String redColor = "\u001B[31m", greenColor = "\u001B[32m";
            String name, email, passHash, meterNo, status;
            java.sql.Date date, dateToday = new java.sql.Date(System.currentTimeMillis());
            BigDecimal initialReading, finalReading;
            int pinCode;
            boolean flag = true;
            String url = "jdbc:mysql://localhost:3306/CSR", username = "root", password = "123456";
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM SmartMeter";
            System.out.print("Enter password for XV558877: ");
            String pass = sc.nextLine();
            if (pass.equals("Pass123")) {
                try (Statement statement= connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        while (resultSet.next()) {
                            name = resultSet.getString("name");
                            System.out.print(name + "->\n\t");
                            meterNo = resultSet.getString("meterNo");
                            if (validateMeterNo(meterNo)) {
                                System.out.print(greenColor + "Meter Number: " + meterNo + "\n\t");
                            } else {
                                System.out.print(redColor + "Meter Number\n\t");
                                flag = false;
                            }
                            email = resultSet.getString("email");
                            if (validateEmail(email)) {
                                System.out.print(greenColor + "Mail Id: " + email + "\n\t");
                            } else {
                                System.out.print(redColor + "Mail Id\n\t");
                                flag = false;
                            }
                            pinCode = resultSet.getInt("pinCode");
                            if (validatePin(pinCode)) {
                                System.out.print(greenColor + "Address: " + resultSet.getString("address") + "\n\t");
                                System.out.print(greenColor + "Zone: " + resultSet.getString("zone") + "\n\t");
                                System.out.print(greenColor + "Pin Code: " + pinCode + "\n\t");
                            } else {
                                System.out.print(redColor + "Address\n\t");
                                System.out.print(redColor + "Zone\n\t");
                                System.out.print(redColor + "Pin Code\n\t");
                                flag = false;
                            }
                            status = resultSet.getString("status");
                            if (status.equalsIgnoreCase("N") || status.equalsIgnoreCase("Y")) {
                                System.out.print(greenColor + "Status: " + status + "\n\t");
                            } else {
                                System.out.print(redColor + "Status\n\t");
                                flag = false;
                            }
                            initialReading = resultSet.getBigDecimal("initialReading");
                            finalReading = resultSet.getBigDecimal("finalReading");
                            int comparisonResult = initialReading.compareTo(finalReading);
                            if (comparisonResult > 0) {
                                System.out.print(redColor + "Initial Reading\n\t");
                                System.out.print(redColor + "Final Reading\n\t");
                                flag = false;
                            } else if (comparisonResult == 0 && status.equalsIgnoreCase("N")) {
                                System.out.print(redColor + "Initial Reading\n\t");
                                System.out.print(redColor + "Final Reading\n\t");
                                flag = false;
                            } else {
                                System.out.print(greenColor + "Initial Reading: " + initialReading + "\n\t");
                                System.out.print(greenColor + "Final Reading: " + finalReading + "\n\t");
                            }
                            date = resultSet.getDate("installationDate");
                            int comparisonDate = date.compareTo(dateToday);
                            if (comparisonDate <= 0) {
                                System.out.print(greenColor + "Date: " + date + "\n\t");
                            } else {
                                System.out.print(redColor + "Date \n\t");
                                flag = false;
                            }
                            passHash = resultSet.getString("password");
                            if (isStringHashed(passHash)) {
                                System.out.print(greenColor + "Password: " + passHash + "\n\t");
                            } else {
                                System.out.print(redColor + "Password\n\t");
                                flag = false;
                            }

                            if (flag) {
                                Double diff = Double.parseDouble(finalReading.toString()) - Double.parseDouble(initialReading.toString());
                                System.out.println("");
                                System.out.print(greenColor + "\tUnits: " + diff + "\n");
                                System.out.print(greenColor + "\tBill Amount: Rs." + diff * 3);
                            }
                            System.out.print("\n\n");
                            flag = true;
                        }
                    } else {
                        System.out.println("No record found for the meter number: ");
                    }
                }
            }else{
                System.out.print("Wrong Password");
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public static boolean validateMeterNo(String meterNo) {
        String Regex1 = "^[A-Z]{1}[0-9]{7}$";
        String Regex2 = "^[A-Z]{2}[0-9]{6}$";
        Pattern pattern1 = Pattern.compile(Regex1);
        Pattern pattern2 = Pattern.compile(Regex2);
        Matcher matcher1 = pattern1.matcher(meterNo);
        Matcher matcher2 = pattern2.matcher(meterNo);
        return matcher1.matches() || matcher2.matches();
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePin(int pinCode) {
        return String.valueOf(pinCode).length() == 5;
    }

    public static boolean isStringHashed(String input) {
        String hashPattern = "^[a-f0-9]{64}$";
        Pattern pattern = Pattern.compile(hashPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
