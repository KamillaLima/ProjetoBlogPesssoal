package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	// Tem como função forncer as infos básicas do usuário
	// pro spring security

	private static final long serialVersionUID = 1L;
	/*
	 * O Atributo private static final long serialVersionUID = 1L é um identificador
	 * da versão da Classe, utilizado para Serializar e Desserializar um Objeto de
	 * uma Classe que implementa a Interface Serializable. Simplificando, usamos o
	 * atributo serialVersionUID para lembrar as versões de uma Classe que
	 * implementa a Interface Serializable, com o objetivo de verificar se uma
	 * classe carregada e o objeto serializado são compatíveis, ou seja, se o Objeto
	 * foi gerado pela mesma versão da Classe. Na prática, esse número seria a
	 * versão da sua Classe. Uma nova versão de uma Classe é criada sempre que você
	 * adicionar ou modificar um ou mais Atributos da Classe. Essa regra não vale
	 * para Métodos, porque a Serialização só leva em consideração os Atributos.
	 */
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;

	public  UserDetailsImpl(Usuario user) {
		this.userName = user.getNome();
		this.password = user.getSenha();
	}
	public  UserDetailsImpl() {}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		/*Método responsável por retornar os direitos de acesso do usuário.
		 * A interrogação significa que o método pode receber um objeto de qualquer classe,caso os direitos
		 * fossem implementados,ai a interrogação ia ser subtituida pelo nome da classe responsável por 
		 * definir os roles do usuário*/
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		/*Indica se o acesso do usuário expirou (tempo de acesso). Uma conta expirada não pode ser autenticada (return false).
		 * */
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		/*Indica se o usuário está bloqueado ou desbloqueado. Um usuário bloqueado não pode ser autenticado (return false).*/
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		/*Indica se as credenciais do usuário (senha) expiraram (precisa ser trocada). Senha expirada impede a autenticação (return false).*/
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		/*	Indica se o usuário está habilitado ou desabilitado. Um usuário desabilitado não pode ser autenticado (return false).*/
		return true;
	}
}
