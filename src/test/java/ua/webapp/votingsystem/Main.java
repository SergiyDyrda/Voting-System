package ua.webapp.votingsystem;

import ua.webapp.votingsystem.util.PasswordUtil;



public class Main {
    public static void main(String[] args) {

        System.out.println("admin - " + PasswordUtil.encode("admin"));
        System.out.println("user1 - " + PasswordUtil.encode("user1"));
        System.out.println("user2 - " + PasswordUtil.encode("user2"));
        System.out.println("user3 - " + PasswordUtil.encode("user3"));
    }
}


