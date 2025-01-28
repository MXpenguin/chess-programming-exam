package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        int[] jumpOne = {-1,1};
        int[] jumpTwo = {-2,2};

        for (int i : jumpOne) {
            for (int j : jumpTwo) {
                ChessPosition endPosition = new ChessPosition(myRow + i, myCol + j);
                if (!isBlocked(board, myPosition, endPosition)) {
                    moves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }
        for (int i : jumpTwo) {
            for (int j : jumpOne) {
                ChessPosition endPosition = new ChessPosition(myRow + i, myCol + j);
                if (!isBlocked(board, myPosition, endPosition)) {
                    moves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        return moves;
    }

    private boolean isBlocked(ChessBoard board, ChessPosition myPosition, ChessPosition endPosition) {
        if (!endPosition.isValid()) return true;
        if (board.getPiece(endPosition) == null) {
            return false;
        }
        return board.getPiece(myPosition).getTeamColor() == board.getPiece(endPosition).getTeamColor();
    }

    private boolean isEnemy(ChessBoard board, ChessPosition myPosition, ChessPosition endPosition) {
        if (isBlocked(board, myPosition, endPosition)) return false;
        if (board.getPiece(endPosition) == null) {
            return false;
        }
        return board.getPiece(myPosition).getTeamColor() != board.getPiece(endPosition).getTeamColor();
    }
}
