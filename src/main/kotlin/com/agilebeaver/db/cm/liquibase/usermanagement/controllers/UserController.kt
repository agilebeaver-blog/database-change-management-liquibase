package com.agilebeaver.db.cm.liquibase.usermanagement.controllers

import com.agilebeaver.db.cm.liquibase.usermanagement.entities.User
import com.agilebeaver.db.cm.liquibase.usermanagement.model.UserModel
import com.agilebeaver.db.cm.liquibase.usermanagement.repositories.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/")
    fun allUsers(): ResponseEntity<List<UserModel>> {
        val users = userRepository.findAll().map { user -> mapUserToUserModel(user) }
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{uuid}")
    fun getUserByUuid(@PathVariable uuid: UUID): ResponseEntity<UserModel> {
        val user = userRepository.findByUuid(uuid)
        return if (user.isPresent) {
            ResponseEntity.ok(mapUserToUserModel(user.get()))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    fun mapUserToUserModel(user: User): UserModel {
        return UserModel(user.id, user.uuid?.toString(), user.login, user.password, user.email,
            user.roles.map { r -> r.name }.toHashSet())
    }

}