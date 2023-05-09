package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Usuario;
import com.example.demo.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtService service;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		String senhaCript = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCript);
		return repositorio.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repositorio.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado na base"));
		
		
		String token = service.gerarToken(usuario);
		System.out.println(token);
		
		boolean tokenValido = service.tokenValido(token);
		System.out.println(tokenValido);
		
		System.out.println(service.obterLoginUsuario(token));
		
		String[] roles = usuario.isAdmin() ? 
				new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

}
