package com.customfusionrestapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customfusionrestapi.entity.MeasuringPoint;

@Repository
public interface MeasuringPointRepository extends JpaRepository<MeasuringPoint, Integer> {

	@Query(value = "from MeasuringPoint model where model.id=:id")
	public MeasuringPoint findByID(@Param("id") Integer id);
	
	@Query(value = "from MeasuringPoint model where model.corrosioncomponent=:corrosioncomponent")
	public MeasuringPoint findByCorrosionComponent(@Param("corrosioncomponent") String corrosioncomponent);

}
