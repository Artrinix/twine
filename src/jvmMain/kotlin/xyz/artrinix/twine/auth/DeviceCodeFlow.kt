package xyz.artrinix.twine.auth

import com.microsoft.aad.msal4j.*;
import java.util.NoSuchElementException

object DeviceCodeFlow {
    const val CLIENT_ID = "79208efb-821a-4d64-97ac-84ce55b2d5c9"
    const val AUTHORITY = "https://login.microsoftonline.com/consumers/"
    val SCOPE = setOf("XboxLive.signin")

    fun deviceCodeConsumer(code: DeviceCode) {
        println(code.message())
    }

    @Throws(Exception::class)
    fun acquireTokenDeviceCode(): IAuthenticationResult {
        val tokenCacheAspect = TokenCacheAspect()

        val pca = PublicClientApplication.builder(CLIENT_ID)
            .authority(AUTHORITY)
            .setTokenCacheAccessAspect(tokenCacheAspect)
            .build()

        val accountsInCache = pca.accounts.join()

        return try {
            val account = accountsInCache.iterator().next()

            val silentParameters = SilentParameters
                .builder(SCOPE, account)
                .build()

            pca.acquireTokenSilently(silentParameters).join()
        } catch (err: Exception) {
            when (err) {
                is MsalException, is NoSuchElementException -> {
                    val parameters = DeviceCodeFlowParameters
                        .builder(SCOPE, ::deviceCodeConsumer)
                        .build()

                    pca.acquireToken(parameters).get()
                }
                else -> {
                    throw err
                }
            }
        }
    }
}