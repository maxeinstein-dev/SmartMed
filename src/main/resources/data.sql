-- Inserir especialidades
INSERT INTO especialidade (nome, descricao)
VALUES ('Cardiologia', 'Especialidade médica que trata do coração'),
       ('Dermatologia', 'Especialidade médica que trata da pele'),
       ('Oftalmologia', 'Especialidade médica que trata dos olhos e da visão.'),
       ('Pediatria', 'Especialidade médica que cuida da saúde de crianças e adolescentes.');

-- Inserir formas de pagamento
INSERT INTO formaPagamento (descricao)
VALUES ('Dinheiro'),
       ('Cartão de Crédito'),
       ('Convênio');

-- Inserir convênios (com diferentes porcentagens de desconto)
INSERT INTO convenio (nome, cnpj, telefone, email, ativo, porcentagemDesconto)
VALUES ('Unimed', '12345678000199', '11999998888', 'unimed@teste.com', TRUE, 0.50),
       ('Amil', '98765432000199', '11988887777', 'amil@teste.com', TRUE, 0.70),
       ('Bradesco Saúde', '56789012000199', '11977776666', 'bradesco@teste.com', TRUE, 0.60);

-- Inserir pacientes (com o campo 'ativo')
INSERT INTO paciente (nome, cpf, dataNascimento, telefone, email, ativo)
VALUES ('João Silva', '12345678901', '1980-01-15', '11999991111', 'joao@teste.com', TRUE),
       ('Maria Souza', '98765432109', '1975-05-20', '11988882222', 'maria@teste.com', TRUE),
       ('Paciente Inativo', '11122233344', '1995-10-25', '11911112222', 'inativo@teste.com', FALSE), -- Inativo
       ('Paciente Teste 4', '11122233345', '1991-01-01', '11911112223', 'paciente4@teste.com', TRUE),
       ('Paciente Teste 5', '11122233346', '1992-02-02', '11911112224', 'paciente5@teste.com', TRUE),
       ('Paciente Teste 6', '11122233347', '1993-03-03', '11911112225', 'paciente6@teste.com', TRUE),
       ('Paciente Teste 7', '11122233348', '1994-04-04', '11911112226', 'paciente7@teste.com', FALSE), -- Inativo
       ('Paciente Teste 8', '11122233349', '1995-05-05', '11911112227', 'paciente8@teste.com', TRUE),
       ('Paciente Teste 9', '11122233350', '1996-06-06', '11911112228', 'paciente9@teste.com', TRUE),
       ('Paciente Teste 10', '11122233351', '1997-07-07', '11911112229', 'paciente10@teste.com', TRUE),
       ('Paciente Teste 11', '11122233352', '1998-08-08', '11911112230', 'paciente11@teste.com', TRUE),
       ('Paciente Teste 12', '11122233353', '1999-09-09', '11911112231', 'paciente12@teste.com', FALSE), -- Inativo
       ('Paciente Teste 13', '11122233354', '2000-10-10', '11911112232', 'paciente13@teste.com', TRUE),
       ('Paciente Teste 14', '11122233355', '2001-11-11', '11911112233', 'paciente14@teste.com', TRUE),
       ('Paciente Teste 15', '11122233356', '2002-12-12', '11911112234', 'paciente15@teste.com', TRUE),
       ('Paciente Teste 16', '11122233357', '2003-01-13', '11911112235', 'paciente16@teste.com', TRUE),
       ('Paciente Teste 17', '11122233358', '2004-02-14', '11911112236', 'paciente17@teste.com', TRUE),
       ('Paciente Teste 18', '11122233359', '2005-03-15', '11911112237', 'paciente18@teste.com', TRUE),
       ('Paciente Teste 19', '11122233360', '2006-04-16', '11911112238', 'paciente19@teste.com', TRUE),
       ('Paciente Teste 20', '11122233361', '2007-05-17', '11911112239', 'paciente20@teste.com', TRUE),
       ('Paciente Teste 21', '11122233362', '2008-06-18', '11911112240', 'paciente21@teste.com', TRUE),
       ('Paciente Teste 22', '11122233363', '2009-07-19', '11911112241', 'paciente22@teste.com', TRUE),
       ('Paciente Teste 23', '11122233364', '2010-08-20', '11911112242', 'paciente23@teste.com', FALSE), -- Inativo
       ('Paciente Teste 24', '11122233365', '2011-09-21', '11911112243', 'paciente24@teste.com', TRUE),
       ('Paciente Teste 25', '11122233366', '2012-10-22', '11911112244', 'paciente25@teste.com', TRUE),
       ('Paciente Teste 26', '11122233367', '2013-11-23', '11911112245', 'paciente26@teste.com', TRUE),
       ('Paciente Teste 27', '11122233368', '2014-12-24', '11911112246', 'paciente27@teste.com', TRUE),
       ('Paciente Teste 28', '11122233369', '2015-01-25', '11911112247', 'paciente28@teste.com', TRUE),
       ('Paciente Teste 29', '11122233370', '2016-02-26', '11911112248', 'paciente29@teste.com', TRUE),
       ('Paciente Teste 30', '11122233371', '2017-03-27', '11911112249', 'paciente30@teste.com', TRUE);

-- Inserir recepcionistas (dados para testes de paginação e filtro)
INSERT INTO recepcionista (id, nome, cpf, dataNascimento, dataAdmissao, telefone, email, ativo)
VALUES
    (1, 'Carlos Andrade', '45678912301', '1990-02-10', '2020-01-15', '11977773333', 'carlos@teste.com', TRUE),
    (2, 'Ana Costa', '11111111111', '1992-05-20', '2023-01-10', '11911111111', 'ana.costa@teste.com', TRUE),
    (3, 'Pedro Santos', '22222222222', '1988-11-12', '2023-02-20', '11922222222', 'pedro.s@teste.com', TRUE),
    (4, 'Sofia Lima', '33333333333', '1995-03-05', '2023-03-01', '11933333333', 'sofia.l@teste.com', TRUE),
    (5, 'Lucas Pereira', '44444444444', '1991-07-30', '2023-04-15', '11944444444', 'lucas.p@teste.com', TRUE),
    (6, 'Mariana Oliveira', '55555555555', '1994-08-18', '2023-05-22', '11955555555', 'mariana.o@teste.com', TRUE),
    (7, 'Felipe Souza', '66666666666', '1987-04-01', '2023-06-05', '11966666666', 'felipe.s@teste.com', TRUE),
    (8, 'Julia Gomes', '77777777777', '1993-09-23', '2023-07-11', '11977777777', 'julia.g@teste.com', TRUE),
    (9, 'Ricardo Neves', '88888888888', '1985-06-07', '2023-08-08', '11988888888', 'ricardo.n@teste.com', TRUE),
    (10, 'Gabriela Pinto', '99999999999', '1990-12-03', '2023-09-19', '11999999999', 'gabriela.p@teste.com', TRUE),
    (11, 'Bruna Lopes', '10101010101', '1996-02-28', '2024-01-05', '11910101010', 'bruna.l@teste.com', TRUE),
    (12, 'Marcelo Rocha', '12121212121', '1989-10-10', '2024-02-14', '11912121212', 'marcelo.r@teste.com', TRUE),
    (13, 'Carolina Ferraz', '13131313131', '1998-04-04', '2024-03-20', '11913131313', 'carolina.f@teste.com', FALSE), -- Inativa
    (14, 'Guilherme Castro', '14141414141', '1993-01-21', '2024-04-02', '11914141414', 'guilherme.c@teste.com', TRUE),
    (15, 'Isabela Martins', '15151515151', '1986-08-09', '2024-05-30', '11915151515', 'isabela.m@teste.com', TRUE);

-- Inserir médicos
INSERT INTO medico (nome, crm, telefone, email, valorConsultaReferencia, ativo, especialidadeId,
                    duracaoPadraoConsulta, horaInicioExpediente, horaFimExpediente)
VALUES ('Dr. Roberto Santos', '123456SP', '11966664444', 'roberto@teste.com', 300.00, TRUE, 1, 30, '08:00:00',
        '18:00:00'),
       ('Dra. Ana Paula', '654321RJ', '11955553333', 'ana@teste.com', 350.00, TRUE, 2, 30, '08:00:00', '18:00:00'),
       ('Dr. Fernando Costa', '789012MG', '11944445555', 'fernando@teste.com', 250.00, TRUE, 3, 30, '09:00:00',
        '17:00:00'),
       ('Dra. Laura Mendes', '345678BA', '11933336666', 'laura@teste.com', 280.00, TRUE, 4, 30, '08:30:00',
        '16:30:00'),
       ('Dr. Lucas Ferreira', '210987RS', '11922227777', 'lucas@teste.com', 310.00, TRUE, 1, 30, '10:00:00',
        '19:00:00');

--  - Inserir consultas de julho e agosto de 2025.
--
--  Status das consultas:
--   - 'REALIZADA': Valor é calculado com desconto (se houver convênio) ou valor integral.
--   - 'CANCELADA': Não afeta o faturamento, mas demonstra cancelamentos.
--   - 'REMARCADA': Usado para consultas que foram reagendadas.
--
--  Valores de referência:
--   - Dr. Roberto (ID 1): R$ 300.00
--   - Dra. Ana Paula (ID 2): R$ 350.00
--  Descontos dos convênios:
--   - Unimed (ID 1): 50% de desconto -> valor fica R$ 150.00 (Dr. Roberto) ou R$ 175.00 (Dra. Ana)
--   - Amil (ID 2): 70% de desconto -> valor fica R$ 90.00 (Dr. Roberto) ou R$ 105.00 (Dra. Ana)
--   - Bradesco Saúde (ID 3): 60% de desconto -> valor fica R$ 120.00 (Dr. Roberto) ou R$ 140.00 (Dra. Ana)
--

--
-- Consultas de Julho
--

-- Consultas REALIZADAS
INSERT INTO consulta (dataHoraConsulta, status, valor, observacoes, pacienteId, medicoId, formaPagamentoId, convenioId, recepcionistaId)
VALUES
    ('2025-07-01 09:00:00', 'REALIZADA', 150.00, 'Consulta de rotina.', 4, 1, 3, 1, 2), -- Dr. Roberto, Unimed (R$ 300 * 0.5)
    ('2025-07-01 14:00:00', 'REALIZADA', 350.00, NULL, 5, 2, 2, NULL, 3),          -- Dra. Ana, Cartão de Crédito
    ('2025-07-02 10:30:00', 'REALIZADA', 120.00, 'Revisão anual.', 6, 1, 3, 3, 4), -- Dr. Roberto, Bradesco (R$ 300 * 0.4)
    ('2025-07-05 15:00:00', 'REALIZADA', 105.00, NULL, 7, 2, 3, 2, 5),          -- Dra. Ana, Amil (R$ 350 * 0.3)
    ('2025-07-10 11:00:00', 'REALIZADA', 300.00, 'Check-up.', 8, 1, 1, NULL, 6), -- Dr. Roberto, Dinheiro
    ('2025-07-15 16:30:00', 'REALIZADA', 140.00, NULL, 9, 2, 3, 3, 7),          -- Dra. Ana, Bradesco (R$ 350 * 0.4)
    ('2025-07-20 09:30:00', 'REALIZADA', 175.00, 'Retorno.', 10, 2, 3, 1, 8),      -- Dra. Ana, Unimed (R$ 350 * 0.5)
    ('2025-07-25 10:00:00', 'REALIZADA', 90.00, NULL, 11, 1, 3, 2, 9),          -- Dr. Roberto, Amil (R$ 300 * 0.3)
    ('2025-07-28 13:00:00', 'REALIZADA', 300.00, 'Sintomas gripais.', 12, 1, 2, NULL, 10); -- Dr. Roberto, Cartão de Crédito

-- Consultas CANCELADAS
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, recepcionistaId)
VALUES
    ('2025-07-03 11:30:00', 'CANCELADA', 300.00, 13, 1, 1, 11),
    ('2025-07-08 09:00:00', 'CANCELADA', 350.00, 14, 2, 2, 12),
    ('2025-07-12 14:30:00', 'CANCELADA', 150.00, 15, 1, 3, 13); -- Com convênio

-- Consultas REMARCADAS
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, recepcionistaId)
VALUES
    ('2025-07-18 10:00:00', 'REMARCADA', 350.00, 16, 2, 1, 14),
    ('2025-07-22 15:00:00', 'REMARCADA', 300.00, 17, 1, 2, 15);
--
-- Consultas de Agosto
--

-- Consultas REALIZADAS
INSERT INTO consulta (dataHoraConsulta, status, valor, observacoes, pacienteId, medicoId, formaPagamentoId, convenioId, recepcionistaId)
VALUES
    ('2025-08-05 08:30:00', 'REALIZADA', 175.00, 'Consulta de retorno.', 18, 2, 3, 1, 1), -- Dra. Ana, Unimed (R$ 350 * 0.5)
    ('2025-08-08 11:00:00', 'REALIZADA', 300.00, 'Exame de rotina.', 19, 1, 2, NULL, 2), -- Dr. Roberto, Cartão
    ('2025-08-10 13:00:00', 'REALIZADA', 105.00, NULL, 20, 2, 3, 2, 3),          -- Dra. Ana, Amil (R$ 350 * 0.3)
    ('2025-08-12 16:00:00', 'REALIZADA', 120.00, 'Revisão de tratamento.', 21, 1, 3, 3, 4), -- Dr. Roberto, Bradesco (R$ 300 * 0.4)
    ('2025-08-15 10:00:00', 'REALIZADA', 350.00, NULL, 22, 2, 1, NULL, 5),          -- Dra. Ana, Dinheiro
    ('2025-08-18 09:30:00', 'REALIZADA', 150.00, NULL, 23, 1, 3, 1, 6),          -- Dr. Roberto, Unimed (R$ 300 * 0.5)
    ('2025-08-20 14:00:00', 'REALIZADA', 90.00, 'Primeira consulta.', 24, 1, 3, 2, 7), -- Dr. Roberto, Amil (R$ 300 * 0.3)
    ('2025-08-22 15:30:00', 'REALIZADA', 140.00, NULL, 25, 2, 3, 3, 8),          -- Dra. Ana, Bradesco (R$ 350 * 0.4)
    ('2025-08-25 11:00:00', 'REALIZADA', 300.00, NULL, 26, 1, 1, NULL, 9);          -- Dr. Roberto, Dinheiro

-- Consultas CANCELADAS
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, recepcionistaId)
VALUES
    ('2025-08-07 10:00:00', 'CANCELADA', 350.00, 27, 2, 1, 10),
    ('2025-08-11 12:00:00', 'CANCELADA', 300.00, 28, 1, 2, 11),
    ('2025-08-16 15:00:00', 'CANCELADA', 175.00, 29, 2, 3, 12); -- Com convênio

-- Consultas REMARCADAS
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, recepcionistaId)
VALUES
    ('2025-08-09 14:00:00', 'REMARCADA', 300.00, 30, 1, 2, 13),
    ('2025-08-21 10:00:00', 'REMARCADA', 350.00, 4, 2, 1, 14);

-- Novas consultas AGENDADAS para teste de cancelamento (setembro e outubro de 2025)
INSERT INTO consulta (id, dataHoraConsulta, status, valor, observacoes, pacienteId, medicoId, formaPagamentoId, convenioId, recepcionistaId)
VALUES
    (101, '2025-09-01 09:00:00', 'AGENDADA', 300.00, 'Consulta agendada para cancelamento.', 1, 1, 1, NULL, 1),
    (102, '2025-09-05 14:30:00', 'AGENDADA', 175.00, 'Consulta com convênio, agendada para cancelamento.', 2, 2, 3, 1, 2),
    (103, '2025-09-10 10:00:00', 'AGENDADA', 300.00, 'Consulta agendada para cancelamento.', 3, 1, 2, NULL, 3),
    (104, '2025-10-01 11:00:00', 'AGENDADA', 350.00, 'Consulta futura para teste.', 4, 2, 1, NULL, 4),
    (105, '2025-10-15 16:00:00', 'AGENDADA', 90.00, 'Outra consulta futura com convênio.', 5, 1, 3, 2, 5);

-- Consultas para teste de reagendamento (outubro e novembro de 2025)
INSERT INTO consulta (id, dataHoraConsulta, status, valor, observacoes, pacienteId, medicoId, formaPagamentoId, convenioId, recepcionistaId)
VALUES
    (151, '2025-10-10 14:00:00', 'AGENDADA', 250.00, 'Consulta para reagendamento. Paciente Teste 4 com Dr. Fernando.', 4, 3, 2, NULL, 1),
    (152, '2025-10-10 15:00:00', 'AGENDADA', 280.00, 'Consulta para teste de conflito. Paciente Teste 5 com Dra. Laura.', 5, 4, 1, NULL, 2),
    (153, '2025-10-10 16:00:00', 'AGENDADA', 310.00, 'Outra consulta para teste de conflito. Paciente Teste 6 com Dr. Lucas.', 6, 5, 1, NULL, 3),
    (154, '2025-11-05 10:30:00', 'AGENDADA', 140.00, 'Consulta para reagendamento com convênio. Paciente Teste 8 com Dra. Ana Paula.', 8, 2, 3, 3, 4),
    (155, '2025-11-05 11:30:00', 'AGENDADA', 300.00, 'Consulta para teste de reagendamento para o mesmo médico. Paciente Teste 9 com Dr. Roberto.', 9, 1, 1, NULL, 5);

-- Consulta que já passou (para testar a regra de negócio)
INSERT INTO consulta (id, dataHoraConsulta, status, valor, observacoes, pacienteId, medicoId, formaPagamentoId, convenioId, recepcionistaId)
VALUES
    (156, '2025-08-01 10:00:00', 'REALIZADA', 300.00, 'Consulta passada que não pode ser reagendada.', 10, 1, 2, NULL, 6);