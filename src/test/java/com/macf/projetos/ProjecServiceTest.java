package com.macf.projetos;

import com.macf.projetos.dto.ProjectDTO;
import com.macf.projetos.entity.Member;
import com.macf.projetos.entity.Project;
import com.macf.projetos.entity.enums.ProjectStatus;
import com.macf.projetos.repository.ProjectRepository;
import com.macf.projetos.service.MemberService;
import com.macf.projetos.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjecServiceTest {

    private ProjectRepository projectRepository;
    private MemberService memberService;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        memberService = mock(MemberService.class);
        projectService = new ProjectService(projectRepository, memberService);
    }

    @Test
    void deveCriarProjetoComRiscoBaixo() {
        ProjectDTO dto = new ProjectDTO();
        dto.name = "Projeto Teste";
        dto.budget = new BigDecimal("50000");
        dto.startDate = LocalDate.now();
        dto.forecastEndDate = LocalDate.now().plusMonths(2);
        dto.memberIds = Set.of(1L);

        Member m = new Member();
        m.setId(1L);
        m.setName("Funcionario");
        m.setRole("funcionário");

        when(memberService.findById(1L)).thenReturn(m);
        when(projectRepository.findActiveMemberIds()).thenReturn(Collections.emptyList());
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectDTO saved = projectService.create(dto);
        assertEquals("Projeto Teste", saved.name);
    }

    @Test
    void naoDevePermitirMaisDe10Membros() {
        ProjectDTO dto = new ProjectDTO();
        dto.name = "Grande Projeto";
        dto.budget = new BigDecimal("600000");
        dto.startDate = LocalDate.now();
        dto.forecastEndDate = LocalDate.now().plusMonths(12);

        Set<Long> ids = new HashSet<>();
        for (long i = 1; i <= 11; i++) {
            ids.add(i);
            Member m = new Member();
            m.setId(i);
            m.setName("Membro " + i);
            m.setRole("funcionário");
            when(memberService.findById(i)).thenReturn(m);
        }

        dto.memberIds = ids;
        when(projectRepository.findActiveMemberIds()).thenReturn(Collections.emptyList());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> projectService.create(dto));
        assertTrue(ex.getMessage().contains("entre 1 e 10 membros"));
    }

    @Test
    void deveImpedirTransicaoDeStatusInvalida() {
        Project p = new Project();
        p.setId(1L);
        p.setStatus(ProjectStatus.EM_ANALISE);

        ProjectDTO dto = new ProjectDTO();
        dto.status = ProjectStatus.PLANEJADO;

        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> projectService.update(1L, dto));
        assertTrue(ex.getMessage().contains("Transição de status inválida"));
    }

    @Test
    void deveImpedirExclusaoDeProjetoAtivo() {
        Project p = new Project();
        p.setId(1L);
        p.setStatus(ProjectStatus.EM_ANDAMENTO);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));

        Exception ex = assertThrows(IllegalStateException.class, () -> projectService.delete(1L));
        assertEquals("Projeto não pode ser excluído no status atual", ex.getMessage());
    }
}
