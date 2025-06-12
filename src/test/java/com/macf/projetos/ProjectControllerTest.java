package com.macf.projetos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.macf.projetos.controller.ProjectController;
import com.macf.projetos.dto.ProjectDTO;
import com.macf.projetos.entity.enums.ProjectStatus;
import com.macf.projetos.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest {

    private MockMvc mockMvc;
    private ProjectService projectService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        projectService = mock(ProjectService.class);
        ProjectController controller = new ProjectController(projectService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveCriarProjetoComSucesso() throws Exception {
        ProjectDTO dto = new ProjectDTO();
        dto.name = "Projeto Teste";
        dto.startDate = LocalDate.now();
        dto.forecastEndDate = LocalDate.now().plusMonths(2);
        dto.budget = new BigDecimal("50000");
        dto.status = ProjectStatus.EM_ANALISE;
        dto.memberIds = Collections.singleton(1L);

        when(projectService.create(any(ProjectDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
