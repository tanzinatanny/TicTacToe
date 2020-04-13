
package hu.ait.tictactoe.view
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.tictactoe.MainActivity
import hu.ait.tictactoe.R
import hu.ait.tictactoe.model.TicTacToeModel


class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackground : Paint = Paint()
    var paintLine : Paint = Paint()

//    var x = -1
//    var y = -1
//
//    var circles = mutableListOf<PointF>() // mutable: as many as we want ; PointF

    var paintText: Paint = Paint()
    var bitmapBg: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.grass)

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 7f

        paintText.color = Color.RED
        paintText.textSize = 50f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

//        canvas?.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paintLine) // diagonal line
//        if (x != -1 && y != -1) {
//            canvas?.drawCircle(x.toFloat(), y.toFloat(), 50f, paintLine)
//        }
//        for (circle in circles) {
//            canvas?.drawCircle(circle.x, circle.y, 50f, paintLine)
//        }
        drawBoard(canvas)
        drawPlayers(canvas)
        //canvas?.drawText("Hello", 0f, height/3, paintText

    }

    //Editing dimensions
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas?.drawLine(
            0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine
        )
        canvas?.drawLine(
            0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine
        )
        // two vertical lines
        canvas?.drawLine(
            (width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine
        )
    }

    private fun drawPlayers(canvas: Canvas?){
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - 2

                    //copy paint and change color
                    var circleLine : Paint = Paint()
                    circleLine.color = Color.RED
                    circleLine.style = Paint.Style.STROKE
                    circleLine.strokeWidth = 7f

                    canvas?.drawCircle(centerX, centerY, radius.toFloat(), circleLine)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas?.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintLine)

                    canvas?.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintLine)
                }
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
//            x = event.x.toInt()
//            y = event.y.toInt()

//            circles.add(PointF(event?.x, event?.y))

            val tX = event.x.toInt() / (width/3)
            val tY = event.y.toInt() / (height/3)

            if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY){

                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())
                TicTacToeModel.changeNextPlayer()

                invalidate() // this forces Android to repaint this View


                //(context as MainActivity).showText(context.getString(R.string.tex_next))

                if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CIRCLE) {
                    (context as MainActivity).showText(
                        context.getString(R.string.text_next_player, "O", 44)
                    )
                } else {
                    (context as MainActivity).showText(
                        context.getString(R.string.text_next_player, "X", 44)
                    )
                }


                }




                //check for winner
                var winner : Short = TicTacToeModel.checkWinner()

                if( winner == TicTacToeModel.CIRCLE ){
                    (context as MainActivity).showText("Circle Wins")
                }

                if( winner == TicTacToeModel.CROSS ){
                    (context as MainActivity).showText("Cross Wins")
                }

                //end check for winner

            }


        return true
    }

    fun clearCircles() {

        TicTacToeModel.resetModel()
        invalidate()
    }

}