package com.customfusionrestapi.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customfusionrestapi.entity.RelianceMdb2ROGCPlantDatabase;

@Repository
public interface RelianceMdb2ROGCPlantDatabaseRepository extends JpaRepository<RelianceMdb2ROGCPlantDatabase, Integer> {

	@Query(value = "from RelianceMdb2ROGCPlantDatabase model where model.linkage=:linkage")
	public RelianceMdb2ROGCPlantDatabase findByLinkage(@Param("linkage") String linkage);

	@Query(value = "from RelianceMdb2ROGCPlantDatabase model where model.equipment=:equipment")
	public List<RelianceMdb2ROGCPlantDatabase> findByEquipment(@Param("equipment") String equipment);

	@Query(value = "from RelianceMdb2ROGCPlantDatabase model where model.tag=:tag")
	public List<RelianceMdb2ROGCPlantDatabase> findByTag(@Param("tag") String tag);

}