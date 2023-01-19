# create database
CREATE DATABASE `imrelax` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `tb_wikidata_train_docinfo` (
  `docid` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `uri` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`docid`),
  KEY `uri_index` (`uri`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COMMENT='train data';

# entities with boundary info
CREATE TABLE `tb_wikidata_train_entities` (
  `seq` int NOT NULL AUTO_INCREMENT,
  `uri` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `boundaries_st` int DEFAULT NULL,
  `boundaries_ed` int DEFAULT NULL,
  `boundaries` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `surfaceform` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `annotator` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `uri_index` (`uri`)
) ENGINE=MyISAM AUTO_INCREMENT=37182818 DEFAULT CHARSET=utf8mb3;

# delete boundary info
CREATE TABLE `tb_wikidata_train_entities_001` (
  `seq` int NOT NULL AUTO_INCREMENT,
  `uri` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `surfaceform` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `annotator` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `uri_index` (`uri`)
) ENGINE=MyISAM AUTO_INCREMENT=37182818 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `tb_wikidata_train_relation` (
  `seq` int NOT NULL AUTO_INCREMENT,
  `uri` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `surfaceform` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `annotator` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `uri_index` (`uri`)
) ENGINE=MyISAM AUTO_INCREMENT=1150 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `tb_wikidata_train_triples` (
  `seq` int NOT NULL AUTO_INCREMENT,
  `docid` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `subject` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `object` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `predicate` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sentence_id` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dependency_path` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `confidence` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `annotator` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`seq`),
  KEY `docid_index` (`docid`) /*!80000 INVISIBLE */,
  KEY `object_index` (`predicate`),
  KEY `subject_index` (`subject`)
) ENGINE=MyISAM AUTO_INCREMENT=9282838 DEFAULT CHARSET=utf8mb3;
