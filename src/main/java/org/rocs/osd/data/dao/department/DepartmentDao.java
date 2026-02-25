package org.rocs.osd.data.dao.department;

import org.rocs.osd.model.department.Department;

import java.util.List;

public interface DepartmentDao {

    Department findDepartmentById(long departmentId);
    List<String> findAllDepartmentName();
}
