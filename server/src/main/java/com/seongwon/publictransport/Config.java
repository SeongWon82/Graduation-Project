package com.seongwon.publictransport;

import com.seongwon.publictransport.repository.JpaUserRepository;
import com.seongwon.publictransport.repository.UserRepository;
import com.seongwon.publictransport.service.UserService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Configuration
public class Config
{

  @PersistenceContext
  private EntityManager em;

  @Autowired
  public Config(EntityManager em)
  {
    this.em = em;
  }
  @Bean
  public UserService userService() { return new UserService(userRepository()); }
  
  @Bean
  public UserRepository userRepository() {
    return new JpaUserRepository(this.em);
  }
  
}
