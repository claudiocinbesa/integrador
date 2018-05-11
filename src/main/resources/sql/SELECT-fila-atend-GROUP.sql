COPY (
SELECT unidade, ano, mes, dia, count(*) as quant
FROM (
  select DISTINCT u.descricaoabreviada as unidade, 
	   f.codpaciente,
       EXTRACT(YEAR FROM f.dataatendimento) as ano,
	   EXTRACT(MONTH FROM f.dataatendimento) as mes,
	   EXTRACT(DAY FROM f.dataatendimento) as dia
  from dbo.pas_filaatendimento AS f, 
         dbo.pas_unidade as u
  where 
  f.cnes = u.cnes
  -- AND EXTRACT(YEAR FROM f.dataatendimento) = 2017
  -- AND EXTRACT(MONTH FROM f.dataatendimento) = 02
  AND f.dataexclusao IS NULL
) AS tp  
GROUP BY 1,2,3,4
) TO 'c:/tmp/rbe/atendimentos.csv'	CSV HEADER;

-- CONSULTA PARA RECUPERAR DADOS DO ATENDIMENTO PARA O DASHBOARD
select DISTINCT u.descricaoabreviada as unidade, 
f.codpaciente, f.uf, f.nome AS munic, f.sexo, 
f.dataatendimento,
	f.nomeinterno AS sala, 
	f.datanascimento, EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) AS idade,
	CASE WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <= 1 THEN 'A:(0 -  1) RECEM NASCIDO '
         WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=11 THEN 'B:(1 - 11) INFANCIA '
		 WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=17 THEN 'C:(12 -17) ADOLESCENCIA '
		 WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=40 THEN 'D:(18 -40) ADULTO JOVEM '
		 WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=65 THEN 'E:(41 -65) ADULTO '
         ELSE 'F:(+65) IDOSO '
    END AS fx_etaria,
EXTRACT(YEAR FROM f.dataatendimento) as ano, 
EXTRACT(MONTH FROM f.dataatendimento) as mes,
 EXTRACT(DAY FROM f.dataatendimento) as dia 
from dbo.pas_filaatendimento AS f, 
dbo.pas_unidade as u 
 where f.cnes = u.cnes
 AND EXTRACT(YEAR FROM f.dataatendimento) = 2018
 AND EXTRACT(MONTH FROM f.dataatendimento) = 3
 AND f.dataexclusao IS NULL 
 AND f.nomeinterno IN ('ClinicoGeral', 'Odontologia', 'Pediatria', 'ServicoSocial', 'Traumatologia', 'Urgencia')
 -- ORDER BY u.descricaoabreviada, f.codpaciente, f.dataatendimento
 