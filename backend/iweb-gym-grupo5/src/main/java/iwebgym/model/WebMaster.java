package iwebgym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "webMasters")
public class WebMaster extends User{
}
