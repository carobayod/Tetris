import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestTetris {

    @Test
    public void tableroInicialVacioYPiezaCentrada() {
        Tetris juego = new Tetris();
        for (int f = 0; f < Tetris.FILAS; f++) {
            for (int c = 0; c < Tetris.COLUMNAS; c++) {
                assertEquals(Tetris.VACIO, juego.getCelda(f, c));
            }
        }
        assertEquals(0, juego.getFilaPieza());
        assertEquals((Tetris.COLUMNAS - 4) / 2, juego.getColPieza());
        assertFalse(juego.isJuegoTerminado());
    }

    @Test
    public void movimientoLateralNoSaledelTablero() {
        Tetris juego = new Tetris();
        for (int i = 0; i < Tetris.COLUMNAS; i++) {
            juego.moverIzquierda();
        }
        assertEquals(0, juego.getColPieza());
        assertFalse(juego.moverIzquierda());

        for (int i = 0; i < Tetris.COLUMNAS; i++) {
            juego.moverDerecha();
        }
        assertTrue(juego.getColPieza() < Tetris.COLUMNAS);
        assertFalse(juego.moverDerecha());
    }

    @Test
    public void piezaCaeYSeFijaEnTablero() {
        Tetris juego = new Tetris();
        for (int i = 0; i < 24; i++) {
            juego.bajar();
        }
        assertFalse(juego.isJuegoTerminado());
        int celdasLlenas = 0;
        for (int f = 0; f < Tetris.FILAS; f++) {
            for (int c = 0; c < Tetris.COLUMNAS; c++) {
                if (juego.getCelda(f, c) != Tetris.VACIO) celdasLlenas++;
            }
        }
        assertTrue(celdasLlenas >= 4);
    }

    @Test
    public void eliminarLineaCompletaSumaPuntos() {
        Tetris juego = new Tetris();
        for (int c = 0; c < Tetris.COLUMNAS; c++) {
            juego.setCelda(Tetris.FILAS - 1, c, PiezaTetris.TIPO_O);
        }
        int eliminadas = juego.eliminarLineasCompletas();
        assertEquals(1, eliminadas);
        assertEquals(Tetris.PUNTOS_POR_LINEA, juego.getPuntaje());
        for (int c = 0; c < Tetris.COLUMNAS; c++) {
            assertEquals(Tetris.VACIO, juego.getCelda(Tetris.FILAS - 1, c));
        }
    }

    @Test
    public void lineaIncompletaNoSeElimina() {
        Tetris juego = new Tetris();
        for (int c = 0; c < Tetris.COLUMNAS - 1; c++) {
            juego.setCelda(Tetris.FILAS - 1, c, PiezaTetris.TIPO_I);
        }
        assertEquals(0, juego.eliminarLineasCompletas());
        assertEquals(0, juego.getPuntaje());
    }

    @Test
    public void dosLineasCompletasSumaDoble() {
        Tetris juego = new Tetris();
        for (int c = 0; c < Tetris.COLUMNAS; c++) {
            juego.setCelda(Tetris.FILAS - 1, c, PiezaTetris.TIPO_Z);
            juego.setCelda(Tetris.FILAS - 2, c, PiezaTetris.TIPO_J);
        }
        assertEquals(2, juego.eliminarLineasCompletas());
        assertEquals(2 * Tetris.PUNTOS_POR_LINEA, juego.getPuntaje());
    }

    @Test
    public void finDeJuegoCuandoLaPiezaNoCabe() {
        Tetris juego = new Tetris();
        for (int f = 0; f < 2; f++) {
            for (int c = 0; c < Tetris.COLUMNAS; c++) {
                juego.setCelda(f, c, PiezaTetris.TIPO_T);
            }
        }
        juego.generarNuevaPieza();
        assertTrue(juego.isJuegoTerminado());
    }
}