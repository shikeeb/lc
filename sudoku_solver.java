class Solution {
    
    private static final char EMPTY = '.';
    private boolean isSolved = false;
    
    public void solveSudoku(char[][] board) {
        if (board.length == 0) {
            return;
        }

        search(board, 0, 0);
    }
    
    public void search(char[][] board, int row, int col) {
        
        if (board[row][col] == EMPTY) {
        
            for (int num = 1; num <= 9; num++) {
                if (validCandidate(board, row, col, (char)(num + '0'))) {
                    
                    board[row][col] = (char)(num + '0'); 
                    
                    moveToNextCell(board, row, col);
                    
                    //this is needed to prevent it from clearing the board right at the end!
                    if (!isSolved) { 
                        board[row][col] = EMPTY;    
                    }
                }
            }
            
        } else moveToNextCell(board, row, col);
        
    }
    
    public void moveToNextCell(char[][] board, int row, int col) {
        if (row == 8 && col == 8) { //last item
            isSolved = true;
            return;
        } else {
            if (col == 8) {
                search(board, row + 1, 0); //move to next row
            } else {
                search(board, row, col + 1); // move to right cell
            }
        }
    }
    
    public boolean validCandidate(char[][] board, int row, int col, char num) {
        int subGridRow = row - (row % 3);
        int subGridCol = col - (col % 3);
        
        // Search subgrid
        for (int i = subGridRow; i < subGridRow + 3; i++) {
            for (int j = subGridCol; j < subGridCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }   
            }
        }
        
        // Search row and column
        for (int i = 0; i < 9; i++) {
            
            if (board[row][i] == num) { // Search row
                return false;
            }
 
            if (board[i][col] == num) { // Search col
                return false;
            }
        }
        
        return true;
    }

}