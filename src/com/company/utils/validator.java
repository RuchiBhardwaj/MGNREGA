package com.company.utils;

import com.company.repo.databaseConnection;

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

    public void listOfProjects() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from project where is_deleted = 'T'");
            while (rs.next())
                System.out.println("Project Name: " + rs.getString(2)+ "\n "+"Address: " + rs.getString(3)+"\n "+"TotalMembers: " + rs.getInt(4)+"\n "+"Cost Estimation: " + rs.getFloat(5)+"\n "+"Start Date: " + rs.getDate(6)+"\n "+"End Date: " + rs.getDate(7)+"\n "+"Project Type: "+rs.getString(8)+"\n---------------");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void listOfGpm() throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from gpm where is_deleted= 'T'");
            while (rs.next())
                System.out.println("Email: " + rs.getString(2)+ "\n "+"Name: " + rs.getString(3)+"\n "+"Age: " + rs.getInt(4)+"\n "+"Address: " + rs.getString(5)+"\n "+"Pincode: " + rs.getInt(7)+"\n "+"Password: " + rs.getString(8)+"\n---------------");
            con.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
