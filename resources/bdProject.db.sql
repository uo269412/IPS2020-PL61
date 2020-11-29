BEGIN TRANSACTION;
DROP TABLE IF EXISTS "CLIENTE";
CREATE TABLE IF NOT EXISTS "CLIENTE" (
	"id_cliente"	varchar(26),
	PRIMARY KEY("id_cliente")
);
DROP TABLE IF EXISTS "SOCIO";
CREATE TABLE IF NOT EXISTS "SOCIO" (
	"id_cliente"	varchar(26),
	"nombre"	varchar(26),
	"apellidos"	varchar(26),
	FOREIGN KEY("id_cliente") REFERENCES "CLIENTE"("id_cliente"),
	PRIMARY KEY("id_cliente")
);
DROP TABLE IF EXISTS "INSTALACION";
CREATE TABLE IF NOT EXISTS "INSTALACION" (
	"codigo_instalacion"	varchar(26),
	"nombre_instalacion"	varchar(26),
	"preciohora"	REAL,
	"estado"	BIT,
	"permite_alquiler"	BIT,
	PRIMARY KEY("codigo_instalacion")
);
DROP TABLE IF EXISTS "ALQUILER";
CREATE TABLE IF NOT EXISTS "ALQUILER" (
	"id_alquiler"	varchar(26),
	"id_instalacion"	varchar(26),
	"id_cliente"	varchar(26),
	"dia"	INTEGER,
	"mes"	INTEGER,
	"año"	INTEGER,
	"horaInicio"	INTEGER,
	"horaFin"	INTEGER,
	FOREIGN KEY("id_cliente") REFERENCES "CLIENTE"("id_cliente"),
	FOREIGN KEY("id_instalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	PRIMARY KEY("id_alquiler")
);
DROP TABLE IF EXISTS "TERCEROS";
CREATE TABLE IF NOT EXISTS "TERCEROS" (
	"id_cliente"	varchar(26),
	"nombre"	varchar(50) NOT NULL,
	FOREIGN KEY("id_cliente") REFERENCES "CLIENTE"("id_cliente"),
	PRIMARY KEY("id_cliente")
);
DROP TABLE IF EXISTS "MONITOR";
CREATE TABLE IF NOT EXISTS "MONITOR" (
	"codigoMonitor"	varchar(26),
	"nombre"	varchar(10),
	"apellido"	varchar(26),
	PRIMARY KEY("codigoMonitor")
);
DROP TABLE IF EXISTS "ACTIVIDAD";
CREATE TABLE IF NOT EXISTS "ACTIVIDAD" (
	"codigo"	varchar(26),
	"nombre"	varchar(26),
	"intensidad"	int,
	PRIMARY KEY("codigo")
);
DROP TABLE IF EXISTS "RESERVA";
CREATE TABLE IF NOT EXISTS "RESERVA" (
	"id_cliente"	varchar(26) NOT NULL,
	"codigo_actividad"	varchar(26) NOT NULL,
	FOREIGN KEY("codigo_actividad") REFERENCES "ACTIVIDAD_PLANIFICADA"("codigoPlanificada"),
	FOREIGN KEY("id_cliente") REFERENCES "SOCIO"("id_cliente"),
	PRIMARY KEY("id_cliente","codigo_actividad")
);
DROP TABLE IF EXISTS "ACTIVIDAD_PLANIFICADA";
CREATE TABLE IF NOT EXISTS "ACTIVIDAD_PLANIFICADA" (
	"codigoActividad"	varchar(26),
	"dia"	INTEGER,
	"mes"	INTEGER,
	"año"	INTEGER,
	"limitePlazas"	INTEGER,
	"horaInicio"	INTEGER,
	"horaFin"	INTEGER,
	"codigoMonitor"	varchar(26),
	"codigoPlanificada"	varchar(26),
	"codigoInstalacion"	varchar(26),
	FOREIGN KEY("codigoActividad") REFERENCES "ACTIVIDAD"("codigo"),
	FOREIGN KEY("codigoInstalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	FOREIGN KEY("codigoMonitor") REFERENCES "MONITOR"("codigoMonitor"),
	PRIMARY KEY("codigoPlanificada")
);
DROP TABLE IF EXISTS "REGISTRO";
CREATE TABLE IF NOT EXISTS "REGISTRO" (
	"id_registro"	varchar(26),
	"id_alquiler"	varchar(26),
	"hora_entrada"	INTEGER,
	"hora_salida"	INTEGER,
	"alquilerPagado"	BIT,
	"socioPresentado"	BIT NOT NULL,
	FOREIGN KEY("id_alquiler") REFERENCES "ALQUILER"("id_alquiler"),
	PRIMARY KEY("id_registro")
);
DROP TABLE IF EXISTS "RECURSOS";
CREATE TABLE IF NOT EXISTS "RECURSOS" (
	"codigo_recurso"	varchar(26),
	"nombre_recurso"	varchar(26),
	"codigo_instalacion"	varchar(26) NOT NULL,
	"codigo_actividad"	varchar(26),
	"unidades"	INTEGER,
	FOREIGN KEY("codigo_instalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	PRIMARY KEY("codigo_recurso")
);
DROP TABLE IF EXISTS "CONFLICTOS";
CREATE TABLE IF NOT EXISTS "CONFLICTOS" (
	"codigoActividadAfectada"	varchar(26),
	"codigoActividadConflictiva"	varchar(26),
	FOREIGN KEY("codigoActividadAfectada") REFERENCES "ACTIVIDAD_PLANIFICADA"("codigoPlanificada"),
	FOREIGN KEY("codigoActividadConflictiva") REFERENCES "ACTIVIDAD_PLANIFICADA"("codigoPlanificada"),
	PRIMARY KEY("codigoActividadAfectada","codigoActividadConflictiva")
);
DROP TABLE IF EXISTS "CIERRE_DIA";
CREATE TABLE IF NOT EXISTS "CIERRE_DIA" (
	"codigo_instalacion"	varchar(26),
	"dia"	INTEGER,
	"mes"	INTEGER,
	"año"	INTEGER,
	FOREIGN KEY("codigo_instalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	PRIMARY KEY("codigo_instalacion","dia","mes","año")
);
DROP TABLE IF EXISTS "CIERRE_ACTIVIDAD";
CREATE TABLE IF NOT EXISTS "CIERRE_ACTIVIDAD" (
	"codigo_instalacion"	varchar(26),
	"codigo_actividad"	varchar(26),
	FOREIGN KEY("codigo_instalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	FOREIGN KEY("codigo_actividad") REFERENCES "ACTIVIDAD"("codigo"),
	PRIMARY KEY("codigo_instalacion","codigo_actividad")
);
INSERT INTO "CLIENTE" VALUES ('C-Pedro_López');
INSERT INTO "CLIENTE" VALUES ('usuario');
INSERT INTO "CLIENTE" VALUES ('C-Club_BCSTO');
INSERT INTO "CLIENTE" VALUES ('C-Laura_Flórez');
INSERT INTO "CLIENTE" VALUES ('C-Diego_Ríos');
INSERT INTO "CLIENTE" VALUES ('C-Club_PAD');
INSERT INTO "CLIENTE" VALUES ('C-Juan_Cuesta');
INSERT INTO "CLIENTE" VALUES ('C-Emilio_Delgado');
INSERT INTO "CLIENTE" VALUES ('C-Mauricio_Hidalgo');
INSERT INTO "CLIENTE" VALUES ('C-Vicenta_Benito');
INSERT INTO "CLIENTE" VALUES ('C-Mariano_Delgado');
INSERT INTO "CLIENTE" VALUES ('C-Isabel_Hierba');
INSERT INTO "CLIENTE" VALUES ('C-Valentin_Can');
INSERT INTO "CLIENTE" VALUES ('C-Delito_Navarro');
INSERT INTO "CLIENTE" VALUES ('C-Club_SALTO');
INSERT INTO "CLIENTE" VALUES ('C-Club_TENMES');
INSERT INTO "CLIENTE" VALUES ('C-Club_FUT');
INSERT INTO "CLIENTE" VALUES ('C-Club_BLMANO');
INSERT INTO "SOCIO" VALUES ('C-Pedro_López','Pedro','López');
INSERT INTO "SOCIO" VALUES ('usuario','usuario','Prueba');
INSERT INTO "SOCIO" VALUES ('C-Laura_Flórez','Laura','Flórez');
INSERT INTO "SOCIO" VALUES ('C-Diego_Ríos','Diego','Ríos');
INSERT INTO "SOCIO" VALUES ('C-Delito_Navarro','Delito','Navarro');
INSERT INTO "SOCIO" VALUES ('C-Emilio_Delgado','Emilio','Delgado');
INSERT INTO "SOCIO" VALUES ('C-Isabel_Hierba','Isabel','Hierba');
INSERT INTO "SOCIO" VALUES ('C-Juan_Cuesta','Juan','Cuesta');
INSERT INTO "SOCIO" VALUES ('C-Mariano_Delgado','Mariano','Delgado');
INSERT INTO "SOCIO" VALUES ('C-Mauricio_Hidalgo','Mauricio','Hidalgo');
INSERT INTO "SOCIO" VALUES ('C-Valentin_Can','Valentín','Can');
INSERT INTO "SOCIO" VALUES ('C-Vicenta_Benito','Vicenta','Benito');
INSERT INTO "INSTALACION" VALUES ('canchaTen','Cancha de tenis',20.5,1);
INSERT INTO "INSTALACION" VALUES ('canchaBal','Cancha de baloncesto',10.0,1);
INSERT INTO "INSTALACION" VALUES ('piscina','Piscina',35.5,1);
INSERT INTO "INSTALACION" VALUES ('salaYog','Sala de yoga',8.0,0);
INSERT INTO "INSTALACION" VALUES ('canchaPad','Cancha de padel',40.5,1);
INSERT INTO "INSTALACION" VALUES ('salaZum','Sala de zumba',5.5,1);
INSERT INTO "INSTALACION" VALUES ('canchaFutSal','Cancha de fútbol sala',50.0,1);
INSERT INTO "INSTALACION" VALUES ('fosoSalto','Foso de salto de longitud',10.0,0);
INSERT INTO "ALQUILER" VALUES ('2ddd3628-77d0-4b46-8fee-f75f85b6b41a','canchaPad','C-Juan_Cuesta',11,11,2020,12,14);
INSERT INTO "ALQUILER" VALUES ('f68ced44-715b-4f95-8af7-2ba3673a40f0','canchaPad','C-Delito_Navarro',10,11,2020,18,20);
INSERT INTO "ALQUILER" VALUES ('c2cc7903-fb2a-4af3-9b84-376771f055c3','salaZum','C-Vicenta_Benito',11,11,2020,12,14);
INSERT INTO "ALQUILER" VALUES ('91f49dfd-da4a-47b0-ac2c-c261d585d293','salaZum','C-Mariano_Delgado',11,11,2020,14,16);
INSERT INTO "ALQUILER" VALUES ('b7404c4e-70b4-4d5f-8665-814c2b1bcf29','canchaFutSal','C-Pedro_López',11,11,2020,9,11);
INSERT INTO "ALQUILER" VALUES ('0a8fafdd-6996-4baa-b21f-7cd927ba108a','piscina','C-Laura_Flórez',9,11,2020,10,11);
INSERT INTO "ALQUILER" VALUES ('d3b69d6f-818c-4be5-9cf7-f40cb3abd7fb','piscina','C-Laura_Flórez',8,11,2020,12,14);
INSERT INTO "ALQUILER" VALUES ('1dcf0465-b935-4eaa-bf06-d8cf5c83eade','piscina','C-Laura_Flórez',14,11,2020,16,18);
INSERT INTO "ALQUILER" VALUES ('a08050d1-aec8-4edf-81ff-baedb4a657c3','piscina','C-Valentin_Can',11,11,2020,11,13);
INSERT INTO "ALQUILER" VALUES ('d3a5aa7e-a83b-4de5-969c-85f9e3cadad6','canchaPad','C-Isabel_Hierba',30,11,2020,21,22);
INSERT INTO "ALQUILER" VALUES ('b85ecdb3-4da2-44e8-9bf6-b34900fe0b4f','canchaPad','6f3e02c6-ec2c-44bf-90e0-e9d0a8103f37',30,11,2020,9,10);
INSERT INTO "TERCEROS" VALUES ('C-Club_BCSTO','Club de baloncesto');
INSERT INTO "TERCEROS" VALUES ('C-Club_PAD','Club de padel');
INSERT INTO "TERCEROS" VALUES ('C-Club_BLMANO','Club de balonmano');
INSERT INTO "TERCEROS" VALUES ('C-Club_FUT','Club de fútbol sala');
INSERT INTO "TERCEROS" VALUES ('C-Club_SALTO','Club de salto de longitud');
INSERT INTO "TERCEROS" VALUES ('C-Club_TENMES','Club de tenis de mesa');
INSERT INTO "TERCEROS" VALUES ('945eace0-e052-4e52-af72-fbf64049e50f','Equipo de Futbol Sala');
INSERT INTO "TERCEROS" VALUES ('53c7c3fb-cf25-417c-950d-f9e2c6e7f425','Club de Baloncesto 2');
INSERT INTO "TERCEROS" VALUES ('6f3e02c6-ec2c-44bf-90e0-e9d0a8103f37','Club de Padel');
INSERT INTO "MONITOR" VALUES ('MonJuan','Juan','Ramírez');
INSERT INTO "MONITOR" VALUES ('MonPepe','Pepe','Martínez');
INSERT INTO "MONITOR" VALUES ('MonRoberto','Roberto','Pérez');
INSERT INTO "MONITOR" VALUES ('MonLeticia','Leticia','Prado');
INSERT INTO "MONITOR" VALUES ('MonAndrea','Andrea','Huerta');
INSERT INTO "MONITOR" VALUES ('MonCarlota','Carlota','Hernández');
INSERT INTO "ACTIVIDAD" VALUES ('Zumba_1','Zumba',1);
INSERT INTO "ACTIVIDAD" VALUES ('Tenis_1','Tenis',1);
INSERT INTO "ACTIVIDAD" VALUES ('Tenis_2','Tenis',2);
INSERT INTO "ACTIVIDAD" VALUES ('Actividad_Prueba','Actividad Prueba',1);
INSERT INTO "ACTIVIDAD" VALUES ('Zumba_2','Zumba',2);
INSERT INTO "ACTIVIDAD" VALUES ('Zumba_0','Zumba',0);
INSERT INTO "ACTIVIDAD" VALUES ('Baloncesto_1','Baloncesto',1);
INSERT INTO "ACTIVIDAD" VALUES ('Baloncesto_2','Baloncesto',2);
INSERT INTO "ACTIVIDAD" VALUES ('Natacion_1','Natacion',1);
INSERT INTO "ACTIVIDAD" VALUES ('Natacion_2','Natacion',2);
INSERT INTO "ACTIVIDAD" VALUES ('Natacion_0','Natacion',0);
INSERT INTO "ACTIVIDAD" VALUES ('Yoga_1','Yoga',1);
INSERT INTO "ACTIVIDAD" VALUES ('Padel_2','Padel',2);
INSERT INTO "ACTIVIDAD" VALUES ('Padel_0','Padel',0);
INSERT INTO "ACTIVIDAD" VALUES ('TenisDeMesa_1','Tenis de mesa',1);
INSERT INTO "ACTIVIDAD" VALUES ('TenisDeMesa_0','Tenis de mesa',0);
INSERT INTO "ACTIVIDAD" VALUES ('FutbolSala_1','Fútbol sala',1);
INSERT INTO "ACTIVIDAD" VALUES ('FutbolSala_2','Fútbol sala',2);
INSERT INTO "ACTIVIDAD" VALUES ('Balonmano_1','Balonmano',1);
INSERT INTO "ACTIVIDAD" VALUES ('Balonmano_0','Balonmano',0);
INSERT INTO "ACTIVIDAD" VALUES ('SaltoDeLongitud_2','Salto de longitud',2);
INSERT INTO "ACTIVIDAD" VALUES ('Tenis con los pies_2','Tenis con los pies',2);
INSERT INTO "RESERVA" VALUES ('C-Laura_Flórez','ceed1d09-36b6-4f8d-9aea-03ee6d3707b2');
INSERT INTO "RESERVA" VALUES ('C-Isabel_Hierba','c9b49040-56f2-4758-9c94-104eeeee74f4');
INSERT INTO "RESERVA" VALUES ('C-Delito_Navarro','946df386-4f71-4139-93aa-9b3f4a607a51');
INSERT INTO "ACTIVIDAD_PLANIFICADA" VALUES ('Actividad_Prueba',29,11,2020,10,13,14,'','ac292105-22d0-4a99-b129-d670a63d27c6','canchaFutSal');
INSERT INTO "ACTIVIDAD_PLANIFICADA" VALUES ('Actividad_Prueba',29,11,2020,10,10,17,'','d328b32d-bf8b-4b1d-8e67-806efdd61b12','canchaPad');
INSERT INTO "ACTIVIDAD_PLANIFICADA" VALUES ('Actividad_Prueba',10,12,2020,12,12,15,NULL,'0e409c6a-c6c5-4b17-aaea-b03149473c3e','canchaBal');
INSERT INTO "ACTIVIDAD_PLANIFICADA" VALUES ('Actividad_Prueba',30,11,2020,10,12,15,NULL,'946df386-4f71-4139-93aa-9b3f4a607a51','canchaPad');
INSERT INTO "REGISTRO" VALUES ('7872bf15-8d9e-4440-8735-ad037ce8e261','f68ced44-715b-4f95-8af7-2ba3673a40f0',18,18,0,1);
INSERT INTO "REGISTRO" VALUES ('2e07778a-d0cc-4c18-98a5-32ac622f6b80','b7404c4e-70b4-4d5f-8665-814c2b1bcf29',9,NULL,0,1);
INSERT INTO "RECURSOS" VALUES ('RAQ_TENIS','raqueta de tenis','canchaTen','ffc0bfd6-9da9-4f00-b3b8-7aef25a7a40d',18);
INSERT INTO "RECURSOS" VALUES ('PEL_TENIS','pelota de tenis','canchaTen',NULL,9);
INSERT INTO "RECURSOS" VALUES ('PEL_BALONC','pelota de baloncesto','canchaBal',NULL,12);
INSERT INTO "RECURSOS" VALUES ('PEL_FUT','pelota de fútbol','canchaFutSal','Tenis con los pies_2',15);
INSERT INTO "RECURSOS" VALUES ('PEL_PADEL','pelota de padel','canchaPad',NULL,9);
INSERT INTO "RECURSOS" VALUES ('RAQ_PADEL','raqueta de padel','canchaPad',NULL,18);
INSERT INTO "RECURSOS" VALUES ('ARO_ZUM','aro de zumba','salaZum','ffc0bfd6-9da9-4f00-b3b8-7aef25a7a40d',20);
INSERT INTO "RECURSOS" VALUES ('CHU_PIS','churro para nadar','piscina',NULL,100);
INSERT INTO "RECURSOS" VALUES ('COR_PIS','corcheras piscina','piscina','aefb21f9-7b43-454a-924b-085b1549231c',6);
INSERT INTO "CONFLICTOS" VALUES ('d328b32d-bf8b-4b1d-8e67-806efdd61b12','ac292105-22d0-4a99-b129-d670a63d27c6');
INSERT INTO "CIERRE_DIA" VALUES ('canchaPad',30,11,2020);
COMMIT;
