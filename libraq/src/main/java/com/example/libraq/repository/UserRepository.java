package com.example.libraq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libraq.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	List<Users> findByUserType(String userType);

	List<Users> findByEmail(String email);

	List<Users> findByName(String name);
}
