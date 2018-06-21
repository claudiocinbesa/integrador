
CREATE OR REPLACE FUNCTION minutos (dt_ini timestamp, dt_fim timestamp)
RETURNS integer AS $$
BEGIN
    RETURN 	EXTRACT(MONTH FROM  age(dt_ini, dt_fim) ) * 30 * 1440 +	
                EXTRACT(DAY  FROM  age(dt_ini, dt_fim) )   * 24 * 60 +	
		EXTRACT(HOUR  FROM  age(dt_ini, dt_fim) )   * 60 +	
		EXTRACT(MINUTE  FROM  age(dt_ini, dt_fim) ) ;
END;
$$ LANGUAGE plpgsql;


