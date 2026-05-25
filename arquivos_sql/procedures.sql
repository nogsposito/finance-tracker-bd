DELIMITER $$

CREATE PROCEDURE sp_atualizar_limite_planejamento(
    IN p_id_planejamento INT,
    IN p_novo_limite DECIMAL(10,2)
)

BEGIN

    UPDATE PlanejamentoFinanceiro
    SET valor_limite = p_novo_limite
    WHERE id_planejamento = p_id_planejamento;

END $$

DELIMITER ;

/*----------------------------------------------------------------------------------------------*/

CREATE TABLE RegistroEstouro (
     id_planejamento INTEGER,
     id_gasto_culpado INTEGER,
     data_estouro DATE,
     valor_passado DECIMAL(10,2)
);

DELIMITER //

CREATE PROCEDURE EncontrarEstouroPlanejamento(IN p_id_planejamento INTEGER)
BEGIN
	DECLARE v_done INT DEFAULT FALSE;
    DECLARE v_id_gasto INT;
    DECLARE v_valor_gasto DECIMAL(10,2);
    DECLARE v_data_gasto DATE;

    DECLARE v_soma DECIMAL(10,2) DEFAULT 0;
    DECLARE v_limite DECIMAL(10,2);

    -- cursor que pega apenas os gastos do planejamento específico, pelas datas
    DECLARE cur_gastos CURSOR FOR
SELECT id_gasto, valor, data
FROM Gasto
WHERE id_planejamento = p_id_planejamento
ORDER BY data ASC;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;

SELECT valor_limite INTO v_limite
FROM PlanejamentoFinanceiro
WHERE id_planejamento = p_id_planejamento;

OPEN cur_gastos;

my_loop: LOOP

    FETCH cur_gastos INTO v_id_gasto, v_valor_gasto, v_data_gasto;

    IF v_done THEN
        LEAVE my_loop;
END IF;

    SET v_soma = v_soma + v_valor_gasto;

    IF v_soma > v_limite THEN

        INSERT INTO RegistroEstouro (id_planejamento, id_gasto_culpado, data_estouro, valor_passado)
        VALUES (p_id_planejamento, v_id_gasto, v_data_gasto, (v_soma - v_limite));

        LEAVE my_loop;

END IF;

END LOOP;

CLOSE cur_gastos;

END //

DELIMITER ;
