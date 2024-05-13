import sys

import utils
from tictactoe import TicTacToe
from utils import Player
from minimax import MinimaxPlayer
from minimax_opponent import OpponentMinimaxPlayer


if __name__ == '__main__':
    play_as_human = False
    if len(sys.argv) > 1 and sys.argv[1].lower() == "true":
        play_as_human = True

    if play_as_human:
        opponent = Player()
    else:
        opponent = OpponentMinimaxPlayer()

    # 3x3 Tic Tac Toe
    utils.WIN_STATE_LEN = 3
    winner = TicTacToe(player1=MinimaxPlayer(), player2=opponent, board_size=3).play()
    assert winner != opponent, "3x3 game should not be lost as player1."
    winner = TicTacToe(player1=opponent, player2=MinimaxPlayer(), board_size=3).play()
    assert winner != opponent, "3x3 game should not be lost as player2."

    # 9x9 Tic Tac Toe
    #utils.WIN_STATE_LEN = 5
    #winner = TicTacToe(player1=MinimaxPlayer(), player2=opponent, board_size=9).play()
    #assert winner != opponent, "9x9 game should not be lost as player1."
    # winner = TicTacToe(player1=opponent, player2=MinimaxPlayer(), board_size=9).play()
    #  assert winner != opponent, "9x9 game should not be lost as player2."
