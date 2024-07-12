
package com.example.tictactoegame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var textViewStatus: TextView
    private lateinit var buttonReset: Button
    private var playerXTurn = true
    private var board = Array(3) { IntArray(3) { -1 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }

        textViewStatus = findViewById(R.id.textViewStatus)
        buttonReset = findViewById(R.id.buttonReset)
        buttonReset.setOnClickListener {
            resetGame()
        }
    }

    private fun initButton(row: Int, col: Int): Button {
        val button: Button = findViewById(resources.getIdentifier("button$row$col", "id", packageName))
        button.setOnClickListener {
            onButtonClick(row, col)
        }
        return button
    }

    private fun onButtonClick(row: Int, col: Int) {
        if (board[row][col] != -1) {
            return
        }
        board[row][col] = if (playerXTurn) 1 else 0
        buttons[row][col].text = if (playerXTurn) "X" else "O"

        if (checkForWin()) {
            textViewStatus.text = if (playerXTurn) "Player X wins!" else "Player O wins!"
            disableButtons()
        } else {
            playerXTurn = !playerXTurn
            textViewStatus.text = if (playerXTurn) "Player X's turn" else "Player O's turn"
        }
    }

    private fun checkForWin(): Boolean {
        // Check rows
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != -1) {
                return true
            }
        }
        // Check columns
        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != -1) {
                return true
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != -1) {
            return true
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != -1) {
            return true
        }
        return false
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        board = Array(3) { IntArray(3) { -1 } }
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
        playerXTurn = true
        textViewStatus.text = "Player X's turn"
    }
}