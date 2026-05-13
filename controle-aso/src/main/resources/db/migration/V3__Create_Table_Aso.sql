CREATE TABLE IF NOT EXISTS `aso` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `crm_medico` varchar(50) NOT NULL,
  `data_emissao` date NOT NULL,
  `data_validade` date NOT NULL,
  `descricao_exame` varchar(1000) DEFAULT NULL,
  `nome_medico` varchar(255) NOT NULL,
  `resultado_aso` enum('APTO','INAPTO') NOT NULL,
  `tipo_aso` enum('ADMISSIONAL','DEMISSIONAL','MUDANCA_FUNCAO','PERIODICO','RETORNO_TRABALHO') NOT NULL,
  `url_documento` varchar(500) NOT NULL,
  `funcionario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl7aoit2pd10a4ueou92f16ppc` (`funcionario_id`),
  CONSTRAINT `FKl7aoit2pd10a4ueou92f16ppc` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`)
)
