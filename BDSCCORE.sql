-- MySQL dump 10.19  Distrib 10.3.38-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: bdvacia
-- ------------------------------------------------------
-- Server version	10.3.38-MariaDB-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sc_adjuntos`
--

DROP TABLE IF EXISTS `sc_adjuntos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_adjuntos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idquery` int(11) NOT NULL DEFAULT 0,
  `iddato` int(10) unsigned NOT NULL,
  `nota` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `color` varchar(8) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `adjunto1` varchar(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `adjunto2` varchar(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `usuario` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_sc_adjuntos` (`idquery`,`iddato`)
) ENGINE=InnoDB AUTO_INCREMENT=663 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_adjuntos`
--

LOCK TABLES `sc_adjuntos` WRITE;
/*!40000 ALTER TABLE `sc_adjuntos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_adjuntos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_conexiones_bd`
--

DROP TABLE IF EXISTS `sc_conexiones_bd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_conexiones_bd` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `servidor` varchar(80) NOT NULL,
  `usuario` varchar(60) NOT NULL,
  `clave` varchar(60) NOT NULL,
  `base` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_conexiones_bd` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_conexiones_bd`
--

LOCK TABLES `sc_conexiones_bd` WRITE;
/*!40000 ALTER TABLE `sc_conexiones_bd` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_conexiones_bd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_fields`
--

DROP TABLE IF EXISTS `sc_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_fields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idquery` int(11) DEFAULT NULL,
  `field_` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `show_name` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `is_required` tinyint(3) unsigned DEFAULT 0,
  `password_field` tinyint(3) unsigned DEFAULT NULL,
  `file_field` tinyint(3) unsigned DEFAULT NULL,
  `color_field` tinyint(3) unsigned DEFAULT NULL,
  `rich_text` tinyint(3) unsigned DEFAULT NULL,
  `default_value_exp` varchar(80) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `prefix_field` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `is_google_point` tinyint(3) unsigned DEFAULT 0,
  `example` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `is_editable` tinyint(3) unsigned NOT NULL DEFAULT 1,
  `visible` tinyint(3) unsigned NOT NULL DEFAULT 1,
  `ocultar_vacio` tinyint(3) unsigned DEFAULT 0,
  `encriptado` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `class` varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `grupo` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `subgrupo` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `field_help` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `ancho` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_fields` (`idquery`,`field_`),
  CONSTRAINT `FK_sc_fields_sc_querys_idquery` FOREIGN KEY (`idquery`) REFERENCES `sc_querys` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5230 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_fields`
--

LOCK TABLES `sc_fields` WRITE;
/*!40000 ALTER TABLE `sc_fields` DISABLE KEYS */;
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1,86,'idItemMenu',' ItemMenu',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2,86,'Item','Item',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4,86,'url','url',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5,86,'target','target',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (6,86,'activo','activo',0,0,0,0,0,'1','',0,'',1,1,0,0,'','','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (7,86,'orden','orden',0,NULL,NULL,NULL,NULL,'5',NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (37,13,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (38,13,'idquery',' query',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (39,13,'field_','field ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (40,13,'show_name','show name',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (41,13,'is_required','is required',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (42,13,'password_field','password field',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (43,13,'file_field','file field',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (44,13,'color_field','color field',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (45,13,'rich_text','rich text',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (46,13,'default_value_exp','default value exp',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (47,13,'prefix_field','prefix field',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (48,13,'is_google_point','is google point',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (54,86,'icon','icon',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (55,37,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (56,37,'queryname','queryname',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (57,37,'querydescription','nombre',0,0,0,0,0,'','',0,'',1,1,0,0,'','','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (58,37,'table_','table ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (59,37,'fields_','fields ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (60,37,'combofield_','combofield ',1,0,0,0,0,'','',0,'',1,1,0,0,'','',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (62,37,'keyfield_','keyfield ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (63,37,'whereexp','whereexp',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (65,37,'order_by','order by',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (66,37,'caninsert','caninsert',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (67,37,'canedit','canedit',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (68,37,'candelete','candelete',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (74,37,'idmenu',' menu',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (75,37,'icon','icon',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (76,114,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (77,114,'fecha','fecha',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'Estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (78,114,'idusuario',' usuario',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (79,114,'codigo_operacion','codigo operacion',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (80,114,'objeto_operado','objeto operado',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (81,114,'id_operado','  operado',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (82,114,'descripcion','descripcion',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (94,4,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (95,4,'idquerymaster',' querymaster',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (96,4,'campo_','campo ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (97,4,'idquery',' query',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (105,5,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (106,5,'nombre','nombre',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (107,5,'telefono','telefono',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (108,5,'email','email',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (109,5,'login','login',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (110,5,'clave','clave',0,1,0,0,0,'','',0,'',0,1,0,0,'',NULL,NULL,'Utilice la opcion Cambiar clave',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (112,5,'habilitado','habilitado',0,0,0,0,0,'1','',0,'',1,1,0,0,'','Estado','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (114,5,'observaciones','observaciones',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (115,5,'esRoot','es root',1,0,0,0,0,'','',0,'',1,1,0,0,'','Estado','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (117,113,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (118,113,'nombre','nombre',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (119,113,'idmenu','menu',0,0,0,0,0,'','',0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (120,113,'url','url',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (121,113,'orden','orden',1,NULL,NULL,NULL,NULL,'5',NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (122,113,'activo','activo',1,0,0,0,0,'1','',0,'',1,1,0,0,'','estado','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (123,113,'icon','icon',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (124,113,'idquery',' query',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (131,18,'id','id',0,0,0,0,0,'',NULL,0,'',1,1,0,0,'',NULL,'','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (132,18,'nombre','nombre',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (133,18,'valor','valor',0,0,0,0,0,'',NULL,0,'',1,1,0,0,'',NULL,'','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (236,133,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (237,133,'nombre','nombre',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (238,13,'example','example',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (239,13,'is_editable','is editable',1,0,0,0,0,'','',0,'',1,1,0,0,'','estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (246,32,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (247,32,'queryname','queryname',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (248,32,'querydescription','querydescription',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (249,32,'table_','table_',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (250,32,'fields_','fields_',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (251,32,'combofield_','combofield_',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'Comodidades',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (253,32,'keyfield_','keyfield_',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (254,32,'whereexp','whereexp',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (256,32,'order_by','order_by',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (257,32,'caninsert','caninsert',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (258,32,'canedit','canedit',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (259,32,'candelete','candelete',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (265,32,'idmenu','menu',0,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (266,32,'icon','icon',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (323,113,'ayuda','ayuda',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (324,113,'condicion','condicion',0,0,0,0,0,'','',0,'',1,1,0,0,'','','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (325,139,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (326,139,'idperfil','perfil',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (327,139,'idoperacion','operacion',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (328,140,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (329,140,'idperfil','perfil',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (330,140,'idquery','consulta',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (331,141,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (332,141,'idusuario','usuario',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (333,141,'idperfil','perfil',1,0,0,0,0,'','',0,'',1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (334,113,'target','target',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (335,13,'class','class',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (339,9,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (340,9,'queryname','queryname',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (341,9,'querydescription','querydescription',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (342,9,'table_','table ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (343,9,'fields_','fields ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (344,9,'combofield_','combofield ',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'Comodidades',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (346,9,'keyfield_','keyfield ',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (347,9,'whereexp','whereexp',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (349,9,'order_by','order by',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (350,9,'caninsert','caninsert',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (351,9,'canedit','canedit',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (352,9,'candelete','candelete',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (358,9,'idmenu','menu',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (359,9,'icon','icon',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (415,147,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (416,147,'idquery','query',1,0,0,0,0,'','',0,'',1,1,0,0,'',NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (417,147,'descripcion','descripcion',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (418,147,'filter','filter',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (909,194,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (910,194,'idquery','idquery',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (911,194,'iddato','iddato',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (912,194,'nota','nota',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (913,194,'color','color',0,0,0,1,0,'','',0,'',1,1,0,0,'','',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (914,194,'adjunto1','adjunto1',0,0,1,0,0,'','',0,'',1,1,0,0,'','',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (915,194,'adjunto2','adjunto2',0,0,1,0,0,'','',0,'',1,1,0,0,'','',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1061,9,'debil','debil',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1062,32,'debil','debil',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1063,37,'debil','debil',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1074,4,'in_master','in master',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1294,113,'grupal','grupal',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1334,255,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1335,255,'nombre','nombre',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1336,255,'descripcion','descripcion',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1337,255,'proximo_numero','proximo numero',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1338,255,'completar_ceros','completar ceros',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1366,9,'info','info',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1367,32,'info','info',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1368,37,'info','info',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1400,9,'oninsert','oninsert',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1401,32,'oninsert','oninsert',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1402,37,'oninsert','oninsert',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1410,266,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1411,266,'nombre','nombre',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1632,194,'usuario','usuario',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1633,13,'grupo','grupo',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1634,261,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1635,261,'idusuario','usuario',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1636,261,'atributo','atributo',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1637,261,'valor1','valor1',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1638,261,'valor2','valor2',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1881,13,'field_help','field help',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1958,9,'onupdate','onupdate',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1959,32,'onupdate','onupdate',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (1960,37,'onupdate','onupdate',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2159,13,'subgrupo','subgrupo',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2547,261,'orden','orden',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2572,507,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2573,507,'idquery1','query1',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2575,507,'idquery2','query2',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2577,507,'id1','id1',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2578,507,'id2','id2',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2644,5,'ultima_actividad','ultima actividad',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2882,5,'idlocalidad','localidad',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'sistema',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2892,13,'encriptado','encriptado',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2906,552,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2907,552,'idusuario','usuario',1,NULL,0,NULL,NULL,'',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2908,552,'idquery','tabla',1,NULL,0,NULL,NULL,'',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2909,552,'filter','filter',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2929,556,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2930,556,'nombre','nombre',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2931,556,'tipo','tipo',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2932,556,'servidor','servidor',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2933,556,'usuario','usuario',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2934,556,'clave','clave',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2935,556,'base','base',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2947,13,'visible','visible',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (2952,5,'punto_venta','punto venta',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'sistema',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3018,113,'emergente','emergente',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3042,564,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3043,564,'codigo','codigo',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3044,564,'nombre','nombre',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3045,564,'hoja','hoja',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3046,564,'tam_fuente','tam fuente',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3047,564,'nombre_fuente','nombre fuente',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3048,564,'apaisada','apaisada',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3049,565,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3050,565,'idreporte','reporte',1,NULL,0,NULL,NULL,'',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3051,565,'texto','texto',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3052,565,'es_campo','es campo',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3053,565,'diff_fuente','diff fuente',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3054,565,'pos_x','pos x',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3055,565,'pos_y','pos y',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3056,565,'ancho_max','ancho max',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3057,565,'negrita','negrita',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3058,565,'italica','italica',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3059,565,'color','color',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3062,565,'pagina','pagina',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3063,564,'margen_izq','margen izq',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'margenes',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3064,564,'margen_sup','margen sup',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'margenes',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3065,564,'margen_der','margen der',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'margenes',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3066,564,'margen_inf','margen inf',1,NULL,0,NULL,NULL,'1',NULL,0,NULL,1,1,0,0,NULL,'margenes',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3067,565,'completar_con','completar con',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3068,565,'expresion_eval','expresion eval',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3158,575,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3159,575,'idreporte','reporte',1,NULL,0,NULL,NULL,'',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3160,575,'hoja','hoja',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3161,575,'corrimiento_x','corrimiento x',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3162,575,'corrimiento_y','corrimiento y',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3166,13,'ancho','ancho',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3297,9,'combofield_2','combofield 2',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3298,32,'combofield_2','combofield 2',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3299,37,'combofield_2','combofield 2',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3506,5,'idcontacto','contacto',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'sistema',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3968,9,'es_cacheable','es cacheable',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3969,32,'es_cacheable','es cacheable',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3970,37,'es_cacheable','es cacheable',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3974,9,'table_checksum','table checksum',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3975,32,'table_checksum','table checksum',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (3976,37,'table_checksum','checksum',0,NULL,0,NULL,NULL,'0',NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4105,86,'color','color',0,NULL,NULL,1,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4106,9,'cargar_siempre','cargar siempre',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4107,32,'cargar_siempre','cargar siempre',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4108,37,'cargar_siempre','cargar siempre',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4129,670,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4130,670,'idperfil','perfil',1,NULL,0,NULL,NULL,'',NULL,0,NULL,1,1,0,0,NULL,'',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4131,670,'dia','dia',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4132,670,'dia_semana','dia semana',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4133,670,'hr_inicio','hr inicio',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4134,670,'hr_fin','hr fin',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4178,9,'en_backup','en backup',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4179,32,'en_backup','en backup',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4180,37,'en_backup','en backup',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4318,13,'ocultar_vacio','ocultar vacio',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4344,698,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4345,698,'idmenu','menu',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4346,698,'idperfil','perfil',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4469,113,'pantalla_inicial','pantalla inicial',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4573,113,'acceso_offline','acceso offline',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4575,5,'recibe_notificaciones','recibe notificaciones',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'estado',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4689,741,'id','id',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4690,741,'idusuario','usuario',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4691,741,'nombre','nombre',1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4692,741,'valor','valor',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4872,9,'selector_tree','selector tree',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'tree',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4873,32,'selector_tree','selector tree',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'tree',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4874,37,'selector_tree','selector tree',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'tree',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4875,9,'campo_jerarquia','campo jerarquia',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'tree',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4876,32,'campo_jerarquia','campo jerarquia',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'tree',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (4877,37,'campo_jerarquia','campo jerarquia',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'tree',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5111,18,'es_cacheable','es cacheable',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5114,802,'id','id',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5115,802,'direccion','direccion',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5116,802,'ultimo_acceso','ultimo acceso',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5117,802,'cantidad','cantidad',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5118,802,'confiable','confiable',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5155,113,'condicion_visible','condicion visible',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5204,9,'cache_metadata','cache metadata',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache servidor',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5205,32,'cache_metadata','cache metadata',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache servidor',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5206,37,'cache_metadata','metadata',0,0,0,0,0,'0',NULL,0,'',1,1,0,0,'','cache servidor','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5207,9,'cache_usuario','cache usuario',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache servidor',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5208,32,'cache_usuario','cache usuario',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache servidor',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5209,37,'cache_usuario','usuario',0,0,0,0,0,'0',NULL,0,'',1,1,0,0,'','cache servidor','','',NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5210,9,'cache_aplicacion','cache aplicacion',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache servidor',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5211,32,'cache_aplicacion','cache aplicacion',0,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,0,0,NULL,'cache servidor',NULL,NULL,NULL);
INSERT INTO `sc_fields` (`id`, `idquery`, `field_`, `show_name`, `is_required`, `password_field`, `file_field`, `color_field`, `rich_text`, `default_value_exp`, `prefix_field`, `is_google_point`, `example`, `is_editable`, `visible`, `ocultar_vacio`, `encriptado`, `class`, `grupo`, `subgrupo`, `field_help`, `ancho`) VALUES (5212,37,'cache_aplicacion','aplicaciÃ³n',0,0,0,0,0,'0',NULL,0,'',1,1,0,0,'','cache servidor','','',NULL);
/*!40000 ALTER TABLE `sc_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_links`
--

DROP TABLE IF EXISTS `sc_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idquery1` int(11) NOT NULL,
  `id1` int(11) NOT NULL,
  `idquery2` int(11) NOT NULL,
  `id2` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_links` (`idquery1`,`idquery2`,`id1`,`id2`),
  KEY `FK_sc_links_sc_querys_idquery2` (`idquery2`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_links`
--

LOCK TABLES `sc_links` WRITE;
/*!40000 ALTER TABLE `sc_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_logs`
--

DROP TABLE IF EXISTS `sc_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_logs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `idusuario` int(10) unsigned NOT NULL DEFAULT 0,
  `codigo_operacion` varchar(20) DEFAULT NULL,
  `objeto_operado` varchar(100) DEFAULT NULL,
  `id_operado` int(10) unsigned DEFAULT NULL,
  `descripcion` varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_log_emails` (`codigo_operacion`,`objeto_operado`,`descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_logs`
--

LOCK TABLES `sc_logs` WRITE;
/*!40000 ALTER TABLE `sc_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_menuconsola`
--

DROP TABLE IF EXISTS `sc_menuconsola`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_menuconsola` (
  `idItemMenu` int(11) NOT NULL AUTO_INCREMENT,
  `Item` varchar(60) DEFAULT NULL,
  `activo` tinyint(3) unsigned DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `icon` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idItemMenu`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_menuconsola`
--

LOCK TABLES `sc_menuconsola` WRITE;
/*!40000 ALTER TABLE `sc_menuconsola` DISABLE KEYS */;
INSERT INTO `sc_menuconsola` (`idItemMenu`, `Item`, `activo`, `orden`, `icon`, `color`) VALUES (4,'Root',1,1000,'fa-solid fa-gear','#648D6A');
INSERT INTO `sc_menuconsola` (`idItemMenu`, `Item`, `activo`, `orden`, `icon`, `color`) VALUES (10,'Administrador',1,900,'fa-regular fa-user','#648D6A');
/*!40000 ALTER TABLE `sc_menuconsola` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_operaciones`
--

DROP TABLE IF EXISTS `sc_operaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_operaciones` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `idmenu` int(10) unsigned DEFAULT NULL,
  `url` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `orden` int(10) unsigned NOT NULL DEFAULT 5,
  `activo` tinyint(3) unsigned NOT NULL DEFAULT 1,
  `icon` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `idquery` int(10) unsigned DEFAULT NULL,
  `ayuda` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `condicion` varchar(500) DEFAULT NULL,
  `condicion_visible` varchar(300) DEFAULT NULL,
  `target` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `grupal` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `emergente` tinyint(3) unsigned DEFAULT 0,
  `pantalla_inicial` tinyint(3) unsigned DEFAULT 0,
  `acceso_offline` tinyint(3) unsigned DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_operacion` (`nombre`,`idmenu`)
) ENGINE=InnoDB AUTO_INCREMENT=1046 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_operaciones`
--

LOCK TABLES `sc_operaciones` WRITE;
/*!40000 ALTER TABLE `sc_operaciones` DISABLE KEYS */;
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (74,'Test controles',4,'app-test.php',5,1,'images/cogwheel.png',NULL,'Probar controles del sistema','if (strcmp($record[\"\"], \"X\") == 0) $condicion = true else $condicion = false','','sc3test',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (363,'Deploy',4,'sc-deployversion.php',5,1,'images/cogwheel.png',NULL,'Armar versiÃ³n','if (strcmp($record[\"\"], \"X\") == 0) $condicion = true else $condicion = false','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (379,'Sql',4,'sc-sql.php',5,1,'images/sql2.png',NULL,'SQL Inside','if (strcmp($record[\"\"], \"X\") == 0) $condicion = true else $condicion = false','','_blanck',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (414,'Descargar backup',10,'sc-backupcsv.php',520,0,'images/zip_icon.gif',NULL,'Permite descargar la base de datos en archivos XLS','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (430,'Cambiar clave',NULL,'sc-cambioclave.php',520,1,'images/lock_edit.png',5,'Cambiar clave de este usuario','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (468,'Navegar archivos',4,'sc-listdir.php',520,1,'images/folder_open.png',NULL,'Navegar carpetas','','','navfiles',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (480,'DiseÃ±ar',NULL,'sc-rpt-disenar.php',520,1,'images/gear.gif',564,'dibujar el reporte','','','rptdesign',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (702,'Generar campos',NULL,'genfieldsinfo.php',520,1,'images/gear.gif',37,'Generar campos','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (708,'Generar codigo',NULL,'sc-generarcodigo.php',520,1,'images/gear.gif',37,'Generar codigo','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (729,'Instalar tabla',4,'sc-instalartablas.php',520,1,'images/gear.gif',NULL,'Agrega tabla de la base de datos al sistema','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (739,'Listar acceso',NULL,'sc-operacionesusuario.php',520,1,'images/gear.gif',5,'Permite listar a que datos y operaciones tiene acceso','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (762,'Usuarios con acceso',NULL,'sc-usuariosoperacion.php',520,1,'images/gear.gif',113,'Permite listar usuarios que acceden','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (768,'Generar horarios',NULL,'sc-perfil-generarhorarios.php',520,1,'images/gear.gif',133,'Permite generar horarios de acceso','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (769,'Generar codigo',NULL,'sc-generarcodigo-op.php',520,1,'images/gear.gif',113,'Generar codigo','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (778,'Exportar FKs',4,'sc-exportar-fk.php',520,1,'images/diagram.jpg',NULL,'Exporta Fk para luego generarlas','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (799,'Convertir UTF8',4,'sc-convertir-utf8.php',520,1,'images/sort.gif',NULL,'Convertir a UTF8','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (800,'Grabar backup',4,'sc-backupbd.php',520,1,'images/zip_icon.gif',NULL,'Grabar backup','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (811,'Copiar perfiles',NULL,'sc-copiarperfiles.php',520,1,'images/arrowbright.gif',5,'Copiar los perfiles de este usuario a otro','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (817,'API Test',4,'sc-api-test.php',520,1,'images/arrowdot.gif',NULL,'Testear API en cualquier servidor','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (848,'Actualizar SC3 Core',4,'sc-updversion-core.php',520,1,'images/sum.png',NULL,'Actualizar nucleo del sistema','','','',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (892,'Definir campos',NULL,'sc-administrarquery.php',520,1,'images/form.png',37,'Administar tabla y campos','','','defquery',0,0,0,0);
INSERT INTO `sc_operaciones` (`id`, `nombre`, `idmenu`, `url`, `orden`, `activo`, `icon`, `idquery`, `ayuda`, `condicion`, `condicion_visible`, `target`, `grupal`, `emergente`, `pantalla_inicial`, `acceso_offline`) VALUES (895,'Adminer',4,'sc-adminer.php',520,1,'images/database.png',NULL,'Administar base de datos','','','adminer',0,0,0,0);
/*!40000 ALTER TABLE `sc_operaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_parametros`
--

DROP TABLE IF EXISTS `sc_parametros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_parametros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `valor` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `es_cacheable` tinyint(3) unsigned DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `IND_sc_parametros_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=976 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_parametros`
--

LOCK TABLES `sc_parametros` WRITE;
/*!40000 ALTER TABLE `sc_parametros` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_parametros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_perfiles`
--

DROP TABLE IF EXISTS `sc_perfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_perfiles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `UQ_perfiles` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_perfiles`
--

LOCK TABLES `sc_perfiles` WRITE;
/*!40000 ALTER TABLE `sc_perfiles` DISABLE KEYS */;
INSERT INTO `sc_perfiles` (`id`, `nombre`) VALUES (1,'Administrador');
INSERT INTO `sc_perfiles` (`id`, `nombre`) VALUES (3,'Root');
/*!40000 ALTER TABLE `sc_perfiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_perfiles_operaciones`
--

DROP TABLE IF EXISTS `sc_perfiles_operaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_perfiles_operaciones` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idperfil` int(10) unsigned NOT NULL DEFAULT 0,
  `idoperacion` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_perfil_ope` (`idperfil`,`idoperacion`),
  KEY `FK_sc_perfiles_operaciones_sc_operaciones_ido` (`idoperacion`),
  CONSTRAINT `FK_sc_perfiles_operaciones_sc_operaciones_ido` FOREIGN KEY (`idoperacion`) REFERENCES `sc_operaciones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_sc_perfiles_operaciones_sc_perfiles_idperf` FOREIGN KEY (`idperfil`) REFERENCES `sc_perfiles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=697 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_perfiles_operaciones`
--

LOCK TABLES `sc_perfiles_operaciones` WRITE;
/*!40000 ALTER TABLE `sc_perfiles_operaciones` DISABLE KEYS */;
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (270,1,414);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (288,1,430);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (487,1,739);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (503,1,762);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (507,1,768);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (542,1,811);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (496,3,74);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (208,3,363);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (227,3,379);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (265,3,414);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (342,3,468);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (356,3,480);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (470,3,702);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (474,3,708);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (480,3,729);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (508,3,769);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (515,3,778);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (529,3,799);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (530,3,800);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (549,3,817);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (561,3,848);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (589,3,892);
INSERT INTO `sc_perfiles_operaciones` (`id`, `idperfil`, `idoperacion`) VALUES (591,3,895);
/*!40000 ALTER TABLE `sc_perfiles_operaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_perfiles_querys`
--

DROP TABLE IF EXISTS `sc_perfiles_querys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_perfiles_querys` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idperfil` int(10) unsigned NOT NULL DEFAULT 0,
  `idquery` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_perfil_query` (`idperfil`,`idquery`),
  KEY `FK_sc_perfiles_querys_querys` (`idquery`),
  CONSTRAINT `FK_sc_perfiles_querys_sc_perfiles_idperfil` FOREIGN KEY (`idperfil`) REFERENCES `sc_perfiles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_sc_perfiles_querys_sc_querys_idquery` FOREIGN KEY (`idquery`) REFERENCES `sc_querys` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=696 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_perfiles_querys`
--

LOCK TABLES `sc_perfiles_querys` WRITE;
/*!40000 ALTER TABLE `sc_perfiles_querys` DISABLE KEYS */;
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (16,1,5);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (17,1,133);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (18,1,139);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (19,1,140);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (20,1,141);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (535,1,670);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (22,3,4);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (23,3,5);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (25,3,13);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (26,3,18);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (28,3,37);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (29,3,86);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (30,3,113);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (31,3,114);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (32,3,133);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (33,3,139);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (34,3,140);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (35,3,141);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (36,3,147);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (38,3,255);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (647,3,507);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (399,3,552);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (406,3,556);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (416,3,565);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (429,3,575);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (623,3,741);
INSERT INTO `sc_perfiles_querys` (`id`, `idperfil`, `idquery`) VALUES (683,3,802);
/*!40000 ALTER TABLE `sc_perfiles_querys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_querys`
--

DROP TABLE IF EXISTS `sc_querys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_querys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `queryname` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `querydescription` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `fields_` varchar(700) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `combofield_` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `combofield_2` varchar(50) DEFAULT NULL,
  `keyfield_` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `whereexp` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `order_by` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `caninsert` tinyint(3) unsigned DEFAULT NULL,
  `canedit` tinyint(3) unsigned DEFAULT NULL,
  `candelete` tinyint(3) unsigned DEFAULT NULL,
  `idmenu` int(10) unsigned DEFAULT NULL,
  `icon` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `debil` tinyint(3) unsigned DEFAULT 0,
  `info` tinyint(3) unsigned DEFAULT 0,
  `oninsert` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `onupdate` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `es_cacheable` tinyint(3) unsigned DEFAULT 0,
  `table_checksum` int(10) unsigned DEFAULT NULL,
  `cargar_siempre` tinyint(3) unsigned DEFAULT 0,
  `en_backup` tinyint(3) unsigned DEFAULT 0,
  `selector_tree` tinyint(3) unsigned DEFAULT 0,
  `campo_jerarquia` varchar(40) DEFAULT NULL,
  `cache_metadata` tinyint(3) unsigned DEFAULT 0,
  `cache_usuario` tinyint(3) unsigned DEFAULT 0,
  `cache_aplicacion` tinyint(3) unsigned DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `IND_sc_querys_table_` (`table_`),
  KEY `IND_sc_querys_queryname` (`queryname`)
) ENGINE=InnoDB AUTO_INCREMENT=815 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_querys`
--

LOCK TABLES `sc_querys` WRITE;
/*!40000 ALTER TABLE `sc_querys` DISABLE KEYS */;
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (4,'sc_referencias','sc_referencias','Ref. entre tablas','id, idquerymaster, campo_, idquery, in_master','campo_',NULL,'id','','id desc',1,1,1,4,'images/table.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (5,'sc_usuarios','sc_usuarios','Usuarios del Sistema','id, nombre, telefono, email, login, habilitado, observaciones, esRoot, ultima_actividad','login','','id','','login',1,1,1,10,'images/programmer.png',0,0,'','',1,0,0,1,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (9,'sc_querys','sc_queryssystem','Tablas del Sistema','queryname,querydescription,table_','queryname',NULL,'id','(table_ not like \'sc%\') and (table_ not like \'sb%\')','table_,queryname,querydescription',1,1,1,4,NULL,0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (13,'sc_fields','sc_fields','Campos','id, idquery, field_, show_name, is_required, is_editable, visible, grupo, subgrupo, class, default_value_exp','show_name + \' (\' + field_ + \')\'','','id','','idquery desc, id desc',1,1,1,4,'images/table.png',0,0,'','',0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (18,'sc_parametros','sc_parametros','Parametros','id, nombre, valor, es_cacheable','nombre',NULL,'id','','id desc',1,1,1,4,'images/filter.png',0,0,'','',0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (32,'sc_querys','sc_querysadmin','Tablas del Administrador','id,queryname,querydescription,table_','queryname',NULL,'id','','table_,queryname,querydescription',1,1,1,4,'images/table.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (37,'sc_querys','sc_querysall','Tablas (todas)','id, queryname, idmenu, querydescription, table_, fields_, order_by, es_cacheable','querydescription','','id','','id desc',1,1,1,4,'images/table.png',0,0,'','',0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (86,'sc_menuconsola','sb_menuconsola','MenÃº del Sistema','idItemMenu, Item, activo, orden, icon, color','Item','','idItemMenu','','orden, item',1,1,1,4,'images/options.png',0,0,'','',1,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (113,'sc_operaciones','qoperaciones','Operaciones','id, nombre, url, idmenu, idquery, orden, activo, grupal, target, emergente, ayuda','nombre',NULL,'id','','id desc',1,1,1,4,'images/comunidad.png',0,0,'','',0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (114,'sc_logs','qlog','Log de operaciones','id, fecha, idusuario, codigo_operacion, objeto_operado, id_operado, descripcion','concat(id, \'-\', fecha)',NULL,'id','','id desc',0,0,0,10,'images/verified.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (133,'sc_perfiles','qperfiles','Perfiles','id, nombre','nombre','','id','','nombre',1,1,1,10,'images/perfiles.gif',0,0,'','',1,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (139,'sc_perfiles_operaciones','qperfilope','Operaciones de perfil','idperfil, idoperacion','id',NULL,'id','','idperfil desc, idoperacion',1,1,1,NULL,'images/base.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (140,'sc_perfiles_querys','qperfilqry','Consultas de perfil','idperfil, idquery','id',NULL,'id','','idperfil desc, idquery',1,1,1,NULL,'images/table.jpg',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (141,'sc_usuarios_perfiles','qusuariosperfiles','Perfiles de usuario','idusuario, idperfil','id',NULL,'id','','idusuario desc, idperfil',1,1,1,NULL,'images/perfiles.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (147,'sc_querys_filters','qscfilters','Filtros de querys','id, idquery, descripcion, filter','descripcion',NULL,'id','','idquery desc, id desc',1,1,1,4,'images/filter.gif',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (194,'sc_adjuntos','qscadjuntos','sc adjuntos','id, idquery, iddato, nota, color, adjunto1','idquery',NULL,'id',NULL,NULL,1,1,1,4,NULL,0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (255,'sc_secuencias','qscsecuencias','Secuencias','id, nombre, descripcion, proximo_numero, completar_ceros','nombre',NULL,'id','','nombre',1,1,1,25,'images/scsecuencias.gif',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,1);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (261,'sc_usuarios_preferencias','qscusuariospreferencias','Preferencias de usuario','id, idusuario, atributo, valor1, valor2','atributo',NULL,'id',NULL,'atributo',1,1,1,4,'images/table.png',0,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (266,'sc_tipos_campos','qsctiposcampos','Tipos de campo','id, nombre','nombre',NULL,'id',NULL,'nombre',1,1,1,4,'images/table.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (507,'sc_links','qsclinks','Referencias','id, idquery1, id1, idquery2, id2','id',NULL,'id',NULL,'',1,1,1,4,'images/movcaja.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (552,'sc_usuarios_filtros','qscusuariosfiltros','Filtros de usuarios','idusuario, idquery, filter','id',NULL,'id',NULL,'',1,1,1,NULL,'images/filter.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (556,'sc_conexiones_bd','qscconexionesbd','Conexiones a Bases','id, nombre, tipo, servidor, usuario, clave, base','nombre',NULL,'id',NULL,'nombre',1,1,1,25,'images/lock_edit.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,1);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (564,'sc_rpt_reportes','qscrptreportes','Reportes','id, codigo, nombre, hoja, tam_fuente, nombre_fuente, apaisada, margen_izq, margen_sup, margen_der, margen_inf','nombre',NULL,'id',NULL,'nombre',1,1,1,40,'images/printer.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (565,'sc_rpt_reportes_campos','qscrptreportescampos','campos','idreporte, texto, pagina, es_campo, diff_fuente, pos_x, pos_y, ancho_max, completar_con, negrita, italica, color','texto',NULL,'id',NULL,'nombre',1,1,1,NULL,'images/t2dex.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (575,'sc_rpt_reportes_corrimientos','qscrptreportescorrimientos','Corrimientos','idreporte, hoja, corrimiento_x, corrimiento_y','hoja',NULL,'id',NULL,'hoja',1,1,1,NULL,'images/arrow_out.png',1,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (670,'sc_perfiles_horarios','qscperfileshorarios','Horarios','idperfil, dia, dia_semana, hr_inicio, hr_fin','id',NULL,'id',NULL,'dia',1,1,1,NULL,'images/watch.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (698,'sc_perfiles_menu','qscperfilesmenu','Menues','idmenu, idperfil','id',NULL,'id',NULL,'id',1,1,1,NULL,'images/index.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (741,'sc_usuarios_atributos','qscusuariosatributos','Atributos','idusuario, nombre, valor','id',NULL,'id',NULL,'nombre',1,1,1,NULL,'images/draft.gif',1,0,NULL,NULL,0,0,0,0,0,NULL,0,1,0);
INSERT INTO `sc_querys` (`id`, `table_`, `queryname`, `querydescription`, `fields_`, `combofield_`, `combofield_2`, `keyfield_`, `whereexp`, `order_by`, `caninsert`, `canedit`, `candelete`, `idmenu`, `icon`, `debil`, `info`, `oninsert`, `onupdate`, `es_cacheable`, `table_checksum`, `cargar_siempre`, `en_backup`, `selector_tree`, `campo_jerarquia`, `cache_metadata`, `cache_usuario`, `cache_aplicacion`) VALUES (802,'sc_origenes','qscorigenes','Accesos','id, direccion, ultimo_acceso, cantidad, confiable','direccion',NULL,'id',NULL,'ultimo_acceso desc',1,1,1,4,'images/checkazul.png',0,0,NULL,NULL,0,0,0,0,0,NULL,1,0,0);
/*!40000 ALTER TABLE `sc_querys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_querys_extra`
--

DROP TABLE IF EXISTS `sc_querys_extra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_querys_extra` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idquery` int(11) NOT NULL,
  `atributo` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `valor` text CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uq_query_extra1` (`idquery`,`atributo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_querys_extra`
--

LOCK TABLES `sc_querys_extra` WRITE;
/*!40000 ALTER TABLE `sc_querys_extra` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_querys_extra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_querys_filters`
--

DROP TABLE IF EXISTS `sc_querys_filters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_querys_filters` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idquery` int(11) NOT NULL DEFAULT 0,
  `descripcion` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `filter` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sc_querys_filters_qry` (`idquery`)
) ENGINE=InnoDB AUTO_INCREMENT=2881 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_querys_filters`
--

LOCK TABLES `sc_querys_filters` WRITE;
/*!40000 ALTER TABLE `sc_querys_filters` DISABLE KEYS */;
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (7,37,'sc3','table_ like \'sc_%\'');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (8,37,'sistema','table_ not like \'sc_%\'');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2664,37,'cacheadas','es_cacheable = 1');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2750,37,'precargar','t1.cargar_siempre = 1');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2752,37,'al backup','t1.en_backup = 1');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2781,113,'pantalla inicial','t1.pantalla_inicial = 1');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2785,18,'smtp','t1.nombre like \'smtp-%\'');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2786,18,'factura electrÃ³nica','t1.nombre like \'ws-%\'');
INSERT INTO `sc_querys_filters` (`id`, `idquery`, `descripcion`, `filter`) VALUES (2787,18,'conf. general','t1.nombre like \'sc3-%\'');
/*!40000 ALTER TABLE `sc_querys_filters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_referencias`
--

DROP TABLE IF EXISTS `sc_referencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_referencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idquerymaster` int(11) DEFAULT NULL,
  `campo_` varchar(40) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `idquery` int(11) DEFAULT NULL,
  `in_master` tinyint(3) unsigned DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_ref2` (`idquerymaster`,`campo_`,`idquery`) USING BTREE,
  KEY `FK_sc_referencias_query` (`idquery`)
) ENGINE=InnoDB AUTO_INCREMENT=999 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_referencias`
--

LOCK TABLES `sc_referencias` WRITE;
/*!40000 ALTER TABLE `sc_referencias` DISABLE KEYS */;
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (1,113,'idquery',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (2,4,'idquerymaster',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (3,4,'idquery',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (4,13,'idquery',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (5,37,'idmenu',86,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (10,114,'idusuario',5,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (11,113,'idmenu',86,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (37,139,'idperfil',133,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (38,139,'idoperacion',113,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (39,140,'idperfil',133,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (40,140,'idquery',37,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (41,141,'idusuario',5,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (42,141,'idperfil',133,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (52,147,'idquery',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (482,507,'idquery1',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (483,507,'idquery2',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (549,552,'idusuario',5,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (550,552,'idquery',37,0);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (566,565,'idreporte',564,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (592,575,'idreporte',564,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (792,670,'idperfil',133,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (822,698,'idperfil',133,1);
INSERT INTO `sc_referencias` (`id`, `idquerymaster`, `campo_`, `idquery`, `in_master`) VALUES (867,741,'idusuario',5,1);
/*!40000 ALTER TABLE `sc_referencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_rpt_reportes`
--

DROP TABLE IF EXISTS `sc_rpt_reportes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_rpt_reportes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) NOT NULL,
  `nombre` varchar(60) NOT NULL,
  `hoja` varchar(10) NOT NULL,
  `tam_fuente` int(11) NOT NULL,
  `nombre_fuente` varchar(40) NOT NULL,
  `apaisada` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `margen_izq` decimal(18,2) NOT NULL DEFAULT 1.00,
  `margen_sup` decimal(18,2) NOT NULL DEFAULT 1.00,
  `margen_der` decimal(18,2) NOT NULL DEFAULT 1.00,
  `margen_inf` decimal(18,2) NOT NULL DEFAULT 1.00,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_rpt_reportes` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_rpt_reportes`
--

LOCK TABLES `sc_rpt_reportes` WRITE;
/*!40000 ALTER TABLE `sc_rpt_reportes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_rpt_reportes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_rpt_reportes_campos`
--

DROP TABLE IF EXISTS `sc_rpt_reportes_campos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_rpt_reportes_campos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idreporte` int(10) unsigned NOT NULL,
  `texto` varchar(255) NOT NULL,
  `pagina` int(10) unsigned NOT NULL DEFAULT 1,
  `es_campo` tinyint(3) unsigned NOT NULL DEFAULT 1,
  `diff_fuente` int(11) NOT NULL DEFAULT 0,
  `pos_x` decimal(18,2) NOT NULL,
  `pos_y` decimal(18,2) NOT NULL,
  `ancho_max` int(11) DEFAULT NULL,
  `completar_con` varchar(10) DEFAULT NULL,
  `negrita` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `italica` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `color` varchar(10) DEFAULT NULL,
  `expresion_eval` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_rpt_reportes_campos` (`idreporte`,`texto`,`es_campo`,`pos_x`,`pos_y`),
  CONSTRAINT `FK_sc_rpt_reportes_campos_sc_rpt_reportes_idr` FOREIGN KEY (`idreporte`) REFERENCES `sc_rpt_reportes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_rpt_reportes_campos`
--

LOCK TABLES `sc_rpt_reportes_campos` WRITE;
/*!40000 ALTER TABLE `sc_rpt_reportes_campos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_rpt_reportes_campos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_rpt_reportes_corrimientos`
--

DROP TABLE IF EXISTS `sc_rpt_reportes_corrimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_rpt_reportes_corrimientos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idreporte` int(10) unsigned NOT NULL,
  `hoja` int(11) NOT NULL,
  `corrimiento_x` decimal(18,2) NOT NULL,
  `corrimiento_y` decimal(18,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_rpt_reportes_corrimientos` (`idreporte`,`hoja`),
  CONSTRAINT `FK_sc_rpt_reportes_corrimientos_sc_rpt_report` FOREIGN KEY (`idreporte`) REFERENCES `sc_rpt_reportes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_rpt_reportes_corrimientos`
--

LOCK TABLES `sc_rpt_reportes_corrimientos` WRITE;
/*!40000 ALTER TABLE `sc_rpt_reportes_corrimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_rpt_reportes_corrimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_secuencias`
--

DROP TABLE IF EXISTS `sc_secuencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_secuencias` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `descripcion` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `proximo_numero` int(10) unsigned NOT NULL DEFAULT 1,
  `completar_ceros` tinyint(3) unsigned NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_secuencias` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_secuencias`
--

LOCK TABLES `sc_secuencias` WRITE;
/*!40000 ALTER TABLE `sc_secuencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_secuencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_tipos_campos`
--

DROP TABLE IF EXISTS `sc_tipos_campos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_tipos_campos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_tipos_campos` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_tipos_campos`
--

LOCK TABLES `sc_tipos_campos` WRITE;
/*!40000 ALTER TABLE `sc_tipos_campos` DISABLE KEYS */;
INSERT INTO `sc_tipos_campos` (`id`, `nombre`) VALUES (1,'texto');
/*!40000 ALTER TABLE `sc_tipos_campos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_usuarios`
--

DROP TABLE IF EXISTS `sc_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `telefono` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `email` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `login` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `clave` varchar(120) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `habilitado` tinyint(3) unsigned DEFAULT NULL,
  `observaciones` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `esRoot` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `ultima_actividad` datetime DEFAULT NULL,
  `recibe_notificaciones` tinyint(3) unsigned DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_usuarios`
--

LOCK TABLES `sc_usuarios` WRITE;
/*!40000 ALTER TABLE `sc_usuarios` DISABLE KEYS */;
INSERT INTO `sc_usuarios` (`id`, `nombre`, `telefono`, `email`, `login`, `clave`, `habilitado`, `observaciones`, `esRoot`, `ultima_actividad`, `recibe_notificaciones`) VALUES (1,'SC3 - root','','info@sc3.com.ar','root','910ebf6f8fe322cdd4d5564a506ca6c5',1,'No pierdo clave',1,NULL,0);
/*!40000 ALTER TABLE `sc_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_usuarios_filtros`
--

DROP TABLE IF EXISTS `sc_usuarios_filtros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_usuarios_filtros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `idquery` int(11) NOT NULL,
  `filter` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_usuarios_filtros` (`idusuario`,`idquery`),
  KEY `FK_sc_usuarios_filtros_sc_querys_idquery` (`idquery`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_usuarios_filtros`
--

LOCK TABLES `sc_usuarios_filtros` WRITE;
/*!40000 ALTER TABLE `sc_usuarios_filtros` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_usuarios_filtros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_usuarios_perfiles`
--

DROP TABLE IF EXISTS `sc_usuarios_perfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_usuarios_perfiles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idusuario` int(10) unsigned NOT NULL DEFAULT 0,
  `idperfil` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_usuario_perfil` (`idusuario`,`idperfil`),
  KEY `FK_sc_usuarios_perfiles_sc_perfiles_idperfil` (`idperfil`),
  CONSTRAINT `FK_sc_usuarios_perfiles_sc_perfiles_idperfil` FOREIGN KEY (`idperfil`) REFERENCES `sc_perfiles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=444 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_usuarios_perfiles`
--

LOCK TABLES `sc_usuarios_perfiles` WRITE;
/*!40000 ALTER TABLE `sc_usuarios_perfiles` DISABLE KEYS */;
INSERT INTO `sc_usuarios_perfiles` (`id`, `idusuario`, `idperfil`) VALUES (206,1,1);
INSERT INTO `sc_usuarios_perfiles` (`id`, `idusuario`, `idperfil`) VALUES (291,1,3);
/*!40000 ALTER TABLE `sc_usuarios_perfiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_usuarios_preferencias`
--

DROP TABLE IF EXISTS `sc_usuarios_preferencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_usuarios_preferencias` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `atributo` varchar(80) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `valor1` text CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `valor2` text CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `orden` int(10) unsigned DEFAULT 20,
  PRIMARY KEY (`id`),
  KEY `FK_sc_usuarios_preferencias_usuarios` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_usuarios_preferencias`
--

LOCK TABLES `sc_usuarios_preferencias` WRITE;
/*!40000 ALTER TABLE `sc_usuarios_preferencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_usuarios_preferencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_usuarios_atributos`
--

DROP TABLE IF EXISTS `sc_usuarios_atributos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_usuarios_atributos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `nombre` varchar(60) NOT NULL,
  `valor` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sc_usuarios_atributos` (`idusuario`,`nombre`,`valor`),
  CONSTRAINT `FK_sc_usuarios_atributos_sc_usuarios_idusuari` FOREIGN KEY (`idusuario`) REFERENCES `sc_usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_usuarios_atributos`
--

LOCK TABLES `sc_usuarios_atributos` WRITE;
/*!40000 ALTER TABLE `sc_usuarios_atributos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_usuarios_atributos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_perfiles_horarios`
--

DROP TABLE IF EXISTS `sc_perfiles_horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_perfiles_horarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idperfil` int(10) unsigned NOT NULL,
  `dia` int(11) NOT NULL,
  `dia_semana` varchar(5) NOT NULL,
  `hr_inicio` int(11) NOT NULL,
  `hr_fin` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sc_perfiles_horarios_sc_perfiles_idperfil` (`idperfil`),
  CONSTRAINT `FK_sc_perfiles_horarios_sc_perfiles_idperfil` FOREIGN KEY (`idperfil`) REFERENCES `sc_perfiles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_perfiles_horarios`
--

LOCK TABLES `sc_perfiles_horarios` WRITE;
/*!40000 ALTER TABLE `sc_perfiles_horarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_perfiles_horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_origenes`
--

DROP TABLE IF EXISTS `sc_origenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_origenes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `direccion` varchar(60) NOT NULL,
  `ultimo_acceso` datetime NOT NULL,
  `cantidad` int(11) NOT NULL,
  `confiable` tinyint(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_sc_origenes_direccion` (`direccion`),
  KEY `IND_sc_origenes_ultimo_acceso` (`ultimo_acceso`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_origenes`
--

LOCK TABLES `sc_origenes` WRITE;
/*!40000 ALTER TABLE `sc_origenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_origenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_tipos_iva`
--

DROP TABLE IF EXISTS `gen_tipos_iva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_tipos_iva` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `descripcion` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `talon_comprobante` varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `leyenda` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_gen_tipos_iva` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_tipos_iva`
--

LOCK TABLES `gen_tipos_iva` WRITE;
/*!40000 ALTER TABLE `gen_tipos_iva` DISABLE KEYS */;
INSERT INTO `gen_tipos_iva` (`id`, `codigo`, `descripcion`, `talon_comprobante`, `leyenda`) VALUES (1,'CF','Consumidor final','B','');
INSERT INTO `gen_tipos_iva` (`id`, `codigo`, `descripcion`, `talon_comprobante`, `leyenda`) VALUES (2,'RI','Responsable Inscripto','A',NULL);
INSERT INTO `gen_tipos_iva` (`id`, `codigo`, `descripcion`, `talon_comprobante`, `leyenda`) VALUES (3,'EX','Excento','B',NULL);
INSERT INTO `gen_tipos_iva` (`id`, `codigo`, `descripcion`, `talon_comprobante`, `leyenda`) VALUES (4,'MO','Monotributo','A','El crÃ©dito fiscal discriminado en el presente comprobante, sÃ³lo podrÃ¡ ser computado a efectos del RÃ©gimen de Sostenimiento e InclusiÃ³n Fiscal para PequeÃ±os Contribuyentes de la Ley Nro 27.618');
/*!40000 ALTER TABLE `gen_tipos_iva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bp_localidades`
--

DROP TABLE IF EXISTS `bp_localidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bp_localidades` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `prefijo` varchar(10) DEFAULT NULL,
  `idprovincia` int(10) unsigned NOT NULL DEFAULT 1,
  `cp` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `orden` int(10) unsigned DEFAULT 5,
  PRIMARY KEY (`id`),
  KEY `FK_bp_localidades_bp_provincias_idprovincia` (`idprovincia`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bp_localidades`
--

LOCK TABLES `bp_localidades` WRITE;
/*!40000 ALTER TABLE `bp_localidades` DISABLE KEYS */;
INSERT INTO `bp_localidades` (`id`, `nombre`, `prefijo`, `idprovincia`, `cp`, `orden`) VALUES (1,'LOCALIDAD',NULL,1,'',5);
INSERT INTO `bp_localidades` (`id`, `nombre`, `prefijo`, `idprovincia`, `cp`, `orden`) VALUES (10,'CABA','CAB',1,'1001',2);
/*!40000 ALTER TABLE `bp_localidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bp_provincias`
--

DROP TABLE IF EXISTS `bp_provincias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bp_provincias` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `idpais` int(10) unsigned NOT NULL DEFAULT 1,
  `codigo_arba` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_provincias` (`nombre`),
  KEY `FK_bp_provincias_bp_paises_idpais` (`idpais`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bp_provincias`
--

LOCK TABLES `bp_provincias` WRITE;
/*!40000 ALTER TABLE `bp_provincias` DISABLE KEYS */;
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (1,'Buenos Aires',1,'B');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (2,'Capital Federal',1,'C');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (3,'Catamarca',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (4,'Chaco',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (5,'Chubut',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (6,'CÃ³rdoba',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (7,'Corrientes',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (8,'Entre RÃ­os',1,'E');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (9,'Formosa',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (10,'Jujuy',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (11,'La Pampa',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (12,'La Rioja',1,'F');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (13,'Mendoza',1,'M');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (14,'Misiones',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (15,'Neuquen',1,'Q');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (16,'Rio Negro',1,'R');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (17,'Salta',1,'A');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (18,'San Juan',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (19,'San Luis',1,'D');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (20,'Santa Cruz',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (21,'Santa FÃ©',1,'S');
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (22,'Santiago del Estero',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (23,'Tierra del Fuego',1,NULL);
INSERT INTO `bp_provincias` (`id`, `nombre`, `idpais`, `codigo_arba`) VALUES (24,'TucumÃ¡n',1,'T');
/*!40000 ALTER TABLE `bp_provincias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_personas`
--

DROP TABLE IF EXISTS `gen_personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_personas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `jur_nombre_fantasia` varchar(60) DEFAULT NULL,
  `telefono` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `telefono2` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `celular` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `web` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `idlocalidad` int(10) unsigned DEFAULT NULL,
  `cp` varchar(10) DEFAULT NULL,
  `direccion` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `observaciones` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `id_tipo_iva` int(10) unsigned DEFAULT NULL,
  `fecha_nacimiento` datetime DEFAULT NULL,
  `es_cliente` tinyint(3) unsigned DEFAULT NULL,
  `idvendedor` int(10) unsigned DEFAULT NULL,
  `es_proveedor` tinyint(3) unsigned DEFAULT NULL,
  `cuit` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `doc_numero` varchar(20) DEFAULT NULL,
  `cuil` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `fecha_alta` datetime DEFAULT NULL,
  `foto` varchar(60) DEFAULT NULL,
  `codigo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gen_personas_gen_tipos_iva_id_tipo_iva` (`id_tipo_iva`),
  KEY `IND_gen_personas_nombre` (`nombre`(2)),
  KEY `FK_gen_personas_bp_localidades_idlocalidad` (`idlocalidad`),
  CONSTRAINT `FK_gen_personas_bp_localidades_idlocalidad` FOREIGN KEY (`idlocalidad`) REFERENCES `bp_localidades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_personas`
--

LOCK TABLES `gen_personas` WRITE;
/*!40000 ALTER TABLE `gen_personas` DISABLE KEYS */;
INSERT INTO `gen_personas` (`id`, `nombre`, `jur_nombre_fantasia`, `telefono`, `telefono2`, `celular`, `email`, `web`, `idlocalidad`, `cp`, `direccion`, `observaciones`, `id_tipo_iva`, `fecha_nacimiento`, `es_cliente`, `idvendedor`, `es_proveedor`, `cuit`, `doc_numero`, `cuil`, `fecha_alta`, `foto`, `codigo`) VALUES (1,'NOMBRE EMPRESA','NOMBRE EMPRESA','(0XXXX) 400000','','','email@empresa.com.ar','',NULL,'7167','DIRECCION EMPRESA','',1,NULL,1,NULL,1,'','','','2008-10-02 22:31:00',NULL,NULL);
INSERT INTO `gen_personas` (`id`, `nombre`, `jur_nombre_fantasia`, `telefono`, `telefono2`, `celular`, `email`, `web`, `idlocalidad`, `cp`, `direccion`, `observaciones`, `id_tipo_iva`, `fecha_nacimiento`, `es_cliente`, `idvendedor`, `es_proveedor`, `cuit`, `doc_numero`, `cuil`, `fecha_alta`, `foto`, `codigo`) VALUES (3,'Marcos Casamayor LUIS','Marcos Casamayor LUIS','404915','','2284548421','marcos.casamayor@gmail.com','http://www.sc3.com.ar/',NULL,'7167','-','',1,NULL,1,NULL,1,'','','','2008-10-02 22:31:00',NULL,NULL);
/*!40000 ALTER TABLE `gen_personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cal_accounts`
--

DROP TABLE IF EXISTS `cal_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cal_accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(80) DEFAULT NULL,
  `pass` varchar(32) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL DEFAULT '',
  `last_name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user` (`user`),
  KEY `user_2` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cal_accounts`
--

LOCK TABLES `cal_accounts` WRITE;
/*!40000 ALTER TABLE `cal_accounts` DISABLE KEYS */;
INSERT INTO `cal_accounts` (`id`, `user`, `pass`, `first_name`, `last_name`) VALUES (1,'marcosc','d76a6638530f100bd1ff9d35e88c477f','','');
/*!40000 ALTER TABLE `cal_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cal_eventtypes`
--

DROP TABLE IF EXISTS `cal_eventtypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cal_eventtypes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(100) NOT NULL DEFAULT '',
  `typedesc` varchar(100) NOT NULL,
  `typecolor` varchar(6) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cal_eventtypes`
--

LOCK TABLES `cal_eventtypes` WRITE;
/*!40000 ALTER TABLE `cal_eventtypes` DISABLE KEYS */;
INSERT INTO `cal_eventtypes` (`id`, `typename`, `typedesc`, `typecolor`) VALUES (1,'Cumpleaños','que los cumplas','F1EA74');
INSERT INTO `cal_eventtypes` (`id`, `typename`, `typedesc`, `typecolor`) VALUES (2,'Importante','evento que no se posterga','FFAAAA');
INSERT INTO `cal_eventtypes` (`id`, `typename`, `typedesc`, `typecolor`) VALUES (3,'Reunión','Junta','999999');
INSERT INTO `cal_eventtypes` (`id`, `typename`, `typedesc`, `typecolor`) VALUES (4,'Viaje','De trabajo','A4CAE6');
/*!40000 ALTER TABLE `cal_eventtypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cal_events`
--

DROP TABLE IF EXISTS `cal_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cal_events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `mod_id` int(11) DEFAULT NULL,
  `mod_username` varchar(50) DEFAULT NULL,
  `mod_stamp` datetime DEFAULT NULL,
  `stamp` datetime DEFAULT NULL,
  `duration` datetime DEFAULT NULL,
  `eventtype` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `alias` varchar(20) DEFAULT NULL,
  `private` char(1) NOT NULL DEFAULT '0',
  `repeat_end` date DEFAULT NULL,
  `repeat_num` mediumint(9) NOT NULL DEFAULT 0,
  `repeat_d` smallint(6) NOT NULL DEFAULT 0,
  `repeat_m` smallint(6) NOT NULL DEFAULT 0,
  `repeat_y` smallint(6) NOT NULL DEFAULT 0,
  `repeat_h` smallint(6) NOT NULL DEFAULT 0,
  `type_id` int(11) NOT NULL DEFAULT 0,
  `special_id` int(11) NOT NULL DEFAULT 0,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `subject` (`subject`,`description`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cal_events`
--

LOCK TABLES `cal_events` WRITE;
/*!40000 ALTER TABLE `cal_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `cal_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cal_options`
--

DROP TABLE IF EXISTS `cal_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cal_options` (
  `opname` varchar(30) NOT NULL DEFAULT '',
  `opvalue` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`opname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cal_options`
--

LOCK TABLES `cal_options` WRITE;
/*!40000 ALTER TABLE `cal_options` DISABLE KEYS */;
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('anon_naming','n');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('hours_24','y');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('language','spanish');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('show_times','y');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('skin','default');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('start_monday','n');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('timeout','5');
INSERT INTO `cal_options` (`opname`, `opvalue`) VALUES ('whole_day','n');
/*!40000 ALTER TABLE `cal_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cal_permissions`
--

DROP TABLE IF EXISTS `cal_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cal_permissions` (
  `user_id` int(11) NOT NULL DEFAULT 0,
  `pname` varchar(30) NOT NULL DEFAULT '',
  `pvalue` char(1) NOT NULL DEFAULT 'n',
  PRIMARY KEY (`user_id`,`pname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cal_permissions`
--

LOCK TABLES `cal_permissions` WRITE;
/*!40000 ALTER TABLE `cal_permissions` DISABLE KEYS */;
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'admin','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'edit','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'editothers','n');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'needapproval','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'read','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'readothers','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'remind_get','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'remind_set','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (1,'write','y');
INSERT INTO `cal_permissions` (`user_id`, `pname`, `pvalue`) VALUES (2,'','');
/*!40000 ALTER TABLE `cal_permissions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-11 19:38:20
