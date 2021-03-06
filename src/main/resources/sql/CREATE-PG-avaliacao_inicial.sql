CREATE TABLE dbo.pas_avaliacaoinicial (
    codavaliacaoinicial         int,
    codpaciente             	int,
    cnes             		VARCHAR(7),
    datacriacao             	timestamp,
    loginusuario             	varchar (25) null,
    dataexclusao             	timestamp,
    datainicioavaliacao         timestamp,
    datafimavaliacao            timestamp,
    loginexclusao             	varchar (25) null,
    loginavaliacaoinicial       varchar (25) null,
    codclassificacaorisco       int,
    codsalaatendimento          int,
    prioritario             	varchar (5) null,
    repouso             	varchar (5) null,
    urgencia             	varchar (5) null,
    leito             		varchar (25) null,
    cboavaliacaoinicial         varchar (6) null,
    observacao             	varchar (255) null,
    codtipoocorrencia           int,
    codtipochegada		int,
    CONSTRAINT          PK_pas_avaliacaoinicial PRIMARY KEY (cnes, codavaliacaoinicial)
);