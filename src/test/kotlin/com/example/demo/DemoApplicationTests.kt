package com.example.demo

import com.amazonaws.services.kms.AWSKMS
import com.amazonaws.services.kms.model.DecryptRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import java.nio.ByteBuffer


@SpringBootTest
class DemoApplicationTests {

    private val cipherTextBlob = ByteBuffer.wrap("secret".toByteArray())

    @Autowired
    private lateinit var mockKms: AWSKMS

    @Value("\${top.password}")
    private lateinit var password: String

    @Test
    internal fun `should decrypt value`() {
        assertThat(password).isEqualTo(MockAwsKmsConfig.PLAINTEXT)

        val decryptRequest = DecryptRequest()
        decryptRequest.encryptionAlgorithm = "SYMMETRIC_DEFAULT"
        decryptRequest.ciphertextBlob = cipherTextBlob
        verify(mockKms, atLeastOnce()).decrypt(decryptRequest)
    }
}
