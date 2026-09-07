import java.awt.Color;
import java.util.Random;

public class PiezaTetris {

    public static final int TIPO_I = 1;
    public static final int TIPO_O = 2;
    public static final int TIPO_T = 3;
    public static final int TIPO_S = 4;
    public static final int TIPO_Z = 5;
    public static final int TIPO_J = 6;
    public static final int TIPO_L = 7;

    private static final int[][][] BASES = new int[8][][];
    private static final Color[] COLORES = new Color[8];

    static {
        BASES[TIPO_I] = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        BASES[TIPO_O] = new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}};
        BASES[TIPO_T] = new int[][]{
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        BASES[TIPO_S] = new int[][]{
                {0, 1, 1, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        BASES[TIPO_Z] = new int[][]{
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        BASES[TIPO_J] = new int[][]{
                {1, 0, 0, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        BASES[TIPO_L] = new int[][]{
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};

        COLORES[TIPO_I] = new Color(0, 224, 224);
        COLORES[TIPO_O] = new Color(255, 224, 0);
        COLORES[TIPO_T] = new Color(160, 96, 224);
        COLORES[TIPO_S] = new Color(96, 224, 96);
        COLORES[TIPO_Z] = new Color(224, 32, 32);
        COLORES[TIPO_J] = new Color(64, 112, 224);
        COLORES[TIPO_L] = new Color(240, 128, 32);
    }

    private static final Random ALEATORIO = new Random();

    private final int tipo;
    private final int[][] forma;

    public PiezaTetris(int tipo) {
        this.tipo = tipo;
        this.forma = copiar(BASES[tipo]);
    }

    private PiezaTetris(int tipo, int[][] forma) {
        this.tipo = tipo;
        this.forma = forma;
    }

    public int getTipo() {
        return tipo;
    }

    public int[][] getForma() {
        return forma;
    }

    public static Color colorDe(int tipo) {
        return COLORES[tipo];
    }

    public static PiezaTetris crearAleatoria() {
        return new PiezaTetris(1 + ALEATORIO.nextInt(7));
    }

    public PiezaTetris rotada() {
        int n = forma.length;
        int[][] rot = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rot[j][n - 1 - i] = forma[i][j];
            }
        }
        return new PiezaTetris(tipo, rot);
    }

    private static int[][] copiar(int[][] fuente) {
        int[][] copia = new int[fuente.length][fuente[0].length];
        for (int i = 0; i < fuente.length; i++) {
            System.arraycopy(fuente[i], 0, copia[i], 0, fuente[0].length);
        }
        return copia;
    }
}