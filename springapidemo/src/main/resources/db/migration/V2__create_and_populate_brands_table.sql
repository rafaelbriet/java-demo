CREATE TABLE brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO brands (name) VALUES
('Chevrolet'),
('Volkswagen'),
('Fiat'),
('Ford'),
('Honda'),
('Hyundai'),
('Toyota'),
('Renault'),
('Jeep'),
('Nissan'),
('Peugeot'),
('Citroën'),
('Mitsubishi'),
('BMW'),
('Mercedes-Benz'),
('Audi');
