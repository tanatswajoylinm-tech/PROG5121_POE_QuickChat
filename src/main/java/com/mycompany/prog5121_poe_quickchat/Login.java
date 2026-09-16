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
        return false;
    }

    public boolean checkCellPhoneNumber(String cellPhone) {
        return false;
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
