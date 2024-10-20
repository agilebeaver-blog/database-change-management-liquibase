package com.agilebeaver.db.cm.liquibase.usermanagement.controllers

import com.agilebeaver.db.cm.liquibase.usermanagement.entities.Role
import com.agilebeaver.db.cm.liquibase.usermanagement.entities.User
import com.agilebeaver.db.cm.liquibase.usermanagement.repositories.UserRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Optional
import java.util.UUID

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userRepository: UserRepository

    // Test getting all Users
    @Test
    fun getAllUsers() {
        `when`(userRepository.findAll()).thenReturn(emptyList())

        mockMvc.perform(get("/api/users/"))
            .andExpect(status().isOk)
            .andDo(print())
    }

    // Test getting a User by valid UUID
    @Test
    fun getUserByValidUuid() {
        val uuid = UUID.randomUUID()
        val role = Role(1, UUID.randomUUID(), "admin", "admin", "admin")
        val user = User(
            1,
            UUID.randomUUID(),
            "itsme",
            "$3cr@t",
            "itsme@agilebeaver.com",
            setOf(role),
            "admin",
            "admin"
        );

        `when`(userRepository.findByUuid(uuid)).thenReturn(Optional.of(user))

        mockMvc.perform(
            get("/api/users/$uuid")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(print())
    }

    // Test getting a User by invalid UUID
    @Test
    fun getUserByInvalidUuid() {
        val uuid = UUID.randomUUID()

        `when`(userRepository.findByUuid(uuid)).thenReturn(Optional.empty())

        mockMvc.perform(
            get("/api/users/$uuid")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andDo(print())
    }
}