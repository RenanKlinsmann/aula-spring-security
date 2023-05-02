package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioServiceImpl;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@GetMapping
	public String getUsuario() {
		return "Usuario";
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Usuario salvar(@RequestBody Usuario  usuario) {
		return usuarioService.salvar(usuario);
		
	}

}
