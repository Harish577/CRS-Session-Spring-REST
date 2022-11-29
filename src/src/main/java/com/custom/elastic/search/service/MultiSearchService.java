package com.custom.elastic.search.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.custom.elastic.search.controller.PlantInfoController;
import com.custom.elastic.search.model.PlantInfo;
import com.custom.elastic.search.model.RequestResponse;
import com.custom.elastic.search.repository.PlantInfoRepository;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import org.springframework.stereotype.Service;

@Service
public class MultiSearchService {
	private static final Logger log = LoggerFactory.getLogger(MultiSearchService.class);
	
	
	@Autowired
	private PlantInfoRepository planInfoRepository;
	public PlantInfo multiMatchQuery(String text) {
		
	 PlantInfo plantinfolist=	planInfoRepository.findByMonikerOrTagOrFeatureid(text,text,text);
		return plantinfolist;
	}
	
    


}
