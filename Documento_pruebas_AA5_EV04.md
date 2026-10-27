# Informe de pruebas de la API ShopMaster

**Evidencia:** GA7-220501096-AA5-EV04  
**Aprendiz:** Sebastián Veloza  
**Herramienta:** Postman  
**Servidor local:** `http://localhost:8080`

## Objetivo

Comprobar con Postman los servicios web desarrollados para el proyecto ShopMaster: catálogo, pedidos, administración de productos y autenticación.

## Resultados observados

| N.º | Solicitud | Resultado observado | Verificación |
|---:|---|---|---|
| 1 | `GET /api/productos` | `200 OK` | La API devolvió el catálogo. |
| 2 | `GET /api/categorias` | `200 OK` | La API devolvió las categorías. |
| 3 | `GET /api/productos/1` | `200 OK` | La API devolvió el detalle del producto. |
| 4 | `POST /api/pedidos` | `201 Created` | Se creó el pedido de demostración. |
| 5 | `GET /api/pedidos/{id}` | `200 OK` | Se consultó el pedido creado. |
| 6 | `GET /api/admin/productos` | `200 OK` | Se listaron productos para administración. |
| 7 | `POST /api/admin/productos` | `201 Created` | Se creó el producto temporal de prueba. |
| 8 | `PUT /api/admin/productos/{id}` | `200 OK` | Se actualizó el producto temporal. |
| 9 | `DELETE /api/admin/productos/{id}` | `204 No Content` | Se eliminó el producto temporal; es normal que la respuesta no incluya cuerpo. |
| 10 | `POST /api/auth/register` | `201 Created` | Se registró el usuario temporal. |
| 11 | `POST /api/auth/login` | `200 OK` | La autenticación fue satisfactoria. |

Las pruebas se ejecutaron con la colección `Postman/ShopMaster-AA5-EV04.postman_collection.json`. Las respuestas anteriores fueron observadas en Postman durante la ejecución guiada.

## Evidencias en capturas

Guardar una captura de cada solicitud en `Capturas_Postman`. En cada imagen procura mostrar la URL, el método, el estado HTTP y el cuerpo de respuesta. Usa estos nombres:

1. `01-listar-productos.png`
2. `02-consultar-categorias.png`
3. `03-detalle-producto.png`
4. `04-crear-pedido.png`
5. `05-consultar-pedido.png`
6. `06-listar-admin.png`
7. `07-crear-producto.png`
8. `08-actualizar-producto.png`
9. `09-eliminar-producto.png`
10. `10-registrar-usuario.png`
11. `11-iniciar-sesion.png`

## Conclusión

Las respuestas observadas indican que los servicios probados de ShopMaster atendieron correctamente las solicitudes de catálogo, pedidos, administración y autenticación en el entorno local.

## Alcance de la demostración

Los productos, usuarios y pedidos se guardan en memoria y se reinician al apagar la aplicación. El pago es de demostración; no se procesa una transacción real.
