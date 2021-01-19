package core.basesyntax.dao;

import core.basesyntax.model.Manufacturer;

public interface ManufacturerDao extends GenericDao<Manufacturer, Long> {
    boolean delete(Manufacturer manufacturer);
}
