package com.customfusionrestapi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.customfusionrestapi.dto.BinzBody;
import com.customfusionrestapi.dto.BinzServicesResponse;
import com.customfusionrestapi.util.HttpUtil;
import com.customfusionrestapi.util.PopulatingFusionDB;
import com.customfusionrestapi.util.PopulatingFusionMDb2;
import com.google.gson.Gson;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.luciad.format.binz.TLcdBinzModelDecoder;
import com.luciad.format.geojson.TLcdGeoJsonModelEncoder;
import com.luciad.model.ILcdModel;

@Service
public class GeoReferenceBinzConvertService {

	@Value("${myApp.uploadsBinzPath}")
	private String binzPath;

	@Value("${myApp.geojsonPath}")
	private String geojsonPath;

	@Value("${myApp.llh}")
	private String llh;

	@Autowired
	private PopulatingFusionDB populatingFusionDB;

	@Autowired
	private PopulatingFusionMDb2 populatingFusionMdb2;

	@Value("${myApp.host}")
	private String host;

	@Value("${myApp.sdxOutput}")
	private String sdxOutput;

	@Autowired
	private HttpUtil httpUtil;

	String binzFileName = "", dataID = "", dtRtHref = "", productID = "", serviceID = "", prodID = "", servID = "";

	private static final Logger log = LoggerFactory.getLogger(GeoReferenceBinzConvertService.class);

//	public ResponseEntity<Map<String, String>> geoReferencedBinzConversion(MultipartFile binzfiles) throws KeyManagementException, NoSuchAlgorithmException, ClassNotFoundException,
//			IOException, JSONException, SQLException, InterruptedException {
//
//		Map<String, String> response = new HashMap<>();
//
//		Set<String> filesNames = binzAndMdb2FilesMatchValidation(binzfiles);
//		System.out.println("filesNames" + filesNames.size());
//
//		generateResponse(filesNames);
//
//		System.out.println("database population started");
//
//		populatingFusionDB.databasePopulation(filesNames);
//
//		log.info("database population ended");
//		response.put("message",
//				"Successfully converted all Binz to ogc3dtiles and created services and populated the db");
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//
//	}

// Testing the build

	public Set<String> binzAndMdb2FilesMatchValidation(MultipartFile Binzfiles)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, JSONException, ClassNotFoundException,
			SQLException, InterruptedException {

		byte[] bytes;
		Path path;

		Map<String, MultipartFile> validBinzFiles = new HashMap<String, MultipartFile>();

		validBinzFiles.put(FilenameUtils.removeExtension(Binzfiles.getOriginalFilename()), Binzfiles);

		Set<String> fileNames = null;

		if (!(validBinzFiles.keySet().isEmpty())) {

			fileNames = validBinzFiles.keySet();

			for (Map.Entry<String, MultipartFile> entry : validBinzFiles.entrySet()) {

				FileUtils.deleteDirectory(new File(binzPath + entry.getKey()));
				Files.createDirectory(Paths.get(binzPath + entry.getKey()));

				bytes = entry.getValue().getBytes();
				path = Paths.get(binzPath + entry.getKey() + "/" + entry.getValue().getOriginalFilename());
				Files.write(path, bytes); // Post the Binz files

				File f = new File(binzPath + entry.getKey() + "/" + entry.getKey() + ".llh");
				f.createNewFile();
				Files.write(f.toPath(), llh.getBytes()); // create llh files
			}

		}

		return fileNames;
	}

	public void generateResponse(Set<String> filesNames)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		for (String itr : filesNames) {

			binzFileName = itr;
			String binzInPath = binzPath + "/" + binzFileName + "/" + itr + ".binz";
			String geojsonFilePath = geojsonPath + binzFileName;
			String productName = binzFileName;
			String serviceName = binzFileName;

			// ILcdModelDecoder decoder = new TLcdBinzModelDecoder();
			// ILcdModel model = decoder.decode(binzInPath);

			FileUtils.deleteDirectory(new File(geojsonFilePath));
			Files.createDirectory(Paths.get(geojsonFilePath));

			File f = new File(geojsonFilePath);
			if (f.isDirectory()) {
				FileUtils.deleteDirectory(new File(geojsonFilePath));
				Files.createDirectory(Paths.get(geojsonFilePath));
			} else {
				f.mkdirs();
				Files.createDirectory(Paths.get(geojsonFilePath));
			}

			ILcdModel featuresModel = new TLcdBinzModelDecoder().decodeFeatures(binzInPath);
			new TLcdGeoJsonModelEncoder().export(featuresModel, geojsonFilePath + File.separator + "features.geojson");

			// create auth credentials
			String authStr = "admin:admin";
			String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

			// ignore SSL
			HttpComponentsClientHttpRequestFactory rF = httpUtil.ignoreSSL();

			// create headers
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);

			HttpEntity request = new HttpEntity(headers);

			dataID = addDataRoot(request, headers, rF);
			productID = addProduct(productName, request, headers, rF);
			addDataToProduct(headers, rF);
			serviceID = addService(serviceName, request, headers, rF);
			addProductToService(headers, rF);
			startService(request, rF);

		}

	}

	public String addProduct(String productName, HttpEntity request, HttpHeaders headers,
			HttpComponentsClientHttpRequestFactory rF) {
		String PROD_GET_URL = host + "/api/products?name=" + productName;

		ResponseEntity<String> PRODGetResponse = new RestTemplate(rF).exchange(PROD_GET_URL, HttpMethod.GET, request,
				String.class);

		List<Any> prodObjArray = JsonIterator.deserialize(PRODGetResponse.getBody()).get("products").asList();

		if (!prodObjArray.isEmpty()) {

			prodID = prodObjArray.get(0).get("id").toString();

			String PROD_DEL_URL = host + "/api/products/" + prodID;

			new RestTemplate(rF).exchange(PROD_DEL_URL, HttpMethod.DELETE, request, String.class);
		}
		String PRODUCT_POST_URL = host + "/api/products";
		// create headers

		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject productPostJsonObject = new JSONObject();
		productPostJsonObject.put("name", productName);
		productPostJsonObject.put("title", productName);

		// create request
		HttpEntity productPostRequest = new HttpEntity(productPostJsonObject.toString(), headers);

		ResponseEntity<String> productResponse = new RestTemplate(rF).exchange(PRODUCT_POST_URL, HttpMethod.POST,
				productPostRequest, String.class);

		productID = JsonIterator.deserialize(productResponse.getBody()).get("product").get("id").toString();

		return productID;
		// System.out.println(productID);
	}

	public String addDataRoot(HttpEntity request, HttpHeaders headers, HttpComponentsClientHttpRequestFactory rF) {

		String binzDataRootPath = binzPath + binzFileName;
		String DR_GET_URL = host + "/api/data-roots?rootPath=" + binzDataRootPath.replace('/', '\\');

		System.out.println(DR_GET_URL);
		ResponseEntity<String> drGetResponse = new RestTemplate(rF).exchange(DR_GET_URL, HttpMethod.GET, request,
				String.class);

		List<Any> datarootArray = JsonIterator.deserialize(drGetResponse.getBody()).get("dataRoots").asList();
		String dataRootPath = binzDataRootPath;

		if (datarootArray.isEmpty()) {
			String DATAROOT_POST_URL = host + "/api/data-roots";

			// create headers
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject dataRootInputJsonObject = new JSONObject();
			dataRootInputJsonObject.put("rootPath", dataRootPath);

			// create request
			HttpEntity dataRootRequest = new HttpEntity(dataRootInputJsonObject.toString(), headers);
			ResponseEntity<String> dataRootResponse = new RestTemplate(rF).exchange(DATAROOT_POST_URL, HttpMethod.POST,
					dataRootRequest, String.class);

			dtRtHref = JsonIterator.deserialize(dataRootResponse.getBody()).get("links").get(0).get("href").toString();
			String CRAWLJOB_GET_URL = host + dtRtHref;

			int t = 1;
			while (t > 0) {
				ResponseEntity<String> crawlJobResponse = new RestTemplate(rF).exchange(CRAWLJOB_GET_URL,
						HttpMethod.GET, request, String.class);

				String crawlJoblastExRes = JsonIterator.deserialize(crawlJobResponse.getBody()).get("crawlJob")
						.get("lastExecutionResult").toString();
				if (crawlJoblastExRes.equals("Success")) {
					break;
				} else
					t++;
			}

		}

		String dataFilePath = dataRootPath.replace("/", "\\") + "\\" + binzFileName + ".binz";
		String DATA_GET_URL = host + "/api/data?filePath=" + dataFilePath;

		ResponseEntity<String> dataResponse = new RestTemplate(rF).exchange(DATA_GET_URL, HttpMethod.GET, request,
				String.class);

		dataID = JsonIterator.deserialize(dataResponse.getBody()).get("data").get(0).get("id").toString();
		return dataID;
	}

	public void addDataToProduct(HttpHeaders headers, HttpComponentsClientHttpRequestFactory rF) {
		String PRODUCT_PUT_URL = host + "/api/products/" + productID + "/styled-data";

		String jsonInputStringProData = "[{\"data\": \"" + dataID + "\" }]";

		// create request
		HttpEntity productPutRequest = new HttpEntity(jsonInputStringProData, headers);

		ResponseEntity<String> productPutResponse = new RestTemplate(rF).exchange(PRODUCT_PUT_URL, HttpMethod.PUT,
				productPutRequest, String.class);
		// System.out.println(productPutResponse);
	}

	public String addService(String serviceName, HttpEntity request, HttpHeaders headers,
			HttpComponentsClientHttpRequestFactory rF) {
		String SERV_GET_URL = host + "/api/services?name=" + serviceName;
		ResponseEntity<String> serviceGetResponse = new RestTemplate(rF).exchange(SERV_GET_URL, HttpMethod.GET, request,
				String.class);
		List<Any> servObjArray = JsonIterator.deserialize(serviceGetResponse.getBody()).get("services").asList();

		if (!servObjArray.isEmpty()) {

			servID = servObjArray.get(0).get("id").toString();

			String SERV_DEL_URL = host + "/api/services/" + servID;

			new RestTemplate(rF).exchange(SERV_DEL_URL, HttpMethod.DELETE, request, String.class);
		}
		String SERVICE_POST_URL = host + "/api/services";
		// create headers

		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject servicePostJsonObject = new JSONObject();
		servicePostJsonObject.put("title", serviceName);
		servicePostJsonObject.put("name", serviceName);
		servicePostJsonObject.put("type", "ogc3dtiles");
		servicePostJsonObject.put("meshCompression", "Draco");

		// create request
		HttpEntity servicePostRequest = new HttpEntity(servicePostJsonObject.toString(), headers);

		ResponseEntity<String> serviceResponse = new RestTemplate(rF).exchange(SERVICE_POST_URL, HttpMethod.POST,
				servicePostRequest, String.class);

		serviceID = JsonIterator.deserialize(serviceResponse.getBody()).get("service").get("id").toString();

		return serviceID;
		// System.out.println(serviceID);
	}

	public void addProductToService(HttpHeaders headers, HttpComponentsClientHttpRequestFactory rF) {

		String SERVICE_POST_PRO_URL = host + "/api/services/" + serviceID + "/products";

		String jsonInputStringServicePro = "[{\"id\": \"" + productID + "\" }]";

		// create request
		HttpEntity serviceProductRequest = new HttpEntity(jsonInputStringServicePro, headers);

		ResponseEntity<String> serviceProPostResponse = new RestTemplate(rF).exchange(SERVICE_POST_PRO_URL,
				HttpMethod.POST, serviceProductRequest, String.class);

		// System.out.println(serviceProPostResponse);
	}

	public void startService(HttpEntity request, HttpComponentsClientHttpRequestFactory rF) {
		String Service_PUT_START_URL = host + "/api/services/" + serviceID + "/start";

		ResponseEntity<String> servicePutStartResponse = new RestTemplate(rF).exchange(Service_PUT_START_URL,
				HttpMethod.PUT, request, String.class);

		log.info("Successfully converted Binz to ogc3dtiles and created a service");

		System.out.println("Successfully converted Binz to ogc3dtiles and created a service");
	}

	public ResponseEntity<Map<String, String>> geoReferencedBinzConversion(BinzBody body)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ClassNotFoundException, JSONException,
			SQLException, InterruptedException {

		File file = new File(sdxOutput);
		FileInputStream input = new FileInputStream(file);
		MultipartFile binzfile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));

		Map<String, String> response = new HashMap<>();

		Set<String> filesNames = binzAndMdb2FilesMatchValidation(binzfile);
		System.out.println("filesNames" + filesNames.size());

		generateResponse(filesNames);

		System.out.println("database population started");

		populatingFusionDB.databasePopulation(filesNames);

		log.info("database population ended");
		response.put("message",
				"Successfully converted all Binz to ogc3dtiles and created services and populated the db");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

//	public ResponseEntity<Map<String, String>> geoReferencedBinzConversionMdb2()
//			throws IOException, KeyManagementException, NoSuchAlgorithmException, ClassNotFoundException, JSONException,
//			SQLException, InterruptedException {
//
//		File file = new File(sdxOutput);
//		FileInputStream input = new FileInputStream(file);
//		MultipartFile binzfile = new MockMultipartFile("file", file.getName(), "text/plain",
//				IOUtils.toByteArray(input));
//
//		Map<String, String> response = new HashMap<>();
//
//		Set<String> filesNames = binzAndMdb2FilesMatchValidation(binzfile);
//		System.out.println("filesNames" + filesNames.size());
//
//		generateResponse(filesNames);
//
//		System.out.println("database population started");
//
//		populatingFusionMdb2.databasePopulation(filesNames);
//
//		log.info("database population ended");
//		response.put("message",
//				"Successfully converted all Binz to ogc3dtiles and created services and populated the db");
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//
//	}

	public String geoReferencedBinzConversionAllServices() throws KeyManagementException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		String serviceUrl = host + "/api/services";

		HttpComponentsClientHttpRequestFactory rF = httpUtil.ignoreSSL();

		String authStr = "admin:admin";
		String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<BinzServicesResponse> serviceGetResponse = new RestTemplate(rF).exchange(serviceUrl,
				HttpMethod.GET, request, BinzServicesResponse.class);

		return new Gson().toJson(serviceGetResponse.getBody());

	}

	public ResponseEntity<Map<String, String>> databaseAutomation(MultipartFile binzfiles, MultipartFile mdb2files,
			String unitname) {

		return null;
	}

	public ResponseEntity<Map<String, String>> databaseProcessing(String unitname)
			throws ClassNotFoundException, SQLException, IOException {

		populatingFusionMdb2.databaseProcessing(unitname);

		Map<String, String> response = new HashMap<>();

		response.put("message",
				"Successfully converted all Binz to ogc3dtiles and created services and populated the db");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	public ResponseEntity<Map<String, String>> dmrLinkageProcessing()
			throws ClassNotFoundException, SQLException, IOException {

		populatingFusionMdb2.dmrLinkageProcessing();

		Map<String, String> response = new HashMap<>();

		response.put("message",
				"Successfully converted all Binz to ogc3dtiles and created services and populated the db");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
