package com.wolodja.slow_and_calm_cinema.comon

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*


class Money @JvmOverloads internal constructor(amount: BigDecimal, val currency: Currency, rounding: RoundingMode? = DEFAULT_ROUNDING) {
    val amount: BigDecimal

    override fun toString(): String {
        return currency.symbol + " " + amount
    }

    fun toString(locale: Locale?): String {
        return currency.getSymbol(locale) + " " + amount
    }

    companion object {
        private val USD = Currency.getInstance("USD")
        private val DEFAULT_ROUNDING = RoundingMode.HALF_EVEN
        fun dollars(amount: BigDecimal): Money {
            return Money(amount, USD)
        }
    }

    init {
        this.amount = amount.setScale(currency.defaultFractionDigits, rounding)
    }
}