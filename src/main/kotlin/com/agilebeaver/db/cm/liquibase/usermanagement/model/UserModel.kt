package com.agilebeaver.db.cm.liquibase.usermanagement.model

import com.fasterxml.jackson.annotation.JsonIgnore

class UserModel(
    @JsonIgnore
    val id: Long?,
    val uuid: String?,
    val login: String,
    @JsonIgnore
    val password: String,
    val email: String,
    val roles: Set<String>
)
