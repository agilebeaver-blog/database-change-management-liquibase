package com.agilebeaver.db.cm.liquibase.usermanagement.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "um_role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('um_roles_um_rl_id_seq')")
    @Column(name = "id", nullable = false)
    var id: Long?,

    @ColumnDefault("gen_random_uuid()")
    @Column(name = "uuid", nullable = false)
    var uuid: UUID? = null,

    @Column(name = "name", nullable = false, length = 12)
    var name: String,

    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by", nullable = false, length = 30)
    var createdBy: String,

    @ColumnDefault("now()")
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_by", nullable = false, length = 30)
    var modifiedBy: String
)
