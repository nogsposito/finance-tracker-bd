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