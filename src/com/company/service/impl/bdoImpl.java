package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.service.bdo;
import com.company.utils.validator;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class bdoImpl implements bdo {
    databaseConnection db = new databaseConnection();
    Connection con = db.getConnection();
    validator vl = new validator();

    public void bdoOption() {
        int choose = 7;
        System.out.println("Choose your option \n 1.Create ");
        switch (choose) {
            case 1:

        }

    }

    @Override
    public void viewComplaints() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from report");
            while (rs.next())
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void login() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your EmailId: ");
            String Email = sc.nextLine();
            System.out.println("Enter your Password");
            String Password = sc.nextLine();
            if (vl.isValid(Email)) {
                ResultSet rs = stmt.executeQuery("select * from user where email = '" + Email + "'  AND password = '" + Password + "' ");
                if (rs.next())
                    System.out.println("****** WELCOME TO BDO MANAGEMENT ******");
                else
                    System.out.println("EmailId and Password is incorrect");
            } else
                System.out.print("Email is not valid");
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void createGpm() throws SQLException{
        Statement stmt = con.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.println("Create new GPM:  \n ");
        System.out.println("Enter Name: ");
        String name = sc.nextLine();




    }
}
