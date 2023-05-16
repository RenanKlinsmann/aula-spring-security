package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dtos.CredenciaisDTO;
import com.example.demo.dtos.TokenDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exceptions.SenhaInvalidaException;
import com.example.demo.jwtConfig.JwtService;
import com.example.demo.service.UsuarioServiceImpl;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping
	public String getUsuario() {
		return "Usuario";
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Usuario salvar(@RequestBody Usuario  usuario) {
		return usuarioService.salvar(usuario);
		
	}
	
	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credencias) {
		
		try {
			Usuario usuario = new Usuario(credencias.getLogin(), credencias.getSenha());
			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
			String token = jwtService.gerarToken(usuario);
			return new TokenDTO(usuario.getLogin(), token);
			
		}catch(UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
		
	}

}
