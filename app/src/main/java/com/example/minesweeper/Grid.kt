package com.example.minesweeper

import kotlin.math.sqrt
import kotlin.random.Random

class Grid(private val gameState: GameState) {

    // Block grid
    val blocks = ArrayList<ArrayList<Block>>(gameState.sizeY)

    // Bomb map
    val bombLocations = HashSet<Int>(sqrt((gameState.bombs).toDouble()).toInt())

    // Block Map
    val blockMap = HashMap<Int, Block>(gameState.sizeX * gameState.sizeY)

    /**
     * Setup the grid, initialising:
     * - The locations of the bombs
     * - The board itself created as a 2D ArrayList of blocks
     * - Setting all blocks to store their number of adjacent bombs
     */
    fun setup() {
        genBombCoordinates()
        initialiseBoard()
        setAdjacentBombs()
    }

    /**
     * Initialise the board given the size provided in the options
     */
    private fun initialiseBoard() {
        for (i in 0 until gameState.sizeY) {
            val blockRow = ArrayList<Block>(gameState.sizeX)
            for (j in 0 until gameState.sizeX) {
                val block = Block(j, i, i * gameState.sizeY + j)
                if (bombLocations.contains(block.getID())) {
                    block.isBomb = true

                }
                blockRow.add(block)
                blockMap[block.getID()] = block
            }
            blocks.add(blockRow)
        }
    }


    /**
     * For every single block loop through and set number of bombs
     */
    private fun setAdjacentBombs() {
        for (i in 0 until gameState.sizeY) {
            for (j in 0 until gameState.sizeX) {
                if (blocks[i][j].isBomb) {
                    continue
                }
                findBombs(i, j)
            }
        }
    }


    /**
     * For the current block locate all bombs directly adjacent
     */
    private fun findBombs(i: Int, j: Int) {
        for (k in -1..1) {
            for (l in -1..1) {
                if (i - k < 0 || i - k >= gameState.sizeY) {
                    break
                }
                if (j - l < 0 || j - l >= gameState.sizeX) {
                    continue
                }

                if (blocks[i - k][j - l].isBomb) {
                    blocks[i][j].numBombs++
                }
            }
        }
    }


    /**
     * Generate random coordinates for the mines
     */
    private fun genBombCoordinates() {
        while (bombLocations.size < gameState.bombs) {
            val index = Random.nextInt(0, gameState.sizeY) * gameState.sizeY + Random.nextInt(0, gameState.sizeX)
            if (!bombLocations.contains(index)) {
                bombLocations.add(index)
            }
        }
    }



}