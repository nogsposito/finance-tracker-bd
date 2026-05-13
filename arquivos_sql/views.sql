CREATE VIEW vw_relatorio_gastos_detalhados AS
SELECT 
    u.nome AS usuario,
    g.id_gasto,
    g.valor,
    g.descricao,
    g.data,
    c.nome AS categoria,
    e.nome AS estabelecimento
FROM Gasto g
JOIN Usuario u
    ON g.id_usuario = u.id_usuario
JOIN Categoria c
    ON g.id_categoria = c.id_categoria
JOIN Estabelecimento e
    ON g.id_estabelecimento = e.id_estabelecimento
WHERE g.valor > 100;

CREATE VIEW vw_total_gastos_usuario AS
SELECT 
    u.id_usuario,
    u.nome,
    e.endereco_email,
    (
        SELECT COUNT(*)
        FROM Gasto g
        WHERE g.id_usuario = u.id_usuario
    ) AS total_gastos
FROM Usuario u
JOIN Email e
    ON u.id_usuario = e.id_usuario;
