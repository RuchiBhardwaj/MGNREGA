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
import java.time.LocalDateTime;
import java.util.Scanner;

public class gpmImpl {
    databaseConnection db = new databaseConnection();
    validator vl = new validator();
    Scanner sc = new Scanner(System.in);
    int UID;
    public void gpmOption() throws SQLException, IOException {
        try {
            System.out.print("Choose your option \n 1.Create Member \n 2.Update Member \n 3.Delete Member" +
                    " \n 4. Project Allotment \n 5. Issue Job Card \n 6. Exit \n");
            int i = sc.nextInt();
            while (i <= 6) {
                if (i == 1)
                    createMember();
                else if (i == 2)
                    updateMember();
                else if (i == 3)
                    deleteMember();
                else if (i == 4)
                    projectAllotmentMember();
                else if (i == 5)
                    issueJobCard();
                else if(i == 6) {
                    System.out.println("\t****Thanks for using 'Mahatma Gandhi National Rural Employment Act' system**** ");
                    break;
                }
                System.out.print("Choose your option \n 1.Create Member \n 2.Update Member \n 3.Delete Member" +
                        " \n 4. Project Allotment \n 5. Issue Job Card \n 6. Exit \n");
                i = sc.nextInt();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void gpmLogin() throws SQLException {
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            System.out.print("Enter your EmailId: ");
            String Email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String Password = sc.nextLine();
            if (vl.isValid(Email)) {
                ResultSet rs = stmt.executeQuery("select * from gpm where email = '" + Email + "'  AND password = '" + Password + "' ");
                if (rs.next()) {
                    int gpmId = rs.getInt(1);
                    this.UID = gpmId;
                    con.close();
                    stmt.close();
                    System.out.print("****** WELCOME TO GPM MANAGEMENT ******");
                    gpmOption();
                } else
                    System.out.print("EmailId and Password is incorrect");
            } else
                System.out.print("Email is not valid");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void createMember() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            int gpmId = this.UID;
            System.out.println("Create new member:");
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Age:  ");
            int age = sc.nextInt();
            System.out.println("Gender(M/F)");
            String gender = bufferedReader.readLine();
            System.out.println("Address");
            String address = bufferedReader.readLine();
            System.out.println("Pincode: ");
            int pincode = sc.nextInt();
            char is_deleted = 'F';
            System.out.println("Email: ");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                System.out.println("Password: ");
                String password = bufferedReader.readLine();
                stmt.executeUpdate("Insert into member(email,name,age,gender,address,pin,gId,password," +
                        "created_at,is_deleted)values('" + email + "','" + name + "','" + age + "','" + gender + "','" + address + "','" + pincode + "','" + gpmId + "','"+password+"',CURRENT_TIMESTAMP,'" + is_deleted + "')");
                System.out.println("Member is Created!!");
            } else
                System.out.println("Email is Invalid!!!");
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateMember() throws SQLException{
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            vl.listOfMember(stmt);
            System.out.print("Enter EmailId of Member: ");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                ResultSet rs = stmt.executeQuery("select * from member where email = '" + email + "' ");
                if (rs.next()) {
                    System.out.print("1.Name: " + rs.getString(3));
                    System.out.print("\n2.Age: " + rs.getInt(4));
                    System.out.println("\nGender: "+ rs.getString(4));
                    System.out.print("\n3.Address: " + rs.getString(6));
                    System.out.print("\n4.Pincode: " + rs.getInt(7));
                    System.out.print("\n5.Password:" + rs.getString(9));
                    System.out.print("\n You can only Update\n 1. Name 2.Address 3.Pincode 4.Password \n Which feild You want to Update");
                    int ch = sc.nextInt();
                    if (ch == 1) {
                        System.out.print("Please update your Name: ");
                        String name = bufferedReader.readLine();
                        stmt.executeUpdate("Update member set name ='" + name + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    }else if (ch == 2) {
                        System.out.print("Please update your Address: ");
                        String address = sc.nextLine();
                        stmt.executeUpdate("Update member set name ='" + address + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    }else if (ch == 3) {
                        System.out.print("Update your Pincode: ");
                        int pincode = sc.nextInt();
                        stmt.executeUpdate("Update member set name ='" + pincode + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    }else if (ch == 4) {
                        System.out.print("Update your Password:");
                        String password = sc.nextLine();
                        stmt.executeUpdate("Update member set name ='" + password + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    }if (ch > 4)
                        System.out.print("Invalid Choice");
                } else
                    System.out.print("this email is not present");
            } else
                System.out.println("email is invalid!");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    public void deleteMember() throws SQLException{
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            vl.listOfMember(stmt);
            System.out.print("Enter EmailId to delete the user: ");
            String email = sc.nextLine();
            if (vl.isValid(email)) {
                stmt.executeUpdate("update member set is_deleted = 'T' where email = '"+email+"'");
                System.out.print("User is Deleted!!!");
            } else
                System.out.print("Email is Invalid!!");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void issueJobCard() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            System.out.println("Issue Admit card..");
            System.out.println("Enter member's Email: ");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                ResultSet rs = stmt.executeQuery("select * from member where email = '" + email + "'");
                if (rs.next()) {
                    System.out.println("Name: " + rs.getString(3));
                    System.out.println("Email: " + rs.getString(2));
                    System.out.println("Age: " + rs.getInt(4));
                    System.out.println("Gender: " + rs.getString(5));
                    System.out.println("Address: " + rs.getString(6));
                    System.out.println("pincode: " + rs.getInt(7));
                    System.out.println("Successfully Admit Card is generated!...");
                } else
                    System.out.println("EmailId is not registered!!!");
            } else
                System.out.println("Invalid Email!");
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void projectAllotmentMember() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            System.out.println("Allotment of Project to Member!!!... ");
            vl.listOfMember(stmt);
            System.out.println("Enter Email:");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                ResultSet rs = stmt.executeQuery("select * from member where email= '" + email + "'");
                if (rs.next()) {
                    System.out.println("NumberOfDays: ");
                    int noOfDays = sc.nextInt();
                    int wage = noOfDays * 100;
                    System.out.println("Project Name:");
                    String projectName = bufferedReader.readLine();
                    String wageStatus = "Not Active";
                    String projectStatus = "Not Active";
                    stmt.executeUpdate("insert into memberWorks(projectName,memail,numberOfDays,wageComputation,wageStatus,ProjectStatus)values('" + projectName + "','" + email + "','" + noOfDays + "','" + wage + "','" + wageStatus + "','" + projectStatus + "')");
                    System.out.println("Project is Allotted!!");
                } else
                    System.out.println("Project is already allotted to this Member!!");
            } else
                System.out.println("Email is Invalid!!!");
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
