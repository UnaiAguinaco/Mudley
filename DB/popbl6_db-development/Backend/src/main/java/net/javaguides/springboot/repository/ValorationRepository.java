package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Valoration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration, Integer> {
}
