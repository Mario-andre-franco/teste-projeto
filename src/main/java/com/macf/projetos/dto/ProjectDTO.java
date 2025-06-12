package com.macf.projetos.dto;


import com.macf.projetos.entity.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(description = "DTO representando um projeto")
public class ProjectDTO {

    @Schema(description = "ID do projeto", example = "1")
    public Long id;

    @Schema(description = "Nome do projeto", example = "Projeto CRM")
    public String name;

    @Schema(description = "Data de início", example = "2025-01-01")
    public LocalDate startDate;

    @Schema(description = "Previsão de término", example = "2025-03-31")
    public LocalDate forecastEndDate;

    @Schema(description = "Data real de término", example = "2025-03-28")
    public LocalDate realEndDate;

    @Schema(description = "Orçamento total", example = "120000.00")
    public BigDecimal budget;

    @Schema(description = "Descrição do projeto", example = "Implantação de sistema CRM")
    public String description;

    @Schema(description = "Status atual do projeto")
    public ProjectStatus status;

    @Schema(description = "ID do gerente responsável", example = "5")
    public Long managerId;

    @Schema(description = "IDs dos membros do projeto", example = "[2, 4, 6]")
    public Set<Long> memberIds;
}
