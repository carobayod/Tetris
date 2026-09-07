import java.util.Arrays;

public class Tetris {

    public static final int FILAS = 20;
    public static final int COLUMNAS = 10;
    public static final int VACIO = 0;
    public static final int PUNTOS_POR_LINEA = 100;

    private static final int TAMANIO_PIEZA = 4;

    private int[][] tablero;
    private PiezaTetris piezaActual;
    private int filaPieza;
    private int colPieza;
    private int puntaje;
    private boolean juegoTerminado;

    public Tetris() {
        tablero = new int[FILAS][COLUMNAS];
        puntaje = 0;
        juegoTerminado = false;
        generarNuevaPieza();
    }

    public void nuevoJuego() {
        tablero = new int[FILAS][COLUMNAS];
        puntaje = 0;
        juegoTerminado = false;
        generarNuevaPieza();
    }

    public void generarNuevaPieza() {
        piezaActual = PiezaTetris.crearAleatoria();
        filaPieza = 0;
        colPieza = (COLUMNAS - TAMANIO_PIEZA) / 2;
        juegoTerminado = hayChoque(piezaActual.getForma(), filaPieza, colPieza);
    }

    public boolean bajar() {
        if (juegoTerminado) return false;
        if (!hayChoque(piezaActual.getForma(), filaPieza + 1, colPieza)) {
            filaPieza++;
            return true;
        }
        fijarPieza();
        eliminarLineasCompletas();
        generarNuevaPieza();
        return false;
    }

    public boolean moverIzquierda() {
        if (juegoTerminado) return false;
        if (hayChoque(piezaActual.getForma(), filaPieza, colPieza - 1)) return false;
        colPieza--;
        return true;
    }

    public boolean moverDerecha() {
        if (juegoTerminado) return false;
        if (hayChoque(piezaActual.getForma(), filaPieza, colPieza + 1)) return false;
        colPieza++;
        return true;
    }

    public boolean girar() {
        if (juegoTerminado) return false;
        PiezaTetris rotada = piezaActual.rotada();
        if (hayChoque(rotada.getForma(), filaPieza, colPieza)) return false;
        piezaActual = rotada;
        return true;
    }

    public int eliminarLineasCompletas() {
        int eliminadas = 0;
        for (int f = FILAS - 1; f >= 0; f--) {
            boolean completa = true;
            for (int c = 0; c < COLUMNAS; c++) {
                if (tablero[f][c] == VACIO) {
                    completa = false;
                    break;
                }
            }
            if (completa) {
                for (int k = f; k > 0; k--) {
                    System.arraycopy(tablero[k - 1], 0, tablero[k], 0, COLUMNAS);
                }
                Arrays.fill(tablero[0], VACIO);
                eliminadas++;
                f++;
            }
        }
        puntaje += eliminadas * PUNTOS_POR_LINEA;
        return eliminadas;
    }

    private boolean hayChoque(int[][] forma, int fila, int col) {
        for (int f = 0; f < forma.length; f++) {
            for (int c = 0; c < forma[0].length; c++) {
                if (forma[f][c] == VACIO) continue;
                int rf = fila + f;
                int rc = col + c;
                if (rf < 0 || rf >= FILAS || rc < 0 || rc >= COLUMNAS) return true;
                if (tablero[rf][rc] != VACIO) return true;
            }
        }
        return false;
    }

    private void fijarPieza() {
        int[][] forma = piezaActual.getForma();
        for (int f = 0; f < forma.length; f++) {
            for (int c = 0; c < forma[0].length; c++) {
                if (forma[f][c] == VACIO) continue;
                tablero[filaPieza + f][colPieza + c] = piezaActual.getTipo();
            }
        }
    }

    public int[][] getTablero() {
        return tablero;
    }

    public PiezaTetris getPiezaActual() {
        return piezaActual;
    }

    public int getFilaPieza() {
        return filaPieza;
    }

    public int getColPieza() {
        return colPieza;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public int getCelda(int fila, int columna) {
        return tablero[fila][columna];
    }

    public void setCelda(int fila, int columna, int valor) {
        tablero[fila][columna] = valor;
    }
}