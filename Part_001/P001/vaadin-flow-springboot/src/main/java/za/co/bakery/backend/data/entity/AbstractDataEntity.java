package za.co.bakery.backend.data.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;

import java.io.Serializable;

@MappedSuperclass
@Getter
public abstract class AbstractDataEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Version
    private int version;
}
