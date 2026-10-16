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

    /** An appeal has been filed against this record and is under review. */
    PROCESSING,

    /** The appeal against this record was approved. */
    APPROVED,

    /** Record has been addressed and resolved (including a denied appeal). */
    RESOLVED
}
