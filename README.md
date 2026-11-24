# API de Gimnasio

API REST para administrar socios, basada en el ejercicio 5 del enunciado y con la estructura del proyecto veterinaria.

## Ejecutar

Requiere Java 17 y Maven. Desde esta carpeta:

```bash
mvn spring-boot:run
```

La API queda en `http://localhost:9090`. H2 Console: `http://localhost:9090/h2-console`, JDBC URL `jdbc:h2:file:./data/gimnasio`, usuario `sa`, contraseña vacía. La base queda en archivos y conserva los datos al reiniciar.

## Entidad Socio

| Campo | Tipo | Ejemplo |
|---|---|---|
| id | int (autogenerado) | 1 |
| nombre | String | Carlos Mena |
| plan | String | mensual, trimestral o anual |
| mensualidad | double | 30.0 |
| activo | boolean | true |

Ejemplo para crear un socio:

```json
{"nombre":"Carlos Mena","plan":"mensual","mensualidad":30.0,"activo":true}
```

## Endpoints

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/socio` | 200, lista todos |
| GET | `/api/socio/{id}` | 200 o 404 |
| POST | `/api/socio` | 201 |
| PUT | `/api/socio/{id}` | 200 o 404 |
| DELETE | `/api/socio/{id}` | 204 o 404 |
| GET | `/api/socio/activos` | 200, socios activos |
| GET | `/api/socio/plan/{plan}` | 200, socios del plan (sin distinguir mayúsculas) |
| GET | `/api/socio/ingresos` | 200, suma de mensualidades de socios activos |
| GET | `/api/socio/ingresos-por-plan` | 200, suma agrupada por plan |
| PUT | `/api/socio/{id}/activar` | 200, 404 o 409 si ya estaba activo |
| PUT | `/api/socio/{id}/desactivar` | 200, 404 o 409 si ya estaba inactivo |

La colección de Postman está en `postman/Gimnasio.postman_collection.json` e incluye una petición por ruta, con casos de 404 y 409 para cambios de estado.
