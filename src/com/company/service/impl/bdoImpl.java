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

public class bdoImpl implements bdo{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    databaseConnection db = new databaseConnection();
    validator vl = new validator();
    Scanner sc = new Scanner(System.in);
    int UID;

    public void bdoOption() throws SQLException, ParseException, IOException {
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            System.out.print("\nChoose your option \n 1.Create Gpm \n 2.Update Gpm \n " +
                    "3.Delete Gpm \n 4.Create Project \n 5.Update Project \n 6.Delete Project \n" +
                    "7.Show all the GPM \n 8.Show all the Member \n 9. Notifications \n 10. Exit \n");
            int i = sc.nextInt();
            while (i <= 10) {
                if (i == 1)
                    createGpm();
                else if (i == 2)
                    updateGpm();
                else if (i == 3)
                    deleteGpm();
                else if (i == 4)
                    createProject();
                else if (i == 5)
                    updateProject();
                else if (i == 6)
                    deleteProject();
                else if( i==7)
                    vl.listOfGpm(stmt);
                else if (i == 8)
                    vl.showMember();
                else if (i ==9)
                    notification();
                else if(i == 10) {
                    System.out.println("\t****Thanks for using 'Mahatma Gandhi National Rural Employment Act' system**** ");
                    break;
                }
                System.out.print("\nChoose your option \n 1.Create Gpm \n 2.Update Gpm \n " +
                        "3.Delete Gpm \n 4.Create Project \n 5.Update Project \n 6.Delete Project \n" +
                        "7.Show all the GPM \n 8.Show all the Member\n 9. Notifications \n 10. Exit\n");                i = sc.nextInt();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void viewComplaints() throws SQLException {
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select member.email, member.name, report.issue from member INNER JOIN report ON member.mId = report.mId");
            while (rs.next())
                System.out.print("Member Email: "+rs.getString(1) + "  \n" + "Member Name: "+rs.getString(2) + " \n " + "Issue: "+rs.getString(3)+"\n-----------------------");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void bdoLogin() throws SQLException {
        try {
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            System.out.print("Enter your EmailId: ");
            String Email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String Password = sc.nextLine();
            if (vl.isValid(Email)) {
                ResultSet rs = stmt.executeQuery("select * from user where email = '" + Email + "'  AND password = '" + Password + "' ");
                if (rs.next()) {
                    int BdoId = rs.getInt(1);
                    this.UID = BdoId;
                    con.close();
                    stmt.close();
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
            Connection con = db.getConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            int BdoId = this.UID;
            char is_deleted = 'F';
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
                ResultSet rs = stmt.executeQuery("select * from gpm where email = '" + email + "'");
                if (rs.next()) {
                    System.out.println("Already user Present!!!");
                } else {
                    System.out.print("Enter Password: ");
                    String password = bufferedReader.readLine();
                    stmt.executeUpdate("Insert into gpm(email,name,age,address,bdoId,pin,password,created_at,is_deleted)values ('" + email + "','" + name + "','" + age + "','" + address + "','" + BdoId + "','" + pin + "','" + password + "',CURRENT_TIMESTAMP,'" + is_deleted + "')");
                    System.out.println("Gpm is created!!!");
                }
            } else
                System.out.print("Invalid EmailId! ");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }

    }


    public void updateGpm() throws SQLException {
        try {
            Connection con = db.getConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            vl.listOfGpm(stmt);
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
                        stmt.execute("Update gpm set name ='" + name + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    } else if (ch == 2) {
                        System.out.print("Please update your Address: ");
                        String address = sc.nextLine();
                        stmt.executeUpdate("Update gpm set name ='" + address + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    } else if (ch == 3) {
                        System.out.print("Update your Pincode: ");
                        int pincode = sc.nextInt();
                        stmt.executeUpdate("Update gpm set name ='" + pincode + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    } else if (ch == 4) {
                        System.out.print("Update your Password:");
                        String password = sc.nextLine();
                        stmt.executeUpdate("Update gpm set name ='" + password + "' ,updated_at=CURRENT_TIMESTAMP where email = '" + email + "'");
                        System.out.println("Updated!!!");
                    } else if (ch > 4)
                        System.out.print("Invalid Choice");
                    con.close();
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
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            vl.listOfGpm(stmt);
            System.out.print("Enter EmailId to delete the user: ");
            String email = sc.nextLine();
            if (vl.isValid(email)) {
                stmt.executeUpdate("update gpm set is_deleted = 'T' where email = '" + email + "'");
                System.out.print("User is Deleted!!!");
            } else
                System.out.print("Email is Invalid!!");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void createProject() throws SQLException, ParseException {
        try {
            Connection con = db.getConnection();
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
                projectType = "Building Construction";
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
            char is_deleted = 'F';
            stmt.executeUpdate("Insert into project(projectName,address,totalMembers,costEstimated,startDate,endDate,projectType,created_at,is_deleted)" +
                    "values('" + projectName + "','" + address + "','" + totalMember + "','" + cost + "','" + sqlDate + "','" + sqlDate1 + "','" + projectType + "',CURRENT_TIMESTAMP,'" + is_deleted + "')");
            System.out.print("Project is created.!!!");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void updateProject() throws SQLException, IOException {
        try {
            Connection con = db.getConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            vl.listOfProjects(stmt);
            System.out.println("Project name: ");
            String projectName = bufferedReader.readLine();
            ResultSet rs = stmt.executeQuery("Select * from project where is_deleted = 'F' and projectName='" + projectName + "' ");
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
                    System.out.println("Updated!");
                } else if (ch == 2) {
                    System.out.println("Please update your Cost Estimation of the project: ");
                    float cost = sc.nextFloat();
                    stmt.executeUpdate("Update project set totalMembers ='" + cost + "',updated_at=CURRENT_TIMESTAMP where projectName = '" + projectName + "'");
                    System.out.println("Updated!");
                } else if (ch > 2) {
                    System.out.println("Invalid Choice");
                }

            } else
                System.out.println("Project is not present!!!");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteProject() throws SQLException, IOException {
        try {
            Connection con = db.getConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            vl.listOfProjects(stmt);
            System.out.print("Enter Project Name to delete it: ");
            System.out.println("Project name: ");
            String projectName = bufferedReader.readLine();
            ResultSet rs = stmt.executeQuery("Select * from project where is_deleted = 'F' and projectName='" + projectName + "' ");
            if (rs.next()) {
                stmt.executeUpdate("update project set is_deleted= 'T' where projectName = '" + projectName + "'");
                System.out.print("Project is Deleted!!!");
            } else
                System.out.println("Project is not present!!!");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void approveWork() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select member.name , member.age,member.gender,member.email,memberWorks.wageComputation" +
                    ",memberWorks.numberOfDays,memberWorks.projectName,project.totalMembers" +
                    ",project.costEstimated,memberWorks.projectStatus from member INNER JOIN memberWorks" +
                    " ON member.email = memberWorks.memail  INNER JOIN project  on project.projectName = memberWorks.projectName" +
                    " where memberWorks.projectStatus= " +
                    "'Not Active'");
            String[] emails = new String[100];
            int i =0;
            while (rs.next()) {
                System.out.println("Name: " + rs.getString(1) + " \n" + "Age: " + rs.getInt(2) + " \n" + "Gender: " + rs.getString(3) + " \n" + "Email: " + rs.getString(4) + " \n" + "Wage Computation: " + rs.getInt(5) +
                        " \n" + "Cost Estimation: " + rs.getInt(6) + " \n" + "Project Status: " + rs.getString(7) + "\n---------");
                emails[i++]=rs.getString(4) ;
            }


                System.out.println("Enter Email of the member to approve his/her work:");
                String email = bufferedReader.readLine();
                if (vl.isValid(email)) {
                    for(int j = 0 ; j < i; j++) {
                        if (email.equals(emails[j])) {
                            stmt.executeUpdate("update memberWorks set projectStatus= 'Active' where email = '" + email + "'");
                            System.out.println("Congratulation! It is Approved!!!...");
                        }
                    }

                } else
                        System.out.println("Email is Invalid!!!");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void approveWages() throws SQLException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Connection con = db.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select member.name ,member.gender,member.email,memberWorks.wageComputation," +
                    "memberWorks.projectName from member INNER JOIN memberWorks ON member.email = memberWorks.memail   where " +
                    "memberWorks.wageStatus='Not Active'");
            String[] emails = new String[100];
            int i =0;
            while (rs.next()) {
                System.out.println("Name: " + rs.getString(1) +" \n"+ "Gender: " + rs.getString(2) +" \n"+ "Email: " + rs.getString(3) +
                        " \n"+"Wage Computation: " + rs.getInt(4) +" \n"+ "Project Name: " + rs.getString(5)+"\n-------------------");
                emails[i++]=rs.getString(3) ;
            }
            System.out.println("\nEnter Email of the member to approve his/her wage:");
            String email = bufferedReader.readLine();
            if (vl.isValid(email)) {
                for(int j = 0 ; j < i; j++) {
                    if(email.equals(emails[j])) {
                        stmt.executeUpdate("update memberWorks set wageStatus= 'Active' where memail = '" + email + "'");
                        System.out.println("Congratulation! It is Approved!!!...");
                    }
                }
            } else
                System.out.println("Email is Invalid!!!");
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void notification() throws SQLException, IOException {
        try {
            System.out.println("\nSelect ...\n 1. Approve Work\n 2. Approve Wages \n 3.Complains\n");
            int i = sc.nextInt();
            while (i <= 3) {
                if (i == 1)
                    approveWork();
                else if (i == 2)
                    approveWages();
                else if (i == 3)
                    viewComplaints();
                else if (i == 4){
                    System.out.println("Back to Main options");
                    break;
                }
                System.out.println("\nSelect ...\n 1. Approve Work\n 2. Approve Wages \n 3.Complains\n");
                i = sc.nextInt();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}

