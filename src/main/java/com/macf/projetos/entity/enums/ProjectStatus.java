package com.macf.projetos.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status de um projeto")
public enum ProjectStatus {

    EM_ANALISE,
    ANALISE_REALIZADA,
    ANALISE_APROVADA,
    INICIADO,
    PLANEJADO,
    EM_ANDAMENTO,
    ENCERRADO,
    CANCELADO;

    public boolean canTransitionTo(ProjectStatus next) {
        if (this == CANCELADO) return false;
        if (next == CANCELADO) return true;
        return next.ordinal() == this.ordinal() + 1;
    }
}
