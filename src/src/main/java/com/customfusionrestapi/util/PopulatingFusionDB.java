package com.customfusionrestapi.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
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
import com.customfusionrestapi.repos.RelianceMdb2ROGCPlantDatabaseRepository;
import com.customfusionrestapi.repos.ReliancePlantRepository;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

@EnableTransactionManagement
@Component
public class PopulatingFusionDB {
	private static final Logger log = LoggerFactory.getLogger(PopulatingFusionDB.class);

//	@Value("${myApp.uploadsMdb2Path}")
//	private String mdb2Path;

	@Value("${myApp.geojsonPath}")
	private String outputBinzPath;

	@Autowired
	private ReliancePlantRepository plantRepository;

	@Autowired
	private RelianceMdb2ROGCPlantDatabaseRepository rogcPlantRepository;

	@Transactional
	public void databasePopulation(Set<String> fileNames) throws SQLException, IOException, ClassNotFoundException {

		// Connection mdb2conn = null;

		// String mdb2InPath = null;

		String geojsonPath;

		// System.out.println("InPopulatingFusionDB1" + fileNames.toString());
		for (String it : fileNames) {

			// System.out.println("InPopulatingFusionDB2" + it);
			// String mdb2FileName = mdb2file + ".mdb2";

			// mdb2InPath = mdb2Path + mdb2FileName;

			// // System.out.println(mdb2InPath);

//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//
//			try {
//
//				mdb2conn = DriverManager.getConnection("jdbc:ucanaccess:///" + mdb2InPath
//						+ ";?useServerPrepStmts=false&rewriteBatchedStatements=true");
//			} catch (Exception e) {
//
//				throw new BusinessException("Cannot connect to the Database " + mdb2file);
//			}

			geojsonPath = outputBinzPath + it + "/features.geojson";

			File geojsonfile = new File(geojsonPath);

			byte[] bytes = FileUtils.readFileToByteArray(geojsonfile);

			String geojsondata = new String(bytes);

//			int value = matchmdb2_geojson(mdb2conn, geojsondata);
//
//			if (value == 0) {
//
//				mdb2conn.close();
//
//				throw new BusinessException(mdb2file + "MDB2 file does not match with its Geojson file");
//
//			}

			Any featuresarray = JsonIterator.deserialize(geojsondata).get("features");

//			PreparedStatement preparedStatement = mdb2conn.prepareStatement("select a.label_name, b.label_value from "
//					+ "label_values b, label_names a,labels c,linkage d "
//					+ "where a.label_name_index=c.label_name_index and b.label_value_index=c.label_value_index and "
//					+ "c.linkage_index=d.linkage_index and d.moniker=?");

//			PreparedStatement preparedStatement = mdb2conn.prepareStatement("select a.label_name, b.label_value from "
//			+ "label_values b, label_names a,labels c,linkage d "
//			+ "where a.label_name_index=c.label_name_index and b.label_value_index=c.label_value_index and "
//			+ "c.linkage_index=d.linkage_index and d.dmr_linkage=?");

			int c = 1;

			// ResultSet rs;
			String linkage;

			JSONObject prop = new JSONObject();

			List<ReliancePlantDatabase> pm = new ArrayList<ReliancePlantDatabase>();

			String name = "";

			for (Any element : featuresarray) {

				System.out.print(element);

				List<Any> coordinates = element.get("geometry").get("coordinates").get(0).asList();

				linkage = element.get("properties").get("Linkage").toString();

//				preparedStatement.setString(1, moniker);
//
//				rs = preparedStatement.executeQuery();

				prop.clear();

//				while (rs.next()) {
//
//					prop.put(rs.getString(1), rs.getString(2));
//
//				}
//
//				if (prop.containsKey("Name")) {
//					name = prop.get("Name").toString();
//				}
//
//				else {
//					name = "";
//				}

				// System.out.println(Arrays.asList(element.get("properties").get("FeatureID").toString().split(",",
				// -1)).toString());

				pm.add(new ReliancePlantDatabase(c, linkage, name, "", element.get("id").toString(), it,
						element.get("geometry").get("coordinates").asList().toString(),
						coordinates.get(2).get(0).toDouble(), coordinates.get(0).get(0).toDouble(),
						coordinates.get(1).get(1).toDouble(), coordinates.get(3).get(1).toDouble(),
						element.get("properties").get("maxZ").toDouble(),
						element.get("properties").get("minZ").toDouble()));

				c = c + 1;

				// System.out.println(c);
			}

			plantRepository.saveAll(pm);

			plantRepository.flush();

			pm.clear();

//			mdb2conn.close();

		}
	}

//	private int matchmdb2_geojson(Connection conn, String geojsondata)
//			throws IOException, ClassNotFoundException, SQLException {
//
//		int value = -1;
//
//		int nodata_count = 0;
//		int data_count = 0;
//
//		Any any = JsonIterator.deserialize(geojsondata).get("features");
//
//		String moniker;
//
//		PreparedStatement preparedStatement = conn.prepareStatement("select * from linkage where moniker=?");
//
//		ResultSet rs;
//
//		for (Any element : any) {
//
//			moniker = element.get("properties").get("Moniker").toString();
//
//			preparedStatement.setString(1, moniker);
//
//			rs = preparedStatement.executeQuery();
//
//			if (!rs.next()) {
//
//				nodata_count = nodata_count + 1;
//				if (nodata_count > 50) {
//					value = 0;
//					break;
//				}
//			} else {
//				data_count = data_count + 1;
//				if (data_count > 50) {
//					value = 1;
//					break;
//				}
//			}
//		}
//
//		return value;
//	}

	@Transactional
	public void databaseProcessing(String unitname) throws SQLException, IOException, ClassNotFoundException {

		Set<String> linkageList = plantRepository.findDistinctLinkage();

		int c = 1;

		double minX, maxX, minY, maxY, minz, maxz = 0.0;

		double minValZ = 0.00;
		double maxValZ = 0.00;

		List<RelianceMdb2ROGCPlantDatabase> rogcPlantDbList = new ArrayList<RelianceMdb2ROGCPlantDatabase>();

		List<String> boundsList = new ArrayList<String>();

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

			rogcPlantDbList.add(new RelianceMdb2ROGCPlantDatabase(c++, linkage, "", "",
					"[" + featureId.substring(0, featureId.length() - 1) + "]", unitname, originalBounds, centerbounds,
					boundaryBounds, maxX, minX, maxY, minY, maxValZ, minValZ));

			boundsList.clear();

			originalBounds = null;
			centerbounds = null;
			boundaryBounds = null;

			System.out.println("Going forward " + " ..." + c);
		}

		rogcPlantRepository.saveAll(rogcPlantDbList);

		rogcPlantRepository.flush();

		rogcPlantDbList.clear();
		System.out.println("Completed");

	}
}