package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OffenseDaoImpl implements OffenseDao
{

    @Override
    public Student getStudentById(String StudentId)
    {
        Student student = new Student();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                        "SELECT " +
                            "s.studentID, " +
                            "s.personID, " +
                            "s.address, " +
                            "s.studentType, " +
                            "s.departmentID, " +
                            "p.lastName, " +
                            "p.firstName, " +
                            "p.middleName " +
                            "FROM student s " +
                            "JOIN person p ON s.personID = p.personID " +
                            "WHERE s.studentID = ?");

            statement.setString(1, StudentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getString("departmentID"));
                student.setLastName(rs.getString("lastName"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public Student getStudentByName(String lastName, String firstName, String middleName)
    {
        Student student = new Student();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                        "SELECT " +
                            "s.studentID," +
                            "s.personID," +
                            "s.address," +
                            "s.studentType," +
                            "s.departmentID," +
                            "p.lastName," +
                            "p.firstName," +
                            "p.middleName" +
                            "FROM student s" +
                            "JOIN person p ON s.personID = p.personID" +
                            "WHERE p.lastName = ?" +
                            "AND p.firstName = ?" +
                            "AND p.middleName = ?");

            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getString("departmentID"));
                student.setLastName(rs.getString("lastName"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public ArrayList<Record> getStudentRecord(String studentID)
    {
        ArrayList<Record> studentRecord = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                            "SELECT" +
                                "r.recordID," +
                                "r.enrollmentID," +
                                "r.employeeID," +
                                "r.offenseID," +
                                "r.dateOfViolation," +
                                "r.actionID," +
                                "r.dateOfResolution," +
                                "r.remarks," +
                                "r.status" +
                                "FROM record r" +
                                "JOIN enrollment e ON r.enrollmentID = e.enrollmentID " +
                                "JOIN student s ON e.studentID = s.studentID " +
                                "WHERE s.studentID = ?");

            statement.setString(1, studentID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                Record record = new Record();
                record.setRecordId(rs.getString("recordID"));
                record.setEnrollmentId(rs.getString("enrollmentID"));
                record.setEmployeeId(rs.getString("employeeID"));
                record.setOffenseId(rs.getString("offenseID"));
                record.setDateOfViolation(rs.getDate("dateOfViolation"));
                record.setActionId(rs.getString("actionID"));
                record.setDateOfResolution(rs.getDate("dateOfResolution"));
                record.setRemarks(rs.getString("remarks"));
                record.setStatus(rs.getString("status"));
                studentRecord.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentRecord;
    }

    @Override
    public Offense getStudentOffense(String offenseID)
    {
        Offense offense = new Offense();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT " +
                            "o.offenseID," +
                            "o.offense," +
                            "o.type," +
                            "o.description" +
                            "FROM offense o where offenseID = ?");

            statement.setString(1, offenseID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                offense.setOffenseId(rs.getString("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("type"));
                offense.setDescription(rs.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offense;
    }

    @Override
    public boolean addStudentViolation(String recordID, String enrollmentID, String prefectID,
                                    String offenseID, String dateOfViolation, String actionID,
                                    String remarks, String status)
    {

        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    " INSERT INTO record (" +
                    "    recordID," +
                    "    enrollmentID," +
                    "    prefectID," +
                    "    offenseID," +
                    "    dateOfViolation," +
                    "    actionID," +
                    "    remarks," +
                    "    status" +
                    "    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            stmt.setString(1, recordID);
            stmt.setString(2, enrollmentID);
            stmt.setString(3, prefectID);
            stmt.setString(4, offenseID);
            stmt.setString(5, dateOfViolation);
            stmt.setString(6, actionID);
            stmt.setString(7, remarks);
            stmt.setString(8, status);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e)
        {
            System.out.println("An SQL Exception occurred." + e.getMessage());

            return false;
        }
    }
}
