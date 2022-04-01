package com.example.findmyage
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TicTacToe : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tic_tac_toe)
        val resetButton = findViewById<Button>(R.id.btnReset)

        resetButton.setOnClickListener{
         resetGame()
        }
    }
    private var isPlayer1 = true
    fun onButtonClick(view: View){
        val btnSelected: Button = view as Button
        var currentCellId = 0
        when(btnSelected.id){
            R.id.btn00->currentCellId = 1
            R.id.btn01->currentCellId = 2
            R.id.btn02->currentCellId = 3
            R.id.btn10->currentCellId = 4
            R.id.btn11->currentCellId = 5
            R.id.btn12->currentCellId = 6
            R.id.btn20->currentCellId = 7
            R.id.btn21->currentCellId = 8
            R.id.btn22->currentCellId = 9
        }
        playGame(currentCellId, btnSelected)
    }

    private var xPlayer = ArrayList<Int>()
    private var oPlayer = ArrayList<Int>()
    private var player1Wins = 0
    private var player2Wins = 0

    private fun playGame(currentCellId: Int , btnSelected: Button){

        if (isPlayer1){
            btnSelected.text = "X"
            xPlayer.add(currentCellId)
        }else{
            btnSelected.text = "O"
            oPlayer.add(currentCellId)
        }
        btnSelected.isEnabled = false
        checkWinner()
    }

    private fun checkWinner(){
        val list = if(isPlayer1) xPlayer else oPlayer
        if((list.contains(1) && list.contains(2) && list.contains(3))
            || (list.contains(4) && list.contains(5) && list.contains(6))
            || (list.contains(7) && list.contains(8) && list.contains(9))
            || (list.contains(1) && list.contains(4) && list.contains(7))
            || (list.contains(2) && list.contains(5) && list.contains(8))
            || (list.contains(3) && list.contains(6) && list.contains(9))
            || (list.contains(1) && list.contains(5) && list.contains(9))
            || (list.contains(3) && list.contains(5) && list.contains(7))){

            Toast.makeText(this, "${if(isPlayer1) "Player 1" else "Player 2"} is the winner", Toast.LENGTH_LONG).show()
            if(isPlayer1)
                player1Wins +=1
            else
                player2Wins +=1
            restartGame()
        } else if(xPlayer.size + oPlayer.size == 9){
            Toast.makeText(this,"It is a Draw", Toast.LENGTH_LONG).show()
            restartGame()
        } else{
            isPlayer1 = !isPlayer1
        }
    }

    private fun restartGame(){
        findViewById<TextView>(R.id.tvPlayer1Count).text="$player1Wins"
        findViewById<TextView>(R.id.tvPlayer2Count).text="$player2Wins"
        xPlayer.clear()
        oPlayer.clear()
        for (cellId in 1..9){
            var currentButton: Button?
            val currentButtonId = when(cellId){
                1-> R.id.btn00
                2-> R.id.btn01
                3-> R.id.btn02
                4-> R.id.btn10
                5-> R.id.btn11
                6-> R.id.btn12
                7-> R.id.btn20
                8-> R.id.btn21
                9-> R.id.btn22

                else -> {R.id.btn00}
            }
                currentButton = findViewById(currentButtonId)
            currentButton.text = ""
            currentButton.isEnabled = true
        }
        isPlayer1 = true

    }
    private fun resetGame(){
        player1Wins = 0
        player2Wins = 0
        restartGame()
    }

}