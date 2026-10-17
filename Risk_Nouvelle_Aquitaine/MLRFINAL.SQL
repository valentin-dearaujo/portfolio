/*
 ----------------------------------------------------------------------------
             Génération d'une base de données pour
                        SQL Server 2005
                       (10/6/2026 15:05:55)
 ----------------------------------------------------------------------------
     Nom de la base : creationBaseFinal
     Projet : Espace de travail
     Auteur : CRIIUT
     Date de dernière modification : 10/6/2026 15:04:48
 ----------------------------------------------------------------------------
*/
use master 

/*
Pour régler les problemes de connexion a la base

*/

ALTER DATABASE EQUIPE4_SAE
SET SINGLE_USER WITH ROLLBACK IMMEDIATE

ALTER DATABASE EQUIPE4_SAE
SET MULTI_USER

drop database EQUIPE4_SAE
go

/* -----------------------------------------------------------------------------
      OUVERTURE DE LA BASE creationBaseFinal
----------------------------------------------------------------------------- */

create database EQUIPE4_SAE
go

use EQUIPE4_SAE
go

CREATE USER etd04 FOR LOGIN etd04
GO
GRANT VIEW DEFINITION, SELECT, UPDATE, INSERT, DELETE TO etd04
GO

CREATE USER ETD
GRANT view definition, select, update, insert, delete TO ETD
/* -----------------------------------------------------------------------------
      TABLE : T_Departement
----------------------------------------------------------------------------- */

create table T_Departement
  (
     num_departement VARCHAR(10)  not null  ,
     lib_departement VARCHAR(40)  not null  , 
     
     constraint PK_T_Departement primary key (num_departement)
  ) 
go



/* -----------------------------------------------------------------------------
      TABLE : T_Contour_Departement
----------------------------------------------------------------------------- */

CREATE TABLE T_Contour_Departement
  (
     num_departement VARCHAR(10)  not null  ,
     geometry VARCHAR(MAX) not null  ,
     constraint PK_T_Contour_Departement primary key (num_departement)
  )
go

/* -----------------------------------------------------------------------------
      TABLE : T_Commune
----------------------------------------------------------------------------- */

create table T_Commune
  (
     code_commune CHAR(5)  not null  ,
     num_departement VARCHAR(10)  not null  ,
     lib_commune VARCHAR(40)  not null  ,
     latitude FLOAT  null  ,
     longitude FLOAT  null  
     ,
     constraint PK_T_Commune primary key (code_commune)
  ) 
go



/* -----------------------------------------------------------------------------
      TABLE : T_Type
----------------------------------------------------------------------------- */

create table T_Type
  (
     id_Type INT identity (1, 1)   ,
     type VARCHAR(32)  not null  
     ,
     constraint PK_T_Type primary key (id_Type)
  ) 
go






/* -----------------------------------------------------------------------------
      TABLE : T_Donnees
----------------------------------------------------------------------------- */

create table T_Donnees
  (
     id_donnée INT identity (1, 1)   ,
     id_Type INT  not null  ,
     code_commune CHAR(5)  not null  ,
     donnée VARCHAR(150)  not null ,
	 date_deb VARCHAR(32) null,
	 date_fin VARCHAR(32) null
     ,
     constraint PK_T_Donnees primary key (id_donnée)
  ) 
go




alter table T_Commune 
     add constraint FK_T_Commune_T_Departement foreign key (num_departement) 
               references T_Departement (num_departement)
go




alter table T_Donnees 
     add constraint FK_T_Donnees_T_Type foreign key (id_Type) 
               references T_Type (id_Type)
go



alter table T_Contour_Departement 
     add constraint FK_T_Contour_T_Departement foreign key (num_departement) 
               references T_Departement (num_departement)
go


alter table T_Donnees 
     add constraint FK_T_Donnees_T_Commune foreign key (code_commune) 
               references T_Commune (code_commune)
go



/*
 -----------------------------------------------------------------------------
               CONTRAINTES
 -----------------------------------------------------------------------------
*/



ALTER TABLE T_Commune 
ADD CONSTRAINT C_Latitude CHECK (latitude >= -90 AND latitude <= 90)

ALTER TABLE T_Commune 
ADD CONSTRAINT C_Longitude CHECK (longitude >= -180 AND longitude <= 180)

ALTER TABLE T_Commune 
ADD CONSTRAINT C_CodeCommune_Length CHECK (LEN(code_commune) = 5)

ALTER TABLE T_Departement
ADD CONSTRAINT U_Departement_Lib UNIQUE (lib_departement)

ALTER TABLE T_Commune
ADD CONSTRAINT U_Commune_Lib_Dept UNIQUE (lib_commune, num_departement)

ALTER TABLE T_Type
ADD CONSTRAINT U_Type_Lib UNIQUE (type)


/*
 -----------------------------------------------------------------------------
               FIN DE GENERATION
 -----------------------------------------------------------------------------
*/