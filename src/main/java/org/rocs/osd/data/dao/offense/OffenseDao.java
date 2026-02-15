package org.rocs.osd.data.dao.offense;



import java.sql.Date;
import java.util.ArrayList;

public interface OffenseDao
{
    boolean editExistingViolation(long recordID, String status, long OffenseID,
                                  String remarks);

    boolean editExistingDateOfViolation(long recordID, Date dateOfViolation);
    boolean editExistingDateOfResolution(long recordID, Date dateOfResolution);
}
