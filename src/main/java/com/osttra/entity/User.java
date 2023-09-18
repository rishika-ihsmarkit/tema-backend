package com.osttra.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	

	@Id
	@NotNull
	private String username;
	
	@NotNull
//	@Min(5)
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String role;
	
	
	

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "user_usergroup",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "usergroup_id")
	)
	
	@JsonProperty(access = Access.WRITE_ONLY)
    private Set<UserGroup> userGroups = new HashSet<>();

}