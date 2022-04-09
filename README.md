# Keep-track

Making use of infix functions to try out railway orientated programming, code [found here](https://github.com/Pieter-127/Keep-track/blob/main/app/src/main/java/com/pieter/keeptrack/MainActivity.kt)

# Example

Mostly making use of a sealed result class: 

    sealed class Result<T>
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val errorMessage: String) : Result<T>()

and an infix extension function:

    private infix fun <T, U> Result<T>.then(function: (T) -> Result<U>) =
        when (this) {
            is Success -> function(this.value)
            is Failure -> Failure(this.errorMessage)
        }

to build up the result of function executions:

    private fun buildHelloWorld() = startWithHello() then ::addWorld

    private fun startWithHello(): Result<String> = Success("Hello")

    private fun addWorld(value: String): Result<String> = Success("$value world")


<img src="https://github.com/Pieter-127/Keep-track/blob/main/art.png" width="25%" />
