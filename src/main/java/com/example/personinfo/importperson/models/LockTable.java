package com.example.personinfo.importperson.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class LockTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String valueLock;

    public LockTable() {
    }

    public LockTable(String valueLock) {
        this.valueLock = valueLock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueLock() {
        return valueLock;
    }

    public void setValueLock(String valueLock) {
        this.valueLock = valueLock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockTable lockTable = (LockTable) o;
        return Objects.equals(id, lockTable.id) && Objects.equals(valueLock, lockTable.valueLock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valueLock);
    }
}
