package com.example.network.emulator.repositories;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query("SELECT c FROM Connection c WHERE c.fromDevice.id = :fromId")
    List<Connection> findByFromDevice(@Param("fromId") Long fromDevice);

    @Query("SELECT c FROM Connection c WHERE c.toDevice.id = :toId")
    List<Connection> findByToDevice(@Param("toId") Long toDevice);
}