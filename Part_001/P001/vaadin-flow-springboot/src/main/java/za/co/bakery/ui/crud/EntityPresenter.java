package za.co.bakery.ui.crud;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import za.co.bakery.app.HasLogger;
import za.co.bakery.backend.data.entity.AbstractDataEntity;
import za.co.bakery.backend.service.CrudService;
import za.co.bakery.ui.views.EntityView;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EntityPresenter<T extends AbstractDataEntity, V extends EntityView<T>> implements HasLogger {

    private CrudService<T> service;
}
