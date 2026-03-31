CREATE DATABASE app_financeiro;
USE app_financeiro;

CREATE TABLE Usuario (
    id_usuario INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    data_cadastro DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE Email (
    endereco_email VARCHAR(150) PRIMARY KEY,
    id_usuario INTEGER,
    
    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE Telefone (
    numero_telefone VARCHAR(20) PRIMARY KEY,
    id_usuario INTEGER NOT NULL,

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
        ON DELETE CASCADE
);

CREATE TABLE Categoria (
    id_categoria INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(255),
    id_categoria_pai INTEGER,

    FOREIGN KEY (id_categoria_pai)
        REFERENCES Categoria(id_categoria)
        ON DELETE SET NULL
);

CREATE TABLE Localidade (
    id_localidade INTEGER PRIMARY KEY,
    pais VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL
);

CREATE TABLE Estabelecimento (
    id_estabelecimento INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    id_localidade INTEGER NOT NULL,

    FOREIGN KEY (id_localidade)
        REFERENCES Localidade(id_localidade)
        ON UPDATE CASCADE
);

CREATE TABLE FormaPagamento (
    id_forma_pagamento INTEGER PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE CartaoCredito (
    id_forma_pagamento INTEGER PRIMARY KEY,

    FOREIGN KEY (id_forma_pagamento)
        REFERENCES FormaPagamento(id_forma_pagamento)
        ON DELETE CASCADE
);

CREATE TABLE PlanejamentoFinanceiro (
    id_planejamento INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    valor_limite DECIMAL(10,2) NOT NULL CHECK (valor_limite >= 0),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    id_usuario INTEGER NOT NULL,

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
        ON DELETE CASCADE
);

CREATE TABLE PlanejamentoFinanceiroCategoria (
    id_planejamento INTEGER,
    id_categoria INTEGER,
    limite_categoria DECIMAL(10,2) NOT NULL CHECK (limite_categoria >= 0),

    PRIMARY KEY (id_planejamento, id_categoria),

    FOREIGN KEY (id_planejamento)
        REFERENCES PlanejamentoFinanceiro(id_planejamento)
        ON DELETE CASCADE,

    FOREIGN KEY (id_categoria)
        REFERENCES Categoria(id_categoria)
);

CREATE TABLE MetaFinanceira (
    id_meta INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    valor_atual DECIMAL(10,2) DEFAULT 0 CHECK (valor_atual >= 0),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    id_usuario INTEGER NOT NULL,

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
);

CREATE TABLE Gasto (
    id_gasto INTEGER PRIMARY KEY AUTO_INCREMENT,
    valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
    descricao VARCHAR(255),
    data DATE NOT NULL,
    id_usuario INTEGER NOT NULL,
    id_planejamento INTEGER,
    id_estabelecimento INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    id_forma_pagamento INTEGER NOT NULL,

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario),

    FOREIGN KEY (id_planejamento)
        REFERENCES PlanejamentoFinanceiro(id_planejamento)
        ON DELETE SET NULL,

    FOREIGN KEY (id_estabelecimento)
        REFERENCES Estabelecimento(id_estabelecimento),

    FOREIGN KEY (id_categoria)
        REFERENCES Categoria(id_categoria),

    FOREIGN KEY (id_forma_pagamento)
        REFERENCES FormaPagamento(id_forma_pagamento)
);

CREATE TABLE Parcela (
    id_parcela INTEGER PRIMARY KEY,
    numero_parcela INTEGER NOT NULL CHECK (numero_parcela > 0),
    valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
    data_vencimento DATE NOT NULL,
    id_gasto INTEGER NOT NULL,
    id_forma_pagamento INTEGER NOT NULL,

    FOREIGN KEY (id_gasto)
        REFERENCES Gasto(id_gasto)
        ON DELETE CASCADE,

    FOREIGN KEY (id_forma_pagamento)
        REFERENCES CartaoCredito(id_forma_pagamento)
);