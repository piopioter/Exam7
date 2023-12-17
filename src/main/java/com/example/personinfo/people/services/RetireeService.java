package com.example.personinfo.people.services;

import com.example.personinfo.people.models.Retiree;
import com.example.personinfo.people.repositories.RetireeRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RetireeService implements IPersonService<Retiree> {

    private RetireeRepository repository;

    public RetireeService(RetireeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Retiree save(Retiree retiree) {
        return repository.save(retiree);
    }

}
