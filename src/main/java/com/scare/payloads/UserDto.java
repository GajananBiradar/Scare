package com.scare.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@Email(message = "Email address is not valid")
	private String email;

	@NotEmpty
	//@Pattern(regexp = "[a-zA-Z0-9]{6,12}", message = "Password must contain between 6 to 12 characters. Must be alphanumeric with both Upper and lowercase characters.")
	private String password;

	@NotEmpty
	@Pattern(regexp = "[a-z]{6,12}", message = "firstName must be between 6 to 12 characters. Must only contain lowercase characters.")
	private String firstName;

	@NotEmpty
	@Pattern(regexp = "[a-z]{6,12}", message = "middleName must be between 6 to 12 characters. Must only contain lowercase characters.")
	private String middleName;

	@NotEmpty
	@Pattern(regexp = "[a-z]{6,12}", message = "lastName must be between 6 to 12 characters. Must only contain lowercase characters.")
	private String lastName;

	//@Pattern(regexp = "^(?:[0-9]|[1-9][0-9]|1[0-4][0-9]|150)$", message = "age must be between 0 to 150.")
	private int age;

	@NotEmpty
	@Pattern(regexp = ".{10,12}", message = "Contact Number must be between 10 to 12 characters.")
	private String contactNumber;

	@NotEmpty
	private String gender;

	private Set<RoleDto> roles = new HashSet<>();
	

}
