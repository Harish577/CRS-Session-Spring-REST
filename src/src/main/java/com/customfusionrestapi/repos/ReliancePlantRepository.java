package com.customfusionrestapi.repos;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customfusionrestapi.entity.ReliancePlantDatabase;

@Repository
public interface ReliancePlantRepository extends JpaRepository<ReliancePlantDatabase, Integer> {

	@Query(value = "select  distinct(linkage) from  ReliancePlantDatabase model where model.linkage  like '% %' ")
	public Set<String> findDistinctLinkage();

	@Query(value = "from ReliancePlantDatabase model where model.linkage=:linkage")
	public List<ReliancePlantDatabase> findByLinkage(@Param("linkage") String linkage);
}