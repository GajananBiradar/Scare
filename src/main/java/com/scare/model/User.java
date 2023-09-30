package com.scare.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "user_email")
	@NotNull(message = "Email cannot be null")
	private String email;

	@NotNull(message = "Password cannot be null")
	private String password;
	
	@Column(name = "first_name", nullable = false, length = 100)
	@NotNull(message = "Name cannot be null")
	private String firstName;
	
	@Column(name = "middle_name", nullable = false, length = 100)
	@NotNull(message = "Name cannot be null")
	private String middleName;
	
	@Column(name = "last_name", nullable = false, length = 100)
	@NotNull(message = "Name cannot be null")
	private String lastName;

	@Column(name = "age")
	private int age;
	
	@Column(name = "contact_number", nullable = false, length = 12)
	@NotNull(message = "Contact number cannot be null")
	private String contactNumber;
	
	@Column(name = "gender", nullable = false)
	@NotNull(message = "gender cannot be null")
	private String gender;

	

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> autories = this.roles.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return autories;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
