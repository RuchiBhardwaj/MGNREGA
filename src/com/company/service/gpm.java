package com.company.service;

import java.io.IOException;
import java.sql.SQLException;

public interface gpm {
    public void gpmOption() throws SQLException, IOException;
    public void gpmLogin() throws SQLException;
    public void createMember() throws SQLException, IOException;
    public void updateMember() throws SQLException;
    public void deleteMember() throws SQLException;
    public void issueJobCard() throws SQLException, IOException;
    public void ProjectAllotmentMember() throws SQLException, IOException;

}
