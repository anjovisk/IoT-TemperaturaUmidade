CREATE TABLE `parametros` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) NOT NULL,
  `valor` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tipo_UNIQUE` (`tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
