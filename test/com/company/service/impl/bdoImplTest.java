package com.company.service.impl;

import com.company.repo.databaseConnection;
import com.company.utils.validator;
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class bdoImplTest {
    @InjectMocks
    bdoImpl bdo;

    @Mock
    databaseConnection databaseConnection;

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
    public void viewComplaintsTry() throws SQLException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        doReturn(resultSet).when(statement).executeQuery("select member.email, member.name, report.issue from member INNER JOIN report ON member.mId = report.mId");
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        doReturn("ruchi").when(resultSet).getString(2);
        bdo.viewComplaints();
        Assert.assertEquals("ruchi",resultSet.getString(2));

    }
    @Test
    public void viewComplaintsCatch() throws SQLException {
     doThrow(SQLException.class).when(databaseConnection).getConnection();
     bdo.viewComplaints();
    }

    @Test
    public void bdoLoginTryIf() throws SQLException, IOException, ParseException {
        bdoImpl temp = new bdoImpl();
        bdoImpl spyTemp = Mockito.spy(temp);
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi@gmail.com","abc");
        when(vl.isValid("ruchi@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from user where email = 'ruchi@gmail.com'  AND password = 'abc' ");
        when(resultSet.next()).thenReturn(true);
        doReturn(true).doReturn(false).when(spyTemp).bdoOption();
        when(bufferedReader.read()).thenReturn(13);
        bdo.bdoLogin(bufferedReader);
    }
    @Test
    public void bdoLoginTryFirstElse() throws SQLException, IOException, ParseException {
        bdoImpl temp = new bdoImpl();
        bdoImpl spyTemp = Mockito.spy(temp);
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi@gmail.com","abc");
        when(vl.isValid("ruchi@gmail.com")).thenReturn(false);
        doReturn(resultSet).when(statement).executeQuery("select * from user where email = 'ruchi@gmail.com'  AND password = 'abc' ");
        when(resultSet.next()).thenReturn(false);
        bdo.bdoLogin(bufferedReader);
    }

    @Test
    public void bdoLoginTrySecondElse() throws SQLException, IOException, ParseException {
        bdoImpl temp = new bdoImpl();
        bdoImpl spyTemp = Mockito.spy(temp);
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi@gmail.com","abc");
        when(vl.isValid("ruchi@gmail.com")).thenReturn(false);
        bdo.bdoLogin(bufferedReader);
    }

    @Test
    public void bdoLoginCatch() throws SQLException, IOException, ParseException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.bdoLogin(bufferedReader);
    }


    @Test
    public void createGpmTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
         when(bufferedReader.readLine()).thenReturn("ruchi","abc","abc@gmail.com");
         when(bufferedReader.read()).thenReturn(23,12345);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com'");
        when(resultSet.next()).thenReturn(true);
        bdo.createGpm(bufferedReader);
    }
    @Test
    public void createGpmElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi","abc","abc@gmail.com","123456");
        when(bufferedReader.read()).thenReturn(23,12345);
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        bdo.createGpm(bufferedReader);
    }

    @Test
    public void createGpmElseSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.readLine()).thenReturn("ruchi","abc","abc@gmail.com","123456");
        when(bufferedReader.read()).thenReturn(23,12345);
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com'");
        when(resultSet.next()).thenReturn(false);
        doReturn(1).when(statement).executeUpdate("Insert into gpm(email,name,age,address,bdoId,pin,password,created_at,is_deleted)values ('abc@gmail.com','ruchi',23,'abc',1,12345,'123456',CURRENT_TIMESTAMP,'F')");
        bdo.createGpm(bufferedReader);
    }

    @Test
    public void createGpmCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.createGpm(bufferedReader);
    }
    @Test
    public void updateGpmTryIfFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(1);
        doReturn(resultSet).when(statement).executeQuery("Update gpm set name ='ruchi' ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        bdo.updateGpm(bufferedReader);
        Assert.assertEquals(1,bufferedReader.read());
    }

    @Test
    public void updateGpmTryIfSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(2);
        doReturn(resultSet).when(statement).executeQuery("Update gpm set address ='ruchi' ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        bdo.updateGpm(bufferedReader);
        Assert.assertEquals(2,bufferedReader.read());
    }

    @Test
    public void updateGpmTryIfThird() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(3);
        doReturn(resultSet).when(statement).executeQuery("Update gpm set pin =123 ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        bdo.updateGpm(bufferedReader);
        Assert.assertEquals(3,bufferedReader.read());
    }
    @Test
    public void updateGpmTryIfFourth() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(4);
        doReturn(resultSet).when(statement).executeQuery("Update gpm set pin =123 ,updated_at=CURRENT_TIMESTAMP where email = 'abc@gmail.com'");
        bdo.updateGpm(bufferedReader);
        Assert.assertEquals(4,bufferedReader.read());
    }
    @Test
    public void updateGpmTryIfFifth() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(7);
        bdo.updateGpm(bufferedReader);
        Assert.assertEquals(7,bufferedReader.read());
    }

    @Test
    public void updateGpmElseFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("select * from gpm where email = 'abc@gmail.com' ");
        when(resultSet.next()).thenReturn(false);
        bdo.updateGpm(bufferedReader);
    }

    @Test
    public void updateGpmElseSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com","ruchi","abc","123456");
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        bdo.updateGpm(bufferedReader);
    }

    @Test
    public void updateGpmCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.updateGpm(bufferedReader);
    }
    @Test
    public void deleteGpmTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        doReturn(resultSet).when(statement).executeQuery("update gpm set is_deleted = 'T' where email = 'abc@gmail.com'");
        bdo.deleteGpm(bufferedReader);
    }

    @Test
    public void deleteGpmTryElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfGpm(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        bdo.deleteGpm(bufferedReader);
    }

    @Test
    public void deleteGpmCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.deleteGpm(bufferedReader);
    }

    @Test
    public void createProjectTryIfFirst() throws SQLException, IOException, ParseException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.read()).thenReturn(1);
        when(bufferedReader.readLine()).thenReturn("xyz","abc","23/02/2020","24/01/2020");
        doReturn(1).when(statement).executeUpdate("Insert into project(projectName,address,totalMembers,costEstimated,startDate,endDate,projectType,created_at,is_deleted)" +
                        "values('xyz','abc',1,100,'23/02/2020','24/01/2020','Road Construction',CURRENT_TIMESTAMP,'f')");
        bdo.createProject(bufferedReader);
    }
    @Test
    public void createProjectTryIfSecond() throws SQLException, IOException, ParseException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.read()).thenReturn(2);
        when(bufferedReader.readLine()).thenReturn("xyz","abc","23/02/2020","24/01/2020");
        doReturn(1).when(statement).executeUpdate("Insert into project(projectName,address,totalMembers,costEstimated,startDate,endDate,projectType,created_at,is_deleted)" +
                "values('xyz','abc',1,100,'23/02/2020','24/01/2020','Road Construction',CURRENT_TIMESTAMP,'f')");
        bdo.createProject(bufferedReader);
    }

    @Test
    public void createProjectTryIfThird() throws SQLException, IOException, ParseException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.read()).thenReturn(3);
        when(bufferedReader.readLine()).thenReturn("xyz","abc","23/02/2020","24/01/2020");
        doReturn(1).when(statement).executeUpdate("Insert into project(projectName,address,totalMembers,costEstimated,startDate,endDate,projectType,created_at,is_deleted)" +
                "values('xyz','abc',1,100,'23/02/2020','24/01/2020','Road Construction',CURRENT_TIMESTAMP,'f')");
        bdo.createProject(bufferedReader);
    }
    @Test
    public void createProjectTryIfFourth() throws SQLException, IOException, ParseException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(bufferedReader.read()).thenReturn(5);
        when(bufferedReader.readLine()).thenReturn("xyz","abc","23/02/2020","24/01/2020");
        bdo.createProject(bufferedReader);
    }

    @Test
    public void createProjectCatch() throws SQLException, IOException, ParseException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.createProject(bufferedReader);
    }

    @Test
    public void updateProjectTryIfFirst() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfProjects(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("xyz");
        doReturn(resultSet).when(statement).executeQuery("Select * from project where is_deleted = 'F' and projectName='xyz' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(1);
        doReturn(1).when(statement).executeUpdate("Update project set totalMembers =200,updated_at=CURRENT_TIMESTAMP where projectName = 'xyz'");
        bdo.updateProject(bufferedReader);
        Assert.assertEquals(1,bufferedReader.read());

    }

    @Test
    public void updateProjectTryIfSecond() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfProjects(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("xyz");
        doReturn(resultSet).when(statement).executeQuery("Select * from project where is_deleted = 'F' and projectName='xyz' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(2);
        doReturn(1).when(statement).executeUpdate("Update project set totalMembers =200,updated_at=CURRENT_TIMESTAMP where projectName = 'xyz'");
        bdo.updateProject(bufferedReader);
        Assert.assertEquals(2,bufferedReader.read());
    }

    @Test
    public void updateProjectTryIfThird() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfProjects(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("xyz");
        doReturn(resultSet).when(statement).executeQuery("Select * from project where is_deleted = 'F' and projectName='xyz' ");
        when(resultSet.next()).thenReturn(true);
        when(bufferedReader.read()).thenReturn(6);
        doReturn(1).when(statement).executeUpdate("Update project set totalMembers =200,updated_at=CURRENT_TIMESTAMP where projectName = 'xyz'");
        bdo.updateProject(bufferedReader);
        Assert.assertEquals(6,bufferedReader.read());
    }

    @Test
    public void updateProjectElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfProjects(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("xyz");
        doReturn(resultSet).when(statement).executeQuery("Select * from project where is_deleted = 'F' and projectName='xyz' ");
        when(resultSet.next()).thenReturn(false);
        bdo.updateProject(bufferedReader);
    }

    @Test
    public void updateProjectCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.updateProject(bufferedReader);
    }
    @Test
    public void deleteProjectTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfProjects(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("xyz");
        doReturn(resultSet).when(statement).executeQuery("Select * from project where is_deleted = 'F' and projectName='xyz' ");
        when(resultSet.next()).thenReturn(true);
        doReturn(1).when(statement).executeUpdate("\"update project set is_deleted= 'T' where projectName = 'xyz'");
        bdo.deleteProject(bufferedReader);
    }

    @Test
    public void deleteProjectTryElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        when(vl.listOfProjects(statement)).thenReturn(true);
        when(bufferedReader.readLine()).thenReturn("xyz");
        doReturn(resultSet).when(statement).executeQuery("Select * from project where is_deleted = 'F' and projectName='xyz' ");
        when(resultSet.next()).thenReturn(false);
        bdo.deleteProject(bufferedReader);
    }

    @Test
    public void deleteProjectCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.deleteProject(bufferedReader);
    }

    @Test
    public void approveWorkTryIf() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        doReturn(resultSet).when(statement).executeQuery("select member.name , member.age,member.gender,member.email,memberWorks.wageComputation" +
                ",memberWorks.numberOfDays,memberWorks.projectName,project.totalMembers" +
                ",project.costEstimated,memberWorks.projectStatus from member INNER JOIN memberWorks" +
                " ON member.email = memberWorks.memail  INNER JOIN project  on project.projectName = memberWorks.projectName" +
                " where memberWorks.projectStatus= " +
                "'Not Active'");
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        doReturn("ruchi").when(resultSet).getString(1);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(true);
        bdo.approveWork(bufferedReader);
    }

    @Test
    public void approveWorkTryElse() throws SQLException, IOException {
        doReturn(connection).when(databaseConnection).getConnection();
        doReturn(statement).when(connection).createStatement();
        doReturn(resultSet).when(statement).executeQuery("select member.name , member.age,member.gender,member.email,memberWorks.wageComputation" +
                ",memberWorks.numberOfDays,memberWorks.projectName,project.totalMembers" +
                ",project.costEstimated,memberWorks.projectStatus from member INNER JOIN memberWorks" +
                " ON member.email = memberWorks.memail  INNER JOIN project  on project.projectName = memberWorks.projectName" +
                " where memberWorks.projectStatus= " +
                "'Not Active'");
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        doReturn("ruchi").when(resultSet).getString(1);
        when(bufferedReader.readLine()).thenReturn("abc@gmail.com");
        when(vl.isValid("abc@gmail.com")).thenReturn(false);
        bdo.approveWork(bufferedReader);
    }

    @Test
    public void approveWorkCatch() throws SQLException, IOException {
        doThrow(SQLException.class).when(databaseConnection).getConnection();
        bdo.approveWork(bufferedReader);
    }

}