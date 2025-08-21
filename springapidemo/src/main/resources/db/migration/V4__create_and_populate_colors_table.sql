CREATE TABLE colors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO colors (name) VALUES
('Branco'),
('Preto'),
('Prata'),
('Cinza'),
('Vermelho'),
('Azul'),
('Verde'),
('Amarelo'),
('Laranja'),
('Marrom');
