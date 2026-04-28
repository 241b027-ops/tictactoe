package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Previewrt androidx.compose.ui.unit.dp
import com.example.tictactoe.ui.theme.TICTACTOETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TICTACTOETheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicTacToeGame(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TicTacToeGame(modifier: Modifier = Modifier) {
    val board = remember {
        List(3) { mutableStateListOf("", "", "") }
    }
    var isXTurn by remember { mutableStateOf(true) }
    var winner by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    Cell(
                        symbol = board[i][j],
                        onClick = {
                            if (board[i][j].isEmpty() && winner == null) {
                                board[i][j] = if (isXTurn) "X" else "O"
                                isXTurn = !isXTurn
                                winner = checkWinner(board)
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (winner != null) {
            Text(text = "Winner: $winner", style = MaterialTheme.typography.headlineMedium)
        } else if (board.flatten().none { it.isEmpty() }) {
            Text(text = "Draw!", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            for (i in 0..2) {
                for (j in 0..2) {
                    board[i][j] = ""
                }
            }
            isXTurn = true
            winner = null
        }) {
            Text("Reset Game")
        }
    }
}

@Composable
fun Cell(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .border(2.dp, Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.headlineLarge,
            color = if (symbol == "X") Color.Red else Color.Blue
        )
    }
}

fun checkWinner(board: List<List<String>>): String? {

    for (i in 0..2) {
        if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) {
            return board[i][0]
        }
    }

    for (j in 0..2) {
        if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j].isNotEmpty()) {
            return board[0][j]
        }
    }

    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) {
        return board[0][0]
    }
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) {
        return board[0][2]
    }
    return null
}

@Preview(showBackground = true)
@Composable
fun TicTacToePreview() {
    TICTACTOETheme {
        TicTacToeGame()
    }
}


