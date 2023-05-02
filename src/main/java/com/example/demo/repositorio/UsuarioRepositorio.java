package com.example.demo.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByLogin(String login);

}
