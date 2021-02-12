package edu.uh.tech.cis3368.semesterproject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface JobRepository extends CrudRepository<Job,Integer> {
}
