package com.generation.blogpessoal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

		public Optional<Usuario> findByUsuario(String usuario);

		//Como aqui eu só vou retornar um único usuário,eu lanço como Optional,para que caso ele seja nulo,eu não caia em uma exceção
	}
