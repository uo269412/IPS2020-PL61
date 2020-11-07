BEGIN TRANSACTION;
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
DROP TABLE IF EXISTS "RECURSOS";
CREATE TABLE IF NOT EXISTS "RECURSOS" (
	"codigo_recurso"	varchar(26),
	"nombre_recurso"	varchar(26),
	"codigo_instalacion"	varchar(26) NOT NULL,
	"codigo_actividad"	varchar(26),
	FOREIGN KEY("codigo_instalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	PRIMARY KEY("codigo_recurso")
);
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
	"estado"	varchar(26),
	FOREIGN KEY("id_instalacion") REFERENCES "INSTALACION"("codigo_instalacion"),
	FOREIGN KEY("id_cliente") REFERENCES "CLIENTE"("id_cliente"),
	PRIMARY KEY("id_alquiler")
);
DROP TABLE IF EXISTS "REGISTRO";
CREATE TABLE IF NOT EXISTS "REGISTRO" (
	"id_registro"	varchar(26),
	"id_alquiler"	varchar(26),
	"hora_entrada"	INTEGER,
	"hora_salida"	INTEGER,
	"alquilerPagado"	BIT NOT NULL,
	"socioPresentado"	BIT NOT NULL,
	FOREIGN KEY("id_alquiler") REFERENCES "ALQUILER"("id_alquiler"),
	PRIMARY KEY("id_registro")
);
DROP TABLE IF EXISTS "TERCEROS";
CREATE TABLE IF NOT EXISTS "TERCEROS" (
	"id_cliente"	varchar(26),
	"nombre"	varchar(50) NOT NULL,
	FOREIGN KEY("id_cliente") REFERENCES "CLIENTE"("id_cliente"),
	PRIMARY KEY("id_cliente")
);
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
INSERT INTO "ACTIVIDAD" VALUES ('Zumba_2','Zumba',1);
INSERT INTO "ACTIVIDAD" VALUES ('Zumba_0','Zumba',0);
INSERT INTO "ACTIVIDAD" VALUES ('Baloncesto_1','Baloncesto',1);
INSERT INTO "ACTIVIDAD" VALUES ('Baloncesto_2','Baloncesto',2);
INSERT INTO "ACTIVIDAD" VALUES ('Natacion_1','Natacion',1);
INSERT INTO "ACTIVIDAD" VALUES ('Natacion_2','Natacion',2);
INSERT INTO "ACTIVIDAD" VALUES ('Natacion_0','Natacion',0);
INSERT INTO "ACTIVIDAD" VALUES ('Yoga_1','Yoga',1);
INSERT INTO "ACTIVIDAD" VALUES ('Padel_2','Padel',2);
INSERT INTO "ACTIVIDAD" VALUES ('Padel_0','Padel',0);
INSERT INTO "ACTIVIDAD" VALUES ('193abe97-1477-478b-bcb5-a150a2b9890a','Tenis con los pies',1);
INSERT INTO "ACTIVIDAD_PLANIFICADA" VALUES ('Yoga_1',8,10,2020,15,15,17,NULL,'550c1532-6bd9-4987-9583-48fd65154f97','canchaBal');
INSERT INTO "ACTIVIDAD_PLANIFICADA" VALUES ('Actividad_Prueba',8,10,2020,25,11,12,NULL,'0bd38b31-309b-466c-b07c-0b7cf356565f','canchaBal');
INSERT INTO "CLIENTE" VALUES ('C-Pedro_López');
INSERT INTO "CLIENTE" VALUES ('usuario');
INSERT INTO "CLIENTE" VALUES ('C-Club_BCSTO');
INSERT INTO "CLIENTE" VALUES ('C-Laura_Flórez');
INSERT INTO "CLIENTE" VALUES ('C-Diego_Ríos');
INSERT INTO "CLIENTE" VALUES ('C-Club_PAD');
INSERT INTO "SOCIO" VALUES ('C-Pedro_López','Pedro','López');
INSERT INTO "SOCIO" VALUES ('usuario','usuario','Prueba');
INSERT INTO "SOCIO" VALUES ('C-Laura_Flórez','Laura','Flórez');
INSERT INTO "SOCIO" VALUES ('C-Diego_Ríos','Diego','Ríos');
INSERT INTO "INSTALACION" VALUES ('canchaTen','Cancha de tenis',20.5);
INSERT INTO "INSTALACION" VALUES ('canchaBal','Cancha de baloncesto',10.0);
INSERT INTO "INSTALACION" VALUES ('piscina','Piscina',35.5);
INSERT INTO "INSTALACION" VALUES ('salaYog','Sala de yoga',8.0);
INSERT INTO "INSTALACION" VALUES ('canchaPad','Cancha de padel',40.5);
INSERT INTO "INSTALACION" VALUES ('salaZum','Sala de zumba',5.5);
INSERT INTO "ALQUILER" VALUES ('Alquiler1','canchaTen','C-Club_BCSTO',29,10,20,20,22,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('54c4af6c-1c39-495a-b5d5-74ef57f1240d','canchaBal','9ffd05ec-341c-4395-8759-0def47c46e19',5,11,2020,16,18,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('95e5f137-8e88-4982-b6a6-0e7e34688a91','canchaBal','9ffd05ec-341c-4395-8759-0def47c46e19',12,11,2020,16,18,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('018d6592-a7e5-422d-8342-ceceda251c7a','canchaBal','9ffd05ec-341c-4395-8759-0def47c46e19',19,11,2020,16,18,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('96fdee70-f391-4eb6-b48e-24f8afd4d3d9','canchaBal','9ffd05ec-341c-4395-8759-0def47c46e19',26,11,2020,16,18,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('aed8be42-380a-47f1-8a2e-08d62d7750f1','canchaBal','559e727b-89f5-4a06-99c2-1469d56fc078',5,11,2020,18,19,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('cb7f5e13-9e2e-4c70-a74a-641de4a9b382','canchaBal','2109600e-ebfe-4c62-8521-0483220cf4a2',2,12,2020,16,18,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('62190f25-8b00-4bcb-95b7-b63377bf6b7a','canchaBal','2109600e-ebfe-4c62-8521-0483220cf4a2',9,12,2020,16,18,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('0143c33d-c3da-457b-915b-b3291b97c020','canchaBal','8eb2a74d-f988-4278-ac28-c1bb9941ba43',7,11,2020,12,13,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('27f90cc7-146c-4aae-8047-63be8a34b9e4','canchaBal','8eb2a74d-f988-4278-ac28-c1bb9941ba43',14,11,2020,12,13,'DISPONIBLE');
INSERT INTO "ALQUILER" VALUES ('aa44b452-4ee8-48c6-9d7c-419d07d0fb14','canchaBal','8eb2a74d-f988-4278-ac28-c1bb9941ba43',21,11,2020,12,13,'DISPONIBLE');
INSERT INTO "REGISTRO" VALUES ('Registo1','Alquiler1',10,11,0,1);
INSERT INTO "TERCEROS" VALUES ('C-Club_BCSTO','Club de baloncesto');
INSERT INTO "TERCEROS" VALUES ('C-Club_PAD','Club de padel');
COMMIT;
