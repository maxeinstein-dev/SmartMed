CREATE TABLE IF NOT EXISTS especialidade
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(64) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS formaPagamento
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS convenio
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    nome                 VARCHAR(255)         NOT NULL,
    cnpj                 CHAR(14)             NOT NULL,
    telefone             CHAR(11)             NOT NULL,
    email                VARCHAR(64)          NOT NULL,
    ativo                BOOLEAN DEFAULT TRUE NOT NULL,
    porcentagemDesconto DECIMAL(3, 2)        NOT NULL
);

CREATE TABLE IF NOT EXISTS paciente
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(255) NOT NULL,
    cpf             CHAR(11)     NOT NULL,
    dataNascimento DATE         NOT NULL,
    telefone        CHAR(11)     NOT NULL,
    email           VARCHAR(64),
    ativo           BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS recepcionista
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(255)         NOT NULL,
    cpf             CHAR(11)             NOT NULL,
    dataNascimento DATE                 NOT NULL,
    dataAdmissao   DATE                 NOT NULL,
    dataDemissao   DATE,
    telefone        CHAR(11)             NOT NULL,
    email           VARCHAR(64)          NOT NULL,
    ativo           BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS medico
(
    id                        INT AUTO_INCREMENT PRIMARY KEY,
    nome                      VARCHAR(255)               NOT NULL,
    crm                       CHAR(8)                    NOT NULL,
    telefone                  CHAR(11)                   NOT NULL,
    email                     VARCHAR(64),
    valorConsultaReferencia DOUBLE PRECISION           NOT NULL,
    ativo                     BOOLEAN DEFAULT TRUE       NOT NULL,
    especialidadeId         INT                        NOT NULL,
    duracaoPadraoConsulta   INT     DEFAULT 30         NOT NULL,
    horaInicioExpediente    TIME    DEFAULT '08:00:00' NOT NULL,
    horaFimExpediente       TIME    DEFAULT '18:00:00' NOT NULL,
    FOREIGN KEY (especialidadeId) REFERENCES especialidade (id)
);

CREATE TABLE IF NOT EXISTS consulta
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    dataHoraConsulta TIMESTAMP        NOT NULL,
    status             CHAR(16)         NOT NULL,
    valor              DOUBLE PRECISION NOT NULL,
    observacoes        VARCHAR(1024),
    pacienteId        INT              NOT NULL,
    medicoId          INT              NOT NULL,
    formaPagamentoId INT,
    convenioId       INT,
    recepcionistaId   INT              NOT NULL,
    FOREIGN KEY (pacienteId) REFERENCES paciente (id),
    FOREIGN KEY (medicoId) REFERENCES medico (id),
    FOREIGN KEY (formaPagamentoId) REFERENCES formaPagamento (id),
    FOREIGN KEY (convenioId) REFERENCES convenio (id),
    FOREIGN KEY (recepcionistaId) REFERENCES recepcionista (id)
);

-- Inserir especialidades
INSERT INTO especialidade (nome, descricao)
VALUES ('Cardiologia', 'Especialidade médica que trata do coração'),
       ('Dermatologia', 'Especialidade médica que trata da pele');

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
       ('Maria Souza', '98765432109', '1975-05-20', '11988882222', 'maria@teste.com', TRUE);
INSERT INTO paciente (nome, cpf, dataNascimento, telefone, email, ativo)
VALUES ('Paciente Inativo', '11122233344', '1995-10-25', '11911112222', 'inativo@teste.com', FALSE);


-- Inserir recepcionistas
INSERT INTO recepcionista (nome, cpf, dataNascimento, dataAdmissao, telefone, email, ativo)
VALUES ('Carlos Andrade', '45678912301', '1990-02-10', '2020-01-15', '11977773333', 'carlos@teste.com', TRUE);

-- Inserir médicos
INSERT INTO medico (nome, crm, telefone, email, valorConsultaReferencia, ativo, especialidadeId,
                    duracaoPadraoConsulta, horaInicioExpediente, horaFimExpediente)
VALUES ('Dr. Roberto Santos', '123456SP', '11966664444', 'roberto@teste.com', 300.00, TRUE, 1, 30, '08:00:00',
        '18:00:00'),
       ('Dra. Ana Paula', '654321RJ', '11955553333', 'ana@teste.com', 350.00, TRUE, 2, 30, '08:00:00', '18:00:00');

-- Inserir consultas REALIZADAS (para aparecer no relatório)
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, convenioId,
                      recepcionistaId)
VALUES ('2025-01-10 09:00:00', 'REALIZADA', 150.00, 1, 1, 3, 1, 1),
       ('2025-01-15 10:00:00', 'REALIZADA', 105.00, 2, 2, 3, 2, 1),
       ('2025-01-20 11:00:00', 'REALIZADA', 350.00, 1, 2, 1, NULL, 1),
       ('2025-01-25 14:00:00', 'REALIZADA', 210.00, 2, 1, 3, 3, 1);

-- Inserir consultas CANCELADAS (não devem aparecer no relatório)
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, convenioId,
                      recepcionistaId)
VALUES ('2025-01-12 09:00:00', 'CANCELADA', 150.00, 1, 1, 3, 1, 1),
       ('2025-01-18 10:00:00', 'CANCELADA', 105.00, 2, 2, 3, 2, 1);

-- Novas consultas para testar a agenda do Dr. Roberto Santos (ID 1) em 2025-08-01
INSERT INTO consulta (dataHoraConsulta, status, valor, pacienteId, medicoId, formaPagamentoId, convenioId, recepcionistaId)
VALUES ('2025-08-01 08:00:00', 'AGENDADA', 300.00, 1, 1, 1, NULL, 1), -- Ocupado
       ('2025-08-01 08:30:00', 'AGENDADA', 300.00, 2, 1, 1, NULL, 1), -- Ocupado
       ('2025-08-01 10:00:00', 'AGENDADA', 150.00, 1, 1, 3, 1, 1),  -- Ocupado (com convênio)
       ('2025-08-01 11:30:00', 'AGENDADA', 300.00, 2, 1, 2, NULL, 1); -- Ocupado