package org.rocs.osd.model.record;

/**
 * Represents the status of a student record in the
 * Office of Student Discipline System.
 *
 * Indicates whether a record is pending, resolved, or has been appealed.
 */
public enum RecordStatus {

    /** Record is newly created and awaiting action or review. */
    PENDING,

    /** Record has been addressed and resolved. */
    RESOLVED,

    /** Record has been appealed by the student. */
    APPEALED
}