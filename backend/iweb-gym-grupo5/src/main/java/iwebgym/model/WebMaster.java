package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "webMasters")
public class WebMaster extends User{

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "webmaster_actividad",
            joinColumns = { @JoinColumn(name = "webmaster_id") },
            inverseJoinColumns = {@JoinColumn(name = "actividad_id")})
    private Set<Actividad> actividades = new HashSet<>();
}
