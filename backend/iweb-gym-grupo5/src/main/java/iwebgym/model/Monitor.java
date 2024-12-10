package iwebgym.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "monitores")
public class Monitor extends User{

    @OneToMany(mappedBy = "instalaciones", cascade = CascadeType.ALL)
    private List<Actividad> actividades;

    public List<Actividad> getActividades(){
        return actividades;
    }

    public boolean addActividad(Actividad nuevaAct){
        
        if (nuevaAct != null) {
            actividades.add(nuevaAct);
            return true;
        }

        return false;
    }

    public boolean removeActividad(Actividad aEliminar){
        
        if (aEliminar != null && actividades.contains(aEliminar)){
            actividades.remove(aEliminar);
            return true;
        }

        return false;
    }
}
