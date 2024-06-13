package com.auth0.android.authentication.storage

import android.os.Build
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertTrue

import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

import org.powermock.modules.junit4.rule.PowerMockRule
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest

@RunWith(RobolectricTestRunner::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*", "com.auth0.*")
@Config(sdk = [Build.VERSION_CODES.P])
@PrepareForTest(BiometricManager::class)
public class LearnTest {

    private lateinit var fragmentActivity: FragmentActivity

    @Mock
    private lateinit var callback: Callback<Credentials, CredentialsManagerException>

    @get:Rule
    public var rule: PowerMockRule = PowerMockRule()

    @Before
    public fun setUp() {
        MockitoAnnotations.openMocks(this)
        fragmentActivity =
            Robolectric.buildActivity(FragmentActivity::class.java).create().resume().get()
    }

    @Test
    public fun test() {
        assertTrue(true)
    }
}