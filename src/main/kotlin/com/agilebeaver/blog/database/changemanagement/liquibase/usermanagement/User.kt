package com.agilebeaver.blog.database.changemanagement.liquibase.usermanagement

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "um_users", schema = "mukkilatte")
data class User(
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "um_us_id", nullable = false, updatable = false)
    var id: Long? = null,

    @Column(name = "um_us_uuid", nullable = false, updatable = false)
    var uuid: UUID? = null,

    @Column(name = "um_us_login", nullable = false, unique = true)
    var login: String,

    @Column(name = "um_us_password", nullable = false)
    var password: String,

    @Column(name = "um_us_email", nullable = false)
    var email: String,

    @ManyToMany
    @JoinTable(
        name = "um_user_role",
        joinColumns = [JoinColumn(name = "um_ur_user_id")],
        inverseJoinColumns = [JoinColumn(name = "um_ur_role_id")]
    )
    val roles: Set<Role> = HashSet(),

    @Column(name = "um_us_created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "um_us_created_by")
    var createdBy: String,

    @Column(name = "um_us_modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "um_us_modified_by", nullable = false)
    var modifiedBy: String,
)