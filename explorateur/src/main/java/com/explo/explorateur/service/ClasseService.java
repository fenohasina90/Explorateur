package com.explo.explorateur.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explo.explorateur.entite.constant.Classe;
import com.explo.explorateur.repository.constant.ClasseRepository;

@Service
public class ClasseService {
    private final ClasseRepository classeRepository;

    @Autowired
    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public Classe findClasseById(Integer id) {
        return classeRepository.findById(id.longValue()).orElse(null);
    }
    
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }
}
