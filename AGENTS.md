# AGENTS.md — Tetris

Juego de **Tetris** en Java Swing. Nació de una sesión de opencode, el
2026-09-06, en un solo commit.

> **Este repo es el más joven de los cuatro** (Triqui → Damas → Ajedrez →
> Tetris). Hereda el patrón de los anteriores: Swing, IA separada de la
> lógica, pruebas unitarias con JUnit 5. **No tiene** la documentación de
> pipeline que sí tiene Triqui. Si algún día hay un libro o video de este
> juego, este es el punto de partida.

---

## Fuente única

- `src/main/java/Tetris.java` — **la lógica** (tablero, movimiento, gravedad,
  borrado de líneas, puntaje, fin de juego). Sin Swing. Es la clase testeable.
- `src/main/java/TableroTetris.java` — **la ventana** (JFrame, botones,
  teclado, temporizador, pintar). Llama a `Tetris` y refleja su estado.
- `src/main/java/PiezaTetris.java` — **la pieza** (las 7 formas, rotación,
  color, pieza aleatoria).

Regla: la lógica nunca toca Swing; la ventana nunca decide reglas. Si hay una
decisión de juego ("¿esta jugada es válida?"), va en `Tetris.java`.

## Compilar, probar, ejecutar

```bash
mvn clean package                      # compila y corre las pruebas (10 tests)
java -jar target/tetris-1.0.jar        # ejecuta el juego
```

Java 17+, Maven. Las pruebas son JUnit 5 (`junit-jupiter` 5.10.2).

Para ver el resultado de los tests:
```bash
rg 'Tests run' target/surefire-reports/*.txt
```

## Arquitectura (nombres reales)

| Clase | Rol | Detalle |
|---|---|---|
| `Tetris` | lógica | tablero `int[20][10]`, pieza actual, posición, puntaje |
| `PiezaTetris` | valor | `int[4][4]` (siempre 4×4, con ceros), tipo 1–7, rotación |
| `TableroTetris` | vista | `JFrame` + `ActionListener`, `JButton[20][10]` para pintar |

Constantes que importan (`Tetris.java`): `FILAS=20`, `COLUMNAS=10`,
`VACIO=0`, `PUNTOS_POR_LINEA=100`, y `TAMANIO_PIEZA=4` (las piezas son
matrices 4×4 con ceros alrededor).

Los 7 tetrominós (I, O, T, S, Z, J, L) están en `PiezaTetris.BASES[]`;
`crearAleatoria()` saca un tipo 1–7. La rotación (`rotada()`) gira la matriz
4×4 y por eso las 4 rotaciones vuelven al original (hay un test de eso).

## Controles

| Tecla | Acción |
|---|---|
| `←` `→` | mover la pieza |
| `↓` | bajar la pieza |
| `↑` / `Espacio` | girar |
| `P` | pausa |

## Convenciones

- Estilo **español**: variables `iTamanio`, `mBotones`, `bFinJuego`; los
  comentarios y los mensajes de la interfaz en español.
- `TableroTetris` **pinta con `JButton`** (no con un canvas): cada celda es
  un botón. Cambiar a `JPanel`+`paintComponent` es un redesign, no un fix.
- La gravedad es un `javax.swing.Timer` en la vista, **no** en la lógica: la
  lógica solo baja la pieza cuando se le llama `bajar()`.

## Pruebas (10, todas verdes)

`TestTetris` (7): tablero inicial, no salir del tablero, la pieza cae y se
fija, línea completa suma puntos, línea incompleta no, dos líneas suman doble,
fin de juego cuando la pieza no cabe.

`TestPiezaTetris` (3): cuatro rotaciones vuelven al original, todas las
piezas tienen 4 celdas, la rotación mantiene el número de celdas.

## Estado y qué falta

- Repo limpio, un solo commit (`9412d76`).
- **Falta** si esto se quiere convertir en producto: manual, portada, diagrama
  de clases (los otros tres juegos los tienen). La lógica está lista; la
  documentación no.
- Si se agrega algo, **commit con mensaje en español al estilo de los otros
  repos** (`feat:`, `fix:`, `test:`, `refactor:`), y avisar en el README.

## Ver también

- Repos hermanos: `../Triqui` (el único con libro y `AGENTS.md` completo),
  `../Damas`, `../Ajedrez`.
- `README.md` — descripción y controles (ya existe).