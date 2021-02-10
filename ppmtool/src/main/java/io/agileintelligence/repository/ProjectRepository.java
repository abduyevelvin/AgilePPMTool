package io.agileintelligence.repository;

import io.agileintelligence.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);
    Iterable<Project> findAllByProjectLeader(String username);
}
