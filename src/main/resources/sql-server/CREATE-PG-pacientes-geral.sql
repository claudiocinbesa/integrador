CREATE SCHEMA dbo;

CREATE TABLE dbo.geral_paciente (
  codpaciente int not null,
  nome varchar (100) not null,
  sexo char (1) not null,
  datanascimento varchar (50) null,
  familia varchar (50) null,
  microarea varchar (50) null,
  equipe varchar (50) null,
  telefone varchar (30) null,
  celular varchar (30) null,
  estadocivil varchar (50) null,
  logradouro varchar (150) null,
  numero int null,
  bairro varchar (100) null,
  codmunicipio int null,
  complemento varchar (100) null,
  referencia varchar (200) null,
  profissao varchar (100) null,
  cartaosus varchar (15) null,
  numeroprontuario varchar (15) null,
  tipodocumento int null,
  numerodocumento varchar (50) null,
  orgaoemissor varchar (50) null,
  estadoorgaoemissor varchar (15) null,
  observacoes varchar  null,
  datacriacao varchar(30),  -- datetime not null,
  dataalteracao varchar(30), -- datetime null,
  codpessoa bigint null,
  codbairro int null,
  cnesorigem varchar (20) null,
  pacienteocasional  varchar (5),-- false ou true bit not null,
  falecido  varchar (5),-- false ou truebit not null,
  dataprevistaparto varchar(30),  -- datetime null,
  codestado int null,
  nomepai varchar (100) null,
  nomemae varchar (100) null,
  codracacor int,  -- not null constraint df__geral_pac__codra__52ae4273 default (99),
  cep varchar (9) null,
  codusuariocadsus varchar (60) null,
  email varchar (100) null,
  recebernotificacao  varchar (5),-- false ou true bit not null constraint df__geral_pac__receb__5f691f13 default (0),
  codpaisorigem varchar (3) null,
  codtipologradouro varchar (3) null,
  codescolaridade varchar (3) null,
  codestadocivil varchar (2) null,
  nomecontato varchar (100) null,
  nomeresponsavellegal varchar (100) null,
  codestadoorigem int null,
  codmunicipioorigem int null,
  frequentainstituicaoensino varchar (5),--bit not null constraint df__geral_pac__frequ__605d434c default (0),
  codtipovinculoprofissional int, -- tinyint null,
  registrofuncional varchar (10) null,
  horariotrabalho varchar (16) null,
  numerocontabancaria varchar (10) null,
  agenciabancaria varchar (5) null,
  codtipocontabancaria   int, -- tinyint null,
  banco varchar (50) null,
  loginresponsavel varchar (25) null,
  rgnumero varchar (20) null,
  rguf varchar (2) null,
  cpf varchar (11) null,
  nomenormalizado varchar (100) null,
  entregoucomprovanteresidencia varchar (5),-- bit null,
  gemeo  varchar (5),--bit null,
  nomesocial varchar (100) null,
  nomeinstituicao varchar (250) null,
  pacientesituacaorua varchar (5),--bit not null,
  nis varchar (11) null,
  codtipoocorrencia int, -- null constraint df__geral_pac__codti__12149a71 default (26),
  grauparentescocontato varchar (50) null,
  idadeaproximada varchar (5) --bit not null constraint df__geral_pac__idade__13fce2e3 default (0),
 );
  
COPY dbo.geral_paciente FROM   'C:/tmp/csv/pacientes.csv' HEADER CSV;
