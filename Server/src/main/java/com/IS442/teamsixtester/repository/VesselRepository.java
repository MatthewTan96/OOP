package com.IS442.teamsixtester.repository;

import com.IS442.teamsixtester.domain.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<Vessel, Long> {
}
