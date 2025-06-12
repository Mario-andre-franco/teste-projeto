package com.macf.projetos.mapper;

import com.macf.projetos.dto.ProjectDTO;
import com.macf.projetos.entity.Member;
import com.macf.projetos.entity.Project;

import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.id = project.getId();
        dto.name = project.getName();
        dto.startDate = project.getStartDate();
        dto.forecastEndDate = project.getForecastEndDate();
        dto.realEndDate = project.getRealEndDate();
        dto.budget = project.getBudget();
        dto.description = project.getDescription();
        dto.status = project.getStatus();
        dto.managerId = project.getManager() != null ? project.getManager().getId() : null;
        dto.memberIds = project.getMembers().stream().map(Member::getId).collect(Collectors.toSet());
        return dto;
    }
}
