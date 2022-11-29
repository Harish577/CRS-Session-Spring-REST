package com.customfusionrestapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customfusionrestapi.entity.RelianceShapesModel;

@Repository
public interface RelianceShapesModelRepository extends JpaRepository<RelianceShapesModel, Integer> {

}
