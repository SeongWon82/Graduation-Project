package com.seongwon.publictransport.repository;

import com.seongwon.publictransport.domain.User;
import java.util.Optional;

public abstract interface UserRepository
{
  public abstract User insert(User paramUser);
  public abstract Optional<User> findById(String paramString);
}
