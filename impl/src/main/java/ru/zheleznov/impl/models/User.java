package ru.zheleznov.impl.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;

    private UUID confirmedCode;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "speakers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Talk> talks;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.LISTENER;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private State state = State.NOT_CONFIRMED;

    public enum State {
        STATE_ACTIVE, STATE_BANNED, NOT_CONFIRMED
    }

    public enum Role {
        SPEAKER, LISTENER, ADMIN
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isActive() { return this.state == State.STATE_ACTIVE; }
}
