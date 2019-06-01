/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  NarF
 * Created: May 31, 2019
 */


CREATE TABLE IF NOT EXISTS mesas (
                            idMesa INTEGER PRIMARY KEY,
                            mesa TEXT NOT NULL UNIQUE,
                            capacidad INTEGER NOT NULL
                        );

CREATE TABLE IF NOT EXISTS categorias (
                            idCategoria INTEGER PRIMARY KEY,
                            categoria TEXT NOT NULL UNIQUE
                        );

CREATE TABLE IF NOT EXISTS ordenes (
                            idOrden INTEGER PRIMARY KEY,
                            apertura TEXT,
                            cierre TEXT,
                            total REAL NOT NULL,
                            idMesa INTEGER NOT NULL REFERENCES mesas (idMesa)
                        );

CREATE TABLE IF NOT EXISTS productos (
                            idProducto INTEGER PRIMARY KEY,
                            producto TEXT NOT NULL UNIQUE,
                            precio REAL NOT NULL,
                            idCategoria INTEGER NOT NULL REFERENCES categorias (idCategoria)
                        );

CREATE TABLE IF NOT EXISTS servidos (
                            idOrden INTEGER,
                            idProducto INTEGER,
                                PRIMARY KEY (idOrden, idProducto),
                                FOREIGN KEY (idOrden) REFERENCES ordenes(idOrden),
                                FOREIGN KEY (idProducto) REFERENCES productos(idProducto)
                        );