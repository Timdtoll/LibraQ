package com.example.libraq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraq.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {

}
