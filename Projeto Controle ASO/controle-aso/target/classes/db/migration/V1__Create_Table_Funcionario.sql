CREATE TABLE IF NOT EXISTS `funcionario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `matricula` varchar(10) NOT NULL,
  `data_nascimento` date NOT NULL,
  `genero_biologico` enum('FEMININO','MASCULINO','NAO_INFORMADO') NOT NULL,
  `setor` enum('ADMINISTRACAO','ARQUITETURA','ENGENHARIA','LIMPEZA','OPERACIONAL','RECURSOS_HUMANOS','SAUDE_TRABALHO','TECNOLOGIA_INFORMACAO') NOT NULL,
  `cargo` enum('ARQUITETO','AUXILIAR','DIRETOR','ENGENHEIRO','ESTAGIARIO','MEDICO','OPERARIO','SECRETARIO','SUPERVISOR','TECNICO') NOT NULL,
  `data_admissao` date NOT NULL,
  `data_demissao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrxosr8731eb3gbnlbt2mqfan8` (`cpf`),
  UNIQUE KEY `UK3uda6owswwy94ktwvq5uhifx1` (`matricula`)
);
