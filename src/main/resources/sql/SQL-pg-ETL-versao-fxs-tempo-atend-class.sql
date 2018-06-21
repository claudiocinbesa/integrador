SELECT unidade, ano, mes, dia, munic || ' (' || uf || ')' AS munic, bairro,  codbairrogeo,                            
 sexo, sala, fx_etaria, fx_hora, t_classif, t_atend, count(*) as quant                                                
 FROM 
-- COPY
 (                                                                                                               
 select DISTINCT u.descricaoabreviada as unidade,                                                                     
 f.codpaciente, f.uf, f.nome AS munic, b.bairro, m.geo_ind as codbairrogeo,  f.sexo,                                  
 f.nomeinterno AS sala,                                                                                               
 f.datanascimento, EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) AS idade,                              
 CASE WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <= 1 THEN 'A:(0 -  1) RECEM NASCIDO '          
      WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=11 THEN 'B:(1 - 11) INFANCIA '               
 	 WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=17 THEN 'C:(12 -17) ADOLESCENCIA '            
 	 WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=40 THEN 'D:(18 -40) JOVEM '                   
 	 WHEN EXTRACT(YEAR FROM age(f.dataatendimento, f.datanascimento)) <=65 THEN 'E:(41 -65) ADULTO '                  
      ELSE 'F:(+65) IDOSO '                                                                                           
 END AS fx_etaria,                                                                                                    
  EXTRACT(YEAR FROM f.dataatendimento) as ano,                                                                        
  EXTRACT(MONTH FROM f.dataatendimento) as mes,                                                                       
  EXTRACT(DAY FROM f.dataatendimento) as dia,                                                                         
 CASE WHEN EXTRACT(HOUR FROM f.datacriacao) >= 7 AND EXTRACT(HOUR FROM f.datacriacao) <= 12  THEN 'A:(7 - 12h)'       
  WHEN EXTRACT(HOUR FROM f.datacriacao) >= 13 AND EXTRACT(HOUR FROM f.datacriacao) <= 19 THEN 'B:(13 - 19h)'          
  WHEN EXTRACT(HOUR FROM f.datacriacao) >= 20 AND EXTRACT(HOUR FROM f.datacriacao) <= 23 THEN 'C:(20 - 23h)'          
  WHEN EXTRACT(HOUR FROM f.datacriacao) >= 00 AND EXTRACT(HOUR FROM f.datacriacao) <= 6  THEN 'D:(0h - 6h)'              
  ELSE 'E:(??)  '                                                                                    
 END AS fx_hora,     		                                                 
 CASE WHEN minutos(i.datafimavaliacao, i.datacriacao) <= 5 THEN 'A - 5m'    
     WHEN minutos(i.datafimavaliacao, i.datacriacao) <= 15 THEN 'B - 15m'   
	 WHEN minutos(i.datafimavaliacao, i.datacriacao) <= 30 THEN 'C -  30m'  
	 WHEN minutos(i.datafimavaliacao, i.datacriacao) <= 60 THEN 'D -  1h'   
	 ELSE 'E - 2h ou +'                                                 
 END AS t_classif,	                                                   
 CASE WHEN minutos(f.datafimatendimento, f.datacriacao) <= 15 THEN 'A - 15m'  
      WHEN minutos(f.datafimatendimento, f.datacriacao) <= 60 THEN 'B - 1h'     
      WHEN minutos(f.datafimatendimento, f.datacriacao) <= 3*60 THEN 'C - 3h'  
      WHEN minutos(f.datafimatendimento, f.datacriacao) <= 6*60 THEN 'D - 6h'  
      WHEN minutos(f.datafimatendimento, f.datacriacao) <= 24*60 THEN 'E - 24h'
      ELSE 'F - 1 dia ou +'                                                           
 END AS t_atend 	                                                           
 from dbo.pas_filaatendimento AS f                                                                                     
 left join dbo.pas_unidade as u on f.cnes = u.cnes  
 left join dbo.bairros as b on f.codbairro = b.codbairro
 left join dbo.bairro_map as m on b.codbairro = m.codbairro 													        
 left join dbo.pas_avaliacaoinicial as i on f.codavaliacaoinicial = i.codavaliacaoinicial                              
  where                                                                                                                
   EXTRACT(YEAR FROM f.dataatendimento) =  2018
   AND EXTRACT(MONTH FROM f.dataatendimento) =  6
   AND f.dataexclusao IS NULL                                                                                          
   AND f.nomeinterno IN ('ClinicoGeral', 'Odontologia', 'Pediatria', 'Traumatologia', 'Urgencia') 
	 -- 'ServicoSocial',   
  ) 
 -- TO 'c:/tmp/maio2018upas.csv' HEADER CSV
 AS tp                                                                                                              
 GROUP BY 1,2,3,4,5,6, 7, 8, 9,10,11,12,13                                                                             
