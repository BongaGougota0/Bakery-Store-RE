package za.co.bakery.backend.data.entity;

import java.io.Serializable;

//@MappedSuperClass()
public class AbstractDataEntity implements Serializable {
    private Long id;
    private int version;
}
