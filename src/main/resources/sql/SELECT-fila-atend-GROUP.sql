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