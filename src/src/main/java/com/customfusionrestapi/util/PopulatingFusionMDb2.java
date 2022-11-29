package com.customfusionrestapi.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.customfusionrestapi.entity.RelianceMdb2ROGCPlantDatabase;
import com.customfusionrestapi.entity.ReliancePlantDatabase;
import com.customfusionrestapi.entity.RelianceMdb2ROGCPlantDatabase;
import com.customfusionrestapi.repos.ReliancePlantRepository;
import com.customfusionrestapi.service.GeoReferenceBinzConvertService;
import com.customfusionrestapi.repos.RelianceMdb2ROGCPlantDatabaseRepository;

@EnableTransactionManagement
@Component
public class PopulatingFusionMDb2 {
	private static final Logger log = LoggerFactory.getLogger(PopulatingFusionMDb2.class);

	@Value("${myApp.uploadsMdb2Path}")
	private String mdb2Path;

	@Value("${myApp.geojsonPath}")
	private String outputBinzPath;

	@Autowired
	private ReliancePlantRepository plantRepository;

	@Autowired
	private RelianceMdb2ROGCPlantDatabaseRepository rogcPlantRepository;

	@Transactional
	public void databaseProcessing(String unitname) throws SQLException, IOException, ClassNotFoundException {

		Set<String> linkageList = plantRepository.findDistinctLinkage();

		int c = 1, i = 1;

		double minX, maxX, minY, maxY, minz, maxz = 0.0;

		double minValZ = 0.00;
		double maxValZ = 0.00;

		List<RelianceMdb2ROGCPlantDatabase> rogcPlantDbList = new ArrayList<RelianceMdb2ROGCPlantDatabase>();

		List<String> boundsList = new ArrayList<String>();

		Connection mdb2conn;

		String mdb2FileName = "J3ROGC" + ".mdb2"; // j3rogc.mdb2

		System.out.println(mdb2Path);

		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			log.info("driver loaded");

		} catch (ClassNotFoundException e) {
			log.info("the class driver can't be loaded");
		}

		mdb2conn = DriverManager.getConnection("jdbc:ucanaccess://C://Automation//mdb2//" + mdb2FileName);

		System.out.println("Before:");

		PreparedStatement preparedStatement = mdb2conn.prepareStatement(
				"select a.label_name, b.label_value from label_values b, label_names a,labels c,linkage d where a.label_name_index=c.label_name_index and b.label_value_index=c.label_value_index and c.linkage_index=d.linkage_index and d.DMRSLinkage=?");

		ResultSet rs;

		JSONObject prop = new JSONObject();

		System.out.print("Coming");
		for (String linkage : linkageList) {

			List<ReliancePlantDatabase> itr = plantRepository.findByLinkage(linkage);
			String featureId = "";
			minValZ = itr.get(0).getMinz();
			maxValZ = itr.get(0).getMaxz();

			for (ReliancePlantDatabase it : itr) {

				featureId = featureId + it.getFeatureid() + ",";

				minz = it.getMinz();
				maxz = it.getMaxz();
				if (minValZ > minz) {
					minValZ = minz;
				}
				if (maxValZ < maxz) {
					maxValZ = maxz;
				}

				boundsList.add(it.getBounds());

			}

			// System.out.println("BoundaryList" + boundsList.toString().replaceAll("\\],",
			// "\\] "));
			String[] keyValuePairs = boundsList.toString().replaceAll("\\s+", "").replaceAll("\\],", "\\] ").split(" ");

			// System.out.println("keyValuePairs" + keyValuePairs);

			Map<Double, Double> doubleMap = new HashMap<>();

			for (String entry : keyValuePairs) {

				// System.out.println("entry:" + entry);

				String[] coordinates = entry.replaceAll("\\[", " ").replaceAll("\\]", " ").split(",");

				if (StringUtils.isNoneBlank(coordinates[0]) & StringUtils.isNoneBlank(coordinates[1])) {
					doubleMap.put(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
				}

			}

			// System.out.println("Center X:" + doubleMap.keySet().stream().mapToDouble(a ->
			// a).average().getAsDouble());

			// System.out.println("Center Y:" + doubleMap.values().stream().mapToDouble(a ->
			// a).average().getAsDouble());

			String centerbounds = "[" + doubleMap.keySet().stream().mapToDouble(a -> a).average().getAsDouble() + ","
					+ doubleMap.values().stream().mapToDouble(a -> a).average().getAsDouble() + "]";

			String originalBounds = boundsList.toString();

			// System.out.println("Max x:" + doubleMap.keySet().stream().mapToDouble(a ->
			// a).max().getAsDouble());

			maxX = doubleMap.keySet().stream().mapToDouble(a -> a).max().getAsDouble();

			// System.out.println("Min x:" + doubleMap.keySet().stream().mapToDouble(a ->
			// a).min().getAsDouble());

			minX = doubleMap.keySet().stream().mapToDouble(a -> a).min().getAsDouble();

			// System.out.println("Max y:" + doubleMap.values().stream().mapToDouble(a ->
			// a).max().getAsDouble());

			maxY = doubleMap.values().stream().mapToDouble(a -> a).max().getAsDouble();

			// System.out.println("Min y:" + doubleMap.values().stream().mapToDouble(a ->
			// a).min().getAsDouble());

			minY = doubleMap.values().stream().mapToDouble(a -> a).min().getAsDouble();

			String boundaryBounds = "[" + "[" + minX + "," + minY + "," + minValZ + "]" + "[" + maxX + "," + maxY + ","
					+ maxValZ + "]" + "]";

			preparedStatement.setString(1, linkage);

			rs = preparedStatement.executeQuery();

			prop.clear();

			while (rs.next()) {

				prop.put(rs.getString(1), rs.getString(2));

			}

			rogcPlantDbList.add(new RelianceMdb2ROGCPlantDatabase(c++, linkage, (String) prop.get("Name"),
					(String) prop.get("Equipment Number"), prop.toString(),
					"[" + featureId.substring(0, featureId.length() - 1) + "]", originalBounds, centerbounds,
					boundaryBounds, maxX, minX, maxY, minY, maxValZ, minValZ));

			boundsList.clear();

			originalBounds = null;
			centerbounds = null;
			boundaryBounds = null;

			log.info("Going forward... " + i++);
		}

		rogcPlantRepository.saveAll(rogcPlantDbList);

		rogcPlantRepository.flush();

		rogcPlantDbList.clear();
		System.out.println("Completed");

	}

//	@Transactional
//	public void databaseProcessing(String unitname) throws SQLException, IOException, ClassNotFoundException {
//
//		Set<String> linkageList = plantRepository.findDistinctLinkage();
//
//		int c = 1;
//
//		double minX, maxX, minY, maxY, minz, maxz = 0.0;
//
//		double minValZ = 0.00;
//		double maxValZ = 0.00;
//
//		List<RelianceMdb2ROGCPlantDatabase> rogcPlantDbList = new ArrayList<RelianceMdb2ROGCPlantDatabase>();
//
//		List<String> boundsList = new ArrayList<String>();
//
//		for (String linkage : linkageList) {
//
//			List<ReliancePlantDatabase> itr = plantRepository.findByLinkage(linkage);
//			String featureId = "";
//			minValZ = itr.get(0).getMinz();
//			maxValZ = itr.get(0).getMaxz();
//
//			for (ReliancePlantDatabase it : itr) {
//
//				featureId = featureId + it.getFeatureid() + ",";
//
//				minz = it.getMinz();
//				maxz = it.getMaxz();
//				if (minValZ > minz) {
//					minValZ = minz;
//				}
//				if (maxValZ < maxz) {
//					maxValZ = maxz;
//				}
//
//				boundsList.add(it.getBounds());
//
//			}
//
//			// System.out.println("BoundaryList" + boundsList.toString().replaceAll("\\],",
//			// "\\] "));
//			String[] keyValuePairs = boundsList.toString().replaceAll("\\s+", "").replaceAll("\\],", "\\] ").split(" ");
//
//			// System.out.println("keyValuePairs" + keyValuePairs);
//
//			Map<Double, Double> doubleMap = new HashMap<>();
//
//			for (String entry : keyValuePairs) {
//
//				// System.out.println("entry:" + entry);
//
//				String[] coordinates = entry.replaceAll("\\[", " ").replaceAll("\\]", " ").split(",");
//
//				if (StringUtils.isNoneBlank(coordinates[0]) & StringUtils.isNoneBlank(coordinates[1])) {
//					doubleMap.put(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
//				}
//
//			}
//
//			// System.out.println("Center X:" + doubleMap.keySet().stream().mapToDouble(a ->
//			// a).average().getAsDouble());
//
//			// System.out.println("Center Y:" + doubleMap.values().stream().mapToDouble(a ->
//			// a).average().getAsDouble());
//
//			String centerbounds = "[" + doubleMap.keySet().stream().mapToDouble(a -> a).average().getAsDouble() + ","
//					+ doubleMap.values().stream().mapToDouble(a -> a).average().getAsDouble() + "]";
//
//			String originalBounds = boundsList.toString();
//
//			// System.out.println("Max x:" + doubleMap.keySet().stream().mapToDouble(a ->
//			// a).max().getAsDouble());
//
//			maxX = doubleMap.keySet().stream().mapToDouble(a -> a).max().getAsDouble();
//
//			// System.out.println("Min x:" + doubleMap.keySet().stream().mapToDouble(a ->
//			// a).min().getAsDouble());
//
//			minX = doubleMap.keySet().stream().mapToDouble(a -> a).min().getAsDouble();
//
//			// System.out.println("Max y:" + doubleMap.values().stream().mapToDouble(a ->
//			// a).max().getAsDouble());
//
//			maxY = doubleMap.values().stream().mapToDouble(a -> a).max().getAsDouble();
//
//			// System.out.println("Min y:" + doubleMap.values().stream().mapToDouble(a ->
//			// a).min().getAsDouble());
//
//			minY = doubleMap.values().stream().mapToDouble(a -> a).min().getAsDouble();
//
//			String boundaryBounds = "[" + "[" + minX + "," + minY + "," + minValZ + "]" + "[" + maxX + "," + maxY + ","
//					+ maxValZ + "]" + "]";
//			
//			
//			
//			
//
//			rogcPlantDbList.add(new RelianceMdb2ROGCPlantDatabase(c++, linkage, "", "",
//					"[" + featureId.substring(0, featureId.length() - 1) + "]", unitname, originalBounds, centerbounds,
//					boundaryBounds, maxX, minX, maxY, minY, maxValZ, minValZ));
//
//			boundsList.clear();
//
//			originalBounds = null;
//			centerbounds = null;
//			boundaryBounds = null;
//
//			System.out.println("Going forward " + " ..." + c);
//		}
//
//		rogcPlantRepository.saveAll(rogcPlantDbList);
//
//		rogcPlantRepository.flush();
//
//		rogcPlantDbList.clear();
//		System.out.println("Completed");
//
//	}

	@Transactional
	public void dmrLinkageProcessing() throws SQLException, IOException, ClassNotFoundException {

		Connection mdb2conn;

		String mdb2FileName = "J3ROGC" + ".mdb2"; // j3rogc.mdb2

		System.out.println(mdb2Path);

		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			log.info("driver loaded");

		} catch (ClassNotFoundException e) {
			log.info("the class driver can't be loaded");
		}

		mdb2conn = DriverManager.getConnection("jdbc:ucanaccess://C://Automation//mdb2//" + mdb2FileName);

		PreparedStatement preparedStatement = mdb2conn.prepareStatement("select * from linkage");

		ResultSet rs = preparedStatement.executeQuery();

		PreparedStatement preparedStatement1 = mdb2conn.prepareStatement(
				"Update  linkage d set d.DMRSLinkage = CONCAT(d.link_Key_1,' ', d.link_key_2,' ',d.link_key_3,' ',d.link_key_4) where d.linkage_index=?");

		int c = 1;
		while (rs.next()) {

			preparedStatement1.setInt(1, c++);
			preparedStatement1.executeUpdate();
			System.out.println("Going Forwad.." + c);
		}

		System.out.println("Finished");

	}
}