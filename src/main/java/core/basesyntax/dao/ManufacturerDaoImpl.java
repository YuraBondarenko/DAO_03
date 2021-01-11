package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Manufacturer;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.manufacturers.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer newManufacturer = getAll().stream()
                .filter(s -> s.getId().equals(manufacturer.getId()))
                .findFirst()
                .get();
        newManufacturer.setName(manufacturer.getName());
        newManufacturer.setCountry(manufacturer.getCountry());
        return newManufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return delete(get(id).get());
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        return Storage.manufacturers.remove(manufacturer);
    }
}
