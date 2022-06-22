# Maze-Solver
A java program that creates a maze in a grid that is 5x5, 5x10, or 12x12 and then solves for a route from the beginning (right side) to the end (left side).

Compile/Run: To run, unzip the project file and then compile with the commands "javac MyGrid.java". Interpret said file with "java MyGrid". It will then ask you for input, a number 1, 2, or 3. This refers to the size of the maze you'll create. level 1: 5x5, level 2: 5x12, level 3: 12x12.

Assumptions: In the visualization, black squares are walls, red squares are the created path, yellow squares are unvisited nodes, and yellow squares refer to the start (left) and end (right). The start and end will only be on the left and right walls respectively, not the top or bottom. In the same directory you access these files through, you have the following classes. Square, Canvas, Cell, Stack1Gen, and Q1Gen.
