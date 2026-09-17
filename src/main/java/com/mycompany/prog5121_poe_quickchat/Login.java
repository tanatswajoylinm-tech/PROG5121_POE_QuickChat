/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_poe_quickchat;

/**
 *
 * @author Student
 */
package com.mycompany.prog5121_poe_quickchat;

public class Login {

        public boolean checkUserName(String username) {
        // Username must contain an underscore and be no more than 5 characters long
        if (username != null && username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }
        public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cellPhone) {
        // Regex for South African cell phone numbers with international code +27, followed by 9 digits
        // Reference: Adapted from https://regexlib.com/ (South African Phone Number Regex)
        String cellPhoneRegex = "^\\+27[0-9]{9}$";
        if (cellPhone == null) return false;
        return cellPhone.matches(cellPhoneRegex);
    }

    public String registerUser(String username, String password, String cellPhone, String fName, String lName) {
        return "";
    }

    public boolean loginUser(String username, String password) {
        return false;
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        return "";
    }
}
