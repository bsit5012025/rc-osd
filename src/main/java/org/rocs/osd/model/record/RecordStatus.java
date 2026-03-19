package org.rocs.osd.model.record;

/**
 * Represents the status of a disciplinary record in the Office of Student Discipline (OSD) System.
 * PENDING: The violation has been recorded but not yet addressed.
 * RESOLVED: The violation has been handled and closed.
 * APPEALED: The student has submitted an appeal for the violation.
 */
public enum RecordStatus
{
    PENDING,
    RESOLVED,
    APPEALED
}
