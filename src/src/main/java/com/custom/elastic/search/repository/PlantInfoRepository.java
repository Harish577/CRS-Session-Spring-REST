package com.custom.elastic.search.repository;

import java.util.List;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import com.custom.elastic.search.model.PlantInfo;
@EnableElasticsearchRepositories
public interface PlantInfoRepository  extends ElasticsearchRepository<PlantInfo, Integer>{

	public PlantInfo findByMoniker(String moniker );
	List<PlantInfo> findByTag(String tag);

	List<PlantInfo> findByFeatureid(Long featureid);
	PlantInfo findByMonikerOrTagOrFeatureid(String moniker, String tag,String featureid );
}
