-- 1) Todos los productos del rubro "librería", creados hoy.

select p.* 
from producto p 
inner join rubro r on 
	p.id_rubro = r.id_rubro
where r.rubro = "libreria" and p.fecha_creacion = curdate(); 

-- 2) Monto total vendido por cliente (mostrar nombre del cliente y monto).
select 	c.nombre, 
		sum(v.cantidad * v.precio_unitario) as monto
from cliente c 
inner join venta v on 
	c.id_cliente = v.id_cliente
group by c.id_cliente;

-- 3) Cantidad de ventas por producto
-- CONSULTAR si es cantidad de ventas (id_venta) o cantidades del producto vendido
select 	p.codigo, p.nombre,
		count(v.id_venta) as cantidad_de_ventas
from producto p
inner join venta v on
	p.codigo = v.codigo_producto
group by p.codigo;

-- 4) Cantidad de productos comprados por cliente en el mes actual.
select 	c.nombre, 
		sum(v.cantidad) as cantidad_de_productos_comprados
from cliente c 
inner join venta v on
	c.id_cliente = v.id_cliente
where month(v.fecha) = month(curdate())
group by c.id_cliente;

-- 5) Ventas que tienen al menos un producto del rubro "bazar".
select v.*
from venta v
inner join producto p on
	v.codigo_producto = p.codigo
inner join rubro r on
	r.id_rubro = p.id_rubro
where r.rubro = 'bazar';

-- 6) Rubros que no tienen ventas en los últimos 2 meses
select r.*
from rubro r
where not exists (
	select *
	from producto p
	left join venta v on v.codigo_producto = p.codigo
	where r.id_rubro = p.id_rubro and 
		v.fecha between 
			date_sub(curdate(), interval 60 day)  and 
			curdate()
	);
