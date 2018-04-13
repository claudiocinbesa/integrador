select datacriacao, dataatendimento , datanascimento,
 EXTRACT(YEAR FROM age(dataatendimento, datanascimento)) AS IDADE
 --, to_date(datanascimento,   'DD MM YYYY')
FROM dbo.pas_filaatendimento
LIMIT 20;
