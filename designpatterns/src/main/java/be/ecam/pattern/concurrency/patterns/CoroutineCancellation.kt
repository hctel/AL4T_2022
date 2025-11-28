package be.ecam.pattern.concurrency.patterns

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// Demonstrates proper, cooperative coroutine cancellation and structured cleanup
fun main() = runBlocking {
    // Job 1 does some repeatable work and cleans up in finally
    val job1 = launch {
        try {
            repeat(1000) {
                // Make sure the coroutine is cancellable by using suspending calls like delay
                delay(50)
                // or check isActive when doing CPU work
                if (!isActive) return@launch
            }
        } catch (_: CancellationException) {
            // Optional: observe cancellation
        } finally {
            // Always runs on cancellation; use NonCancellable for final suspending cleanup
            withContext(NonCancellable) {
                // e.g., close resources, flush buffers
                delay(50)
                println("job1: cleaned up")
            }
        }
    }

    // Job 2 uses an isActive loop to cooperate with cancellation
    val job2 = launch {
        try {
            while (isActive) {
                delay(80)
            }
        } finally {
            println("job2: cancelled and finished")
        }
    }

    // Let them run a bit
    delay(250)

    // Proper cancellation: cancel AND join to await completion of the cleanup blocks
    job1.cancelAndJoin()
    job2.cancelAndJoin()

    println("All jobs cancelled and joined")
}