import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TableroTetris extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Color COLOR_CLARO = new Color(48, 48, 54);
    private static final Color COLOR_OSCURO = new Color(36, 36, 42);
    private static final int DELAY_GRAVEDAD = 500;

    private JButton[][] mBotones;
    private JPanel pTablero;
    private JPanel pSur;
    private JButton bNuevoJuego;
    private JLabel lMensaje;
    private JLabel lPuntaje;
    private Tetris juego;
    private Timer temporizador;
    private boolean pausado = false;

    public TableroTetris() {
        this.setTitle("Tetris - carobayo");

        pTablero = new JPanel();
        pTablero.setLayout(new GridLayout(Tetris.FILAS, Tetris.COLUMNAS));

        pSur = new JPanel();
        pSur.setLayout(new BorderLayout());

        lMensaje = new JLabel("Usa las flechas para mover y girar la pieza");
        lMensaje.setHorizontalAlignment(JLabel.CENTER);
        lMensaje.setFont(new Font("Arial", Font.BOLD, 14));
        lMensaje.setForeground(Color.DARK_GRAY);

        lPuntaje = new JLabel("Puntos: 0");
        lPuntaje.setHorizontalAlignment(JLabel.CENTER);
        lPuntaje.setFont(new Font("Arial", Font.PLAIN, 13));
        lPuntaje.setForeground(Color.GRAY);

        bNuevoJuego = new JButton("Nuevo juego");
        bNuevoJuego.setFont(new Font("Arial", Font.BOLD, 12));
        bNuevoJuego.addActionListener(this);

        JPanel pSurInferior = new JPanel();
        pSurInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSurInferior.add(bNuevoJuego);

        pSur.add(lPuntaje, BorderLayout.NORTH);
        pSur.add(lMensaje, BorderLayout.CENTER);
        pSur.add(pSurInferior, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        mBotones = new JButton[Tetris.FILAS][Tetris.COLUMNAS];
        for (int fila = 0; fila < Tetris.FILAS; fila++) {
            for (int columna = 0; columna < Tetris.COLUMNAS; columna++) {
                mBotones[fila][columna] = new JButton();
                mBotones[fila][columna].setPreferredSize(new Dimension(40, 40));
                mBotones[fila][columna].setFocusable(false);
                pTablero.add(mBotones[fila][columna]);
            }
        }

        this.add(pTablero, BorderLayout.CENTER);
        this.add(pSur, BorderLayout.SOUTH);

        this.setSize(480, 900);
        this.setMinimumSize(new Dimension(420, 800));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                manejarTecla(e.getKeyCode());
            }
        });

        temporizador = new Timer(DELAY_GRAVEDAD, e -> {
            juego.bajar();
            actualizarTablero();
            if (juego.isJuegoTerminado()) {
                manejarFinJuego();
            }
        });

        init();
    }

    public void init() {
        juego = new Tetris();
        pausado = false;
        temporizador.stop();
        lPuntaje.setText("Puntos: 0");
        lMensaje.setText("Usa las flechas para mover y girar la pieza");
        lMensaje.setForeground(Color.DARK_GRAY);
        actualizarTablero();
        temporizador.start();
        requestFocusInWindow();
    }

    private void manejarTecla(int codigo) {
        if (juego.isJuegoTerminado()) return;

        if (codigo == KeyEvent.VK_P) {
            pausado = !pausado;
            if (pausado) {
                temporizador.stop();
                lMensaje.setText("Pausa - Pulsa P para continuar");
            } else {
                temporizador.start();
                lMensaje.setText("Usa las flechas para mover y girar la pieza");
            }
            return;
        }
        if (pausado) return;

        switch (codigo) {
            case KeyEvent.VK_LEFT:
                juego.moverIzquierda();
                break;
            case KeyEvent.VK_RIGHT:
                juego.moverDerecha();
                break;
            case KeyEvent.VK_DOWN:
                juego.bajar();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                juego.girar();
                break;
            default:
                return;
        }
        actualizarTablero();
        if (juego.isJuegoTerminado()) {
            manejarFinJuego();
        }
    }

    private void actualizarTablero() {
        for (int fila = 0; fila < Tetris.FILAS; fila++) {
            for (int columna = 0; columna < Tetris.COLUMNAS; columna++) {
                boolean esClara = (fila + columna) % 2 == 0;
                mBotones[fila][columna].setBackground(esClara ? COLOR_CLARO : COLOR_OSCURO);
            }
        }

        int[][] tablero = juego.getTablero();
        for (int fila = 0; fila < Tetris.FILAS; fila++) {
            for (int columna = 0; columna < Tetris.COLUMNAS; columna++) {
                if (tablero[fila][columna] != Tetris.VACIO) {
                    mBotones[fila][columna].setBackground(PiezaTetris.colorDe(tablero[fila][columna]));
                }
            }
        }

        int[][] forma = juego.getPiezaActual().getForma();
        int filaPieza = juego.getFilaPieza();
        int colPieza = juego.getColPieza();
        for (int f = 0; f < forma.length; f++) {
            for (int c = 0; c < forma[0].length; c++) {
                if (forma[f][c] == Tetris.VACIO) continue;
                int rf = filaPieza + f;
                int rc = colPieza + c;
                if (rf >= 0 && rf < Tetris.FILAS && rc >= 0 && rc < Tetris.COLUMNAS) {
                    mBotones[rf][rc].setBackground(PiezaTetris.colorDe(juego.getPiezaActual().getTipo()));
                }
            }
        }

        lPuntaje.setText("Puntos: " + juego.getPuntaje());
    }

    private void manejarFinJuego() {
        temporizador.stop();
        lMensaje.setText("Fin del juego - Pulsa 'Nuevo juego'");
        lMensaje.setForeground(Color.RED);
        actualizarTablero();
        JOptionPane.showMessageDialog(this,
                "Fin del juego - Puntos: " + juego.getPuntaje(),
                "Tetris", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        TableroTetris t = new TableroTetris();
        t.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(bNuevoJuego)) {
            init();
        }
    }
}