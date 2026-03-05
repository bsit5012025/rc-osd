package org.rocs.osd.model.person.student;

import org.rocs.osd.model.person.Person;

    /**
    * Represents a student
    */
    public class Student extends Person {
    /**
     * Unique student identifier
     */
    private String studentId;
    /**
     * address of the student
     */
    private String address;
    /**
     * Type of student
     */
    private String studentType;
    /**
     * Department ID of the student
     */
    private String departmentId;

        /**
         * Default constructor
         */
    public Student() {
    }

        /**
         * @param personID     unique ID from the Person class
         * @param lastName     student's last name
         * @param firstName    student's first name
         * @param middleName   student's middle name
         * @param studentId    unique student ID/enrollment number
         * @param address      student's address
         * @param studentType  type of student
         * @param departmentId department ID of the student
         */
    public Student(Long personID, String lastName, String firstName, String middleName, String studentId, String address, String studentType, String departmentId) {
        super(personID, lastName, firstName, middleName);
        this.studentId = studentId;
        this.address = address;
        this.studentType = studentType;
        this.departmentId = departmentId;
    }



        /**
         * Gets the student ID
         * @return the studentId
         */
    public String getStudentId() {
        return studentId;
    }

        /**
         * Sets the student ID.
         * @param studentId the studentId to set
         */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

        /**
         * Gets the student's address.
         *
         * @return the address
         */
    public String getAddress() {
        return address;
    }

        /**
         * Sets the student's address.
         * @param address  the address to set
         */
    public void setAddress(String address) {
        this.address = address;
    }

        /**
         * Gets the type of student
         * @return the studentType
         */
        public String getStudentType() {
        return studentType;
    }

        /**
         * Sets the type of student
         * @param studentType the studentType to set
         */
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

        /**
         * Gets the department ID of the student
         * @return the departmentId
         */
    public String getDepartmentId() {
        return departmentId;
    }

        /**
         * Sets the department ID of the student
         * @param departmentId the departmentId to set
         */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
