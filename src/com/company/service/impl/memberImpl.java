package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.utils.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class memberImpl {
    databaseConnection db = new databaseConnection();
    validator vl = new validator();
    Scanner sc = new Scanner(System.in);
    int UID;
    public void memberOption() throws SQLException, IOException {
        System.out.print("Choose your option \n 1.See your Profile \n 2.file your complain \n 3.Exit \n");
        int i=sc.nextInt();
       while(i<=3){
           if(i==1)
               memberDetails();
           else if(i==2)
               fileComplain();
           else if(i == 3) {
               System.out.println("\t****Thanks for using 'Mahatma Gandhi National Rural Employment Act' system**** ");
               break;
           }
           System.out.print("Choose your option \n 1.See your Profile \n 2.file your complain \n 3.Exit \n");
           i = sc.nextInt();

       }
    }
    public void memberLogin() throws SQLException {
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            System.out.print("Enter your EmailId: ");
            String Email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String Password = sc.nextLine();
            if (vl.isValid(Email)) {
                ResultSet rs = stmt.executeQuery("select * from member where email = '" + Email + "'  AND password = '" + Password + "' ");
                if (rs.next()) {
                    int mId = rs.getInt(1);
                    this.UID = mId;
                    con.close();
                    stmt.close();
                    System.out.print("****** WELCOME TO MEMBER MANAGEMENT ******");
                    memberOption();
                } else
                    System.out.print("EmailId and Password is incorrect");
            } else
                System.out.print("Email is not valid");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    public void memberDetails() throws SQLException{
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            int mid = this.UID;
            ResultSet rs = stmt.executeQuery("select member.name,member.age,member." +
                    "gender,member.address,member." +
                    "pin,memberWorks.wageComputation,memberWorks" +
                    ".numberOfDays,memberWorks.projectName FROM member INNER JOIN " +
                    " memberWorks ON member.email=memberWorks.memail" +
                    " where mId = '" + mid + "'");
            if (rs.next()) {
                System.out.println("Name: " + rs.getString(1));
                System.out.println("Age: "+rs.getInt(2));
                System.out.println("Gender: "+rs.getString(3));
                System.out.println("Address: "+rs.getString(4));
                System.out.println("Pincode: "+rs.getInt(5));
                System.out.println("Wage Computation: "+rs.getInt(6));
                System.out.println("Number of Days: "+rs.getInt(7));
                System.out.println("Project Name = "+rs.getString(8));
                System.out.println("\n---------------------------------");
            }
            else
                System.out.println("No detail is present!!!");
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void fileComplain() throws SQLException, IOException {
        try {
            Connection con = db.getConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            System.out.println("File your Complain here !!!...");
            int mid = this.UID;
            System.out.println("Write your Issues: ");
            String issue = bufferedReader.readLine();
            stmt.executeUpdate("insert into report(mId,issue,created_at)values('" + mid + "','" + issue + "',CURRENT_TIMESTAMP)");
            System.out.println("Complaint filed!!!");
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
