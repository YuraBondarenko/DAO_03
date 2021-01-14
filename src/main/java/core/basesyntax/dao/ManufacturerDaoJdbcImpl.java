package core.basesyntax.dao;

import core.basesyntax.exceptions.DataProcessingException;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoJdbcImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerQuery = "INSERT INTO `manufacturers`"
                + " (manufacturer_name, manufacturer_country)"
                + " VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(createManufacturerQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdQuery = "SELECT * FROM manufacturers WHERE manufacturer_id = ? "
                + "AND manufacturer_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturersQuery = "SELECT * FROM manufacturers "
                + "WHERE manufacturer_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(getAllManufacturersQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet).get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get any manufacturer", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET manufacturer_name = ?, "
                + "manufacturer_country = ? "
                + "WHERE manufacturer_id = ? AND manufacturer_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteByIdQuery = "UPDATE manufacturers SET manufacturer_deleted = true "
                + "WHERE manufacturer_id = ? AND manufacturer_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(deleteByIdQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        String deleteByManufacturerQuery = "UPDATE manufacturers SET manufacturer_deleted = true "
                + "WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(deleteByManufacturerQuery)) {
            preparedStatement.setLong(1, manufacturer.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer " + manufacturer, e);
        }
    }

    private Optional<Manufacturer> getManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufactureName = resultSet.getObject("manufacturer_name", String.class);
        String manufacturerCountry = resultSet.getObject("manufacturer_country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufactureName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        return Optional.of(manufacturer);
    }
}
