package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<Date, Integer> {
}
