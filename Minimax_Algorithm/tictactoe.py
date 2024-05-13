import time
import numpy as np
from contextlib import contextmanager
import threading
import _thread

import utils


class GameEndException(Exception):
    """
    To announce the game end.
    """
    def __init__(self, winner):
        super().__init__(f"Winner: {winner}")
        self.winner = winner


class TimeoutException(Exception):
    """
    For the time limit.
    """
    def __init__(self, seconds):
        super().__init__(f"limit of {seconds} seconds per turn passed")


@contextmanager
def time_limit(seconds):
    if seconds is None:
        yield
    else:
        timer = threading.Timer(seconds, lambda: _thread.interrupt_main())
        timer.start()
        try:
            yield
        except KeyboardInterrupt:
            raise TimeoutException(seconds)
        finally:
            timer.cancel()


class TicTacToe:
    def __init__(self, player1: utils.Player, player2: utils.Player, board_size=3, turn_time_limit=None):
        """
        Prepares the game and settings.

        :param player1: Player instance of player 1
        :param player2: Player instance of player 2
        :param board_size: Size of the board
        :param turn_time_limit: Limit for turn in seconds. Set to None to turn off.
        """
        assert board_size >= utils.WIN_STATE_LEN
        player1.MARKER = 1
        player2.MARKER = -1

        self.player1 = player1
        self.player2 = player2
        self.board = np.zeros((board_size, board_size), dtype=int)  # 3x3 matrix of zeros as empty board
        self.turn_time_limit = turn_time_limit

    def _end_game(self, winner):
        print("\nGame Over")
        if winner is None:
            print("DRAW")
        else:
            print(f"\t{winner} WON")
        raise GameEndException(winner)

    def _validate_board(self):
        game_end, winner = utils.evaluate_board_state(board=self.board)
        if game_end:
            if winner is None:
                self._end_game(None)
            elif winner == self.player1.MARKER:
                self._end_game(self.player1)
            elif winner == self.player2.MARKER:
                self._end_game(self.player2)
            else:
                raise NotImplementedError(f"Unknown winner marker '{winner}'.")

    def _move(self, row, col, player):
        assert self.board[row][col] == 0, f"Board position [{row}, {col}] is taken"
        self.board[row][col] = player.MARKER

    def _player_turn(self, player):
        print(f"\n{player} turn:")

        start = time.time()
        with time_limit(None if type(player) == utils.Player else self.turn_time_limit):
            row, col = player.next_move(self.board.copy())  # hard copy
        end = time.time()
        print(f"Time duration: {end - start}")

        self._move(row, col, player)
        print()
        utils.print_board(self.board)
        self._validate_board()

    def play(self):
        """
        Starts the game.
        """
        print(f"Player 1: {self.player1}")
        print(f"Player 2: {self.player2}")
        utils.print_board(self.board)

        try:
            while True:
                self._player_turn(self.player1)
                self._player_turn(self.player2)
        except GameEndException as e:
            return e.winner


