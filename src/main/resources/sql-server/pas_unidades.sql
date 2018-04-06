CREATE TABLE dbo.pas_unidade (
	codunidade    int ,
    cnes          varchar(7) primary key,
    descricao     varchar(150),                                              
    externo  	int, 
    descricaoabreviada        varchar(50),
    unidadeorcamentaria   varchar(50),
    ativo int
); 
COPY dbo.pas_unidade FROM   'C:/bi/upa/carga/pas_unidade.CSV' HEADER CSV;
