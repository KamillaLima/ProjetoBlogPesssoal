package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
//Vai se tratar de uma entidade
@Table(name = "tb_postagens")
//Qual vai ser o nome lá no banco
public class Postagem {

	@Id
	// Vai ser o Id da minha tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Quero que o meu id seja gerado de forma automática(auto incremento)
	// Existem alguns outros também,como o AUTO ( Valor padrão, deixa com o provedor
	// de persistência a escolha da estratégia mais adequada de acordo com o Banco
	// de dados.)
	// Sequence (O provedor pode passar uma sequencia de como ele gostaria que
	// fossem criada a sequência das chaves primárias)
	// Table(É necessário criar uma tabela pra gerenciar as chaves primárias)
	private Long id;

	@NotBlank(message = "O atributo é obrigatório e deve ser preenchido! ")
	// Informando que é obrigatório o preenchimento
	@Size(min = 10, max = 1000, message = "deve ter entre 10 e 1000 caracteres")
	// Informando o tamanho que esse campo tem de minimo e maximo
	private String titulo;

	@NotBlank(message = "O atributo é obrigatório e deve ser preenchido! ")
	@Size(min = 100, max = 10000, message = "deve ter entre 100 e 10000 caracteres")
	// Informando o tamanho que esse campo tem de minimo e maximo
	private String texto;

	@UpdateTimestamp
	// Timestamp, ou seja, o Spring se encarregará de obter a data e a hora do
	// Sistema Operacional e inserir no
	// Atributo data toda vez que um Objeto da Classe Postagem for criado ou
	// atualizado.
	private LocalDateTime data;

	@ManyToOne
	// Muitas postagens estão ligadas a um tema
	@JsonIgnoreProperties("postagem")
	/**
	 * Uma parte do json será ignorado , ou seja, como a Relação entre as Classes
	 * será do tipo Bidirecional, ao listar o Objeto Postagem numa consulta, por
	 * exemplo, o Objeto Tema, que será criado na linha 39, será exibido como um
	 * "Sub Objeto" do Objeto Postagem, como mostra a figura abaixo, devido ao
	 * Relacionamento que foi criado.
	 * 
	 * { "id": 1, "titulo": "Título da Postagem 01", "texto": "Texto da postagem
	 * 01", "data": "2022-05-02T09:27:11.2221618", "tema": { "id": 1, "descricao":
	 * "Tema 01" } }
	 */
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

}
