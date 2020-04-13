package hu.ait.tictactoe.model

import android.media.AsyncPlayer

object TicTacToeModel {

    public val EMPTY: Short = 0
    public val CIRCLE: Short = 1
    public val CROSS: Short = 2

    private val model = arrayOf(
        shortArrayOf(EMPTY ,EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY,EMPTY, EMPTY)
    )

    // WHO IS THE NEXT PLAYER

    private var nextPlayer = CROSS

    fun getFieldContent(x: Int, y: Int): Short {
        return model[x][y]
    }

    fun setFieldContent(x: Int, y: Int, player: Short){

        model [x][y] = player
    }

    fun getNextPlayer () = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }

    fun resetModel(){
        for (i in 0..2){
            for (j in 0..2){
                model [i][j] = EMPTY
            }
        }
    }

    fun checkWinner(): Short{

        //check rows
        for ( i in 0..2 ) {
            if( model[i][0] == model [i][1] && model[i][0] == model [i][2] ){
                return model[i][0]
            }
        }

        //check columns
        for ( j in 0..2 ) {
            if( model [0][j] == model [1][j] && model[0][j] == model [2][j] ){
                return model[0][j]
            }
        }

        //check diag 1
        if( model[0][0] == model[1][1] && model[1][1] == model[2][2] ){
            return model[0][0]
        }

        //check diag 2
        if( model[0][2] == model[1][1] && model[1][1] == model[2][0] ){
            return model[0][2]
        }

        return  EMPTY

    }
}