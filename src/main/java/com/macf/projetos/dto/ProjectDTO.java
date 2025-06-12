package com.macf.projetos.dto;


import com.macf.projetos.entity.enums.ProjectStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectDTO {
    public Long id;
    public String name;
    public LocalDate startDate;
    public LocalDate forecastEndDate;
    public LocalDate realEndDate;
    public BigDecimal budget;
    public String description;
    public ProjectStatus status;     // status atual do projeto
    public Long managerId;           // id do gerente
    public Set<Long> memberIds;
}
