package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.Algorithm;

public interface AlgorithmRepository extends CrudRepository<Algorithm, Integer> {
}
