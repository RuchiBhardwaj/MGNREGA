package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.service.bdo;
import com.company.utils.validator;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.bind.v2.model.core.NonElement;

import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class bdoImpl {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    databaseConnection db = new databaseConnection();
    Connection con = db.getConnection();
    validator vl = new validator();
    Scanner sc = new Scanner(System.in);
    int UID;

    public void bdoOption() throws SQLException, ParseException, IOException {
        System.out.print("Choose your option \n 1.Create Gpm \n 2.Update Gpm \n 3.Delete Gpm \n 4.Create Project \n 5.Update Project \n 6.Delete Project\n");
        int i=sc.nextInt();
        switch(i)
        {
            case 1:
                createGpm();
                break;
            case 2:
                updateGpm();
                break;
            case 3:
                deleteGpm();
                break;
            case 4:
                createProject();
                break;
            case 5:
                updateProject();
                break;
            case 6:
                deleteProject();
                break;
            default:
                System.out.println("Default ");
        }

    }

    public void viewComplaints() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from report");
            while (rs.next())
                System.out.print(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
//                System.out.println(rs.getString(2)+" "+rs.getString(3)+"  "+rs.getInt(4)+" "+rs.getFloat(5)+"  "+rs.getDate(6)+"  "+rs.getDate(7)+"   "+rs.getString(8));
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void bdoLogin() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            System.out.print("Enter your EmailId: ");
            String Email = sc.nextLine();
            System.out.print("Enter your Password");
            String Password = sc.nextLine();
            if (vl.isValid(Email)) {
                ResultSet rs = stmt.executeQuery("select * from user where email = '" + Email + "'  AND password = '" + Password + "' ");
                if (rs.next()) {
                    int BdoId = rs.getInt(1);
                    this.UID = BdoId;
                    System.out.print("****** WELCOME TO BDO MANAGEMENT ******");
                    bdoOption();
                } else
                    System.out.print("EmailId and Password is incorrect");
            } else
                System.out.print("Email is not valid");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void createGpm() throws SQLException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            int BdoId = this.UID;
            char is_deleted = 'T';
            System.out.print("Create new GPM:  \n ");
            System.out.print("Enter Name: ");
            String name = bufferedReader.readLine();
            System.out.print("Enter Address : ");
            String address = bufferedReader.readLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            System.out.print("Enter Pincode: ");
            int pin = sc.nextInt();
            System.out.print("Enter EmailId: ");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                System.out.print("Enter Password: ");
                String password = bufferedReader.readLine();
                stmt.executeUpdate("Insert into gpm(email,name,age,address,bdoId,pin,password,created_at,is_deleted)values ('" + email + "'','" + name + "','" + age + "','" + address + "','" + BdoId + "','" + pin + "','" + password + "',CURRENT_TIMESTAMP,'"+is_deleted+"')");
                con.close();
            } else
                System.out.print("Invalid EmailId! ");
        } catch (Exception e) {
            System.out.print(e);
        }

    }


    public void updateGpm() throws SQLException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            vl.listOfGpm();
            Statement stmt = con.createStatement();
            LocalDateTime updated_at = LocalDateTime.now();
            System.out.print("Enter EmailId of GPM: ");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                ResultSet rs = stmt.executeQuery("select * from gpm where email = '" + email + "' ");
                if (rs.next()) {
                    System.out.print("1.Name: " + rs.getString(3));
                    System.out.print("2.Age: " + rs.getInt(4));
                    System.out.print("3.Address: " + rs.getString(5));
                    System.out.print("4.Pincode: " + rs.getInt(7));
                    System.out.print("5.Password:" + rs.getString(8));
                    System.out.print("\n You can only Update\n 1. Name 2.Address 3.Pincode 4.Password \n Which feild You want to Update");
                    int ch = sc.nextInt();
                    if (ch == 1) {
                        System.out.print("Please update your Name: ");
                        String name = bufferedReader.readLine();
                        stmt.executeUpdate("Update gpm set name ='" + name + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        con.close();
                    }if (ch == 2) {
                        System.out.print("Please update your Address: ");
                        String address = sc.nextLine();
                        stmt.executeUpdate("Update gpm set name ='" + address + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        con.close();
                    }if (ch == 3) {
                        System.out.print("Update your Pincode: ");
                        int pincode = sc.nextInt();
                        stmt.executeUpdate("Update gpm set name ='" + pincode + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        con.close();
                    }if (ch == 4) {
                        System.out.print("Update your Password:");
                        String password = sc.nextLine();
                        stmt.executeUpdate("Update gpm set name ='" + password + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        con.close();
                    }if (ch > 4)
                        System.out.print("Invalid Choice");
                } else
                    System.out.print("this email is not present");
            } else
                System.out.println("email is invalid!");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void deleteGpm() throws SQLException {
        try {
            vl.listOfGpm();
            Statement stmt = con.createStatement();
            System.out.print("Enter EmailId to delete the user: ");
            String email = sc.nextLine();
            if (vl.isValid(email)) {
                stmt.executeUpdate("update gpm set is_deleted = 'F' where email = '"+email+"'");
                con.close();
                System.out.print("User is Deleted!!!");
            } else
                System.out.print("Email is Invalid!!");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void createProject() throws SQLException, ParseException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            System.out.print("Create your Project :\n");
            System.out.print("Your Project Type 1.Road_Costruction,2.Sewage_Treatment,3.Building_Construction");
            String projectType = "";
            System.out.print("Choose your option");
            int ch = sc.nextInt();
            if (ch == 1)
                projectType = "Road Construction";
            if (ch == 2)
                projectType = "Sewage Treatment";
            if (ch == 3)
                projectType = "Builduing Construction";
            if (ch > 3)
                System.out.print("Invalid choice!!!");
            System.out.print("Project Name:");
            String projectName = bufferedReader.readLine();
            System.out.print("Address: ");
            String address = bufferedReader.readLine();
            System.out.print("Total member: ");
            int totalMember = sc.nextInt();
            System.out.print("Cost Estimation: ");
            float cost = sc.nextFloat();
            System.out.print("Enter Start date in format 'dd/MM/yyyy': ");
            String date1 = bufferedReader.readLine();
            java.util.Date myDate = new java.util.Date(date1);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            System.out.print("Enter End date in form 'dd/MM/yyyy");
            String date2 = sc.next();
            java.util.Date myDate1 = new java.util.Date(date2);
            java.sql.Date sqlDate1 = new java.sql.Date(myDate1.getTime());
            char is_deleted = 'T';
            stmt.executeUpdate("Insert into project(projectName,address,totalMembers,costEstimated,startDate,endDate,projectType,created_at,is_deleted)" +
                    "values('" + projectName + "','" + address + "','" + totalMember + "','" + cost + "','" + sqlDate + "','" + sqlDate1 + "','" + projectType + "',CURRENT_TIMESTAMP,'" + is_deleted + "')");
            con.close();
            System.out.print("Project is created.!!!");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void updateProject() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            vl.listOfProjects();
            System.out.println("Project name: ");
            String projectName = bufferedReader.readLine();
            ResultSet rs = stmt.executeQuery("Select * from project where is_deleted = 'T' and projectName='" + projectName + "' ");
            if (rs.next()) {
                System.out.println("1.Project Name: " + rs.getString(2));
                System.out.println("2.Address: " + rs.getString(3));
                System.out.println("3.TotalMembers: " + rs.getInt(4));
                System.out.println("4.Cost Estimation: " + rs.getFloat(5));
                System.out.println("5.StartDate: " + rs.getDate(6));
                System.out.println("6.EndDate: " + rs.getDate(7));
                System.out.println("7.ProjectType: " + rs.getString(8));
                System.out.println("You can only Update these fields\n 1.TotalMembers  2.Cost Estimation");
                int ch = sc.nextInt();
                if (ch == 1) {
                    System.out.print("Please update your Total Member of the Project : ");
                    int totalMmebers = sc.nextInt();
                    stmt.executeUpdate("Update project set totalMembers ='" + totalMmebers + "',updated_at=CURRENT_TIMESTAMP where projectName = '" + projectName + "'");
                    con.close();
                }
                if (ch == 2) {
                    System.out.println("Please update your Cost Estimation of the project: ");
                    float cost = sc.nextFloat();
                    stmt.executeUpdate("Update project set totalMembers ='" + cost + "',updated_at=CURRENT_TIMESTAMP where projectName = '" + projectName + "'");
                }
                if (ch > 2) {
                    System.out.println("Invalid Choice");
                }

            } else
                System.out.println("Project is not present!!!");

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteProject() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            vl.listOfProjects();
            Statement stmt = con.createStatement();
            System.out.print("Enter Project Name to delete it: ");
            System.out.println("Project name: ");
            String projectName = bufferedReader.readLine();
            ResultSet rs = stmt.executeQuery("Select * from project where is_deleted = 'T' and projectName='" + projectName + "' ");
            if (rs.next()) {
                stmt.executeUpdate("update project set is_deleted= 'F' where projectName = '"+projectName+"'");
                con.close();
                System.out.print("Project is Deleted!!!");
            } else
                System.out.println("Project is not present!!!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
