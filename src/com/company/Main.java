package com.company;

import com.company.service.impl.bdoImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
        bdoImpl bdo = new bdoImpl();
//        bdo.viewComplaints();
        bdo.login();
    }
}
