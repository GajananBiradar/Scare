package com.scare.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDto {

	private int id;
	
	@NotEmpty( message = "Role name can't be empty")
	private String name;
	
}
