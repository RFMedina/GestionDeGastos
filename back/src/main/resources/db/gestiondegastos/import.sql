
--USUARIOS

INSERT INTO usuario (nombre,contrasenya,correo,telefono,modo_oscuro) VALUES ('Raul','$2a$10$PkHebxbh3IMniYV09VtQNu32xDLX8yZLkO0JX1/GNqjvyR0wMY7JS','RaulHiberus@gmail.com','665379845',1);
INSERT INTO usuario (nombre,contrasenya,correo,telefono,modo_oscuro) VALUES ('Diego','$2a$10$YQAzwUghK2aSkISs3ok3Cu4Ha4gcebPisgp6qlOcUjvpI4lbfr2QK','DiegoHiberus@gmail.com','655352261',0);
INSERT INTO usuario (nombre,contrasenya,correo,telefono,modo_oscuro) VALUES ('Sofia','$2a$10$Vj6jJOvVEtWqkIBWx5KIjuB3hkIcc0567UUblBbnoPXCM4n1mMMe6','SofiaHiberus@gmail.com','722161974',1);
INSERT INTO usuario (nombre,contrasenya,correo,telefono,modo_oscuro) VALUES ('Martin','$2a$10$H3yBhQpiyjqG02boZPcwlu7l5Je3Bu0E6N6mFb0Td4lxQT0GtyXW2','MartinHiberus@gmail.com','677826971',1);
INSERT INTO usuario (nombre,contrasenya,correo,telefono,modo_oscuro) VALUES ('Jose María','$2a$10$R/YwN2b8AKfwxu0upaIA/Onufw7A5wCKZfgDc87sIFuFKdn8CSt26','JosemaHiberus@gmail.com','646582521',0);

--#CONTACTOS#
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (1,2);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (1,3);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (2,1);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (4,5);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (4,2);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (4,1);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (5,1);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (5,2);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (5,3);
INSERT INTO contactos (usuarioHost,usuarioInv) VALUES (5,4);

--#GRUPOS#

INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Mi Grupo', CURDATE());
INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Mi Grupo', CURDATE());
INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Mi Grupo', CURDATE());
INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Mi Grupo', CURDATE());
INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Mi Grupo', CURDATE());
INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Grupo Diego-Raul-Josema', CURDATE());
INSERT INTO grupo (nombre,fecha_creacion) VALUES ('Grupo Sofi-Martin', CURDATE());

--#USUARIO-GRUPO# 0=false >=0=true

INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (1,1,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (2,2,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (3,3,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (4,4,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (5,5,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (1,6,0);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (2,6,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (5,6,0);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (3,7,1);
INSERT INTO usuario_grupo (id_usuario,id_grupo,rol) VALUES (4,7,0);

--#MOVIMIENTO# categoria: Entretenimiento, Alimentacion, Ropa, Hogar, Higiene, Transporte, Comunicacion, Medicina, Regalo, Mascota

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-12.0,'Alimentacion','Compra de 5 yatekomo',CURDATE(),7);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (12.0,'Alimentacion','Vendi 5 yatekomo caducados',CURDATE(),9);

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-50,'Hogar','Aspiradora Turbo3000',CURDATE(),1);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-9.0,'Entretenimiento','2 Entradas Cruella',CURDATE(),2);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+1200.75,'Hogar','PAY-DAY BABY',CURDATE(),3);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-40,'Ropa','Pantalones Talla L y Camisa',CURDATE(),4);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-10,'Transporte','Recarga de tarjeta bus',CURDATE(),5);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+30,'Regalo','Propina de la abuela <3',CURDATE(),6);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (1.50,'Transporte','Monedas en el suelo, que suerte',CURDATE(),7);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-11.90,'Regalo','Libro de Stephen King para Paloma',CURDATE(),8);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-1.80,'Alimentacion','Cafe con churros en Hiberus',CURDATE(),9);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+4.50,'Hogar','Deuda de mi hermano',CURDATE(),10);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-45.50,'Entretenimiento','Cascos New Skill Kimera V2',CURDATE(),6);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+600.65,'Comunicacion','Resolución juicio del bar',CURDATE(),6);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-40.32,'Higiene','Cremas, pasta de dientes etc',CURDATE(),8);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-0.90,'Entretenimiento','Humble Bundle',CURDATE(),7);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-61.20,'Alimentación','Liquidos de vaper',CURDATE(),9);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+120,'Regalo','Cumple YaY',CURDATE(),10);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-0.10,'Alimentacion','Propina del café',CURDATE(),10);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-102.80,'Comunicación','Factura Orange',CURDATE(),9);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-20,'Transporte','Gasolina',CURDATE(),5);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+5.50,'Regalo','Tarjeta reagalo hipercor',CURDATE(),6);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-60,'Alimentacion','Carro de la compra',CURDATE(),7);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-100,'Alimentacion','Café con "descuento" de Hiberus',CURDATE(),10);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+20,'Higiene','Arreglar tuberia',CURDATE(),9);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-3.00,'Transporte','Mas viajes de tranvia :(',CURDATE(),8);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-30.50,'Alimentacion','Cena UDON',CURDATE(),6);

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-30.50,'Entretenimiento','Nintendo Switch',CURDATE(),1);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+30.50,'Entretenimiento','Cancelación "Nintendo Switch"',CURDATE(),1);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (5,'Regalo','Reintegro cupon ONCE',CURDATE(),1);

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-20.50,'Entretenimiento','Dark Souls REMASTERED',CURDATE(),2);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (1000,'Hogar','Beca grado NICE',CURDATE(),2);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-60,'Transporte','Bono tranvia TUZSA',CURDATE(),2);

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-15.70,'Medicina','Pastillas, vitaminas etc',CURDATE(),3);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-1.50,'Transporte','Viaje bus sencillo',CURDATE(),3);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (+250.1,'Medicina','PLUS lesiones de servicio',CURDATE(),3);

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-300,'Hogar','Frigorifico antifrost',CURDATE(),4);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-4.5,'Hogar','FLEXICINTA',CURDATE(),4);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (100,'Comunicacion','Indemnización toxicidad FLEXICINTA',CURDATE(),4);

INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (120,'Mascota','Concurso de belleza Perro',CURDATE(),5);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-25,'Mascota','Miscelánea para mi doggo',CURDATE(),5);
INSERT INTO movimiento (cantidad,categoria,concepto,fecha,id_usuario_grupo) VALUES (-100,'Comunicacion','Daños y perjuicios FLEXICINTA',CURDATE(),5);

--#PRESUPUESTOS#
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (100,55,"2021-09-15","2021-09-22",1);
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (220,1130.5,"2021-09-15","2021-09-22",2);
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (200,1633.65,"2021-09-15","2021-09-22",3);
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (300.50,56,"2021-09-15","2021-09-22",4);
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (355,340,"2021-09-15","2021-09-22",5);
--Calculos para cantidad_final en revisión (si no sabes a que me refiero, no deberias estar aqui :<D)
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (1000,1433.13,"2021-09-15","2021-09-29",6);
INSERT INTO presupuesto (cantidad_inicio,cantidad_final,fecha_inicio,fecha_final,id_grupo) VALUES (185.5,76.1,"2021-09-15","2021-09-29",7); 
