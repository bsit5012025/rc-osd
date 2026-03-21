package org.rocs.osd.model.request;

/**
 * Represents the status of a Request in the
 * Office of Student Discipline System.
 * It indicates the current state of the request.
 */
public enum RequestStatus {

    /** The request has been submitted and is awaiting review or action. */
    PENDING,

    /** The request has been reviewed and approved. */
    APPROVED,

    /** The request has been reviewed and denied. */
    DENIED
}
