package com.liven.userapi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateUserDTO {
  @NotBlank(message = "Campo obrigatório.")
  public String name;

  @Email(message = "E-mail inválido.")
  @NotBlank(message = "Campo obrigatório.")
  public String email;

  @NotBlank(message = "Campo obrigatório.")
  public String password;
}
