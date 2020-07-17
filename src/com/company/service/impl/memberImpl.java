package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.utils.validator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class memberImpl {
    databaseConnection db = new databaseConnection();
    Connection con = db.getConnection();
    validator vl = new validator();
    Scanner sc = new Scanner(System.in);
    int UID;
    public void memberLogin() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            System.out.print("Enter your EmailId: ");
            String Email = sc.nextLine();
            System.out.print("Enter your Password");
            String Password = sc.nextLine();
            if (vl.isValid(Email)) {
                ResultSet rs = stmt.executeQuery("select * from member where email = '" + Email + "'  AND password = '" + Password + "' ");
                if (rs.next()) {
                    int gpmId = rs.getInt(1);
                    this.UID = gpmId;
                    System.out.print("****** WELCOME TO MEMBER MANAGEMENT ******");
                } else
                    System.out.print("EmailId and Password is incorrect");
            } else
                System.out.print("Email is not valid");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
