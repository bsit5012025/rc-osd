package org.rocs.osd.model.record;

public enum RecordStatus
{
    PENDING("PENDING"),
    APPROVED("APPROVED");

    private final String status;

    RecordStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

}
