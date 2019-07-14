# unbeatable-tic-tac-toe
A tic-tac-toe Java program that cannot be beat

## About the strategy
The program uses 1 of 3 core strategies based off of the player's first move.

1. If player's first move is in the center, place computer's first move in a corner and spend the rest of the game blocking the player from acheiving 3 in a row (since every move from the center will result in 2 in a row)

2. If the player's first move is in the corner, play computer's first move in center and remaining moves in the side spaces (neither corner nor center). This preemtively blocks the player from getting 3 in a row on the sides of the board or diagonally. 

3. If the player's first move is on a side space, play computer's first move in center and remaining move in the corners. This forces the player to spend the rest of the game defending against the diagonal 3 in a row or the vertical and horizontal 3 in a rows through the center.

Disclaimer: This program will never lose, but is not guaranteed to win either. 

## Computer win demo
![ezgif com-video-to-gif](https://user-images.githubusercontent.com/13570258/61187444-237c2600-a63f-11e9-881e-ecfc76a2d3eb.gif)

## Draw demo
![ezgif com-video-to-gif (1)](https://user-images.githubusercontent.com/13570258/61187493-c03ec380-a63f-11e9-9e52-691315a6c6a0.gif)


