-- FilaAtendimento => Medico + atendimentos ambulatoriais (exames, coleta, etc)  
SELECT DISTINCT [F].[CNES], [F].[CodPaciente],
-- [U].[DescricaoAbreviada], 
  [M].[UF], [M].[CodigoIBGE] , [M].[Nome], 
-- convert(varchar, [F].[DataAtendimento], 111),
  [F].[DataCriacao],
  [F].[DataAtendimento],
  [F].[DataExclusao], 
  [F].[DataInicioAtendimento],
  [F].[DataFimAtendimento],
  [F].[CodAvaliacaoInicial],
  [F].[MotivoEncerramento],
  [F].[DescricaoMotivo],
  [P].[Sexo], [P].[DataNascimento], [P].[CodBairro], [P].[Cep],
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
   AND [F].[DataExclusao] >= {DTEXCLUSAO}  
        