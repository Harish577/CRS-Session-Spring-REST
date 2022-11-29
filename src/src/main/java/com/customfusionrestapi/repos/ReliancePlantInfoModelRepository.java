package com.customfusionrestapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customfusionrestapi.entity.ReliancePlantInfoModel;

@Repository
public interface ReliancePlantInfoModelRepository extends JpaRepository<ReliancePlantInfoModel, Integer> {

	@Query(value = "from ReliancePlantInfoModel model where model.moniker=:moniker")
	public ReliancePlantInfoModel findbyMoniker(@Param("moniker") String moniker);

}
