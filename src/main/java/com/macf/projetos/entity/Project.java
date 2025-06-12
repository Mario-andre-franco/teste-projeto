package com.macf.projetos.entity;


import com.macf.projetos.entity.enums.ProjectRisk;
import com.macf.projetos.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate startDate;
    private LocalDate forecastEndDate;
    private LocalDate realEndDate;
    private BigDecimal budget;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Enumerated(EnumType.STRING)
    private ProjectRisk risk;

    @ManyToOne
    private Member manager;

    @ManyToMany
    private Set<Member> members = new HashSet<>();

    public void calculateRisk() {
        long months = ChronoUnit.MONTHS.between(startDate, forecastEndDate);
        if (budget.compareTo(new BigDecimal("100000")) <= 0 && months <= 3) {
            this.risk = ProjectRisk.BAIXO;
        } else if ((budget.compareTo(new BigDecimal("100000")) > 0 && budget.compareTo(new BigDecimal("500000")) <= 0)
                || (months > 3 && months <= 6)) {
            this.risk = ProjectRisk.MEDIO;
        } else {
            this.risk = ProjectRisk.ALTO;
        }
    }

    public boolean canBeDeleted() {
        return !(status == ProjectStatus.INICIADO || status == ProjectStatus.EM_ANDAMENTO || status == ProjectStatus.ENCERRADO);
    }

}
