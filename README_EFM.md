# Halloween_Terror — README completo

Última actualización: 2025-11-25

Índice
- Resumen
- Tecnologías y dependencias
- Estructura del repositorio
- Capturas de la aplicación
- Cómo compilar y ejecutar
- Cómo ejecutar pruebas
- Flujo detallado de la aplicación
- Casos de uso y comportamiento (qué hace el programa en cada caso)
- Arquitectura y descripción detallada por clases (métodos y responsabilidades)
- Manejo de recursos e imágenes
- Errores comunes y resolución
- Siguiente paso (para documentar con el código real)
- Contribuir y contacto
- Licencia

---

Resumen
-------
Halloween_Terror es una aplicación Java con interfaz gráfica (Swing) cuyo objetivo es ofrecer una experiencia temática de Halloween con un formulario de entrada de usuario y un módulo de "Ruleta del Terror" (Truco/Trato). El README explica con detalle el flujo desde el inicio hasta la ruleta, qué hace la aplicación en cada caso y las responsabilidades de cada clase propuesta.

Tecnologías y dependencias
-------------------------
- Java 11+ (recomendado)
- Maven (incluye mvnw / mvnw.cmd)
- Java Swing para la GUI
- Recursos gráficos en `src/main/resources` o `resources/`
- Dependencias adicionales (revisar `pom.xml`)

Estructura del repositorio
--------------------------
- README.md — Este archivo.
- Halloween_Terror/
  - pom.xml
  - mvnw, mvnw.cmd
  - src/
    - main/
      - java/...
      - resources/... (imágenes, iconos, audio)
    - test/
  - docs/
    - screenshot.png (pantalla de inicio)
    - ruleta.png (pantalla de la ruleta)
  - target/

Capturas de la aplicación
-------------------------
A continuación se muestran las dos capturas que describen el flujo visual principal de la app. Coloca las imágenes en `docs/` con los nombres indicados.

1) Pantalla de información / registro (Login)
![Captura de la aplicación Halloween_Terror (Login)](docs/screenshot.png)

2) Pantalla Ruleta del Terror (Truco / Trato)
![Ruleta del Terror](docs/ruleta.png)

Cómo compilar y ejecutar
------------------------
1. Clona el repositorio:
   - git clone https://github.com/user-efm0009/Tarea_Modulo_5_EFM.git

2. Accede al módulo:
   - cd Tarea_Modulo_5_EFM/Halloween_Terror

3. Compilar:
   - UNIX/macOS: ./mvnw clean package
   - Windows: mvnw.cmd clean package

4. Ejecutar:
   - java -jar target/<nombre-del-jar>.jar
   - O usando Maven exec si no hay jar ejecutable:
     - ./mvnw exec:java -Dexec.mainClass="com.halloween.Main"

Cómo ejecutar pruebas
--------------------
- ./mvnw test
- Resultados en `target/surefire-reports/`.

Flujo detallado de la aplicación
--------------------------------
1. Inicio (Main)
   - Se configura el Look & Feel (opcional) y se lanza el hilo de GUI: SwingUtilities.invokeLater.
   - Se carga AppConfig (rutas, textos, lista de cursos) y recursos iniciales.

2. LoginFrame (pantalla de información)
   - Muestra formulario: Nombre, Apellidos, Curso (JComboBox) y botón "Entrar si te atreves".
   - Al pulsar Entrar:
     - validateForm() valida campos.
     - Si falla: se muestra un JOptionPane o un JLabel con error, no se avanza.
     - Si pasa: se crea Player con los datos y se notifica al LoginController.

3. Transición a Ruleta
   - LoginController abre RuletaFrame (o MenuPrincipal que contiene botón para abrir Ruleta).
   - Se pasa el objeto Player a la siguiente ventana.

4. RuletaFrame (Ruleta del Terror)
   - Presenta la ruleta gráfica dividida en secciones (p. ej. Truco / Trato).
   - Botón "Girar ruleta":
     - Lanza animación de giro (timer/Thread).
     - Determina aleatoriamente resultado (con probabilidad configurable).
     - Muestra resultado en diálogo y lo registra (puntuación, historial).
   - Después del giro: se puede ofrecer opción de volver al menú, salir o jugar otra vez.

5. Persistencia opcional
   - Guardado de sesiones/puntuaciones en fichero JSON/CSV si está implementado.

Casos de uso y comportamiento — qué hace el programa en cada caso
-----------------------------------------------------------------
Caso 1 — Usuario válido
- Entrada: Nombre y Apellidos no vacíos, Curso seleccionado.
- Resultado: Se crea Player, se abre la Ruleta y se puede interactuar con ella.

Caso 2 — Campos vacíos
- Comportamiento: Se muestra mensaje de error con instrucciones; botón Entrar no procede.

Caso 3 — Recurso faltante (imagen o icono)
- Comportamiento: ResourceLoader devuelve un placeholder; la aplicación sigue funcionando y se loguea la incidencia.

Caso 4 — Error en ejecución (I/O, NullPointer)
- Comportamiento: Captura de excepción en controladores; muestra diálogo de error y, si procede, guarda traza en log. Evita crash completo cuando sea posible.

Arquitectura y descripción detallada por clases
-----------------------------------------------
A continuación se detalla un diseño recomendado y la funcionalidad de cada clase. Ajusta nombres si tu código usa otros distintos.

- com.halloween.Main
  - Responsabilidad:
    - Punto de entrada de la app.
    - Carga configuración y lanza la GUI en el hilo correcto.
  - Métodos:
    - public static void main(String[] args)
      - loadConfig(); SwingUtilities.invokeLater(() -> new LoginFrame(new LoginController()));

- com.halloween.config.AppConfig
  - Responsabilidad:
    - Leer `config.properties` o valores por defecto (lista de cursos, probabilidades de ruleta).
  - Métodos:
    - public static void load()
    - public static List<String> getCursos()
    - public static double getProbabilidadTruco() / getProbabilidadTrato()

- com.halloween.model.Player
  - Atributos:
    - private String nombre;
    - private String apellidos;
    - private String curso;
    - private LocalDateTime fechaRegistro;
  - Métodos:
    - constructor, getters/setters, toString()

- com.halloween.ui.LoginFrame (extends JFrame)
  - Componentes:
    - JTextField txtNombre
    - JTextField txtApellidos
    - JComboBox<String> comboCurso
    - JButton btnEntrar
    - JLabel lblError
  - Métodos:
    - public LoginFrame(LoginController controller)
    - private void initComponents()
    - private boolean validateForm()
  - Eventos:
    - btnEntrar.addActionListener(e -> controller.onEntrar(nombre, apellidos, curso))

- com.halloween.controller.LoginController
  - Responsabilidad:
    - Gestionar la lógica de entrada desde LoginFrame.
  - Métodos:
    - public void onEntrar(String nombre, String apellidos, String curso)
      - Valida campos (usando ValidationUtils).
      - Si OK: Player p = new Player(...); new RuletaFrame(p, new RuletaController(p));
      - Si no OK: pedir a la vista que muestre error.

- com.halloween.ui.RuletaFrame (extends JFrame)
  - Componentes:
    - JPanel ruletaPanel (pintado custom con Graphics2D)
    - JButton btnGirar
    - JLabel lblResultado
  - Métodos:
    - public RuletaFrame(Player player, RuletaController controller)
    - private void startSpinAnimation()
    - private void stopSpinAndShowResult(String resultado)
  - Animación:
    - Usa javax.swing.Timer o un Thread para animar el giro de la ruleta; al terminar se delega en el controller para calcular el resultado.

- com.halloween.controller.RuletaController
  - Responsabilidad:
    - Lógica de la ruleta: cálculo de resultado, probabilidades, registro en historial.
  - Métodos:
    - public void onGirar()
      - Genera número aleatorio y determina "Truco" o "Trato".
      - Registra resultado (opcionalmente guarda en fichero).
      - Notifica a la vista para mostrar el resultado.

- com.halloween.util.ResourceLoader
  - Métodos:
    - public static ImageIcon loadIcon(String path)
    - public static BufferedImage loadImage(String path)
  - Comportamiento:
    - Busca recurso en classpath (getResourceAsStream), si no existe devuelve placeholder y registra.

- com.halloween.util.ValidationUtils
  - Métodos:
    - public static boolean isNotEmpty(String s)
    - public static boolean isValidName(String s) // opcional con regex

- com.halloween.persistence.SessionStore (opcional)
  - Responsabilidad:
    - Guardar/leer sesiones o puntuaciones (JSON, CSV).
  - Métodos:
    - public void saveResult(Player p, String result)
    - public List<Result> loadResults()

Ejemplos de métodos y pseudocódigo
---------------------------------
LoginController.onEntrar:
- if (!ValidationUtils.isNotEmpty(nombre)) view.showError("Nombre obligatorio");
- else if (!ValidationUtils.isNotEmpty(apellidos)) view.showError("Apellidos obligatorios");
- else { Player p = new Player(nombre, apellidos, curso); view.openRuleta(p); }

RuletaController.onGirar:
- double r = Math.random();
- if (r < probTruco) result = "Truco"; else result = "Trato";
- persistResult(player, result);
- view.showResult(result);

Manejo de recursos e imágenes
----------------------------
- Guarda las capturas y otros recursos en `src/main/resources/docs/` o `docs/` en la raíz del proyecto.
  - docs/screenshot.png  (pantalla login)
  - docs/ruleta.png      (pantalla ruleta)
- Cargar imágenes desde el classpath:
  - ResourceLoader.loadImage("/docs/screenshot.png")
- Fallback: si la imagen no existe, dibujar fondo liso o mostrar texto en lugar de la imagen.

Errores comunes y resolución
----------------------------
- Permisos al ejecutar mvnw: chmod +x mvnw
- Main-Class no especificada: configurar `maven-jar-plugin` o `maven-shade-plugin` para crear jar ejecutable.
- Recursos no encontrados: asegurarse de que están en `src/main/resources` y usar rutas relativas al classpath (`/docs/ruleta.png`).
- Problemas de threading en Swing: siempre manipular componentes UI en el EDT (Event Dispatch Thread) con SwingUtilities.invokeLater.

Siguiente paso (para documentar con el código real)
---------------------------------------------------
Para convertir esta documentación estimada en documentación exacta y completa necesito los archivos fuente principales:
- Main.java
- LoginFrame.java (o clase que construye la ventana de login)
- LoginController.java
- RuletaFrame.java
- RuletaController.java
- Player.java
- ResourceLoader.java (si existe)

Si me pegas el contenido de esas clases, actualizaré inmediatamente este README:
- Añadiré firmas reales de métodos, atributos y ejemplos de uso concretos.
- Añadiré diagramas de flujo simples (ASCII o textual).
- Incluiré ejemplos de entrada/salida y mensajes exactos que muestra la app.

Contribuir y contacto
--------------------
- Para proponer cambios crea una rama `feature/<descripcion>` y abre PR contra `main`.
- Añade tests unitarios para nueva lógica.
- Abre issues para bugs o mejoras.

Licencia
--------
- Añade un archivo LICENSE con la licencia deseada (por ejemplo MIT) si quieres que el proyecto sea reutilizable públicamente.

---

Notas finales
-------------
He agregado la segunda imagen solicitada y ampliado la documentación con un desglose detallado de la funcionalidad y responsabilidades de clases, así como pseudocódigo y flujo de eventos que corresponde con las dos pantallas mostradas. Pega aquí los archivos fuente indicados y completaré el README con la documentación exacta (firmas de métodos, comportamiento preciso, ejemplos de ejecución concretos y recomendaciones específicas de refactorización si procede).