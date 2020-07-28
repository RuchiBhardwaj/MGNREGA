package com.company.utils;

import com.company.repo.databaseConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class validator {
    databaseConnection db = new databaseConnection();
    Connection con = db.getConnection();
    public boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean listOfProjects(Statement stmt) throws SQLException {
        try {
            ResultSet rs = stmt.executeQuery("select * from project where is_deleted = 'F'");
            while (rs.next())
                System.out.println("Project Name: " + rs.getString(2)+ "\n "+"Address: " + rs.getString(3)+"\n "+"TotalMembers: " + rs.getInt(4)+"\n "+"Cost Estimation: " + rs.getFloat(5)+"\n "+"Start Date: " + rs.getDate(6)+"\n "+"End Date: " + rs.getDate(7)+"\n "+"Project Type: "+rs.getString(8)+"\n---------------");
            return true;
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    public boolean listOfGpm(Statement stmt) throws SQLException {
        try {
            ResultSet rs = stmt.executeQuery("select * from gpm where is_deleted= 'F'");
            while (rs.next())
                System.out.println("Email: " + rs.getString(2)+ "\n "+"Name: " + rs.getString(3)+"\n "+"Age: " + rs.getInt(4)+"\n "+"Address: " + rs.getString(5)+"\n "+"Pincode: " + rs.getInt(7)+"\n "+"Password: " + rs.getString(8)+"\n---------------");
            return true;
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }
    public boolean listOfMember(Statement stmt) throws SQLException{
        try {
            System.out.println("Here is all the list of Member.....");
            ResultSet rs = stmt.executeQuery("select * from member where is_deleted= 'F'");
            while (rs.next())
                System.out.println("Email: " + rs.getString(2)+ "\n "+"Name: " + rs.getString(3)+"\n "+"Age: " + rs.getInt(4)+"\n "+"Address: " + rs.getString(6)+"\n "+"Pincode: " + rs.getInt(7)+"\n "+"Password: " + rs.getString(9)+"\n---------------");
            return true;
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }

    }

    public void showMember() throws SQLException, IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Statement stmt = con.createStatement();
            System.out.println("Enter Gpm EmailId: ");
            String email = bufferedReader.readLine();
            if (isValid(email)) {
                ResultSet rs = stmt.executeQuery("select member.name,member.age,member.gender,member.address," +
                        "member.pin,memberWorks.wageComputation,memberWorks.numberOfDays FROM member INNER JOIN " +
                        " memberWorks ON member.email=memberWorks.memail  INNER JOIN gpm ON gpm.gId = member.gId " +
                        "where gpm.email='" + email + "'");
                System.out.println("Hers is the members under this GPM");
                while (rs.next()) {
                    System.out.println("Member Name: " + rs.getString(1) +" \n"+ "Age: " +
                            rs.getInt(2)+" \n"+"Gender: "+rs.getString(4)+" \n"+"Pincode: "
                            +rs.getInt(5)+" \n"+"Wage Computation: "+rs.getInt(6)+" \n"+"NUmber Of Days: "+rs.getInt(7)+"\n---------------");
                }
            }
            else
                System.out.println("Invalid Email!!!...");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
