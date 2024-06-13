package com.auth0.android.authentication.storage

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals

import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.Executors


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@PowerMockIgnore(
    "org.mockito.*",
    "org.robolectric.*",
    "android.*",
    "com.auth0.*"
)
@PrepareForTest(BiometricManager::class)
//@RunWith(PowerMockRunner::class)
//@PowerMockRunnerDelegate(RobolectricTestRunner::class)
public class LocalAuthenticationManagerTest {


    private lateinit var fragmentActivity: FragmentActivity

    @Mock
    private lateinit var biometricManager: BiometricManager

    @Mock
    private lateinit var callback: Callback<Boolean, CredentialsManagerException>

    @get:Rule
    public var rule: PowerMockRule = PowerMockRule()

    private val credentialsCaptor: KArgumentCaptor<Credentials> = argumentCaptor()

    private val exceptionCaptor: KArgumentCaptor<CredentialsManagerException> = argumentCaptor()

    @Before
    public fun setUp() {
        Thread.currentThread().contextClassLoader = Context::class.java.classLoader
        MockitoAnnotations.openMocks(this)
        fragmentActivity =
            Robolectric.buildActivity(FragmentActivity::class.java).create().resume().get()
    }

    @Test
    public fun testCanAuthenticateHWUNAVAILABLE() {
        val localAuthenticationManager = LocalAuthenticationManager(
            fragmentActivity,
            LocalAuthenticationOptions.Builder().title("title").subtitle("subtitle").description("description").authenticator(AuthenticationLevel.STRONG).enableDeviceCredentialFallback(false).negativeButtonText("negativeButtonText").build(),
            Executors.newSingleThreadExecutor()
        )
//        PowerMockito.mockStatic(BiometricManager::class.java)
//        Mockito.`when`(BiometricManager.from(any())).thenReturn(biometricManager)
//
//        Mockito.`when`(biometricManager.canAuthenticate(any()))
//            .thenReturn(BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE)

//        // Create a mock Callback
//        val callback = mock(Callback::class.java) as Callback<Boolean, CredentialsManagerException>
//
//        // Create an ArgumentCaptor for CredentialsManagerException
//        val exceptionCaptor = ArgumentCaptor.forClass(CredentialsManagerException::class.java)
//
//        localAuthenticationManager.authenticate(callback)
//
//        // Verify that onFailure was called with a CredentialsManagerException
//        verify(callback).onFailure(exceptionCaptor.capture())
//
//        // Assert that the captured exception is of the expected type
//        assertTrue(exceptionCaptor.value is CredentialsManagerException)
//        // Assert that the message of the exception is as expected
//        assertEquals("Biometric hardware is unavailable", exceptionCaptor.value.message)

        localAuthenticationManager.authenticate(callback)
        // verify the callback is called with the expected credentials manager exception object and its message is also as expected
        verify(callback).onFailure(exceptionCaptor.capture())

        println(exceptionCaptor.firstValue.message)
        assertTrue(exceptionCaptor.firstValue.message == "Biometric hardware is unavailable")
        assert(true)
    }

    private fun getAuthenticationOptions(
        title: String = "title",
        description: String = "description",
        subtitle: String = "subtitle",
        negativeButtonText: String = "negativeButtonText",
        authenticator: AuthenticationLevel = AuthenticationLevel.STRONG,
        enableDeviceCredentialFallback: Boolean = false
    ): LocalAuthenticationOptions {

        val builder = LocalAuthenticationOptions.Builder()
        builder.apply {
            title(title)
            subtitle(subtitle)
            description(description)
            negativeButtonText(negativeButtonText)
            authenticator(authenticator)
            enableDeviceCredentialFallback(enableDeviceCredentialFallback)
        }
        return builder.build()
    }
}

