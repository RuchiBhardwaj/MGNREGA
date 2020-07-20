package com.company.service;

import java.io.IOException;
import java.sql.SQLException;

public interface member {
    public void memberOption() throws SQLException, IOException;
    public void memberLogin() throws SQLException;
    public void memberDetails() throws SQLException;
    public void fileComplain() throws SQLException, IOException;
}
