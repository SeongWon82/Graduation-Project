package com.seongwon.publictransport.repository;

import com.seongwon.publictransport.domain.User;
import java.util.Optional;
import javax.persistence.EntityManager;

public class JpaUserRepository implements UserRepository
{
  private final EntityManager em;

  public JpaUserRepository(EntityManager em){ this.em = em; }

  public User insert(User user)
  {
    this.em.persist(user);
    return user;
  }

  public Optional<User> findById(String id)
  {
	  User user = (User)this.em.find(User.class, id);
	  return Optional.ofNullable(user);
  }
  
}