package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();

		usuarioRepository.save(new Usuario(0L, "Marcos Pedro", "marcos.pedro.cardoso@trietto.com.br", "G54XQhknIR",
				"https://randompicturegenerator.com/img/cat-generator/g2fd95366935aad59aab5fc76aa196915ba5733934fca9288130cb400e00a18ac031b1bf6f959337bbfb141a381461289_640.jpg"));

		usuarioRepository.save(new Usuario(0L, "Flávia Eduarda", "flavia_eduarda_drumond@uou.com.br", "cISBmmPTwl",
				"https://randompicturegenerator.com/img/cat-generator/g39f0643e10377ec9c43a5c0f056087a4b03b46c4c8a0e778ef74a365424a021cecbdbd215370e2a1024dc66f8b7a05e6_640.jpg"));

		usuarioRepository.save(new Usuario(0L, "Marlene Maria", "marlene_maria_aparicio@ideiaviva.com.br", "dsKzwWCm1G",
				"https://randompicturegenerator.com/img/cat-generator/g2fc68cf0f63d2112c75f211d1d83c2b12a26c06d9ade2b09ef9bac29f4706281e5e918bf479f5eb7c31ca1fa3032da8c_640.jpg"));

		usuarioRepository.save(new Usuario(0L, "Luís Bruno", "luisbrunodias@lifefp.com.br", "28PTPpKgfN",
				"https://randompicturegenerator.com/img/cat-generator/g01782305a4fa8328276cb0d34a80ce95fdb7b950e09cd8cf2f9beee3bd83c752a1ca084b5f3a0cc70219dea10d39138e_640.jpg"));

	}

	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("marcos.pedro.cardoso@trietto.com.br");
		assertTrue(usuario.get().getUsuario().equals("marcos.pedro.cardoso@trietto.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("a");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Marcos Pedro"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Flávia Eduarda"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Marlene Maria"));
		
	}

	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}

}
