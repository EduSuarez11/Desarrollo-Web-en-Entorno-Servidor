package es.daw.clinicaapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=40)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AppUser> users = new HashSet<>();

    public void addUser(AppUser user) {
        users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(AppUser user) {
        users.remove(user);
        user.getRoles().remove(this);
    }

}
