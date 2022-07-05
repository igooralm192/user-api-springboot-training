package com.liven.userapi.dtos;

import javax.validation.constraints.NotBlank;

public class CreateUserAddressDTO {
  @NotBlank(message = "Campo obrigatório.")
  public String streetName;

  public String streetNumber;

  @NotBlank(message = "Campo obrigatório.")
  public String zipCode;

  public String city;
  public String state;
  public String country;
  public String complement;
}
