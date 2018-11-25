DROP SCHEMA IF EXISTS `db_ntm` ;

-- -----------------------------------------------------
-- Schema db_ntm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_ntm` DEFAULT CHARACTER SET utf8 ;
USE `db_ntm` ;

-- -----------------------------------------------------
-- Table `db_ntm`.`cargo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`cargo` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`cargo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cargo` VARCHAR(60) NOT NULL,
  `descricao` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`usuario` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL UNIQUE,
  `senha` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `sobrenome` VARCHAR(60) NULL DEFAULT NULL,
  `telefone` VARCHAR(15) NULL DEFAULT NULL,
  `celular` VARCHAR(15) NULL DEFAULT NULL,
  `cpf_cnpj` VARCHAR(15) NOT NULL,
  `rg` VARCHAR(12) NOT NULL,
  `observacao` VARCHAR(150) NULL DEFAULT NULL,
  `perfil_acesso` ENUM('analista', 'gestor', 'adm') NOT NULL,
  `cep` VARCHAR(9) NOT NULL,
  `endereco` VARCHAR(100) NOT NULL,
  `numero_endereco` VARCHAR(11) NULL DEFAULT NULL,
  `complemento` VARCHAR(100) NULL DEFAULT NULL,
  `cidade` VARCHAR(45) NULL DEFAULT NULL,
  `uf` CHAR(2) NOT NULL,
  `imagePath` varchar(300) default null,
  `cargo_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_usuario_cargo1_idx` (`cargo_id` ASC),
  index `email_idx`(`email`),
  CONSTRAINT `fk_usuario_cargo1`
    FOREIGN KEY (`cargo_id`)
    REFERENCES `db_ntm`.`cargo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`cliente` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`cliente` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `cpf_cnpj` VARCHAR(15) NOT NULL,
  `telefone` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `observacao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`projeto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`projeto` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`projeto` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `numero_projeto` VARCHAR(20) NOT NULL UNIQUE,
  `nome` VARCHAR(60) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `estimativa_esforco` INT(11) NULL DEFAULT NULL,
  `inicio` DATETIME NULL DEFAULT NULL,
  `fim` DATETIME NULL DEFAULT NULL,
  `status` ENUM('iniciado', 'em andamento', 'finalizado', 'cancelado') NOT NULL,
  `cliente_id` INT(11) NOT NULL,
  `usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_projeto_cliente_idx` (`cliente_id` ASC),
  INDEX `fk_projeto_usuario_idx` (`usuario_id` ASC),
  INDEX `numero_projeto_idx` (`numero_projeto`),
  INDEX `nome_idx` (`nome`),
  CONSTRAINT `fk_projeto_cliente`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `db_ntm`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projeto_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `db_ntm`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`atividade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`atividade` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`atividade` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `descricao` TEXT NOT NULL,
  `complexidade` INT(1) NOT NULL,
  `data_criacao` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `data_entrega` DATE NULL,
  `cep` VARCHAR(10) NULL DEFAULT NULL,
  `endereco` VARCHAR(60) NULL DEFAULT NULL,
  `numero_endereco` VARCHAR(11) NULL DEFAULT NULL,
  `complemento` VARCHAR(100) NULL DEFAULT NULL,
  `cidade` VARCHAR(60) NULL DEFAULT NULL,
  `uf` CHAR(2) NULL DEFAULT NULL,
  `status` ENUM('pendente','iniciada','pausada', 'cancelada', 'finalizada') NULL DEFAULT 'pendente',
  `projeto_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_atividade_projeto_idx` (`projeto_id` ASC),
  CONSTRAINT `fk_atividade_projeto`
    FOREIGN KEY (`projeto_id`)
    REFERENCES `db_ntm`.`projeto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`atividade_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`atividade_usuario` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`atividade_usuario` (
  `atividade_id` INT(11) NOT NULL,
  `usuario_id` INT(11) NOT NULL,
  `status`  INTEGER NOT NULL default 1,
  PRIMARY KEY (`atividade_id`, `usuario_id`),
  INDEX `fk_atividade_usuario_idx` (`usuario_id` ASC),
  INDEX `fk_atividade_usuario_atividade_idx` (`atividade_id` ASC),
  CONSTRAINT `fk_atividade_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `db_ntm`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_atividade_usuario_atividade`
    FOREIGN KEY (`atividade_id`)
    REFERENCES `db_ntm`.`atividade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`anexo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`anexo` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`anexo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `local_armazenamento` VARCHAR(300) NOT NULL,
  `nome_arquivo` VARCHAR(255) NOT NULL,
  `descricao` VARCHAR(300) NOT NULL,
  `extensao` varchar(10) NULL DEFAULT NULL,
  `data_insercao` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `tamanho` LONG NULL DEFAULT NULL,
  `atividade_usuario_atividade_id` INT(11) NOT NULL,
  `atividade_usuario_usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_anexo_atividade_usuario_idx` (`atividade_usuario_atividade_id` ASC, `atividade_usuario_usuario_id` ASC),
  CONSTRAINT `fk_anexo_atividade_usuario`
    FOREIGN KEY (`atividade_usuario_atividade_id` , `atividade_usuario_usuario_id`)
    REFERENCES `db_ntm`.`atividade_usuario` (`atividade_id` , `usuario_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`comentario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`comentario` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`comentario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `comentario` VARCHAR(255) NOT NULL,
  `data_comentario` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `atividade_usuario_atividade_id` INT(11) NOT NULL,
  `atividade_usuario_usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comentario_atividade_usuario_idx` (`atividade_usuario_atividade_id` ASC, `atividade_usuario_usuario_id` ASC),
  CONSTRAINT `fk_comentario_atividade_usuario`
    FOREIGN KEY (`atividade_usuario_atividade_id` , `atividade_usuario_usuario_id`)
    REFERENCES `db_ntm`.`atividade_usuario` (`atividade_id` , `usuario_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`habilidade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`habilidade` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`habilidade` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`habilidade_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`habilidade_usuario` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`habilidade_usuario` (
  `habilidade_id` INT(11) NOT NULL,
  `usuario_id` INT(11) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `nivel` ENUM('tecnico', 'junior', 'pleno', 'senior') NOT NULL,
  PRIMARY KEY (`habilidade_id`, `usuario_id`),
  INDEX `fk_habilidade_usuario_idx` (`usuario_id` ASC),
  INDEX `fk_habilidade_usuario_habilidade_idx` (`habilidade_id` ASC),
  CONSTRAINT `fk_habilidade_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `db_ntm`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_habilidade_usuario_habilidade`
    FOREIGN KEY (`habilidade_id`)
    REFERENCES `db_ntm`.`habilidade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`historico_alocacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`historico_alocacao` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`historico_alocacao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `status` INT(1) NOT NULL,
  `data_alteracao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `atividade_usuario_atividade_id` INT(11) NOT NULL,
  `atividade_usuario_usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_historico_alocacao_atividade_usuario_idx` (`atividade_usuario_atividade_id` ASC, `atividade_usuario_usuario_id` ASC),
  CONSTRAINT `fk_historico_alocacao_atividade_usuario`
    FOREIGN KEY (`atividade_usuario_atividade_id` , `atividade_usuario_usuario_id`)
    REFERENCES `db_ntm`.`atividade_usuario` (`atividade_id` , `usuario_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `db_ntm`.`horario_trabalho`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_ntm`.`horario_trabalho` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`horario_trabalho` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `data_inicio` timestamp NULL DEFAULT NULL,
  `data_fim` timestamp NULL DEFAULT NULL,
  `latitude` VARCHAR(45) NULL DEFAULT NULL,
  `longitude` VARCHAR(45) NULL DEFAULT NULL,
  `atividade_usuario_atividade_id` INT(11) NOT NULL,
  `atividade_usuario_usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_horario_atividade_usuario_idx` (`atividade_usuario_atividade_id` ASC, `atividade_usuario_usuario_id` ASC),
  CONSTRAINT `fk_horario_atividade_usuario`
    FOREIGN KEY (`atividade_usuario_atividade_id` , `atividade_usuario_usuario_id`)
    REFERENCES `db_ntm`.`atividade_usuario` (`atividade_id` , `usuario_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

create table convite(
	id Integer primary key auto_increment not null,
    email varchar(100) not null,
    nivel_acesso varchar(100) not null,
    cargo integer not null,
    usado boolean default false,
    data_convite timestamp default current_timestamp,
    id_usuario integer
);

-- -----------------------------------------------------
-- Table `db_ntm`.`token`
-- -----------------------------------------------------
/*
DROP TABLE IF EXISTS `db_ntm`.`token` ;

CREATE TABLE IF NOT EXISTS `db_ntm`.`token` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(255) NOT NULL,
  `data_geracao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_token_usuario_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_token_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `db_ntm`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
*/

/*describe cargo;

describe cliente;
describe usuario;
describe habilidade;

*/

#Insert Cargo
insert into cargo (cargo) values ('Analista Júnior');
insert into cargo (cargo) values ('Analista Senior');
insert into cargo (cargo) values ('Analista Pleno');
insert into cargo (cargo) values ('Gerente de Projetos');
insert into cargo (cargo) values ('Administrador de Projetos');

#Insert Clientes
insert into cliente (nome, cpf_cnpj, telefone, email, observacao) values ('Cartórios de Protesto - IEPTB-SP', '111111111000100', '(11) 3242-2008', 'ieptb-spc@cartorio.com.br', 'É um fato conhecido de todos que um leitor se distrairá com o conteúdo de texto legível de uma página quando estiver examinando sua diagramação. A vantagem de usar Lorem Ipsum é que ele tem uma distribuição normal de letras,');
insert into cliente (nome, cpf_cnpj, telefone, email, observacao) values ('7º Cartório de Registro Cívil', '999999911111', '(11) 3881-4556', 'registrocivil@cartorio.com.br', 'É um fato conhecido de todos que um leitor se distrairá com o conteúdo de texto legível de uma página quando estiver examinando sua diagramação. A vantagem de usar Lorem Ipsum é que ele tem uma distribuição normal de letras,');
insert into cliente (nome, cpf_cnpj, telefone, email, observacao) values ('Cartório de Registro de Imóveis de São Paulo', '888888811111', '(11) 3779-0000', 'registroimoveis@cartorio.com.br', 'É um fato conhecido de todos que um leitor se distrairá com o conteúdo de texto legível de uma página quando estiver examinando sua diagramação. A vantagem de usar Lorem Ipsum é que ele tem uma distribuição normal de letras,');
insert into cliente (nome, cpf_cnpj, telefone, email, observacao) values ('Cartorio De Notas De Osasco', '888888811111', '(11) 3779-0000', 'notasdeosascos@cartorio.com.br', 'É um fato conhecido de todos que um leitor se distrairá com o conteúdo de texto legível de uma página quando estiver examinando sua diagramação. A vantagem de usar Lorem Ipsum é que ele tem uma distribuição normal de letras,');
insert into cliente (nome, cpf_cnpj, telefone, email, observacao) values ('Tabelionato de Notas da Capital', '777777711111', '(11) 3056-6766', 'tabelionato@cartorio.com.br', 'É um fato conhecido de todos que um leitor se distrairá com o conteúdo de texto legível de uma página quando estiver examinando sua diagramação. A vantagem de usar Lorem Ipsum é que ele tem uma distribuição normal de letras,');


#Insert Habilidades
insert into habilidade (nome) values ('Java Web');
insert into habilidade (nome) values ('Java');
insert into habilidade (nome) values ('Python');
insert into habilidade (nome) values ('PHP');
insert into habilidade (nome) values ('HTML');
insert into habilidade (nome) values ('CSS');
insert into habilidade (nome) values ('JavaScript');
insert into habilidade (nome) values ('C');
insert into habilidade (nome) values ('C++');
insert into habilidade (nome) values ('C#');
insert into habilidade (nome) values ('.NET');
insert into habilidade (nome) values ('SQL');
insert into habilidade (nome) values ('MYSQL');
insert into habilidade (nome) values ('PostgreSQL');


#Insert Usuarios
insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('renan.gouveia@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Renan', 'Gouveia', '(11) 7070-7070', '(11) 97070-7070', '00000000010', '33333333-3', 'TESTE DO ANALISTA', 'analista', '00000-000', 'Rua Analista', 	'123', 'Perto da casa do Analista', 'São Paulo', 'SP', 1);
#Vinculando Habilidades
insert habilidade_usuario value(1, 1, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'tecnico');
insert habilidade_usuario value(2, 1, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'junior');
insert habilidade_usuario value(3, 1, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'junior');

insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('rodrigo.santos@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Rodrigo', 'Santos', '(11) 7070-7070', '(11) 97070-7070', '00017000010', '33333333-3', 'TESTE DO ANALISTA', 'analista', '00000-000', 'Rua Analista', 	'123', 'Perto da casa do Analista', 'São Paulo', 'SP', 1);
#Vinculando Habilidades
insert habilidade_usuario value(8, 2, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'tecnico');
insert habilidade_usuario value(9, 2, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'junior');
insert habilidade_usuario value(5, 2, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'junior');


insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('guilherme.brito@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Guilherme', 'Brito', '(21) 8080-8080', '(21) 98080-8080', '00000000011', '44444444-4', 'TESTE DO ANALISTA', 'analista', '00000-000', 'Rua Analista', 	'123', 'Perto da casa do Analista', 'Rio de Janeiro', 'RJ', 2);
#Vinculando Habilidades
insert habilidade_usuario value(4, 3, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'tecnico');
insert habilidade_usuario value(5, 3, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'pleno');
insert habilidade_usuario value(6, 3, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'senior');

insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('jorge.sampaio@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Jorge', 'Sampaio', '(21) 8080-8080', '(21) 98080-8080', '00000012011', '44444444-4', 'TESTE DO ANALISTA', 'analista', '00000-000', 'Rua Analista', 	'123', 'Perto da casa do Analista', 'Rio de Janeiro', 'RJ', 2);
#Vinculando Habilidades
insert habilidade_usuario value(13,4, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'tecnico');
insert habilidade_usuario value(1, 4, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'pleno');
insert habilidade_usuario value(2, 4, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'senior');


insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('leonardo.siqueira@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Leonardo', 'Siqueira', '(11) 9090-9090', '(11) 99090-9090', '00000000012', '55555555-5', 'TESTE DO ANALISTA', 'analista', '00000-000', 'Rua Analista', 	'123', 'Perto da casa do Analista', 'Minas Gerais', 'MG', 3);
#Vinculando Habilidades
insert habilidade_usuario value(7, 5, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'tecnico');
insert habilidade_usuario value(8, 5, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'pleno');
insert habilidade_usuario value(9, 5, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'senior');

insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('fabricio.caires@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Fabrício', 'Caires', '(11) 9090-9090', '(11) 99090-9090', '000000000147', '55555555-5', 'TESTE DO ANALISTA', 'analista', '00000-000', 'Rua Analista', 	'123', 'Perto da casa do Analista', 'Minas Gerais', 'MG', 3);
#Vinculando Habilidades
insert habilidade_usuario value(10, 6, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'junior');
insert habilidade_usuario value(11, 6, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'pleno');
insert habilidade_usuario value(12, 6, 'Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos', 'senior');

insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('rodrigo.bossini@nilone.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Rodrigo', 'Bossini', '(11) 1010-1010', '(11) 91010-1010', '00000000013', '66666666-6', 'TESTE DO GERENTE', 'gestor', '00000-000', 'Rua Gestor', 	'123', 'Perto da casa do Gestor', 'São Paulo', 'SP', 4);

insert into usuario (email, senha, nome, sobrenome, telefone, celular, cpf_cnpj, rg, observacao, perfil_acesso, cep, endereco, numero_endereco, complemento, cidade, uf, cargo_id)
values ('admin@empresa.com.br', '$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu', 'Admin', 'Projeto', '(11) 1010-1010', '(11) 91010-1010', '00000000013', '66666666-6', 'TESTE DO ADMIN', 'adm', '00000-000', 'Rua Gestor', 	'123', 'Perto da casa do Gestor', 'São Paulo', 'SP', 5);



#Insert Projetos
insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000001', 'Projeto Teste INC.', 'Projeto da empresa 1, software Web.', 10, '2018-09-04', 'finalizado', 1, 1);
insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000002', 'Projeto Teste INC.', 'Projeto da empresa 1, software Web.', 10, '2018-09-04', 'finalizado', 1, 1);

insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000003', 'Projeto Teste INC.', 'Projeto da empresa 2, software Web.', 10, '2018-09-04', 'finalizado', 2, 1);
insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000004', 'Projeto Teste INC.', 'Projeto da empresa 2, software Web.', 10, '2018-09-04', 'finalizado', 2, 1);

insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000005', 'Projeto Teste INC.', 'Projeto da empresa 3, software Web.', 10, '2018-09-10', 'iniciado', 3, 1);
insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000006', 'Projeto Teste INC.', 'Projeto da empresa 3, software Web.', 10, '2018-09-04', 'finalizado', 3, 1);

insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000007', 'Projeto Teste INC.', 'Projeto da empresa 4, software Web.', 10, '2018-09-04', 'finalizado', 4, 1);
insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000008', 'Projeto Teste INC.', 'Projeto da empresa 4, software Web.', 10, '2018-09-04', 'finalizado', 4, 1);

insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000009', 'Projeto Teste INC.', 'Projeto da empresa 5, software Web.', 10, '2018-09-04', 'finalizado', 5, 1);
insert into projeto (numero_projeto, nome, descricao, estimativa_esforco, inicio, status, cliente_id, usuario_id)
values ('00000000010', 'Projeto Teste INC.', 'Projeto da empresa 5, software Web.', 10, '2018-09-04', 'finalizado', 5, 1);



#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 1);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 1);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 1);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 1);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 1);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 2);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 2);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 2);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 2);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 2);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 3);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 3);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 3);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 3);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +1 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 3);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 4);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 4);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 4);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 4);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 4);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 5);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 5);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 5);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 5);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +2 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 5);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 6);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 6);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 6);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 6);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 6);


#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 7);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 7);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 7);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 7);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 7);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 8);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 8);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 8);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 8);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 8);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 9);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 9);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 9);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 9);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 9);

#Insert Atividades
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Menu', 'O menu deverá ser criado...', 1, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 10);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Login', 'O menu deverá ser criado...', 2, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 10);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Home', 'O menu deverá ser criado...', 3, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 10);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Criar Lista', 'O menu deverá ser criado...', 4, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 10);
insert into atividade (nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, cidade, uf, status, projeto_id)
values ('Funcao Envio de Email', 'O menu deverá ser criado...', 5, '2018-09-08 19:00:00', date_add(now(), INTERVAL +3 MONTH), '00000-000', 'Avenida Vital Brasil', '1000', 'São Paulo', 'SP', 'iniciada', 10);

#Vincular Usuarios a atividades `atividade_id`,`usuario_id`
insert atividade_usuario(atividade_id, usuario_id) values(1,1);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 1, 1);
insert atividade_usuario(atividade_id, usuario_id) values(2,1);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 2, 1);
insert atividade_usuario(atividade_id, usuario_id) values(3,1);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 3, 1);
insert atividade_usuario(atividade_id, usuario_id) values(4,1);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 4, 1);
insert atividade_usuario(atividade_id, usuario_id) values(5,1);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 5, 1);

insert atividade_usuario(atividade_id, usuario_id) values(6,1);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 6, 1);
insert atividade_usuario(atividade_id, usuario_id) values(6,2);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(0, 6, 2);
insert atividade_usuario(atividade_id, usuario_id) values(7,2);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 7, 2);
insert atividade_usuario(atividade_id, usuario_id) values(8,2);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 8, 2);
insert atividade_usuario(atividade_id, usuario_id) values(9,2);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 9, 2);
insert atividade_usuario(atividade_id, usuario_id) values(10,2);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 10, 2);


insert atividade_usuario(atividade_id, usuario_id) values(11,2);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 11, 2);
insert atividade_usuario(atividade_id, usuario_id) values(11,3);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 11, 3);
insert atividade_usuario(atividade_id, usuario_id) values(12,3);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 12, 3);
insert atividade_usuario(atividade_id, usuario_id) values(13,3);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 13, 3);
insert atividade_usuario(atividade_id, usuario_id) values(14,3);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 14, 3);
insert atividade_usuario(atividade_id, usuario_id) values(15,3);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 15, 3);

insert atividade_usuario(atividade_id, usuario_id) values(16,3);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 16, 3);
insert atividade_usuario(atividade_id, usuario_id) values(16,4);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 16, 4);
insert atividade_usuario(atividade_id, usuario_id) values(17,4);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 17, 4);
insert atividade_usuario(atividade_id, usuario_id) values(18,4);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 18, 4);
insert atividade_usuario(atividade_id, usuario_id) values(19,4);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 19, 4);
insert atividade_usuario(atividade_id, usuario_id) values(20,4);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 20, 4);

insert atividade_usuario(atividade_id, usuario_id) values(21,4);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 21, 4);
insert atividade_usuario(atividade_id, usuario_id) values(21,5);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 21, 5);
insert atividade_usuario(atividade_id, usuario_id) values(22,5);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 22, 5);
insert atividade_usuario(atividade_id, usuario_id) values(23,5);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 23, 5);
insert atividade_usuario(atividade_id, usuario_id) values(24,5);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 24, 5);
insert atividade_usuario(atividade_id, usuario_id) values(25,5);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 25, 5);

insert atividade_usuario(atividade_id, usuario_id) values(26,5);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 26, 5);
insert atividade_usuario(atividade_id, usuario_id) values(26,6);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 26, 6);
insert atividade_usuario(atividade_id, usuario_id) values(27,6);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 27, 6);
insert atividade_usuario(atividade_id, usuario_id) values(28,6);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 28, 6);
insert atividade_usuario(atividade_id, usuario_id) values(29,6);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 29, 6);
insert atividade_usuario(atividade_id, usuario_id) values(30,6);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 30, 6);

insert atividade_usuario(atividade_id, usuario_id) values(31,6);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 31, 6);
insert atividade_usuario(atividade_id, usuario_id) values(31,7);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 31, 7);
insert atividade_usuario(atividade_id, usuario_id) values(32,7);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 32, 7);
insert atividade_usuario(atividade_id, usuario_id) values(33,7);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 33, 7);
insert atividade_usuario(atividade_id, usuario_id) values(34,7);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 34, 7);
insert atividade_usuario(atividade_id, usuario_id) values(35,7);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 35, 7);

insert atividade_usuario(atividade_id, usuario_id) values(36,7);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 36, 7);
insert atividade_usuario(atividade_id, usuario_id) values(36,8);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 36, 8);
insert atividade_usuario(atividade_id, usuario_id) values(37,8);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 37, 8);
insert atividade_usuario(atividade_id, usuario_id) values(38,8);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 38, 8);
insert atividade_usuario(atividade_id, usuario_id) values(39,8);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 39, 8);
insert atividade_usuario(atividade_id, usuario_id) values(40,8);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 40, 8);
insert atividade_usuario(atividade_id, usuario_id) values(1,8);
#historico alocacao status - idatividade - idusuario
insert historico_alocacao(status,atividade_usuario_atividade_id, atividade_usuario_usuario_id) value(1, 1, 8);


#insert Comentario
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 1, 1);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 2, 1);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 3, 1);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 4, 1);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 5, 1);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 6, 2);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 7, 2);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 8, 2);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 9, 2);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 10, 2);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 11, 3);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 12, 3);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 13, 3);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 14, 3);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 15, 3);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 16, 4);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 17, 4);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 18, 4);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 19, 4);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 20, 4);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 21, 5);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 22, 5);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 23, 5);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 24, 5);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 25, 5);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 26, 6);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 27, 6);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 28, 6);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 29, 6);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 30, 6);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 31, 7);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 32, 7);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 33, 7);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 34, 7);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 35, 7);

insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 36, 8);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 37, 8);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 38, 8);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 39, 8);
insert comentario(comentario,atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('Ao contrário do que se acredita, Lorem Ipsum não é simplesmente um texto randômico. Com mais de 2000 anos, suas', 40, 8);


#desc anexo;
#insert Anexos

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 1,1, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 2,1, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 3,1, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 4,1, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 5,1, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 6,2, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 7,2, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 8,2, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 9,2, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 10,2, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 11,3, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 12,3, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 13,3, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 14,3, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 15,3, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 16,4, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 17,4, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 18,4, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 19,4, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 20,4, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 21,5, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 22,5, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 23,5, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 24,5, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 25,5, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 26,6, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 27,6, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 28,6, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 29,6, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 30,6, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 31,7, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 32,7, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 33,7, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 34,7, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 35,7, 'codigoFonte.pdf', 'Descrição do anexo');

insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 36,8, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 37,8, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 38,8, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 39,8, 'codigoFonte.pdf', 'Descrição do anexo');
insert anexo(local_armazenamento,tamanho,atividade_usuario_atividade_id, atividade_usuario_usuario_id, nome_arquivo, descricao)
value('C:/meu documentos', 12348, 40,8, 'codigoFonte.pdf', 'Descrição do anexo');


#desc horario_trabalho;
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 1,1);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 2,1);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 3,1);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 4,1);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 5,1);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 6,2);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 7,2);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 8,2);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 9,2);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 10,2);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 11,3);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 12,3);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 13,3);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 14,3);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 15,3);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 16,4);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 17,4);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 18,4);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 19,4);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 20,4);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 21,5);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 22,5);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 23,5);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 24,5);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 25,5);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 26,6);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 27,6);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 28,6);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 29,6);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 30,6);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 31,7);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 32,7);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 33,7);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 34,7);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 35,7);

insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-27 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 36,8);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-10 09:00:00', '2018-09-15 17:00:00', '-23.53', '-46.79', 37,8);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-15 09:00:00', '2018-09-27 17:00:00', '-23.53', '-46.79', 38,8);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-01 09:00:00', '2018-09-09 17:00:00', '-23.53', '-46.79', 39,8);
insert horario_trabalho(data_inicio, data_fim, latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id)
value('2018-09-20 09:00:00', '2018-09-25 17:00:00', '-23.53', '-46.79', 40,8);