SELECT DISTINCT [B].[codBairro], [B].[descricao] AS bairro, [B].[codLocalidade],
  [L].[descricao] AS municipio, [codMunicipioIBGE]
--[B].[CodBairro], [B].[descricao]
FROM  [dbo].[Endereco_Bairro] AS B
,   [dbo].[Endereco_Cep] AS C
, [dbo].[Endereco_Localidade] AS L
WHERE 
 C.CodBairro = B.CodBairro
 AND [B].[codLocalidade] = [L].[codLocalidade] 
 ORDER BY 1
