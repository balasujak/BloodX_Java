package com.example.BloodXClient.client

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority

class ZitadelGrantedAuthoritiesMapper : GrantedAuthoritiesMapper {

    companion object {
        const val ZITADEL_ROLES_CLAIM = "urn:zitadel:iam:org:project:roles"
    }

    override fun mapAuthorities(authorities: Collection<GrantedAuthority>): Collection<GrantedAuthority> {
        val mappedAuthorities = HashSet<GrantedAuthority>()

        authorities.forEach { authority ->
            when (authority) {
                is SimpleGrantedAuthority -> mappedAuthorities.add(authority)
                is OidcUserAuthority -> addRolesFromUserInfo(mappedAuthorities, authority)
            }
        }

        return mappedAuthorities
    }

    private fun addRolesFromUserInfo(mappedAuthorities: HashSet<GrantedAuthority>, oidcUserAuthority: OidcUserAuthority) {
        val userInfo = oidcUserAuthority.userInfo
        val roleInfo = userInfo.getClaimAsMap(ZITADEL_ROLES_CLAIM) ?: return

        roleInfo.keys.forEach { zitadelRoleName ->
            mappedAuthorities.add(SimpleGrantedAuthority("ROLE_$zitadelRoleName"))
        }
    }
}
