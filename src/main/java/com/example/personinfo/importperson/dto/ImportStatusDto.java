package com.example.personinfo.importperson.dto;

import com.example.personinfo.importperson.models.StatusType;

import java.time.LocalDateTime;

public class ImportStatusDto {
    private StatusType status;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private Long processedRows;

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Long getProcessedRows() {
        return processedRows;
    }

    public void setProcessedRows(Long processedRows) {
        this.processedRows = processedRows;
    }
}
