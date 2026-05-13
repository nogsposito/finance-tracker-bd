-- a) só categorias que o gasto é > 100
SELECT c.nome AS categoria, SUM(g.valor) AS total_gasto
FROM gasto g
JOIN categoria c ON g.id_categoria = c.id_categoria
GROUP BY c.nome
HAVING SUM(g.valor) > 100;

-- b) usuário específico com descricao, valor e estabelecimento de seus gastos
SELECT u.nome AS usuario, g.descricao, g.valor, e.nome AS estabelecimento
FROM gasto g
JOIN usuario u ON g.id_usuario = u.id_usuario
JOIN estabelecimento e ON g.id_estabelecimento = e.id_estabelecimento
WHERE u.id_usuario = 1;

-- c) usuários sem nenhum gasto registrado
SELECT u.nome as usuario_sem_gasto
FROM usuario u
LEFT JOIN gasto g on g.id_usuario = u.id_usuario
WHERE g.id_gasto IS NULL;

-- d) valor e descrição de todos os gastos maiores do que a média no sistema
SELECT valor, descricao
FROM gasto
WHERE valor > (
    SELECT AVG(valor)
    FROM gasto
);