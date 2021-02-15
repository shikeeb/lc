class Solution {
    
    private static final int EMPTY = 0;
    
    public int totalNQueens(int n) {
        int[][] board = new int[n][n];
        return search(0, 0, board);
    }
    
    public int search(int row, int count, int[][] board) {
        
        for(int col = 0; col < board.length; col++) { 
            
            if(board[row][col] == EMPTY) {

                int queenID = row+1;
                board = modifyBoard(row, col, board, EMPTY, queenID); //place queen
                
                if (row == board.length - 1) {
                    count++;
                } else {
                    count = search(row+1, count, board);
                }
                
                board = modifyBoard(row, col, board, queenID, EMPTY); //remove queen
            } 
        }
        
        return count;
    }
    
    public int[][] modifyBoard(int row, int col, int[][] board, int source, int replacement) {
        int n = board.length;
        board[row][col] = replacement;
        
        //right
        int colRight = col + 1;
        while(colRight < n) {
            if (board[row][colRight] == source) {
                board[row][colRight] = replacement;
            }
            colRight++;
        }
        
        //left
        int colLeft = col - 1;
        while (colLeft >= 0) {
            if (board[row][colLeft] == source) {
                board[row][colLeft] = replacement;
            }
            colLeft--;
        }
        
        //dale
        int rowDale = row + 1;
        int colDale = col + 1;
        while (rowDale < n && colDale < n) {
            if(board[rowDale][colDale] == source) {
                board[rowDale][colDale] = replacement;
            }
            rowDale++;
            colDale++;
        }
        
        //down
        int rowDown = row + 1;
        while (rowDown < n) {
            if (board[rowDown][col] == source) {
                board[rowDown][col] = replacement;
            }
            rowDown++;
        }
        
        //hill
        int rowHill = row + 1;
        int colHill = col - 1;
        while (colHill >= 0 && rowHill < n) {
            if (board[rowHill][colHill] == source) {
                board[rowHill][colHill] = replacement;
            }
            rowHill++;
            colHill--;
        }
        

        return board;
    }
    
}