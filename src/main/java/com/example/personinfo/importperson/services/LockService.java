package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.models.LockTable;
import com.example.personinfo.importperson.repositories.LockRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class LockService {

    private LockRepository lockRepository;

    public LockService(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    public boolean lock() {
        try {
            lockRepository.save(new LockTable("lock"));
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }


    public void unlock() {
        lockRepository.deleteAll();
    }
}
