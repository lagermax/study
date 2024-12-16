package com.example.memorina

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var cardViews: ArrayList<ImageView>
    private val cards = mutableListOf<Int>()
    private var openedCards = mutableListOf<ImageView>()
    private var isGameLocked = false
    private var matchedPairs = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(applicationContext).apply {
            orientation = LinearLayout.VERTICAL
        }
        val params = LinearLayout.LayoutParams(200, 600).apply {
            weight = 1f
        }
        val images = arrayOf(
            R.drawable.card7, R.drawable.card7,
            R.drawable.card1, R.drawable.card1,
            R.drawable.card8, R.drawable.card8,
            R.drawable.card3, R.drawable.card3,
            R.drawable.card6, R.drawable.card6,
            R.drawable.card4, R.drawable.card4,
            R.drawable.card5, R.drawable.card5,
            R.drawable.card2, R.drawable.card2
        )
        cards.addAll(images)
        cards.shuffle()
        cardViews = ArrayList()
        for (i in cards.indices) {
            cardViews.add(ImageView(applicationContext).apply {
                setImageResource(R.drawable.card_back)
                layoutParams = params
                tag = "card$i"
                setOnClickListener { view ->
                    onCardClicked(view)
                }
            })
        }
        val rows = Array(4) { LinearLayout(applicationContext) }
        var count = 0
        for (view in cardViews) {
            val row: Int = count / 4
            rows[row].addView(view)
            count++
        }
        for (row in rows) {
            layout.addView(row)
        }
        setContentView(layout)
    }
    private fun onCardClicked(view: View) {
        if (isGameLocked) return
        val imageView = view as ImageView
        val cardIndex = (imageView.tag as String).removePrefix("card").toInt()
        if (openedCards.contains(imageView)) return
        imageView.setImageResource(cards[cardIndex])
        openedCards.add(imageView)
        if (openedCards.size == 2) {
            isGameLocked = true
            GlobalScope.launch(Dispatchers.Main) {
                delay(400)
                checkOpenedCards()
                isGameLocked = false
                if (matchedPairs == cards.size / 2) {
                    onGameWon()
                }
            }
        }
    }
    private fun checkOpenedCards() {
        val firstCard = openedCards[0]
        val secondCard = openedCards[1]
        val firstCardIndex = (firstCard.tag as String).removePrefix("card").toInt()
        val secondCardIndex = (secondCard.tag as String).removePrefix("card").toInt()
        if (cards[firstCardIndex] == cards[secondCardIndex]) {
            firstCard.visibility = View.INVISIBLE
            secondCard.visibility = View.INVISIBLE
            matchedPairs++
        } else {
            firstCard.setImageResource(R.drawable.card_back)
            secondCard.setImageResource(R.drawable.card_back)
        }
        openedCards.clear()
    }
    private fun onGameWon() {
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Победа!")
            .setMessage("Все карты найдены. Поздравляем!")
            .create()
        dialog.show()
    }
}