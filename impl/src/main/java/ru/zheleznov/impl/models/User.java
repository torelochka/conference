package ru.zheleznov.impl.models;

import lombok.*;
import org.hibernate.annotations.Cascade;

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
    private String email;
    private String password;

    private UUID confirmedCode;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "speakers")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Talk> talks;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_LISTENER;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private State state = State.STATE_ACTIVE;

    public enum State {
        STATE_ACTIVE, STATE_BANNED
    }

    public enum Role {
        ROLE_SPEAKER, ROLE_LISTENER, ROLE_ADMIN
    }

    public boolean isAdmin() {
        return this.role == Role.ROLE_ADMIN;
    }

    public boolean isActive() { return this.state == State.STATE_ACTIVE; }
}
