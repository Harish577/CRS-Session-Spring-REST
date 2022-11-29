package com.customfusionrestapi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.customfusionrestapi.dto.ShapeModel;
import com.customfusionrestapi.entity.RelianceShapesModel;
import com.customfusionrestapi.repos.RelianceShapesModelRepository;
import com.luciad.datamodel.ILcdDataObject;
import com.luciad.datamodel.TLcdDataProperty;
import com.luciad.datamodel.TLcdDataType;
import com.luciad.format.database.TLcdPrimaryKeyAnnotation;
import com.luciad.format.geojson.TLcdGeoJsonModelDecoder;
import com.luciad.format.shp.TLcdSHPModelDecoder2;
import com.luciad.internal.util.ALinOGCFilterAccessor;
import com.luciad.model.ILcd2DBoundsIndexedModel;
import com.luciad.model.ILcdModel;
import com.luciad.ogc.filter.model.ILcdOGCCondition;
import com.luciad.shape.ILcdBounded;
import com.luciad.shape.ILcdBounds;

@Service
public class ShapesModelService {

	@Autowired
	private RelianceShapesModelRepository shapesmodelRepository;

	public RelianceShapesModel findById(int theId) {
		// TODO Auto-generated method stub
		Optional<RelianceShapesModel> result = shapesmodelRepository.findById(theId);

		RelianceShapesModel theshapedata = null;

		if (result.isPresent()) {
			theshapedata = result.get();
		} else {
			// we didn't find the employee
			throw new RuntimeException("Did not find shape data id - " + theId);
		}

		return theshapedata;
	}

	public void save(RelianceShapesModel theshapedatamodel) {
		// TODO Auto-generated method stub
		shapesmodelRepository.save(theshapedatamodel);

	}

	public List<RelianceShapesModel> findAll() {
		// TODO Auto-generated method stub
		return shapesmodelRepository.findAll();
	}

	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		shapesmodelRepository.deleteById(theId);
	}

	public List<RelianceShapesModel> findByEvent() {
		// TODO Auto-generated method stub
		RelianceShapesModel sm = new RelianceShapesModel();

		sm.setTypeOfEvent("Hazardous");
		;
		Example<RelianceShapesModel> example = Example.of(sm);
		List<RelianceShapesModel> list = shapesmodelRepository.findAll(example);

		return list;
	}

	public ResponseEntity<Map<String, String>> persistShapes(ShapeModel shapeModel) throws IOException {

		RelianceShapesModel relianceShapeModel = new RelianceShapesModel();

		Map<String, String> response = new HashMap<>();

		relianceShapeModel.setEventStartDate(String.valueOf(shapeModel.getProperties().getEventStartDate()));
		relianceShapeModel.setEventEndDate(String.valueOf(shapeModel.getProperties().getEventEndDate()));
		relianceShapeModel.setTypeOfEvent(shapeModel.getProperties().getTypeOfEvent());

//		relianceShapeModel.setMaximumHeight(String.valueOf(shapeModel.getGeometry().getMaximumHeight()));
//		relianceShapeModel.setMinimumHeight(String.valueOf(shapeModel.getGeometry().getMinimumHeight()));

		if ("CircleByCenterPoint".equalsIgnoreCase(shapeModel.getGeometry().getBaseShape().getType())) {

			relianceShapeModel.setRadius(shapeModel.getGeometry().getBaseShape().getRadius());
			relianceShapeModel
					.setCoordinates(shapeModel.getGeometry().getBaseShape().getCenter().getCoordinates().toString());

		} else {
			relianceShapeModel.setX(String.valueOf(shapeModel.getGeometry().getBaseShape().getX()));
			relianceShapeModel.setY(String.valueOf(shapeModel.getGeometry().getBaseShape().getY()));
			relianceShapeModel.setWidth(String.valueOf(shapeModel.getGeometry().getBaseShape().getWidth()));
			relianceShapeModel.setHeight(String.valueOf(shapeModel.getGeometry().getBaseShape().getHeight()));

		}
		shapesmodelRepository.save(relianceShapeModel);

		response.put("message", "Successfully persisted shp file data");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	public List processShapes() throws IOException {

		List<RelianceShapesModel> list = shapesmodelRepository.findAll();

		RelianceShapesModel shape = list.get(0);

		System.out.println("Shape:" + shape);
//
//		if (shape.getRadius() == null) {
//
//			TLcdLonLatBounds baseShape = new TLcdLonLatBounds(Double.parseDouble(shape.getX()),
//					Double.parseDouble(shape.getY()), Double.parseDouble(shape.getWidth()),
//					Double.parseDouble(shape.getHeight()));
//
//			System.out.println("BaseShape:" + baseShape.toString());
//
//			TLcdExtrudedShape extrudedShape = new TLcdExtrudedShape(baseShape,
//					Double.parseDouble(shape.getMinimumHeight()), Double.parseDouble(shape.getMaximumHeight()));
//
//			System.out.println("extrudedShape:" + extrudedShape.toString());
//
//			TLcdGeoJsonModelDecoder modelDecoder = new TLcdGeoJsonModelDecoder();
//			ILcd2DBoundsIndexedModel model = (ILcd2DBoundsIndexedModel) modelDecoder
//					.decode("C:\\Automation\\geojson\\test\\features1.geojson");
//			ILcdOGCCondition bbox = ALinOGCFilterAccessor.bbox(extrudedShape.getBounds(), null);
//
//			TLcdDataType dt = ((ILcdDataObject) model.elements().nextElement()).getDataType();
//
//			System.out.println("TLcdDataType:" + dt.getDataModel().toString());
//
//			TLcdPrimaryKeyAnnotation annotation = dt.getAnnotation(TLcdPrimaryKeyAnnotation.class);
//			TLcdDataProperty property = annotation != null ? annotation.getProperty() : dt.getProperty("FeatureID");
//
//			System.out.println("Property:" + property.toString());
//
//			List featureIds = model.query(ILcdModel.filter(bbox)).map(ILcdDataObject.class::cast)
//					.filter(o -> extrudedShape.getMinimumZ() < (double) o.getValue("minZ")
//							&& extrudedShape.getMaximumZ() >= (double) o.getValue("maxZ"))
//					.map(o -> o.getValue(property)).collect(Collectors.toList());
//
//			System.out.println("FeatureIds:" + featureIds);
//
//			return featureIds;
//
//		} else {

//		TLcdLonLatCircle baseShape = new TLcdLonLatCircle(-111.73610111544825,  57.326774785032036, 9.942403842283296,
//				TLcdEllipsoid.DEFAULT);
//
//		System.out.println("BaseShape:" + baseShape.toString());
//
//		TLcdExtrudedShape extrudedShape = new TLcdExtrudedShape(baseShape, 7.474925735965371, 8);
//
//		System.out.println("extrudedShape:" + extrudedShape.toString());
//
//		TLcdGeoJsonModelDecoder modelDecoder = new TLcdGeoJsonModelDecoder();
//		ILcd2DBoundsIndexedModel model = (ILcd2DBoundsIndexedModel) modelDecoder
//				.decode("C:\\Automation\\geojson\\test\\features1.geojson");
//		ILcdOGCCondition bbox = ALinOGCFilterAccessor.bbox(extrudedShape.getBounds(), null);
//
//		TLcdDataType dt = ((ILcdDataObject) model.elements().nextElement()).getDataType();
//
//		System.out.println("TLcdDataType:" + dt.getDataModel().toString());
//
//		TLcdPrimaryKeyAnnotation annotation = dt.getAnnotation(TLcdPrimaryKeyAnnotation.class);
//		TLcdDataProperty property = annotation != null ? annotation.getProperty() : dt.getProperty("FeatureID");
//
//		System.out.println("Property:" + property.toString());
//
//		List featureIds = model.query(ILcdModel.filter(bbox)).map(ILcdDataObject.class::cast)
//
//				.map(o -> o.getValue(property)).collect(Collectors.toList());
//
//		System.out.println("FeatureIds:" + featureIds);

		ILcdModel baseShapeModel = new TLcdSHPModelDecoder2()
				.decode("C:\\ShapeConversion\\PhastConsequence_Point Explosion.shp");
		ILcdBounds bounds = ((ILcdBounded) baseShapeModel).getBounds();

		System.out.println("Base Shape Model" + baseShapeModel.toString());

		System.out.println("Base Shape Model" + bounds.toString());

		TLcdGeoJsonModelDecoder modelDecoder = new TLcdGeoJsonModelDecoder();
		ILcd2DBoundsIndexedModel model = (ILcd2DBoundsIndexedModel) modelDecoder
				.decode("C:\\Automation\\geojson\\test\\features1.geojson");
		ILcdOGCCondition bbox = ALinOGCFilterAccessor.bbox(bounds, null);

		TLcdDataType dt = ((ILcdDataObject) model.elements().nextElement()).getDataType();

		System.out.println("TLcdDataType:" + dt.getDataModel().toString());

		TLcdPrimaryKeyAnnotation annotation = dt.getAnnotation(TLcdPrimaryKeyAnnotation.class);
		TLcdDataProperty property = annotation != null ? annotation.getProperty() : dt.getProperty("FeatureID");

		System.out.println("Property:" + property.toString());

		List featureIds = model.query(ILcdModel.filter(bbox)).map(ILcdDataObject.class::cast)
				.map(o -> o.getValue(property)).collect(Collectors.toList());

		return featureIds;
		// }

	}

}