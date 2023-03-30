package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", " "));
	}

	@Test
	@DisplayName("Cadastrar Um Usuario")
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Marlene Maria", "marlene_maria_aparicio@ideiaviva.com.br", "dsKzwWCm1G",
				"https://randompicturegenerator.com/img/cat-generator/g2fc68cf0f63d2112c75f211d1d83c2b12a26c06d9ade2b09ef9bac29f4706281e5e918bf479f5eb7c31ca1fa3032da8c_640.jpg"));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}

	@Test
	@DisplayName("Não deve permitir duplicacao do Usuario")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Flávia Eduarda", "flavia_eduarda_drumond@uou.com.br", "cISBmmPTwl",
				"https://randompicturegenerator.com/img/cat-generator/g39f0643e10377ec9c43a5c0f056087a4b03b46c4c8a0e778ef74a365424a021cecbdbd215370e2a1024dc66f8b7a05e6_640.jpg"));
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"Flávia Eduarda", "flavia_eduarda_drumond@uou.com.br", "cISBmmPTwl",
				"https://randompicturegenerator.com/img/cat-generator/g39f0643e10377ec9c43a5c0f056087a4b03b46c4c8a0e778ef74a365424a021cecbdbd215370e2a1024dc66f8b7a05e6_640.jpg"));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}
	
	/* 
	   "Luís Bruno", "luisbrunodias@lifefp.com.br", "28PTPpKgfN",
	   "https://randompicturegenerator.com/img/cat-generator/g01782305a4fa8328276cb0d34a80ce95fdb7b950e09cd8cf2f9beee3bd83c752a1ca084b5f3a0cc70219dea10d39138e_640.jpg"
	*/
	
	@Test
	@DisplayName("Atualizar um Usuario")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Luís Bruno", "luisbrunodias@outlook.com.br", "28PTPpKgfN", "https://i.imgur.com/yDRVeK7.jpg"));

		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), 
			"Luís Bruno", "luisbrunodias@outlook.com.br", "28PTPpKgfN", "https://i.imgur.com/yDRVeK7.jpg");
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> corpoResposta = testRestTemplate
			.withBasicAuth("root@root.com", "rootroot")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}
	
	@Test
	@DisplayName("Listar todos os Usuarios")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Flávia Eduarda", "flavia_eduarda_drumond@uou.com.br", "cISBmmPTwl",
				"https://randompicturegenerator.com/img/cat-generator/g39f0643e10377ec9c43a5c0f056087a4b03b46c4c8a0e778ef74a365424a021cecbdbd215370e2a1024dc66f8b7a05e6_640.jpg"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Luís Bruno", "luisbrunodias@lifefp.com.br", "28PTPpKgfN",
				"https://randompicturegenerator.com/img/cat-generator/g01782305a4fa8328276cb0d34a80ce95fdb7b950e09cd8cf2f9beee3bd83c752a1ca084b5f3a0cc70219dea10d39138e_640.jpg"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
					.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

}
