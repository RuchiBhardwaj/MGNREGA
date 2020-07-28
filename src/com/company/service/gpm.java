package com.company.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public interface gpm {
    public void gpmOption() throws SQLException, IOException;
    public void gpmLogin() throws SQLException;
    public void createMember(BufferedReader bufferedReader) throws SQLException, IOException;
    public void updateMember(BufferedReader bufferedReader) throws SQLException;
    public void deleteMember(BufferedReader bufferedReader) throws SQLException;
    public void issueJobCard(BufferedReader bufferedReader) throws SQLException, IOException;
    public void projectAllotmentMember(BufferedReader bufferedReader) throws SQLException, IOException;

}
