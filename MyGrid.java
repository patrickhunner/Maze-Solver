import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

/**
 * Updated by Yuha Yoo and Austin Franzen 3.3.2022
 * Written by Cormac Pearce on 11.10.2021
 * Significant portions of code taken from Noah Park's Spring 2021 solution
 */
public class MyGrid {

    Cell[][] grid;
    int rows, cols, startRow, endRow;

    /**
     * Default constructor for MyMaze object.
     *
     * @param rows     total rows for the maze.
     * @param cols     total columns for the maze.
     * @param startRow row index of maze entrance
     * @param endRow   row index of maze exit
     */
    public MyGrid(int rows, int cols, int startRow, int endRow) {
        grid = new Cell[rows][cols];
        for(int i = 0; i < rows; i++){      // set grid nodes to cells
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Cell();
            }
        }
        this.startRow = startRow;
        this.endRow = endRow;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Draws grid and displays to the user.
     *
     */
    public void drawGrid() {
        Canvas test = new Canvas();
        Color wall = Color.BLACK;
        Color visited = Color.RED;
        Color not_visited = Color.ORANGE;
        Color start_end = Color.GREEN;
        for(int i = 0; i < cols * 2 + 1; i++){      // top and bottom border
            test.drawShape(new Square(50 + (20 * (i - 1)),30,20,20,wall));
            test.drawShape(new Square(50 + (20 * (i - 1)),30 + 20 * (rows * 2),20,20,wall));
        }
        for(int i = 0; i < rows * 2 + 1; i++){      // right and left border
            test.drawShape(new Square(30,50 + (20 * (i - 1)),20,20,wall));
            test.drawShape(new Square(30 + 20 * (cols * 2),50 + (20 * (i - 1)),20,20,wall));
        }
        for(int i = 0; i < rows; i++){      // fill in based on position and node type
            for(int j = 0; j < cols; j++){
                if(i < rows - 1 && j < cols - 1){       // walls in diagonal coordinates
                    test.drawShape(getSquare(i + 1,j + 1,wall,"diagonal"));
                }
                if(grid[i][j].getBottom() == true){
                    test.drawShape(getSquare(i + 1,j,wall,"bottom"));
                }
                if(grid[i][j].getRight() == true){
                    test.drawShape(getSquare(i, j + 1, wall,"right"));
                }
                if(grid[i][j].getVisited() == false){
                    if(grid[i][j].getBottom() == false){
                        test.drawShape(getSquare(i + 1, j, not_visited, "bottom"));
                    }
                    if(grid[i][j].getRight() == false){
                        test.drawShape(getSquare(i, j + 1, not_visited,"right"));
                    }
                    test.drawShape(getSquare(i, j, not_visited,"other"));
                }
                if(grid[i][j].getVisited() == true){
                    if(grid[i][j].getBottom() == false){
                        test.drawShape(getSquare(i + 1, j, visited, "bottom"));
                    }
                    if(grid[i][j].getRight() == false){
                        test.drawShape(getSquare(i, j + 1, visited,"right"));
                    }
                    test.drawShape(getSquare(i, j, visited,"other"));
                }
                else if(grid[i][j].getVisited() == true){
                    test.drawShape(getSquare(i, j, visited,"other"));

                }
            }
        }
        test.drawShape(new Square(30 + ((20 * 0)) + 1,50 + ((20 * startRow * 2)),20,20,start_end));     // start node
        test.drawShape(new Square(10 + ((20 * (cols * 2 + 1))) + 1,50 + ((20 * endRow * 2)),20,20,start_end));  // end node
        return;
    }

    /* bottom: node just below current node
       right: node just to the right of current node
       diagonal: node diagonal of current node
       other: node is on main rows and columns
    */

    public Square getSquare(int j, int i, Color color, String direction){   // create square at correct position
        Square sqr;
        if(direction.equals("bottom")){
            sqr = new Square(50 + ((20 * i) * 2),30 + ((20 * j) * 2),20,20,color);
        }
        else if(direction.equals("right")){
            sqr = new Square(30 + ((20 * i) * 2),50 + ((20 * j) * 2),20,20,color);
        }
        else if(direction.equals("diagonal")){
            if(color == Color.BLACK){
                sqr = new Square(30 + ((20 * i) * 2),30 + ((20 * j) * 2),20,20,color);
            }
            else{
                sqr = new Square(50 + ((20 * i) * 2),50 + ((20 * j) * 2),20,20,color);
            }
        }
        else{
            if(color == Color.BLACK){
                sqr = new Square(10 + ((20 * i) * 2),30 + ((20 * j) * 2),20,20,color);
            }
            else{
                sqr = new Square(50 + ((20 * i) * 2),50 + ((20 * j) * 2),20,20,color);
            }
        }
        return sqr;
    }

    /**
     * Generates a random maze using the algorithm from the write up.
     *
     * @param level difficulty level for maze (1-3) that decides maze dimensions
     *              level 1 -> 5x5, level 2 -> 5x10, level 3 -> 12x12
     * @return MyMaze object fully generated.
     */
    public static MyGrid makeGrid(int level) {
        MyGrid newGrid;
        Random ran = new Random();      
        if(level == 1){         // user level input
            int start = ran.nextInt(5);
            int end = ran.nextInt(5);
            newGrid = new MyGrid(5,5,start,end);
        }
        else if(level == 2){         // user level input
            int start = ran.nextInt(5);
            int end = ran.nextInt(5);
            newGrid = new MyGrid(5,10,start,end);
        }
        else{         // user level input
            int start = ran.nextInt(12);
            int end = ran.nextInt(12);
            newGrid = new MyGrid(12,12,start,end);
        }
        Stack1Gen<int[]> stack = new Stack1Gen<int[]>();
        stack.push(new int[]{newGrid.startRow,0});
        int[] top_element;
        int row, col;
        int direction = 4;
        while(!stack.isEmpty()){
            top_element = stack.pop();      // remove top element to get data then put back on
            row = top_element[0];
            col = top_element[1];
            stack.push(top_element);
            newGrid.grid[row][col].setVisited(true);
            if(direction == 4){     // new random int only if last run was successful
                direction = ran.nextInt(3);
            }
            if(allVisited(newGrid, row, col)){
                stack.pop();
            }
            else if(direction == 0){        // to avoid randomly generating the same number and stalling, randomly get one then iterate if unsuccessful
                if(row + 1 < newGrid.rows && newGrid.grid[row + 1][col].getVisited() == false){
                    newGrid.grid[row][col].setBottom(false);
                    stack.push(new int[]{row + 1, col});
                    newGrid.grid[row + 1][col].setVisited(true);
                    direction = 4;
                }
                else{      // if the random direction didn't work, increment by 1 until we find the one that does
                    direction += 1;
                }
            }
            else if(direction == 1){
                if(col + 1 < newGrid.cols && newGrid.grid[row][col + 1].getVisited() == false){
                    newGrid.grid[row][col].setRight(false);
                    stack.push(new int[]{row, col + 1});
                    newGrid.grid[row][col + 1].setVisited(true);
                    direction = 4;
                }
                else{
                    direction += 1;
                }
            }
            else if(direction == 2){
                if(row - 1 >= 0 && newGrid.grid[row - 1][col].getVisited() == false){
                    newGrid.grid[row - 1][col].setBottom(false);
                    stack.push(new int[]{row - 1, col});
                    newGrid.grid[row - 1][col].setVisited(true);
                    direction = 4;
                }
                else{
                    direction += 1;
                }
            }
            else if(direction == 3){
                if(col - 1 >= 0 && newGrid.grid[row][col - 1].getVisited() == false){
                    newGrid.grid[row][col - 1].setRight(false);
                    stack.push(new int[]{row, col - 1});
                    newGrid.grid[row][col - 1].setVisited(true);
                    direction = 4;
                }
                else{
                    direction = 0;
                }
            }
        }
        for(int i = 0; i < newGrid.rows; i++){      // set all nodes to not visited
            for(int j = 0; j < newGrid.cols; j++){
                newGrid.grid[i][j].setVisited(false);
            }
        }
        return newGrid;
    }

    public static boolean allVisited(MyGrid newGrid, int i, int j){     // checks if every open node around it has been visited
        if(i - 1 >= 0 && newGrid.grid[i - 1][j].getVisited() == false){
            return false;
        }
        else if(j - 1 >= 0 && newGrid.grid[i][j - 1].getVisited() == false){
            return false;
        }
        else if(i + 1 < newGrid.rows && newGrid.grid[i + 1][j].getVisited() == false){
            return false;
        }
        else if(j + 1 < newGrid.cols && newGrid.grid[i][j + 1].getVisited() == false){
            return false;
        }
        return true;
    }

    /**
     * Solves the maze using the algorithm from the writeup.
     */
    public void solveGrid() {
        Q1Gen<int[]> queue = new Q1Gen<>();
        queue.add(new int[]{startRow,0});
        int[] cur;
        int curRow, curCol;
        while(queue.length() > 0){
            cur = queue.remove();
            curRow = cur[0];
            curCol = cur[1];
            grid[curRow][curCol].setVisited(true);
            if(curRow == endRow && curCol == cols - 1){     // found end
                break;
            }
            // check all directions, if any are unvisited, add to the queue
            if(curRow + 1 < rows && grid[curRow + 1][curCol].getVisited() == false && grid[curRow][curCol].getBottom() == false){
                queue.add(new int[]{curRow + 1,curCol});
            }
            if(curRow - 1 >= 0 && grid[curRow - 1][curCol].getVisited() == false && grid[curRow - 1][curCol].getBottom() == false){
                queue.add(new int[]{curRow - 1,curCol});
            }
            if(curCol + 1 < cols && grid[curRow][curCol + 1].getVisited() == false && grid[curRow][curCol].getRight() == false){
                queue.add(new int[]{curRow,curCol + 1});
            }
            if(curCol - 1 >= 0 && grid[curRow][curCol - 1].getVisited() == false && grid[curRow][curCol - 1].getRight() == false){
                queue.add(new int[]{curRow,curCol - 1});
            }
        }
    }
    public static <T> void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int level;
        while(true){
            System.out.println("Enter level (1,2,3): ");
            level = s.nextInt();
            if(level == 1 || level == 2 || level == 3){
                break;
            }
            System.out.println("Invalid input. Please put the number 1, 2, or 3");
        }
        MyGrid test = makeGrid(level);
        test.solveGrid();
        test.drawGrid();
        s.close();
    }
}