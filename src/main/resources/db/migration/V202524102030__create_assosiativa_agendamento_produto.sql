CREATE TABLE agendamento_produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade_utilizada INT NOT NULL CHECK (quantidade_utilizada > 0),
    quantidade_reservada INT NOT NULL CHECK (quantidade_reservada > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação do vínculo',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data de atualização do vínculo',
    FOREIGN KEY (agendamento_id) REFERENCES agendamento(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);
