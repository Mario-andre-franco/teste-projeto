package com.macf.projetos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class PortfolioReportDTO {
    public Map<String, Long> quantidadeProjetosPorStatus;
    public Map<String, BigDecimal> totalOrcadoPorStatus;
    public double mediaDuracaoProjetosEncerrados;
    public long totalMembrosUnicosAlocados;
}
