CREATE TABLE veiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    veiculo VARCHAR(255),
    marca VARCHAR(255),
    ano INT,
    descricao TEXT,
    vendido BOOLEAN,
    created DATETIME,
    updated DATETIME
);