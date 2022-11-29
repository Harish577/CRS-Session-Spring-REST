package com.customfusionrestapi.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.customfusionrestapi.dto.Bounds;
import com.customfusionrestapi.dto.CorrosionResponse;
import com.customfusionrestapi.dto.Crs;
import com.customfusionrestapi.dto.Feature;
import com.customfusionrestapi.dto.Geometry;
import com.customfusionrestapi.dto.Properties;
import com.customfusionrestapi.dto.Properties_;
import com.customfusionrestapi.dto.TagResponse;
import com.customfusionrestapi.entity.RelianceMdb2ROGCPlantDatabase;
import com.customfusionrestapi.entity.ReliancePlantDatabase;
import com.customfusionrestapi.repos.RelianceMdb2ROGCPlantDatabaseRepository;
import com.customfusionrestapi.repos.ReliancePlantRepository;
import com.customfusionrestapi.util.HttpUtil;
import com.google.gson.Gson;

@Service
@EntityScan("com.customfusionrestapi.entity")
@EnableJpaRepositories({ "com.customfusionrestapi.repos" })
public class CorrosionLoopService {

//	@Autowired
//	private RelianceMdb2ROGCPlantDatabaseRepository plantInfoModelRepository;

	@Autowired
	private ReliancePlantRepository plantInfoModelRepository;

	@Value("${sdx.linkageDataForCorrosionLoop}")
	private String linkageDataForCorrosionLoopEndPoint;

	@Value("${sdx.tagMetaData}")
	private String sdxTagMetaDataEndPoint;

	@Value("${sdx.linkageData}")
	private String linkageDataEndPoint;

	@Autowired
	private HttpUtil httpUtil;

	public String getCorrosionLoopBasedOnLoopName(String authorization, String loopName)
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {

		String linkageResponse = getLinkageForCorrosionLoop(authorization, loopName);

		System.out.println(linkageResponse);

		String[] linkageArray = linkageResponse.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "")
				.replaceAll("Related_GOIDs:", "").split(",");

		CorrosionResponse corrosionResponse = new CorrosionResponse();
		corrosionResponse.setType("FeatureCollection");

		Crs crs = new Crs();
		crs.setType("name");

		Properties_ crsProperties = new Properties_();
		crsProperties.setName("urn:ogc:def:crs:OGC:1.3:CRS84");
		crs.setProperties(crsProperties);

		corrosionResponse.setCrs(crs);

		List<Feature> featureList = new ArrayList<Feature>();

		AtomicInteger count = new AtomicInteger(1);

		Arrays.asList(linkageArray).stream().map(it -> it.toString()).forEach(it -> {

//			RelianceMdb2ROGCPlantDatabase pm = plantInfoModelRepository.findByLinkage(it);

			ReliancePlantDatabase pm = plantInfoModelRepository.findByLinkage(it).get(0);

			if (pm != null) {
				Feature feature = new Feature();
				feature.setId(count.getAndIncrement());
				feature.setType("feature");

				Geometry geometry = new Geometry();

				Bounds bounds = new Bounds(pm.getMinx(), pm.getMaxx(), pm.getMiny(), pm.getMaxy(), pm.getMinz(),
						pm.getMaxz());
				geometry.setBounds(bounds);
				geometry.setType("Polygon");

				feature.setGeometry(geometry);

				Properties featureProperties = new Properties();

				featureProperties.setFeatureID(pm.getFeatureid());
				featureProperties.setLinkage(it);
				// String[] centroid = pm.getOriginalBounds().replaceAll("\\[", "
				// ").replaceAll("\\]", " ").split(",");
				String[] centroid = pm.getBounds().replaceAll("\\[", " ").replaceAll("\\]", " ").split(",");
				featureProperties.setCenterX(centroid[0]);
				featureProperties.setCenterY(centroid[1]);

				feature.setProperties(featureProperties);
				featureList.add(feature);

			}

		});

		corrosionResponse.setFeatures(featureList);

		return new Gson().toJson(corrosionResponse);

	}

	public String getLinkageAndTagInfo(String select, String filter, String authorization)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		// TODO Auto-generated method stub

		HttpHeaders headers = httpUtil.getHttpHeaders();

		headers.add("Authorization", authorization);
		headers.add("X-Ingr-OnBehalfOf", "sujana");
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.getRestTemplate();

		String url = linkageDataEndPoint + "?$select=" + select + "&$filter=" + filter;

		System.out.println("Url:" + url);
		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				TagResponse.class);

		return new Gson().toJson(responseEntity.getBody().getValue());
	}

	public Object getTagMetadata(String select, String filter, String authorization)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		HttpHeaders headers = httpUtil.getHttpHeaders();
		headers.add("Authorization", authorization);
		headers.add("X-Ingr-OnBehalfOf", "sujana");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.getRestTemplate();

		String url = sdxTagMetaDataEndPoint + "?$select=" + select + "&$filter=" + filter;

		System.out.println("Url:" + url);

		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				TagResponse.class);
		return responseEntity.getBody().getValue();

	}

	public String getTagBasedOnLinkage(String linkage, String authorization)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {

		String filter = "Graphic_OID eq " + "'" + linkage + "'";

		System.out.println("Filter:" + filter);
		String tagResponse = getLinkageAndTagInfo("Tag", filter, authorization);

		String tagParsedResponse = tagResponse.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "")
				.replaceAll("Tag:", "");

		System.out.print("tagParsedResponse" + tagParsedResponse);

		String select = "UID,Name,Description,Level_Classification,Config,Creation_User,Id";

		List<Object> response = Arrays.asList(tagParsedResponse.split(",")).stream().map(it -> {
			try {
				return getTagMetadata(select, "Name eq " + "'" + it + "'", authorization);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {

				return null;
			}

		}).collect(Collectors.toList());

		return new Gson().toJson(response);

	}

	public String getLinkageForCorrosionLoop(String authorization, String loopName)
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		HttpHeaders headers = httpUtil.getHttpHeaders();
		headers.add("Authorization", authorization);
		headers.add("X-Ingr-OnBehalfOf", "sujana");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.getRestTemplate();

		String filter = "Name eq " + "'" + loopName + "'";

		String url = linkageDataForCorrosionLoopEndPoint + "?$select=" + "Related_GOIDs" + "&$filter=" + filter;

		System.out.print("Url" + url);

		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				TagResponse.class);

		return new Gson().toJson(responseEntity.getBody().getValue());
	}

	public String getLinkages(String authorization)
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		HttpHeaders headers = httpUtil.getHttpHeaders();
		headers.add("Authorization", authorization);
		headers.add("X-Ingr-OnBehalfOf", "sujana");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.getRestTemplate();

		String url = linkageDataForCorrosionLoopEndPoint + "?$select=" + "Name,Related_GOIDs";

		System.out.print("Url" + url);

		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				TagResponse.class);

		responseEntity.getBody().getValue();

		System.out.println("CorrosionLoop:" + responseEntity.getBody().getValue());

		return new Gson().toJson(responseEntity.getBody().getValue());

	}

	public String getLinkagesBasedOnSelectedParam(String filter, String authorization)
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		HttpHeaders headers = httpUtil.getHttpHeaders();
		headers.add("Authorization", authorization);
		headers.add("X-Ingr-OnBehalfOf", "sujana");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.getRestTemplate();

		String url = linkageDataForCorrosionLoopEndPoint + "?$select=" + "Related_GOIDs" + "&$filter=" + filter;

		System.out.print("Url" + url);

		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				TagResponse.class);

		responseEntity.getBody().getValue();

		System.out.println("CorrosionLoop:" + responseEntity.getBody().getValue());

		return new Gson().toJson(responseEntity.getBody().getValue());

	}

	public String getCorrosionLoopNames(String authorization)
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		HttpHeaders headers = httpUtil.getHttpHeaders();
		headers.add("Authorization", authorization);
		headers.add("X-Ingr-OnBehalfOf", "sujana");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.getRestTemplate();

		String url = linkageDataForCorrosionLoopEndPoint + "?$select=" + "Name";

		System.out.print("Url" + url);

		ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				TagResponse.class);

		responseEntity.getBody().getValue();

		System.out.println("CorrosionLoop:" + responseEntity.getBody().getValue());

		return new Gson().toJson(responseEntity.getBody().getValue());

	}

}
