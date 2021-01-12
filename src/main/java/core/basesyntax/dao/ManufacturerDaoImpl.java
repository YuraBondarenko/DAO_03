package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Manufacturer;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
        int index = IntStream.range(0, Storage.manufacturers.size())
                .filter(i -> Storage.manufacturers.get(i).getId().equals(manufacturer.getId()))
                .findFirst()
                .getAsInt();
        Storage.manufacturers.set(index, manufacturer);
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.remove(get(id).get());
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        return Storage.manufacturers.remove(manufacturer);
    }
}
