# Tetris

Juego de **Tetris** en Java con interfaz gráfica **Swing**.

## Características

- Tablero de **10 x 20** con las 7 piezas (tetrominós) a color
- Gravedad con temporizador, rotación y movimiento con el teclado
- Borrado de líneas y puntuación (100 puntos por línea)
- Fin de juego al no poder colocar una nueva pieza, con botón de "Nuevo juego"
- Lógica del juego separada y testeable (`Tetris.java`, `PiezaTetris.java`)
- Pruebas unitarias con **JUnit 5** (10 tests)

## Controles

| Tecla | Acción |
|---|---|
| `←` `→` | Mover la pieza |
| `↓` | Bajar la pieza |
| `↑` / `Espacio` | Girar la pieza |
| `P` | Pausa |

## Compilar y ejecutar

Requisitos: Java 17+ y Maven.

```bash
mvn clean package          # compila y ejecuta las pruebas
java -jar target/tetris-1.0.jar   # ejecuta el juego
```

## Probar

```bash
mvn test
```