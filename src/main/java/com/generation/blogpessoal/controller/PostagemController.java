package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
public class PostagemController {
	@Autowired
	private PostagemRepository postagemRepository;

	@GetMapping
	// Informando que o método GetAll abaixo irá retornar uma requisição GET
	public ResponseEntity<List<Postagem>> getAll() {
		// O get All quer dizer que ele vai responder toda requisição do tipo HTPP GET
		// que for pro endereco : http://localhost:8080/postagens/.

		// ATENÇÃO: O Endereço do endpoint será igual ao Endereço do Recurso
		// (@RequestMapping) apenas quando a anotação @GetMapping não possuir um
		// endereço personalizado, como um parâmetro por exemplo. Caso existam dois ou
		// mais Métodos do tipo GET será necessário personalizar o
		// endereço de cada Método anotado com @GetMapping, pois não pode existir
		// endereços duplicados.

		// O método getAll é do tipo responseEntity porque ele vai responder a
		// requisição http com uma resposta http
		// Esse método terá como retorno um objeto da classe ResponseEntity e no
		// parametro body será um objeto da classe List
		return ResponseEntity.ok(postagemRepository.findAll());
		// Mas por que eu não chamei a lista na hora de fazer o return?
		// A razão para não chamar diretamente a lista no retorno do método está
		// relacionada ao encapsulamento da resposta HTTP.
		// A classe ResponseEntity fornece um meio de incluir informações adicionais
		// sobre a resposta HTTP, como headers, status code,
		// etc. Ao encapsular a lista de postagens dentro de ResponseEntity.ok(...),
		// você está indicando explicitamente que a operação
		// foi bem-sucedida (código 200 OK) e está incluindo o corpo da resposta, que é
		// a lista de postagens.

		// Se você chamasse diretamente postagemRepository.findAll() sem envolvê-lo em
		// ResponseEntity.ok(...), estaria retornando apenas
		// a lista de postagens, sem qualquer informação adicional relacionada à
		// resposta HTTP. Isso pode ser apropriado em alguns casos,
		// mas usar ResponseEntity oferece maior flexibilidade ao lidar com diferentes
		// casos de resposta e permite definir cabeçalhos HTTP
		// e status personalizados, se necessário.

	}
}
