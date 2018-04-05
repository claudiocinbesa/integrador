-- Cadastro geral de Pacientes   
SELECT * FROM dbo.Geral_Paciente 
WHERE DataCriacao >= {DTCRIACAO} OR DataAlteracao >= {DTALTERACAO}