-- cria tabela de bairros
CREATE TABLE dbo.bairros (
    codBairro   INT PRIMARY KEY,
    bairro      VARCHAR(150),
    codLocalidade  INT,
    municipio   VARCHAR(150),
    codMunicipioIBGE VARCHAR(10)
);

COPY dbo.bairros  FROM   'C:/tmp/csv/bairros-cep.csv' HEADER CSV;