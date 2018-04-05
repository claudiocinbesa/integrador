-- FilaAtendimento => Medico + atendimentos ambulatoriais (exames, coleta, etc)  
SELECT  * FROM [dbo].[PAS_FilaAtendimento]
WHERE DataAtendimento >= {DTATENDIMENTO} 
OR DataCriacao >= {DTCRIACAO}  
OR DataInicioAtendimento >= {DTINIATENDIMENTO} 
OR DataFimAtendimento >= {DTFIMATENDIMENTO} 
OR DataExclusao >= {DTEXCLUSAO}  