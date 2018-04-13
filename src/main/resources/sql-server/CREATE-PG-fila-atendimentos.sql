-- cria tabela FilaAtendimento No POSTGRES
DROP TABLE dbo.pas_filaatendimento ;
CREATE TABLE dbo.pas_filaatendimento (
    cnes    VARCHAR(7), 
    codpaciente    INT, 
    uf    VARCHAR(2), 
    codigoibge    VARCHAR(6), 
    nome    VARCHAR(100), 
    datacriacao    timestamp , 
    dataatendimento    timestamp , 
    dataexclusao    timestamp , 
    datainicioatendimento    timestamp , 
    datafimatendimento   timestamp , 
    codavaliacaoinicial    INT, 
    motivoencerramento    VARCHAR(30), 
    descricaomotivo    VARCHAR(250), 
    sexo    VARCHAR(1), 
    datanascimento    DATE, 
    codbairro    INT, 
    cep    VARCHAR(10), 
    codescolaridade   smallint, 
    codestadocivil    smallint, 
    pacientesituacaorua    VARCHAR(6), 
    cboatendimento    VARCHAR(10), 
    codsalaatendimento    smallint, 
    nomeinterno VARCHAR(30)
);
COPY dbo.pas_filaatendimento  FROM   'C:/tmp/csv/filaatendimentosPRESENTES.csv' HEADER CSV;