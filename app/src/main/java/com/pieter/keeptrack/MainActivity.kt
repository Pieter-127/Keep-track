package com.pieter.keeptrack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val number = 7

        Log.v("you guessed it:", (buildHelloWorld() as Success).value)

        val randomValue = randomSeed(
            getRandomValue(),
            getRandomValue()
        ) onError ::defaultValue

        Log.v("random ->", randomValue.toString())

        number to ::double then ::addOne display ::formatToOutput
    }

    sealed class Result<T>
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val errorMessage: String) : Result<T>()

    private infix fun <T, U> Result<T>.then(function: (T) -> Result<U>) =
        when (this) {
            is Success -> function(this.value)
            is Failure -> Failure(this.errorMessage)
        }

    private infix fun <T> Result<T>.onError(function: () -> Result<T>) = when (this) {
        is Success -> Success(this.value)
        is Failure -> function()
    }

    private infix fun <T> Result<T>.display(function: (String) -> Unit) {
        when (this) {
            is Success -> function(this.value.toString())
            is Failure -> function(this.errorMessage)
        }
    }

    private infix fun <T, U> T.to(function: (T) -> Result<U>) = Success(this).then(function)

    private fun buildHelloWorld() = startWithHello() then ::addWorld

    private fun startWithHello(): Result<String> = Success("Hello")

    private fun addWorld(value: String): Result<String> = Success("$value world")

    private fun randomSeed(firstRandomValue: Int, secondRandomNumber: Int): Result<Int> {
        return if (firstRandomValue > secondRandomNumber) {
            Success(firstRandomValue)
        } else {
            Failure("first value < second value")
        }
    }

    private fun getRandomValue() = (0..10).random()

    private fun defaultValue(): Result<Int> {
        Log.v("random ->", "hit default")
        return Success(getRandomValue())
    }

    private fun double(value: Int): Result<Int> = Success(value.times(2))

    private fun addOne(value: Int): Result<Int> = Success(value.plus(1))

    private fun formatToOutput(value: String) {
        Log.v("math ->", "the output is: $value")
    }
}