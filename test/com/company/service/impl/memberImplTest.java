package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.utils.validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class memberImplTest {

    @InjectMocks
    memberImpl member;

    @Mock
    databaseConnection databaseConnection;

    @Mock
    Connection connection;

    @Mock
    Statement statement;

    @Mock
    ResultSet resultSet;

    @Mock
    BufferedReader bufferedReader;

    @Mock
    validator vl;

    @Test
    public void memberDetailsTry() throws SQLException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        doReturn(resultSet).when(statement).executeQuery("select member.name,member.age,member." +
                "gender,member.address,member." +
                "pin,memberWorks.wageComputation,memberWorks" +
                ".numberOfDays,memberWorks.projectName FROM member INNER JOIN " +
                " memberWorks ON member.email=memberWorks.memail" +
                " where mId = 1");
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        doReturn("ruchi").when(resultSet).getString(1);
        member.memberDetails();
        Assert.assertEquals("ruchi",resultSet.getString(1));

    }
    @Test
    public void memberDetailsCatch() throws SQLException{
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        member.memberDetails();
    }

    @Test
    public void fileComplainTry() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("I am not happy!");
        doReturn(1).when(statement).executeUpdate("insert into report(mId,issue,created_at)values(1,'I am not happy!',CURRENT_TIMESTAMP)");
        member.fileComplain(bufferedReader);

    }

    @Test
    public void fileComplainCatch() throws IOException, SQLException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        member.fileComplain(bufferedReader);
    }

    @Test
    public void memberLogin() throws SQLException, IOException {
        memberImpl temp = new memberImpl();
        memberImpl spyTemp = Mockito.spy(temp);
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi@gmail.com","abc");
        when(vl.isValid("ruchi@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'ruchi@gmail.com'  AND password = 'abc' ");
        when(resultSet.next()).thenReturn(true);
        doReturn(true).doReturn(false).when(spyTemp).memberOption();
        when(bufferedReader.read()).thenReturn(5);
        member.memberLogin(bufferedReader);
    }
}