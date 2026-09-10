INSERT INTO tb_categoria (nome, descricao) VALUES ('Eletrônicos', 'Dispositivos e aparelhos eletrônicos');
INSERT INTO tb_categoria (nome, descricao) VALUES ('Livros', 'Livros físicos e e-books');
INSERT INTO tb_categoria (nome, descricao) VALUES ('Vestuário', 'Roupas e acessórios');
INSERT INTO tb_categoria (nome, descricao) VALUES ('Casa e Cozinha', 'Artigos para o lar');
INSERT INTO tb_categoria (nome, descricao) VALUES ('Esportes', 'Equipamentos esportivos');



INSERT INTO tb_produto (nome, preco, categoria_id) VALUES ('Smartphone', 2500.00, 1);
INSERT INTO tb_produto (nome, preco, categoria_id) VALUES ('Livro de Java', 120.00, 2);
INSERT INTO tb_produto (nome, preco, categoria_id) VALUES ('Camiseta Algodão', 59.90, 3);
INSERT INTO tb_produto (nome, preco, categoria_id) VALUES ('Cafeteira Elétrica', 199.90, 4);
INSERT INTO tb_produto (nome, preco, categoria_id) VALUES ('Bola de Futebol', 89.90, 5);


INSERT INTO tb_cliente (nome, email, telefone) VALUES ('João Silva', 'joao@gmail.com', '11987654321');
INSERT INTO tb_cliente (nome, email, telefone) VALUES ('Maria Santos', 'maria@gmail.com', '21987654322');
INSERT INTO tb_cliente (nome, email, telefone) VALUES ('Carlos Oliveira', 'carlos@gmail.com', '31987654323');
INSERT INTO tb_cliente (nome, email, telefone) VALUES ('Ana Souza', 'ana@gmail.com', '41987654324');
INSERT INTO tb_cliente (nome, email, telefone) VALUES ('Lucas Lima', 'lucas@gmail.com', '51987654325');


INSERT INTO tb_pedido (data, status, valor_total, cliente_id) VALUES ('2026-03-01T10:00:00', 'AGUARDANDO_PAGAMENTO', 2500.00, 1);
INSERT INTO tb_pedido (data, status, valor_total, cliente_id) VALUES ('2026-03-01T11:30:00', 'PAGO', 120.00, 2);
INSERT INTO tb_pedido (data, status, valor_total, cliente_id) VALUES ('2026-03-02T14:15:00', 'PAGO', 59.90, 3);
INSERT INTO tb_pedido (data, status, valor_total, cliente_id) VALUES ('2026-03-02T16:45:00', 'ENVIADO', 199.90, 4);
INSERT INTO tb_pedido (data, status, valor_total, cliente_id) VALUES ('2026-03-02T18:00:00', 'ENTREGUE', 89.90, 5);


INSERT INTO tb_item_pedido (quantidade, valor_unitario, pedido_id, produto_id) VALUES (1, 2500.00, 1, 1);
INSERT INTO tb_item_pedido (quantidade, valor_unitario, pedido_id, produto_id) VALUES (1, 120.00, 2, 2);
INSERT INTO tb_item_pedido (quantidade, valor_unitario, pedido_id, produto_id) VALUES (1, 59.90, 3, 3);
INSERT INTO tb_item_pedido (quantidade, valor_unitario, pedido_id, produto_id) VALUES (1, 199.90, 4, 4);
INSERT INTO tb_item_pedido (quantidade, valor_unitario, pedido_id, produto_id) VALUES (1, 89.90, 5, 5);


INSERT INTO tb_pagamento (valor, data, status, tipo, pedido_id) VALUES (2500.00, '2026-03-01T10:05:00', 'PENDENTE', 'PIX', 1);
INSERT INTO tb_pagamento (valor, data, status, tipo, pedido_id) VALUES (120.00, '2026-03-01T11:35:00', 'CONFIRMADO', 'CARTAO_CREDITO', 2);
INSERT INTO tb_pagamento (valor, data, status, tipo, pedido_id) VALUES (59.90, '2026-03-02T14:20:00', 'CONFIRMADO', 'PIX', 3);
INSERT INTO tb_pagamento (valor, data, status, tipo, pedido_id) VALUES (199.90, '2026-03-02T16:50:00', 'CONFIRMADO', 'BOLETO', 4);
INSERT INTO tb_pagamento (valor, data, status, tipo, pedido_id) VALUES (89.90, '2026-03-02T18:05:00', 'CONFIRMADO', 'CARTAO_CREDITO', 5);