package com.example.personinfo.importperson.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "imports")
public class ImportStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private StatusType status;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private Long processedRows;

    public ImportStatus() {
    }

    public ImportStatus(StatusType status, LocalDateTime creationDate, LocalDateTime startDate, Long processedRows) {
        this.status = status;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.processedRows = processedRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportStatus that = (ImportStatus) o;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(creationDate, that.creationDate) && Objects.equals(startDate, that.startDate) && Objects.equals(processedRows, that.processedRows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, creationDate, startDate, processedRows);
    }
}
