package org.springframework.samples.petclinic.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;

import java.util.List;


public interface UserRepository extends  CrudRepository<User, String>{
    List<User> findAll();
}
