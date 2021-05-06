package com.example.demo

import com.amazonaws.services.kms.AWSKMS

import com.amazonaws.services.kms.model.DecryptResult

import com.amazonaws.services.kms.model.DecryptRequest
import org.mockito.Mockito.mock
import org.mockito.invocation.InvocationOnMock

import org.mockito.stubbing.Answer

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.ByteBuffer

@Configuration
@ConditionalOnProperty(prefix = "aws.kms", name = ["useMock"], havingValue = "true")
class MockAwsKmsConfig {

    @Bean
    fun kms(): AWSKMS {
        return awsKms
    }

    companion object {
        private val defaultAnswer: Answer<*> = Answer<Any> { invocation: InvocationOnMock ->
            val decryptMethod = AWSKMS::class.java.getMethod("decrypt", DecryptRequest::class.java)
            if (invocation.method == decryptMethod) {
                DecryptResult().withPlaintext(ByteBuffer.wrap(PLAINTEXT.toByteArray()))
            } else {
                throw IllegalStateException("Unexpected mock invocation: $invocation")
            }
        }
        private val awsKms = mock(AWSKMS::class.java, defaultAnswer)
        const val PLAINTEXT = "Hello World"
    }
}
