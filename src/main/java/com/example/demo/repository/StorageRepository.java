package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

}
