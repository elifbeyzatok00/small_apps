import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HafizaOyunu extends JFrame {

    private static final int BOARD_SIZE = 4;
    private static final int TOTAL_CARDS = BOARD_SIZE * BOARD_SIZE;
    private static final int MAX_ERRORS = 3;

    private JButton[][] buttons;
    private Color[][] cardColors;
    private boolean[][] cardFlipped;
    private int flippedCount;
    private int errors;

    public HafizaOyunu() {
        setTitle("Hafıza Oyunu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        cardColors = new Color[BOARD_SIZE][BOARD_SIZE];
        cardFlipped = new boolean[BOARD_SIZE][BOARD_SIZE];
        flippedCount = 0;
        errors = 0;

        initializeGame();
        initializeButtons();
        showColors(); // Başlangıçta renkleri göster

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideColors(); // Belirli bir süre sonra renkleri gizle
            }
        });
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
    }

    private void initializeGame() {
        // Renkleri rastgele belirle
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA};
        int[] colorCounts = new int[colors.length];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int randomColorIndex;
                do {
                    randomColorIndex = (int) (Math.random() * colors.length);
                } while (colorCounts[randomColorIndex] >= 2);

                cardColors[i][j] = colors[randomColorIndex];
                colorCounts[randomColorIndex]++;
            }
        }
    }

    private void initializeButtons() {
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.BLACK);
                buttons[i][j].setOpaque(true);
                buttons[i][j].addActionListener(new CardClickListener(i, j));

                add(buttons[i][j]);
            }
        }
    }

    private void showColors() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setBackground(cardColors[i][j]);
                cardFlipped[i][j] = true;
            }
        }
    }

    private void hideColors() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setBackground(Color.BLACK);
                cardFlipped[i][j] = false;
            }
        }
    }

    private class CardClickListener implements ActionListener {
        private int row;
        private int col;

        public CardClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!cardFlipped[row][col]) {
                buttons[row][col].setBackground(cardColors[row][col]);
                cardFlipped[row][col] = true;
                flippedCount++;

                if (flippedCount % 2 == 0) {
                    checkMatch();
                }
            }
        }

        private void checkMatch() {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (cardFlipped[i][j] && !(i == row && j == col)) {
                        if (cardColors[i][j].equals(cardColors[row][col])) {
                            // Eşleşme
                            clearFlippedCards();
                            checkWin();
                        } else {
                            // Eşleşmeme
                            errors++;
                            if (errors >= MAX_ERRORS) {
                                JOptionPane.showMessageDialog(HafizaOyunu.this, "Oyun bitti. Hatalarınız: " + errors, "Oyun Sonu", JOptionPane.INFORMATION_MESSAGE);
                                System.exit(0);
                            } else {
                                JOptionPane.showMessageDialog(HafizaOyunu.this, "Hatalı eşleşme! Kalan hakkınız: " + (MAX_ERRORS - errors), "Hata", JOptionPane.WARNING_MESSAGE);
                                resetFlippedCards();
                            }
                        }
                    }
                }
            }
        }

        private void resetFlippedCards() {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (cardFlipped[i][j]) {
                        buttons[i][j].setBackground(Color.BLACK);
                        cardFlipped[i][j] = false;
                    }
                }
            }
        }

        private void clearFlippedCards() {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (cardFlipped[i][j]) {
                        cardFlipped[i][j] = false;
                    }
                }
            }
            flippedCount = 0;
        }

        private void checkWin() {
            int matchedPairs = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (!cardFlipped[i][j]) {
                        matchedPairs++;
                    }
                }
            }

            if (matchedPairs == 0) {
                JOptionPane.showMessageDialog(HafizaOyunu.this, "Tebrikler! Oyunu kazandınız.", "Oyun Sonu", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HafizaOyunu());
    }
}