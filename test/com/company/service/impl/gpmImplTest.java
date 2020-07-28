package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.utils.validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class gpmImplTest {
    @InjectMocks
    gpmImpl gpm;

    @Mock
    com.company.repo.databaseConnection databaseConnection;

    @Mock
    Connection connection;

    @Mock
    Statement statement;

    @Mock
    ResultSet resultSet;

    @Mock
    validator vl;

    @Mock
    BufferedReader bufferedReader;


    @Test
    public void createMemberTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi","m","abc","abc@gmail.com","12345");
        when(bufferedReader.read()).thenReturn(23,12345);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(1).when(statement).executeUpdate("Insert into member(email,name,age,gender,address,pin,gId,password,created_at,is_deleted)values('abc@gmail.com','ruchi',23,'m','abc'," +
                "12345,1,'12345',CURRENT_TIMESTAMP,'F')");
        gpm.createMember(bufferedReader);
    }

    @Test
    public void createMemberTryElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi","m","abc","abc@gmail.com","12345");
        when(bufferedReader.read()).thenReturn(23,12345);
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        gpm.createMember(bufferedReader);
    }

    @Test
    public void createMemberCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        gpm.createMember(bufferedReader);
    }

    @Test
    public void updateMemberTryIfFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(1);
        doReturn(resultSet).when(statement).executeQuery("Update member set name ='ruchi' ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        gpm.updateMember(bufferedReader);
        Assert.assertEquals(1,bufferedReader.read());
    }

    @Test
    public void updateMemberTryIfSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(2);
        doReturn(resultSet).when(statement).executeQuery("Update member set address ='abc' ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        gpm.updateMember(bufferedReader);
        Assert.assertEquals(2,bufferedReader.read());
    }

    @Test
    public void updateMemberTryIfThird() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(3);
        doReturn(resultSet).when(statement).executeQuery("Update member set pin =123 ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        gpm.updateMember(bufferedReader);
        Assert.assertEquals(3,bufferedReader.read());
    }

    @Test
    public void updateMemberTryIfFourth() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(4);
        doReturn(resultSet).when(statement).executeQuery("Update member set password ='12345' ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        gpm.updateMember(bufferedReader);
        Assert.assertEquals(4,bufferedReader.read());
    }

    @Test
    public void updateMemberTryIfFifth() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(7);
        gpm.updateMember(bufferedReader);
        Assert.assertEquals(7,bufferedReader.read());
    }

    @Test
    public void updateMemberTryElseFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(false);
        gpm.updateMember(bufferedReader);
    }

    @Test
    public void updateMemberTryElseSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","12345");
        when(bufferedReader.read()).thenReturn(123);
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        gpm.updateMember(bufferedReader);
    }

    @Test
    public void updateMemberCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        gpm.updateMember(bufferedReader);
    }

    @Test
    public void deletteMemberTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("update member set is_deleted = 'T' where email = 'abc@gmail.com'");
        gpm.deleteMember(bufferedReader);
    }

    @Test
    public void deletteMemberTryElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        gpm.deleteMember(bufferedReader);
    }
    @Test
    public void deletteMemberCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        gpm.deleteMember(bufferedReader);
    }

    @Test
    public void issueJobCardTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com'");
        when(resultSet.next()).thenReturn(true);
        gpm.issueJobCard(bufferedReader);

    }

    @Test
    public void issueJobCardTryElseFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email = 'abc@gmail.com'");
        when(resultSet.next()).thenReturn(false);
        gpm.issueJobCard(bufferedReader);

    }

    @Test
    public void issueJobCardTryElseSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        gpm.issueJobCard(bufferedReader);
    }

    @Test
    public void issueJobCardCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        gpm.issueJobCard(bufferedReader);
    }

    @Test
    public void projectAllotmentMemberTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","xyz");
        when(bufferedReader.read()).thenReturn(23);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email= 'abc@gmail.com'");
        when(resultSet.next()).thenReturn(true);
        doReturn(1).when(statement).executeUpdate("insert into memberWorks(projectName,memail,numberOfDays,wageComputation,wageStatus,ProjectStatus)" +
                "values('xyz','abc@gmail.com',23,2300,'Not Active','Not Active')");
        gpm.projectAllotmentMember(bufferedReader);
    }

    @Test
    public void projectAllotmentMemberTryElseFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","xyz");
        when(bufferedReader.read()).thenReturn(23);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from member where email= 'abc@gmail.com'");
        when(resultSet.next()).thenReturn(false);
        gpm.projectAllotmentMember(bufferedReader);
    }

    @Test
    public void projectAllotmentMemberTryElseSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfMember(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","xyz");
        when(bufferedReader.read()).thenReturn(23);
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        gpm.projectAllotmentMember(bufferedReader);
    }

    @Test
    public void projectAllotmentMemberCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        gpm.projectAllotmentMember(bufferedReader);
    }










}