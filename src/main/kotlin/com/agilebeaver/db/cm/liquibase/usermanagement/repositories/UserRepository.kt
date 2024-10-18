package com.agilebeaver.db.cm.liquibase.usermanagement.repositories

import com.agilebeaver.db.cm.liquibase.usermanagement.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, UUID> {

    fun findByUuid(uuid: UUID): java.util.Optional<User>

}