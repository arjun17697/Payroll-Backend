package com.bridgelabz.employeepayroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.employeepayroll.model.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}