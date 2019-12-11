package com.tuga.konum.domain.mappger

import com.tuga.konum.base.Mapper
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CreateUserDto
import javax.inject.Inject

class UserMapper @Inject constructor() :
  Mapper<User, CreateUserDto> {
  override fun invoke(input: User): CreateUserDto {
    return CreateUserDto(
      phone = input.phoneNumber,
      userName = input.username,
      password = input.password,
      emailAddress = input.email,
      image = input.image,
      phoneVerified = input.phoneVerified
    )
  }
}

