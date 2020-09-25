package com.IS442.teamsixtester.dao;

import com.IS442.teamsixtester.model.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PostgresDataAccessService implements Dao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertVessel(UUID id, Vessel vessel) {
        String sql = "INSERT INTO shipping_info (vessel_id, vessel_short_name, incoming_voyage_number, " +
                "outgoing_voyage_number, berth_time_required, expected_datetime_departure, berth_number, " +
                "status, change_count, degree_change, first_berth_time)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                id,
                vessel.getAbbrVslM(),
                vessel.getInVoyN(),
                vessel.getOutVoyN(),
                vessel.getBthgDt(),
                vessel.getUnbthgDt(),
                vessel.getBerthN(),
                vessel.getStatus(),
                vessel.getChangeCount(),
                vessel.getDegreeChange(),
                vessel.getBthgDt()
        );
    }

    @Override
    public List<Vessel> selectAllVessels() {
        final String sql = "SELECT * FROM shipping_info;";
        return jdbcTemplate.query(sql, mapVesselFromDB());
    }

    @Override
    public Optional<Vessel> selectVesselByIncoming(String name, String incoming){
        Vessel resultVessel = null;
        try {
            String sql = "SELECT * FROM shipping_info WHERE " +
                    "vessel_short_name = ? and " +
                    "incoming_voyage_number = ?";
            resultVessel = jdbcTemplate.queryForObject(sql, new Object[]{name, incoming}, mapVesselFromDB());
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
        return Optional.ofNullable(resultVessel);
    }

    @Override
    public Optional<Vessel> selectVesselByOutgoing(String name, String outgoing) {
        Vessel resultVessel = null;
        try {
            String sql = "SELECT * FROM shipping_info WHERE " +
                    "vessel_short_name = ? and " +
                    "outgoing_voyage_number = ?";
            resultVessel = jdbcTemplate.queryForObject(sql, new Object[]{name, outgoing}, mapVesselFromDB());
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(resultVessel);
    }

    @Override
    public int deleteVesselById(UUID id) {
        String sql = "DELETE FROM shipping_info WHERE vessel_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateVessel(UUID id, Vessel vessel) {
        return 0;
    }

    private RowMapper<Vessel> mapVesselFromDB() {
        return (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("vessel_id"));
            String vesselShortName = resultSet.getString("vessel_short_name");
            String incomingVoyageNumber = resultSet.getString("incoming_voyage_number");
            String outgoingVoyageNumber = resultSet.getString("outgoing_voyage_number");
            String berthTimeRequired = resultSet.getString("berth_time_required");
            String expectedDatetimeDeparture = resultSet.getString("expected_datetime_departure");
            String berthNumber = resultSet.getString("berth_number");
            String status = resultSet.getString("status");
            int changeCount = resultSet.getInt("change_count");
            double degreeChange = resultSet.getDouble("degree_change");
            String firstBerthTime = resultSet.getString("first_berth_time");
            return new Vessel(id, vesselShortName, incomingVoyageNumber, outgoingVoyageNumber,
                            berthTimeRequired, expectedDatetimeDeparture, berthNumber, status,
                            changeCount, degreeChange, firstBerthTime);
        };
    }

}
