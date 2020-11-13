-- create the databases
CREATE DATABASE IF NOT EXISTS dev character set UTF8 collate utf8_bin;

USE dev;

CREATE TABLE consumer (
	id smallint unsigned not null auto_increment,
    name varchar(30) not null,
    constraint pk_users primary key (id)
);

INSERT INTO consumer ( id, name ) VALUES ( null, 'Francisco Buyo' );
INSERT INTO consumer ( id, name ) VALUES ( null, 'Alfonso Pérez' );
INSERT INTO consumer ( id, name ) VALUES ( null, 'Raúl González' );
INSERT INTO consumer ( id, name ) VALUES ( null, 'José María Gutiérrez' );
INSERT INTO consumer ( id, name ) VALUES ( null, 'Daniel Álvarez' );
INSERT INTO consumer ( id, name ) VALUES ( null, 'Pepe García' );

CREATE TABLE group_expenses (
	id smallint unsigned not null auto_increment,
    name varchar(30) not null,
    constraint pk_squads primary key (id)
);
INSERT INTO group_expenses ( id, name ) VALUES ( null, 'Grupo 1' );

CREATE TABLE expense (
	id smallint unsigned not null auto_increment,
    amount DECIMAL(10,2) not null,
    description varchar(50),
    create_time timestamp,
    payer_id smallint,
    group_expense_id smallint,
    primary key (id)
);

INSERT INTO expense ( id, amount, description, create_time, payer_id, group_expense_id )
	VALUES ( null, 234.21, "Cena", now(), 1, 1);
INSERT INTO expense ( id, amount, description, create_time, payer_id, group_expense_id )
	VALUES ( null, 12.31, "Comida", now(), 4, 1);
INSERT INTO expense ( id, amount, description, create_time, payer_id, group_expense_id )
	VALUES ( null, 234.21, "Cine", now(), 3, 2);
INSERT INTO expense ( id, amount, description, create_time, payer_id, group_expense_id )
	VALUES ( null, 12.31, "Compra", now(), 2, 2);