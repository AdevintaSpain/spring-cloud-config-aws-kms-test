package com.example.demo

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoApplicationTests {

    @Value("\${top.secret}")
    private lateinit var secret: String

    @Test
    internal fun `should decrypt value`() {
        assertThat(secret).isEqualTo("12345")
    }
}
