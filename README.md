💼 Desafio Técnico – Java

 

🧩 Contexto:

Você deve desenvolver um sistema para gerenciar o portfólio de projetos de uma empresa. Esse sistema deverá permitir o acompanhamento completo do ciclo de vida de cada projeto, desde a análise de viabilidade até a finalização, incluindo gerenciamento de equipe, orçamento e risco.

 

📋 Regras de Negócio (ampliadas e mais desafiadoras):

* O sistema deve permitir CRUD completo de projetos com os seguintes campos:

    * Nome

    * Data de início

    * Previsão de término

    * Data real de término

    * Orçamento total (BigDecimal)

    * Descrição

    * Gerente responsável (relacionamento com a entidade membro)

    * Status atual

* A classificação de risco deve ser calculada dinamicamente com base nas seguintes regras:

    * Baixo risco: orçamento até R$ 100.000 e prazo ≤ 3 meses

    * Médio risco: orçamento entre R$ 100.001 e R$ 500.000 ou prazo entre 3 a 6 meses

    * Alto risco: orçamento acima de R$ 500.000 ou prazo superior a 6 meses

* Os status possíveis dos projetos são fixos (não cadastráveis) e seguem esta ordem:

    * em análise → análise realizada → análise aprovada → iniciado → planejado → em andamento → encerrado

    * Com a exceção de cancelado, que pode ser aplicado a qualquer momento.

    * Regra extra: a transição de status deve respeitar a sequência lógica. Não é permitido pular etapas.

* Se o status estiver em iniciado, em andamento ou encerrado, o projeto não poderá ser excluído.

* O cadastro de membros não deve ser feito diretamente.

    * Deve ser disponibilizada uma API REST externa (mockada) para criar e consultar membros, enviando nome e atribuição (cargo).

* O sistema deve permitir associar membros aos projetos. Apenas membros com a atribuição “funcionário” podem ser associados.

* Cada projeto deve permitir a alocação de no mínimo 1 e no máximo 10 membros. Um membro não pode estar alocado em mais de 3 projetos simultaneamente com status diferente de encerrado ou cancelado.

* Adicionar um endpoint para gerar um relatório resumido do portfólio contendo:

    * Quantidade de projetos por status

    * Total orçado por status

    * Média de duração dos projetos encerrados

    *  Total de membros únicos alocados

 

🧱 Regras de Implementação

* Utilizar arquitetura MVC

* Utilizar framework Spring Boot

* Utilizar JPA + Hibernate para persistência

* Banco de dados: PostgreSQL

* Aplicar princípios de Clean Code e SOLID

* Utilizar DTOs e mapeamento

* Utilizar Swagger/OpenAPI para documentação dos endpoints

* Implementar tratamento global de exceções

* Aplicar testes unitários (mínimo 70% de cobertura nas regras de negócio)

* Diferenciar bem camadas de controller, service e repository

* Implementar paginação e filtros para listagem de projetos

* Implementar segurança básica com Spring Security (usuário/senha hardcoded ou em memória)
