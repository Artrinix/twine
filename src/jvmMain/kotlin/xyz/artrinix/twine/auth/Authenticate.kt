package xyz.artrinix.twine.auth

import me.nullicorn.msmca.minecraft.MinecraftAuth
import me.nullicorn.msmca.minecraft.MinecraftToken
import me.nullicorn.msmca.xbox.XboxLiveAuth

object Authenticate {
    val minecraft = MinecraftAuth()

    fun authenticateToMinecraft(): MinecraftToken {
        val result = DeviceCodeFlow.acquireTokenDeviceCode()

        println("Access token: ${result.accessToken()}")
        println("Id token: ${result.idToken()}")
        println("Account username: ${result.account().username()}")

        return minecraft.loginWithMicrosoft(result.accessToken())
    }

    fun funni(token: MinecraftToken) {
        println("user: ${token.user}\ntype: ${token.type}\nvalue: ${token.value}")
    }
}