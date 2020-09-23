package com.IS442.teamsixtester.repository;

import com.IS442.teamsixtester.domain.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VesselRepository extends JpaRepository<Vessel, Integer> {
}
