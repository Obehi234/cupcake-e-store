package com.example.onlinecakeshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 3.00
private const val PRICE_SAME_DAY_PICKUP = 2.00

class SharedViewModel : ViewModel() {
    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private var _price = MutableLiveData<Double>()
    val price: LiveData<String> = _price.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private var _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    fun orderCupcakes(numberOfCupcakes: Int) {
        _quantity.value = numberOfCupcakes
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setPrice(quantity : Int) {
        val orderPrice = quantity * PRICE_PER_CUPCAKE
        _price.value = orderPrice
    }

    fun setDate(pickUpDate : String) {
        _date.value = pickUpDate
    }

    private fun isSameDay(date: String?): Boolean {
        if (date.isNullOrEmpty()) {
            return false
        }

        val selectedCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("E MMM d", Locale.getDefault())

        return try {
            val selectedDate = sdf.parse(date)
            val currentCalendar = Calendar.getInstance()
            (selectedCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
                    selectedCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR))
        } catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }


    fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (isSameDay(date.value ?: "")) {
            calculatedPrice += PRICE_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _price.value = 0.0
    }

}