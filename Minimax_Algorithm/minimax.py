import numpy as np
import utils
from utils import Player


class MinimaxPlayer(Player):
    def __init__(self):
        super().__init__()

    def next_move(self, board: np.ndarray):
        """
        :param board: TicTacToe board as 2D ndarray. It contains +1, -1 and 0 values.
                        +1 - player 1
                        -1 - player 2
                         0 - empty
        :return: row, col - position of the next move in board: board[row][col]
        """
        print(self.MARKER)  # marker of the player is +1 or -1
        print(utils.WIN_STATE_LEN)  # winning sequence length
        print(board.shape)  # board size
        print(board)
        print(board[0][0])  # access value
        print(board[0, 0])  # access value
        print(board[:, 0])  # access column
        print(board[1:, :-1])  # sub-matrix

        best_score = float('-inf')
        best_move = None

        # Projdeme hraci pole a vybereme policko
        for i in range(board.shape[0]):
            for j in range(board.shape[0]):
                if board[i][j] == 0:
                    # dame znak
                    board[i][j] = self.MARKER
                    # a minimaxem projdeme
                    score = self.minimax(board, False)
                    #po testu opět resetujeme pole
                    board[i][j] = 0

                    # A pokud je vrácené skóre lepší než předešlé, přenastavíme
                    if score > best_score:
                        best_score = score
                        best_move = (i, j)

        # a následně vrátíme pozici našeho tahu
        return best_move

    """
    Minimax algoritmus 
    """
    def minimax(self, board, is_maximizing, alpha=float('-inf'), beta=float('inf')):

        #Pokud hra skončila
        game_end, winner = utils.evaluate_board_state(board)
        if game_end:
            if winner is None:
                return 0
            elif winner == self.MARKER:
                return 1
            else:
                return -1

        if is_maximizing:
            # Na řadě je max hráč, nastavíme hodnotu na -nekonecno
            best_score = float('-inf')
            for i in range(board.shape[0]):
                for j in range(board.shape[1]):
                    #projdeme board a opakujeme
                    if board[i][j] == 0:
                        # Označíme a zavoláme opět minimax
                        board[i][j] = self.MARKER
                        score = self.minimax(board, False, alpha, beta)
                        board[i][j] = 0

                        #rekurzivně pak nastavíme nejpřívětivější hodnotu pro max hráče
                        best_score = max(score, best_score)
                        alpha = max(alpha, best_score)

                        # A pokud je beta <= alpha tak prorizneme
                        if beta <= alpha:
                            break

            return best_score
        else:
            # Na řadě je min hráč, nastavíme hodnotu na nekonečno
            best_score = float('inf')
            #projdeme pole pro prázdný prvek
            for i in range(board.shape[0]):
                for j in range(board.shape[1]):
                    if board[i][j] == 0:
                        # Označíme a zavoláme opět minimax
                        board[i][j] = -self.MARKER
                        score = self.minimax(board, True, alpha, beta)
                        board[i][j] = 0  #pole zpátky nastavíme na 0

                        #rekurzivně pak nastavíme nejpřívětivější hodnotu pro min hráče
                        best_score = min(score, best_score)
                        beta = min(beta, best_score)

                        # A pokud je beta <= alpha tak prorizneme
                        if beta <= alpha:
                            break

            #a vratime nejlepsi vysledek
            return best_score