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
/*
 * SpringBootTest indica que a Classe UsuarioControllerTest é uma Classe Spring
 * Boot Testing. A Opção environment indica que caso a porta principal (8080
 * para uso local) esteja ocupada, o Spring irá atribuir uma outra porta
 * automaticamente.
 **/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
 * @TestInstance indica que o Ciclo de vida da Classe de Teste será por Classe
 */
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	/*
	 * foi injetado (@Autowired), um objeto da Classe TestRestTemplate para enviar
	 * as requisições para a nossa aplicação.
	 */

	@Autowired
	private UsuarioService usuarioService;
	/*
	 * foi injetado (@Autowired), um objeto da Classe UsuarioService para persistir
	 * os objetos no Banco de dados de testes com a senha criptografada.
	 **/

	@Autowired
	private UsuarioRepository usuarioRepository;
	/*
	 * foi injetado (@Autowired), um objeto da Interface UsuarioRepository para
	 * limpar o Banco de dados de testes
	 */

	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		usuarioService.cadastrarUsuario(new Usuario(0L, "root", "root@root.com", "rootroot", " "));
	}
	/*
	 * o Método start(), anotado com a anotação @BeforeAll, apaga todos os dados da
	 * tabela e cria o usuário root@root.com para testar os Métodos protegidos por
	 * autenticação.
	 */

	@Test
	/*
	 * Método deveCriarUmUsuario() foi anotado com a anotação @Test que indica que
	 * este Método executará um teste.
	 */
	@DisplayName("cadastrar um usuário")
	/*
	 * a anotação @DisplayName configura uma mensagem que será exibida ao invés do
	 * nome do Método
	 */
	public void deveCriarUmUsuario() {
		/*
		 * o processo é equivalente ao que o Insomnia faz em uma requisição do tipo
		 * POST: Transforma os Atributos num objeto da Classe Usuario, que será enviado
		 * no corpo da requisição (Request Body).
		 */
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Paulo", "Paulo@gmail.com", "12345678", "-"));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		/*
		 * a Requisição HTTP será enviada através do Método exchange() da Classe
		 * TestRestTemplate e a Resposta da Requisição (Response) será recebida pelo
		 * objeto corpoResposta do tipo ResponseEntity. Para enviar a requisição, o será
		 * necessário passar 4 parâmetros:
		 * 
		 * A URI: Endereço do endpoint (/usuarios/cadastrar); O Método HTTP: Neste
		 * exemplo o Método POST; O Objeto HttpEntity: Neste exemplo o objeto
		 * requisicao, que contém o objeto da Classe Usuario; O conteúdo esperado no
		 * Corpo da Resposta (Response Body): Neste exemplo será do tipo Usuario
		 * (Usuario.class).
		 */
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
		/*
		 * AssertEquals(), checaremos se a resposta da requisição (Response), é a
		 * resposta esperada (CREATED 🡪 201). Para obter o status da resposta vamos
		 * utilizar o Método getStatusCode() da Classe ResponseEntity.
		 */
	}

	@Test
	@DisplayName("Não deve permitir duplicação do usuario")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "maria", "maria@gmail.com", "123456789", "-"));
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "maria", "maria@gmail.com", "123456789", "-"));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
		/*
		 * Como o teste tem por objetivo checar se está duplicando usuários no Banco de
		 * dados, ao invés de checarmos se o objeto foi persistido (CREATE 🡪 201),
		 * checaremos se ele não foi persistido (BAD_REQUEST 🡪 400). Se retornar o
		 * Status 400, o teste será aprovado!
		 * 
		 */
	}

	@Test
	@DisplayName("Deve permitir a atualização do usuario")
	public void deveAtualizarUmUsuario() {
		Optional<Usuario> usuarioCadastrado = usuarioService
				.cadastrarUsuario(new Usuario(0L, "JULIA", "Julia@gmail.com", "julia1234545", "-"));
		/*
		 * Objeto Optional, do tipo Usuario, chamado usuarioCadastrado, para armazenar o
		 * resultado da persistência de um Objeto da Classe Usuario no Banco de dados,
		 * através do Método cadastrarUsuario() da Classe UsuarioService.
		 */
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Julia Souza", "julia_souza@gmail.com",
				"julia1234", "-");
		/*
		 * Objeto do tipo Usuario, chamado usuarioUpdate, que será utilizado para
		 * atualizar os dados persistidos no Objeto usuarioCadastrado
		 */
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		/*
		 * objeto HttpEntity chamado corpoRequisicao, recebendo o objeto da Classe
		 * Usuario chamado usuarioUpdate. Nesta etapa, o processo é equivalente ao que o
		 * Insomnia faz em uma requisição do tipo PUT: Transforma os Atributos num
		 * objeto da Classe Usuario, que será enviado no corpo da requisição (Request
		 * Body).
		 */
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				/*
				 * como o Blog Pessoal está com o Spring Security habilitado com a autenticação
				 * do tipo Http Basic, o Objeto testRestTemplate, que será enviada para os
				 * endpoints protegidos (exigem autenticação), deverá efetuar o login com um
				 * usuário e uma senha válida para realizar os testes.A gente cadastrou ele no
				 * metodo start,na linha 57
				 * 
				 * Para autenticar o usuário e a senha utilizaremos o Método
				 * withBasicAuth(username, password) da Classe TestRestTemplate
				 */
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar todos os usuários")
	public void deveMostrarTodosUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "Sabrina", "sabrina@gmail.com", "4545454545", "-"));
		usuarioService.cadastrarUsuario(new Usuario(0L, "Laura", "laura@gmail.com", "78787877", "-"));
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		/*
		 * O Objeto HttpEntity: O objeto será nulo (null). Requisições do tipo GET não
		 * enviam Objeto no corpo da requisição; O conteúdo esperado no Corpo da
		 * Resposta (Response Body): Neste exemplo como o objeto da requisição é nulo, a
		 * resposta esperada será do tipo String (String.class).
		 * 
		 * Observe que no Método GET não foi criado o Objeto da Clase HttpEntity, porque
		 * o Método GET não envia um Objeto no Corpo da Requisição. Lembre-se: Ao criar
		 * uma requisição do tipo GET no Insomnia é enviado apenas a URL do endpoint.
		 * Esta regra também vale para o Método DELETE.
		 */
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
