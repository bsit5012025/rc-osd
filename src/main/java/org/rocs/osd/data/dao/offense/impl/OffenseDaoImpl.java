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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OffenseDaoImpl implements OffenseDao
{

    @Override
    public List<Student> getAllStudent() {
        ArrayList<Student> studentList = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                    " SELECT " +
                            "    s.studentID, " +
                            "    s.personID, " +
                            "    s.address, " +
                            "    s.studentType, " +
                            "    s.departmentID, " +
                            "    p.lastName, " +
                            "    p.firstName, " +
                            "    p.middleName " +
                            "FROM student s " +
                            "JOIN person p ON s.personID = p.personID "
            );
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                Student student = new Student();
                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getString("departmentID"));
                student.setLastName(rs.getString("lastName"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));

                studentList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    @Override
    public List<Student> getStudentByDepartmentID(long departmentID)
    {
        ArrayList<Student> studentList = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                    " SELECT " +
                    "    s.studentID, " +
                    "    s.personID, " +
                    "    s.address, " +
                    "    s.studentType, " +
                    "    s.departmentID, " +
                    "    p.lastName, " +
                    "    p.firstName, " +
                    "    p.middleName " +
                    "FROM student s " +
                    "JOIN person p ON s.personID = p.personID " +
                    "WHERE departmentID = ?"
            );
            statement.setLong(1, departmentID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                Student student = new Student();
                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getString("departmentID"));
                student.setLastName(rs.getString("lastName"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));

                studentList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

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
    public List<Record> getStudentRecord(String studentID)
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
                record.setRecordId(rs.getLong("recordID"));
                record.setEnrollmentId(rs.getLong("enrollmentID"));
                record.setEmployeeId(rs.getString("employeeID"));
                record.setOffenseId(rs.getLong("offenseID"));
                record.setDateOfViolation(rs.getDate("dateOfViolation"));
                record.setActionId(rs.getLong("actionID"));
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
                offense.setOffenseId(rs.getLong("offenseID"));
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
    public boolean addStudentViolation(long enrollmentID, String employeeID,
                                       long offenseID, Date dateOfViolation, long  actionID,
                                       String remarks, String status)
    {

        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                        " INSERT INTO record (" +
                            "    enrollmentID," +
                            "    employeeID," +
                            "    offenseID," +
                            "    dateOfViolation," +
                            "    actionID," +
                            "    remarks," +
                            "    status" +
                            "    ) VALUES (?, ?, ?, ?, ?, ?, ?)");

            stmt.setLong(1, enrollmentID);
            stmt.setString(2, employeeID);
            stmt.setLong(3, offenseID);
            stmt.setDate(4, new Date(dateOfViolation.getTime()));
            stmt.setLong(5, actionID);
            stmt.setString(6, remarks);
            stmt.setString(7, status);
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
