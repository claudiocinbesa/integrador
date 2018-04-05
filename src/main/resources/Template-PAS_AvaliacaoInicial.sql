-- PAS_AvaliacaoInicial => Identificacao e Classificação de Risco  
SELECT * FROM dbo.PAS_AvaliacaoInicial 
WHERE DataCriacao >= {DTCRIACAO} 
OR DataExclusao >= {DTEXCLUSAO} 
OR DataInicioAvaliacao >= {DTINIAVALIACAO} 
OR DataFimAvaliacao >= {DTFIMAVALIACAO} 