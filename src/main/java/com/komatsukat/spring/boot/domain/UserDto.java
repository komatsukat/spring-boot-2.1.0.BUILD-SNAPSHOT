package com.komatsukat.spring.boot.domain;

import lombok.Data;

@Data
public class UserDto {
  private Integer userId;
  private String username;
  private String password;
  private String phone;
}
