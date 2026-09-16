# ECOLIM

Aplicación móvil Android para la gestión de productos de **ECOLIM S.A.C.**, desarrollada como parte del proyecto final del curso de Software Engineering with AI (SENATI). Consume el webservice **WSECOLIM**.

## Descripción técnica

ECOLIM permite registrar, listar, buscar, actualizar y eliminar productos, consumiendo un webservice REST (WSECOLIM) mediante peticiones HTTP. A diferencia del proyecto anterior (SINFO), esta app utiliza una arquitectura de **una sola Activity con múltiples Fragments**, navegando mediante una barra de navegación inferior (`BottomNavigationView`), similar al patrón usado en apps como WhatsApp.

### Flujo de navegación

```
Splash (2 seg) → Login → Home (Listar / Buscar / Registrar)
```

Desde **Home**, la barra inferior permite cambiar entre tres Fragments sin salir de la misma pantalla:
- **Listar** (`ListarFragment`) — lista de productos con `RecyclerView`
- **Buscar** (`BuscarFragment`) — buscar un producto por ID, con opción de actualizar o eliminar
- **Registrar** (`RegistrarFragment`) — formulario para dar de alta un nuevo producto

## Tecnologías utilizadas

- **Java** (Android Studio)
- **Volley** — librería para consumir el webservice (peticiones HTTP asíncronas)
- **RecyclerView** — para el listado de productos
- **Fragments + BottomNavigationView (Material Components)** — navegación tipo pestañas dentro de una sola Activity
- **JSON** (org.json) — parseo de las respuestas del webservice

## Arquitectura del proyecto

| Componente | Rol |
|---|---|
| `Splash` | Pantalla de bienvenida (2 seg), primera en abrirse |
| `Login` | Autenticación (credenciales estáticas en esta versión) |
| `Home` | Única Activity tras el login — contiene el `FragmentContainerView` y la `BottomNavigationView` |
| `fragments/ListarFragment` | Lista de productos (`RecyclerView`) — consume `GET /productos` |
| `fragments/BuscarFragment` | Busca un producto por ID (`GET /productos/:id`), permite actualizar (`PUT`) o eliminar (`DELETE`) |
| `fragments/RegistrarFragment` | Formulario de alta — envía un `POST` al webservice |
| `adapters/AdapterProductos` | Adapter del `RecyclerView`, enlaza cada `Producto` con su vista (`item_producto.xml`) |
| `model/Producto` | Clase modelo — representa un producto con sus atributos (nombre, categoría, descripción, garantía, precio, stock) |

### Comunicación entre Home y los Fragments

`Home` usa el `FragmentManager` (`getSupportFragmentManager()`) para reemplazar el Fragment visible dentro del `FragmentContainerView` cada vez que el usuario toca una pestaña de la `BottomNavigationView`, mediante `beginTransaction().replace(...).commit()`. La barra inferior permanece fija y visible en todo momento; solo cambia el contenido de arriba.

### Comunicación con el webservice

Cada Fragment que consume el WS crea su propia `RequestQueue` de Volley (usando `requireContext()`, ya que un Fragment no es un `Context` por sí mismo) y apunta al endpoint base:
```
http://localhost:3000/productos
```
> Para probar en el **emulador de Android Studio**, es necesario ejecutar `adb reverse tcp:3000 tcp:3000` antes de correr la app, para que `localhost` dentro del emulador redirija correctamente al servidor corriendo en la PC. Este comando debe repetirse cada vez que se reinicia el emulador.

### Validación

- `RegistrarFragment` valida que nombre, categoría y precio no estén vacíos antes de enviar el formulario.
- `BuscarFragment` guarda el ID del producto actualmente mostrado y muestra un diálogo de confirmación antes de actualizar o eliminar.

## Tema visual

La app usa una paleta de tonos rosa (definida en `res/values/colors.xml`), aplicada al Login, Splash y la barra de navegación inferior, junto con un ícono de launcher personalizado.

## Estado actual

- [x] CRUD funcional (Registrar, Listar, Buscar/Actualizar/Eliminar)
- [x] Consumo del webservice mediante Volley
- [x] Arquitectura de Fragments + BottomNavigationView
- [x] Login (credenciales estáticas) y Splash
- [x] Validación de campos obligatorios
- [x] Tema visual personalizado (colores + ícono de launcher)

## Requisitos para ejecutar

- Android Studio
- El webservice **WSECOLIM** debe estar corriendo (`node index.js`)
- Si se usa el emulador: ejecutar `adb reverse tcp:3000 tcp:3000` antes de correr la app
