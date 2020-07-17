package com.company.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface bdo {

    public void viewComplaints() throws SQLException;
    public void bdoLogin() throws SQLException;
    public void bdoOption() throws SQLException;
    public void deleteGpm() throws SQLException;
    public void createProject() throws SQLException, ParseException;
    public void updateProject() throws SQLException;
    public void deleteProject() throws SQLException, IOException;
}
