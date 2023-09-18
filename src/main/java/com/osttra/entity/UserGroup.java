package com.osttra.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class UserGroup {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long groupid; 
		private String groupname;
		private String description;
		
		@ManyToMany(mappedBy = "userGroups", fetch = FetchType.LAZY)
		
		@JsonProperty(access = Access.WRITE_ONLY)
	    private Set<User> users = new HashSet<>();
	    

}