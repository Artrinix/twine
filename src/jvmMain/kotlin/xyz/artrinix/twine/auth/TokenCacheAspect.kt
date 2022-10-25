package xyz.artrinix.twine.auth

import com.microsoft.aad.msal4j.ITokenCacheAccessAspect
import com.microsoft.aad.msal4j.ITokenCacheAccessContext

class TokenCacheAspect(var data: String? = null) : ITokenCacheAccessAspect {
    override fun beforeCacheAccess(iTokenCacheAccessContext: ITokenCacheAccessContext) {
        iTokenCacheAccessContext.tokenCache().deserialize(data)
    }

    override fun afterCacheAccess(iTokenCacheAccessContext: ITokenCacheAccessContext) {
        data = iTokenCacheAccessContext.tokenCache().serialize()
        println(data)
    }

}