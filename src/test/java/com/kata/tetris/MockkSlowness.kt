package com.kata.tetris

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/*

        <dependency>
            <groupId>io.mockk</groupId>
            <artifactId>mockk</artifactId>
            <version>1.9</version>
            <scope>test</scope>
        </dependency>

 */

typealias Foo = String

class FooLoader {
    fun allFoos() : List<Foo> {
        return listOf("com", "kata", "tetris")
    }
}

class RandomFoo(private val fooLoader: FooLoader) {
    fun giveFoo() : Foo {
        val foos = fooLoader.allFoos()
        return foos[(0 until foos.size).random()]
    }
}

class MockkSlownessTest {
    @Test
    fun tooSlow() {
        val time = System.currentTimeMillis()
        val fooLoader = mockk<FooLoader>()
        every { fooLoader.allFoos() } returns listOf("mock","slowness", "test")
        println("Time elapsed : ${System.currentTimeMillis() - time} ms")
        // Almost 3.5 seconds to init mock
        val randomFoo = RandomFoo(fooLoader)
        assertThat(randomFoo.giveFoo()).isIn("mock","slowness", "test")
    }
}