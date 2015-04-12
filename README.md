# Boggle
The primary purpose of this project is to implement a recursive searching algorithm on the board to look for words.

<img src="https://cloud.githubusercontent.com/assets/10458699/7106758/c581c778-e101-11e4-8749-98ba95219298.png"
 alt="Boggle" title="boggle" align="center" />

## Board Searching Process

1. If the word string is empty, we have found the word on the board.
2. Otherwise, make sure the row and column values we are looking at are valid.
3. Then make sure the first letter of the word string matches the tile we are looking at.
4. Chop off the first letter of the word string.
5. Recursivly call this function with all adjacent tiles, and save the boolean return value.
6. Mark the current tile as visited.
7. Finally, return recursive calls result.
