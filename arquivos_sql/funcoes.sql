DELIMITER $$

CREATE FUNCTION fn_total_gasto_usuario(p_id_usuario INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC

BEGIN

    DECLARE total DECIMAL(10,2);

    SELECT SUM(valor)
    INTO total
    FROM Gasto
    WHERE id_usuario = p_id_usuario;

    RETURN IFNULL(total, 0);

END $$

DELIMITER ;

/* ------------------------------------------------------------------------------------------------------*/

DELIMITER $$

CREATE FUNCTION fn_situacao_planejamento(p_id_planejamento INT)
RETURNS VARCHAR(30)
DETERMINISTIC

BEGIN

    DECLARE total_gasto DECIMAL(10,2);
    DECLARE limite_planejamento DECIMAL(10,2);

    SELECT valor_limite
    INTO limite_planejamento
    FROM PlanejamentoFinanceiro
    WHERE id_planejamento = p_id_planejamento;

    SELECT SUM(valor)
    INTO total_gasto
    FROM Gasto
    WHERE id_planejamento = p_id_planejamento;

    IF IFNULL(total_gasto,0) > limite_planejamento THEN
        RETURN 'ACIMA DO LIMITE';
    ELSE
        RETURN 'DENTRO DO LIMITE';
    END IF;

END $$

DELIMITER ;
