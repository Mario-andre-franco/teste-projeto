package com.macf.projetos.service;


import com.macf.projetos.dto.PortfolioReportDTO;
import com.macf.projetos.dto.ProjectDTO;
import com.macf.projetos.entity.Member;
import com.macf.projetos.entity.Project;
import com.macf.projetos.entity.enums.ProjectStatus;
import com.macf.projetos.mapper.ProjectMapper;
import com.macf.projetos.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    public final ProjectRepository projectRepository;
    public final MemberService memberService;

    public ProjectService(ProjectRepository projectRepository, MemberService memberService) {
        this.projectRepository = projectRepository;
        this.memberService = memberService;
    }

    public List<ProjectDTO> listAll() {
        return projectRepository.findAll().stream()
                .map(ProjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO create(ProjectDTO dto) {
        Project project = new Project();
        updateProjectFields(project, dto);
        project.setStatus(ProjectStatus.EM_ANALISE);
        project.calculateRisk();
        return ProjectMapper.toDTO(projectRepository.save(project));
    }

    public ProjectDTO update(Long id, ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        if (!project.getStatus().canTransitionTo(dto.status)) {
            throw new IllegalArgumentException("Transição de status inválida");
        }

        updateProjectFields(project, dto);
        project.setStatus(dto.status);
        project.calculateRisk();
        return ProjectMapper.toDTO(projectRepository.save(project));
    }

    public void delete(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        if (!project.canBeDeleted()) {
            throw new IllegalStateException("Projeto não pode ser excluído no status atual");
        }
        projectRepository.delete(project);
    }

    public PortfolioReportDTO gerarRelatorio() {
        List<Project> projetos = projectRepository.findAll();
        PortfolioReportDTO dto = new PortfolioReportDTO();

        dto.quantidadeProjetosPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(p -> p.getStatus().name(), Collectors.counting()));

        dto.totalOrcadoPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(p -> p.getStatus().name(),
                        Collectors.mapping(Project::getBudget,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        dto.mediaDuracaoProjetosEncerrados = projetos.stream()
                .filter(p -> p.getStatus() == ProjectStatus.ENCERRADO && p.getStartDate() != null && p.getRealEndDate() != null)
                .mapToLong(p -> ChronoUnit.DAYS.between(p.getStartDate(), p.getRealEndDate()))
                .average().orElse(0.0) / 30.0;

        dto.totalMembrosUnicosAlocados = projetos.stream()
                .flatMap(p -> p.getMembers().stream())
                .map(Member::getId)
                .distinct()
                .count();

        return dto;
    }

    private void updateProjectFields(Project project, ProjectDTO dto) {
        project.setName(dto.name);
        project.setStartDate(dto.startDate);
        project.setForecastEndDate(dto.forecastEndDate);
        project.setRealEndDate(dto.realEndDate);
        project.setBudget(dto.budget);
        project.setDescription(dto.description);

        if (dto.managerId != null) {
            Member manager = memberService.findById(dto.managerId);
            project.setManager(manager);
        }

        if (dto.memberIds != null) {
            Set<Member> members = dto.memberIds.stream()
                    .map(memberService::findById)
                    .filter(m -> "funcionário".equalsIgnoreCase(m.getRole()))
                    .collect(Collectors.toSet());

            if (members.size() < 1 || members.size() > 10)
                throw new IllegalArgumentException("Projeto deve conter entre 1 e 10 membros");

            List<Long> ativos = projectRepository.findActiveMemberIds();
            for (Member m : members) {
                if (Collections.frequency(ativos, m.getId()) >= 3)
                    throw new IllegalArgumentException("Membro " + m.getName() + " já está em 3 projetos ativos");
            }

            project.setMembers(members);
        }
    }
}
