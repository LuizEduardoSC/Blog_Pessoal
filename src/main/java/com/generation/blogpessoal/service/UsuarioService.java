package com.generation.blogpessoal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.security.JwtService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailService emailService;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

		if (usuario.getSenha() == null || usuario.getSenha().isBlank())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha é obrigatória!", null);

		if (usuario.getSenha().length() < 8)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve ter no mínimo 8 caracteres!", null);

		if (usuario.getFoto() == null || usuario.getFoto().isBlank())
			usuario.setFoto("https://i.imgur.com/I8MfmC8.png");

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		Optional<Usuario> usuarioSalvo = Optional.ofNullable(usuarioRepository.save(usuario));

		// Enviar e-mail de boas-vindas assincronamente
		if (usuarioSalvo.isPresent()) {
			emailService.enviarEmailBoasVindas(usuarioSalvo.get().getUsuario(), usuarioSalvo.get().getNome());
		}

		return usuarioSalvo;

	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		Optional<Usuario> usuarioBanco = usuarioRepository.findById(usuario.getId());

		if (usuarioBanco.isPresent()) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			if (usuario.getFoto() == null || usuario.getFoto().isBlank())
				usuario.setFoto("https://i.imgur.com/I8MfmC8.png");

			// Atualizar o campo "sobre"
			usuarioBanco.get().getSobre(); // Só para garantir que o objeto está carregado

			// Se a senha estiver vazia, mantém a senha atual do banco (já criptografada)
			if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
				usuario.setSenha(usuarioBanco.get().getSenha());
			} else {
				// Se enviou uma nova senha, valida o tamanho e criptografa
				if (usuario.getSenha().length() < 8)
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve ter no mínimo 8 caracteres!", null);
				
				usuario.setSenha(criptografarSenha(usuario.getSenha()));
			}

			return Optional.ofNullable(usuarioRepository.save(usuario));
		}

		return Optional.empty();
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		Authentication authentication = authenticationManager.authenticate(credenciais);

		if (authentication.isAuthenticated()) {

			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

			if (usuario.isPresent()) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setSenha("");
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));

				return usuarioLogin;
			}
		}
		return Optional.empty();
	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

}
