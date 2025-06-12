package com.macf.projetos.entity.enums;

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
