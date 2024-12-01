package iwebgym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "monitores")
public class Monitor extends User{
}
