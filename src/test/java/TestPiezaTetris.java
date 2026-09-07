import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestPiezaTetris {

    @Test
    public void cuatroRotacionesVuelvenAlOriginal() {
        int[] tipos = {PiezaTetris.TIPO_I, PiezaTetris.TIPO_O, PiezaTetris.TIPO_T,
                PiezaTetris.TIPO_S, PiezaTetris.TIPO_Z, PiezaTetris.TIPO_J, PiezaTetris.TIPO_L};
        for (int tipo : tipos) {
            PiezaTetris pieza = new PiezaTetris(tipo);
            int[][] original = pieza.getForma();
            PiezaTetris rotada = pieza;
            for (int i = 0; i < 4; i++) {
                rotada = rotada.rotada();
            }
            assertArrayEquals(original, rotada.getForma(), "tipo=" + tipo);
        }
    }

    @Test
    public void todasLasPiezasTienenCuatroCeldas() {
        int[] tipos = {PiezaTetris.TIPO_I, PiezaTetris.TIPO_O, PiezaTetris.TIPO_T,
                PiezaTetris.TIPO_S, PiezaTetris.TIPO_Z, PiezaTetris.TIPO_J, PiezaTetris.TIPO_L};
        for (int tipo : tipos) {
            PiezaTetris pieza = new PiezaTetris(tipo);
            int celdas = 0;
            for (int[] fila : pieza.getForma()) {
                for (int celda : fila) {
                    if (celda != Tetris.VACIO) celdas++;
                }
            }
            assertEquals(4, celdas, "tipo=" + tipo);
        }
    }

    @Test
    public void rotacionMantieneCeldas() {
        int[] tipos = {PiezaTetris.TIPO_I, PiezaTetris.TIPO_O, PiezaTetris.TIPO_T,
                PiezaTetris.TIPO_S, PiezaTetris.TIPO_Z, PiezaTetris.TIPO_J, PiezaTetris.TIPO_L};
        for (int tipo : tipos) {
            PiezaTetris pieza = new PiezaTetris(tipo);
            PiezaTetris rotada = pieza.rotada();
            int celdasAntes = contarCeldas(pieza.getForma());
            int celdasDespues = contarCeldas(rotada.getForma());
            assertEquals(celdasAntes, celdasDespues, "tipo=" + tipo);
            assertEquals(pieza.getTipo(), rotada.getTipo());
        }
    }

    private int contarCeldas(int[][] forma) {
        int celdas = 0;
        for (int[] fila : forma) {
            for (int celda : fila) {
                if (celda != Tetris.VACIO) celdas++;
            }
        }
        return celdas;
    }
}