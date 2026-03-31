CREATE TABLE Usuario (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Telefone (
    numero_telefone SERIAL,
    id_usuario INT,
    PRIMARY KEY (numero_telefone, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Email (
    endereco_email SERIAL,
    id_usuario INT,
    PRIMARY KEY (endereco_email, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE MetaFinanceira(
    id_meta SERIAL,
    id_usuario INT,
    PRIMARY KEY (id_meta, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Gasto (
    id_gasto SERIAL PRIMARY KEY,
    valor DECIMAL(10, 2) NOT NULL,
    descricao VARCHAR(255),
    data DATE,
    id_usuario INT,
    id_planejamento_financeiro INT,
    id_estabelecimento INT,
    id_categoria INT,
    id_forma_pagamento INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_planejamento_financeiro) REFERENCES MetaFinanceira(id_meta),
    FOREIGN KEY (id_estabelecimento) REFERENCES Estabelecimento(id_estabelecimento),
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
    FOREIGN KEY (id_forma_pagamento) REFERENCES FormaPagamento(id_forma_pagamento)
);

CREATE TABLE PlanejamentoFinanceiro (
    id_planejamento_financeiro SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor_limite DECIMAL(10, 2) NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Estabelecimento (
    id_estabelecimento SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255),
    id_localidade INT,
    FOREIGN KEY (id_localidade) REFERENCES Localidade(id_localidade)
);

CREATE TABLE Localidade (
    id_localidade SERIAL PRIMARY KEY,
    id_estabelecimento INT,
    cidade VARCHAR(255),
    estado VARCHAR(255),
    pais VARCHAR(255),
    PRIMARY KEY (id_localidade, id_estabelecimento),
    FOREIGN KEY (id_estabelecimento) REFERENCES Estabelecimento(id_estabelecimento)
);

CREATE TABLE Categoria (
    id_categoria SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    categoria_possuinte INT,
    FOREIGN KEY (categoria_possuinte) REFERENCES Categoria(id_categoria)
);

CREATE TABLE PlanejamentoFinanceiroCategoria (
    id_planejamento_financeiro INT,
    id_categoria INT,
    limite_categoria DECIMAL(10, 2),
    PRIMARY KEY (id_planejamento_financeiro, id_categoria),
    FOREIGN KEY (id_planejamento_financeiro) REFERENCES PlanejamentoFinanceiro(id_planejamento_financeiro),
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

CREATE TABLE FormaPagamento (
    id_forma_pagamento SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE CartaoCredito (
    id_forma_pagamento INT PRIMARY KEY,
    FOREIGN KEY (id_forma_pagamento) REFERENCES FormaPagamento(id_forma_pagamento)
);

CREATE TABLE Parcela (
    id_parcela SERIAL,
    id_forma_pagamento INT,
    valor DECIMAL(10, 2) NOT NULL,
    data_vencimento DATE,
    PRIMARY KEY (id_parcela, id_forma_pagamento),
    FOREIGN KEY (id_forma_pagamento) REFERENCES FormaPagamento(id_forma_pagamento)
);