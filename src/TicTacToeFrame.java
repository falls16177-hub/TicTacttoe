import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTacToeFrame extends JFrame {
    private final JButton[][] buttons = new JButton[3][3];
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private int moveCount = 0;

    public TicTacToeFrame() {
        super("Tic Tac Toe – Swing GUI");
        initUI();
        resetBoard();
    }

    private void initUI() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        TicTacToeFrame.this,
                        "Do you want to quit the game?",
                        "Quit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(
                            TicTacToeFrame.this,
                            "You chose to quit. Goodbye!",
                            "Quit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                }
            }
        });

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel turnLabel = new JLabel("Turn: X");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(turnLabel.getFont().deriveFont(Font.BOLD, 18f));
        root.add(turnLabel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 6, 6));
        Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 36);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int row = r;
                int col = c;
                JButton btn = new JButton("");
                btn.setFont(btnFont);
                btn.setFocusPainted(false);
                btn.addActionListener(e -> handleMove(row, col, turnLabel));
                buttons[r][c] = btn;
                grid.add(btn);
            }
        }

        root.add(grid, BorderLayout.CENTER);

        setContentPane(root);
        setSize(360, 400);
        setLocationRelativeTo(null);
    }

    private void handleMove(int row, int col, JLabel turnLabel) {
        if (board[row][col] != '\0') {
            JOptionPane.showMessageDialog(
                    this,
                    "Illegal move. That square is already taken.",
                    "Illegal Move",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        board[row][col] = currentPlayer;
        buttons[row][col].setText(String.valueOf(currentPlayer));
        moveCount++;

        if (moveCount >= 5 && checkWin(currentPlayer)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Player " + currentPlayer + " wins!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );
            askPlayAgain();
            return;
        }

        if (moveCount >= 7 && isBoardFull()) {
            JOptionPane.showMessageDialog(
                    this,
                    "It's a tie!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );
            askPlayAgain();
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        turnLabel.setText("Turn: " + currentPlayer);
    }

    private boolean isBoardFull() {
        return moveCount >= 9;
    }

    private boolean checkWin(char player) {
        for (int r = 0; r < 3; r++) {
            if (board[r][0] == player && board[r][1] == player && board[r][2] == player) return true;
        }
        for (int c = 0; c < 3; c++) {
            if (board[0][c] == player && board[1][c] == player && board[2][c] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private void askPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Play again?",
                "Tic Tac Toe",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Thanks for playing!",
                    "Goodbye",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        }
    }

    private void resetBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = '\0';
                if (buttons[r][c] != null) {
                    buttons[r][c].setText("");
                    buttons[r][c].setEnabled(true);
                }
            }
        }
        currentPlayer = 'X';
        moveCount = 0;
        Container content = getContentPane();
        if (content instanceof JComponent) {
            for (Component comp : ((JComponent) content).getComponents()) {
                if (comp instanceof JLabel) {
                    ((JLabel) comp).setText("Turn: X");
                    break;
                }
            }
        }
    }
}
