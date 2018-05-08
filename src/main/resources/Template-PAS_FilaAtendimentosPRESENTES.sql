-- FilaAtendimento => Medico + atendimentos ambulatoriais (exames, coleta, etc)  
SELECT DISTINCT 
   [F].[CNES], 
   [F].[CodFilaAtendimento], 
   [F].[CodPaciente],
-- [U].[DescricaoAbreviada], 
  [M].[UF], [M].[CodigoIBGE] , [M].[Nome], 
-- convert(varchar, [F].[DataAtendimento], 111),
  CONVERT(varchar, [F].[DataCriacao], 120) AS DataCriacao, 
  CONVERT(varchar, [F].[DataAtendimento],  120) AS DataAtendimento, 
  CONVERT(varchar, [F].[DataExclusao],  120) AS DataExclusao, 
  CONVERT(varchar, [F].[DataInicioAtendimento],  120) AS DataInicioAtendimento, 
  CONVERT(varchar, [F].[DataFimAtendimento],  120) AS DataFimAtendimento, 
  [F].[CodAvaliacaoInicial],
  [F].[MotivoEncerramento],
  [F].[DescricaoMotivo],
  [P].[Sexo],
  -- [P].[DataNascimento], 
  CONVERT(varchar, [P].[DataNascimento], 120)  AS DataNascimento,
  [P].[CodBairro], [P].[Cep],
  [P].[CodEscolaridade], [P].[CodEstadoCivil], [P].[PacienteSituacaoRua],
  [F].[CBOAtendimento],
  [F].[CodSalaAtendimento], [S].[NomeInterno]
FROM [dbo].[PAS_FilaAtendimento] AS F, 
     [dbo].[Geral_Paciente] AS P,
     [dbo].[PAS_Municipio] AS M 
     , [dbo].[PAS_Unidade] AS U 
     , [dbo].[PAS_SalaAtendimento] AS S
WHERE [F].[CodPaciente] = [P].[CodPaciente]
   AND [P].[CodMunicipio] = [M].[codMunicipioBdpmv]  -- [M].[CodMunicipio]
   AND [F].[CNES] = [U].[CNES]
   AND [F].[CodSalaAtendimento] = [S].[CodSalaAtendimento]
   AND  F.DataAtendimento >= {DTATENDIMENTO} 
        AND F.DataCriacao >= {DTCRIACAO}  
        AND [F].[DataExclusao] IS NULL
        AND ( F.DataInicioAtendimento >= {DTINIATENDIMENTO} 
        OR F.DataFimAtendimento >= {DTFIMATENDIMENTO} )
      