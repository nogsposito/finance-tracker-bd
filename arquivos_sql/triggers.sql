
CREATE TABLE LogGasto (
    id_log INTEGER PRIMARY KEY AUTO_INCREMENT,
    acao VARCHAR(50),
    id_gasto_excluido INTEGER,
    valor_antigo DECIMAL(10,2),
    data_modificacao DATETIME DEFAULT CURRENT_TIMESTAMP
)

DELIMITER //

CREATE TRIGGER trg_log_delete_gasto
    AFTER DELETE ON Gasto
    FOR EACH ROW
BEGIN
    INSERT INTO LogGasto (acao, id_gasto_excluido, valor_antigo)
    VALUES ('EXCLUSAO', OLD.id_gasto, OLD.valor);
END //

DELIMITER ;






DELIMITER //

CREATE TRIGGER trg_valida_data_gasto
    BEFORE INSERT ON Gasto
    FOR EACH ROW
BEGIN
    DECLARE v_data_inicio DATE;
    DECLARE v_data_fim DATE;

    IF NEW.id_planejamento IS NOT NULL THEN

    SELECT data_inicio, data_fim INTO v_data_inicio, v_data_fim
    FROM PlanejamentoFinanceiro
    WHERE id_planejamento = NEW.id_planejamento;

    IF NEW.data < v_data_inicio OR NEW.data > v_data_fim THEN
			SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Erro; Data do gasto não está dentro do período do Planejamento Financeiro';
END IF;

END IF;

END //

DELIMITER ;