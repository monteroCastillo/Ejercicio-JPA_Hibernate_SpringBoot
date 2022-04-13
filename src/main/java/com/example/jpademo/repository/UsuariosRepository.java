package com.example.jpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpademo.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
