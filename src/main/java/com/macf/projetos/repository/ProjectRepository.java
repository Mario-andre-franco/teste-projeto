package com.macf.projetos.repository;

import com.macf.projetos.entity.Project;
import com.macf.projetos.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT DISTINCT m.id FROM Project p JOIN p.members m WHERE p.status <> 'ENCERRADO' AND p.status <> 'CANCELADO'")
    List<Long> findActiveMemberIds();

    List<Project> findByStatus(ProjectStatus status);
}
