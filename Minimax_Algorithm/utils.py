import numpy as np


WIN_STATE_LEN = 3    # will be set in main TicTacToe

marker_to_symbol_dict = {
    -1: "O",
    0: "-",
    1: "X"
}


def _get_win_states(board: np.ndarray):
    """
    :param board: TicTacToe board as 2D list of markers.
    :return: List that contains rows, columns and diagonals as 1D ndarray of at least WIN_STATE_LEN length to verify the winner.
    """
    assert board.shape[0] == board.shape[1]
    states = []
    # rows
    for row in board:
        states.append(row)
    # cols
    for coli in range(board.shape[1]):
        states.append(board[:, coli])

    # All possible diagonals of at least WIN_STATE_LEN
    flipboard = np.fliplr(board)
    for i in range(-board.shape[0] + WIN_STATE_LEN, board.shape[0] - WIN_STATE_LEN +1):
        states.append(board.diagonal(offset=i))
        states.append(flipboard.diagonal(offset=i))

    return states


def evaluate_board_state(board: np.ndarray):
    """
    Evaluates whether the game is over and, if so, determines who is the winner.

    example usage:
    game_end, winner = evaluate_board_state(board)
    if game_end:
        if winner is None:
            # DRAW
        elif winner == my_marker:
            # I WON
        else:
            # Opponent WON


    :param board: TicTacToe board as 2D ndarray.
    :return bool, int: If the game ended and the winner:
        True, 1 - game ended by the win of player 1
        True, -1 - game ended by the win of player 2
        True, None - game ended in a draw
        False, None - game has not ended yet
    """
    kernel = np.ones(WIN_STATE_LEN, dtype=board.dtype)
    for state in _get_win_states(board):
        if np.sum(state == 1) < WIN_STATE_LEN and np.sum(state == -1) < WIN_STATE_LEN:
            continue
        statesums = np.convolve(state, kernel, 'valid')
        if WIN_STATE_LEN in statesums:
            return True, 1
        elif -WIN_STATE_LEN in statesums:
            return True, -1

    if 0 in board:     # still empty positions on board
        return False, None  # still playing and nobody wins so far
    else:
        return True, None  # game ended, nobody wins


def print_board(board: np.ndarray):
    for row in board:
        print("\t".join([marker_to_symbol_dict[val] for val in row]))


class Player:
    def __init__(self):
        """
        Player constructor
        """
        self.MARKER = 0  # will be set to 1 or -1, do not edit

    def __str__(self):
        return f"{self.__class__.__name__}({marker_to_symbol_dict[self.MARKER]})"

    def next_move(self, board: np.ndarray):
        """
        Decides next move based on the input from human.
        :param board: TicTacToe board as 2D ndarray.
        :return: row, col - position of the next move in board: board[row][col]
        """
        print("Board positions are as follows: ")
        for r in range(board.size - board.shape[1] + 1, -1, -board.shape[1]):
            line = "\t".join([str(r + i) for i in range(board.shape[1])])
            print(line)
        # input via number
        while True:
            try:
                num = int(input(f'Use numpad 1 - {board.size}: '))
                assert 1 <= num <= board.size, f"{num} not in range from 1 to {board.size}"
                num -= 1
                row = (board.shape[0] - 1) - (num // board.shape[1])
                col = num % board.shape[1]
                assert board[row, col] == 0, f"position [{row}, {col}] is taken"
                return row, col
            except Exception as e:
                print(f"Invalid move: {e}")
