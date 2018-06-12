SELECT unidade, ano, mes, dia, munic || ' (' || uf || ')' AS munic, bairro, geo_ind,  
     sexo, sala, fx_etaria, count(*) as quant  
     FROM (  
     select DISTINCT u.descricaoabreviada as unidade,   
     f.codpaciente, f.uf, f.nome AS munic, b.bairro, m.geo_ind,  f.sexo,  
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
     from dbo.pas_filaatendimento AS f
     left join dbo.pas_unidade as u on f.cnes = u.cnes 
     left join dbo.bairros as b on f.codbairro = b.codbairro
     left join dbo.bairro_map as m on b.codbairro = m.codbairro 													
      where 
       EXTRACT(YEAR FROM f.dataatendimento) = 2018
       AND EXTRACT(MONTH FROM f.dataatendimento) = 4
       AND f.dataexclusao IS NULL  
       AND f.nomeinterno IN ('ClinicoGeral', 'Odontologia', 'Pediatria', 'ServicoSocial', 'Traumatologia', 'Urgencia')    
      ) AS tp  
     GROUP BY 1,2,3,4,5,6, 7, 8, 9,10