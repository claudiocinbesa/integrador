-- PAS_AvaliacaoInicial => Identificacao e Classificação de Risco  
SELECT 
    CodAvaliacaoInicial,
    CodPaciente,
    CNES,
    CONVERT(varchar, DataCriacao, 120) AS DataCriacao,
    -- DataCriacaoUTC,
    LoginUsuario,
    CONVERT(varchar, DataExclusao, 120) AS DataExclusao,
    -- DataExclusaoUTC,
    CONVERT(varchar, DataInicioAvaliacao, 120) AS DataInicioAvaliacao,
    CONVERT(varchar, DataFimAvaliacao, 120) AS DataFimAvaliacao,
    LoginExclusao,
    LoginAvaliacaoInicial,
    CodClassificacaoRisco,
    CodSalaAtendimento,
    Prioritario,
    Repouso,
    Urgencia,
    Leito,
    CBOAvaliacaoInicial,
    Observacao,
    CodTipoOcorrencia,
    CodTipoChegada
FROM dbo.PAS_AvaliacaoInicial 
WHERE DataCriacao >= {DTCRIACAO} 
OR DataExclusao >= {DTEXCLUSAO} 
OR DataInicioAvaliacao >= {DTINIAVALIACAO} 
OR DataFimAvaliacao >= {DTFIMAVALIACAO} 