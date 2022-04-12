package com.example.jpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpademo.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

}
