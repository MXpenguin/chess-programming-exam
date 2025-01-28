package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        int step;
        int promoRow;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            step = 1;
            promoRow = 8;
        } else {
            step = -1;
            promoRow = 1;
        }

        ChessPosition forwardOne = new ChessPosition(myRow + step, myCol);
        ChessPosition forwardTwo = new ChessPosition(myRow + 2*step, myCol);
        ChessPosition leftForward = new ChessPosition(myRow + step, myCol - step);
        ChessPosition rightForward = new ChessPosition(myRow + step, myCol + step);

        if (!isBlocked(board, myPosition, forwardOne) && !isEnemy(board, myPosition, forwardOne)) {
            addMove(moves, myPosition, forwardOne, promoRow);
            if (myPosition.getRow() == 2 || myPosition.getRow() == 7) {
                if (!isBlocked(board, myPosition, forwardTwo) && !isEnemy(board, myPosition, forwardTwo)) {
                    addMove(moves, myPosition, forwardTwo, promoRow);
                }
            }
        }

        if (isEnemy(board, myPosition, leftForward)) {
            addMove(moves, myPosition, leftForward, promoRow);
        }
        if (isEnemy(board, myPosition, rightForward)) {
            addMove(moves, myPosition, rightForward, promoRow);
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

    private void addMove(Collection<ChessMove> moves, ChessPosition myPosition, ChessPosition endPosition, int promoRow) {
        if (endPosition.getRow() == promoRow) {
            moves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
        } else {
            moves.add(new ChessMove(myPosition, endPosition, null));
        }
    }
}
