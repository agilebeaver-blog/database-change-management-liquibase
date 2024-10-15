package com.agilebeaver.blog.database.changemanagement.liquibase.usermanagement

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
@Table(name = "um_roles", schema = "mukkilatte")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('um_roles_um_rl_id_seq')")
    @Column(name = "um_rl_id", nullable = false)
    var id: Long?,

    @ColumnDefault("gen_random_uuid()")
    @Column(name = "um_rl_uuid", nullable = false)
    var uuid: UUID? = null,

    @Column(name = "um_rl_name", nullable = false, length = 12)
    var name: String,

    @ColumnDefault("now()")
    @Column(name = "um_rl_created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "um_rl_created_by", nullable = false, length = 30)
    var createdBy: String,

    @ColumnDefault("now()")
    @Column(name = "um_rl_modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "um_rl_modified_by", nullable = false, length = 30)
    var modifiedBy: String
)
