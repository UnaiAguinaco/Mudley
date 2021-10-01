package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Gender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
}
