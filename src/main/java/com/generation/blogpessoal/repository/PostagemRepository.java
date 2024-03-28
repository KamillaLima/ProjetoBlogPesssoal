package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;



public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	//A interface JPARepository sempre recebe 2 parametros
	// A entidade que vai ser mapeada (no nosso caso é a classe postagem)
	//A chave primária que a entidade tem (no nosso caso é o Id que é Long)
	//Pode receber qualquer tipo de objeto ali (já que se trata de um java Generics)

//Alguns métodos que a interface JpaRepository possui :
//Método	            Descrição
//save(Objeto objeto)	Cria ou Atualiza um objeto no Banco de Dados.
//findById(Long id)	    Retorna (exibe) um Objeto persistido de acordo com o id informado.
//existsById(Long id)	Retorna True se um Objeto identificado pelo id estiver persistido no Banco de dados.
//findAll()	            Retorna (exibe) todos os Objetos persistidos.
//deleteById(Long id)	Localiza um Objeto persistido pelo id e deleta caso ele seja encontrado. Não é possível desfazer esta operação.
//deleteAll()	        Deleta todos os Objetos persistidos.Não é possível desfazer esta operação.

//QUERY METHOD : Método de consulta personalizada 
public List<Postagem> findByTituloContainingIgnoreCase(@Param("titulo")String titulo);
//Criando uma lista pra armazenar todos as postagens;
//Find : SELECT
//by : WHERE
//Titulo : Atributo da classe postagem
//Containing : Like
//IgnoreCase: Ignorando letras maiúsculas ou minúsculas
//@Param("titulo") : Define a variável String titulo como um parâmetro da consulta. Esta anotação é obrigatório em consultas do tipo Like.
}
