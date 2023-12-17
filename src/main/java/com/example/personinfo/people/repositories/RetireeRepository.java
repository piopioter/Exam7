package com.example.personinfo.people.repositories;

import com.example.personinfo.people.models.Retiree;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RetireeRepository extends PersonGenericRepository<Retiree> {

}
