package com.company.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public interface member {
    public boolean memberOption() throws SQLException, IOException;
    public void memberLogin(BufferedReader bufferedReader) throws SQLException;
    public void memberDetails() throws SQLException;
    public void fileComplain(BufferedReader bufferedReader) throws SQLException, IOException;
}
