CREATE TABLE `historico_temperatura_umidade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` timestamp NOT NULL,
  `temperatura` decimal(5,2) NOT NULL,
  `umidade` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_data` (`data`) /*!80000 INVISIBLE */,
  KEY `idx_temperatura` (`temperatura`),
  KEY `idx_umidade` (`umidade`)
) ENGINE=InnoDB AUTO_INCREMENT=14447 DEFAULT CHARSET=utf8;
