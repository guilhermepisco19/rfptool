package application;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.domain.Request;
import application.domain.RfpConfig;
import application.repositories.RfpConfigRepository;
import application.services.RequestService;
import application.services.RfpConfigService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


@Component
public class MainWindowController implements Initializable{
	
	@FXML
	private JFXButton newReqButton;
	
	@FXML
	private JFXButton reqListButton;
	
	@FXML
	private Pane newReqPane;
	
	@FXML
	private Pane reqListPane;
	
	@Autowired
	private RfpConfigRepository rfpToolRepository;
	
	@FXML
	private JFXComboBox teams;
	
	@FXML
	private JFXComboBox positions;
	
	@FXML
	private JFXButton submitNewRfpBtn;
	
	@FXML
	private JFXTreeTableView<Request> requestTable;
	
	@Autowired
	private RfpConfigService rfpConfigService;
	
	@Autowired
	private RequestService requestService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		/*RfpConfig rc = new RfpConfig(null, "000", "LiveWatch", "team");
		RfpConfig rc1 = new RfpConfig(null, "001", "Europagode", "team");
		RfpConfig rc2 = new RfpConfig(null, "002", "Amanda", "team");
		RfpConfig rc3 = new RfpConfig(null, "003", "AceTP", "team");
		RfpConfig rc4 = new RfpConfig(null, "000", "Developer", "position");
		RfpConfig rc5 = new RfpConfig(null, "001", "Business Analyst", "position");
		RfpConfig rc6 = new RfpConfig(null, "002", "Team Leader", "position");
		RfpConfig rc7 = new RfpConfig(null, "003", "DBA", "position");
		
		rfpToolRepository.saveAll(Arrays.asList(rc,rc1,rc2,rc3,rc4,rc5,rc6,rc7));*/
		
		List<RfpConfig> teamsList = rfpConfigService.getRfpConfigByType("team");
		ObservableList<RfpConfig> oListTeams = FXCollections.observableArrayList(teamsList);
		teams.setItems(oListTeams);
		
		List<RfpConfig> positionsList = rfpConfigService.getRfpConfigByType("position");
		ObservableList<RfpConfig> oPositionsListTeams = FXCollections.observableArrayList(positionsList);
		positions.setItems(oPositionsListTeams);
		
	}
	
	@FXML
	public void onNewRequest(ActionEvent event) {
		
		newReqPane.setVisible(true);
		reqListPane.setVisible(false);
	}
	
	@FXML
	public void onRequestList(ActionEvent event) {
		
		newReqPane.setVisible(false);
		reqListPane.setVisible(true);
		
		List<Request> requests = requestService.getRequests();
		
		JFXTreeTableColumn<Request, Integer> requestId = new JFXTreeTableColumn<>("Request ID");
		
		requestId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Request,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Request, Integer> param) {
				// TODO Auto-generated method stub
				ObservableValue<Integer> id= new SimpleIntegerProperty(param.getValue().getValue().getId()).asObject();
				return id;
			}
		});
		
		JFXTreeTableColumn<Request, String> requestTeam = new JFXTreeTableColumn<>("Team");
		
		requestTeam.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Request,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Request, String> param) {
				// TODO Auto-generated method stub
				ObservableValue<String> team= new SimpleStringProperty(param.getValue().getValue().getTeam());
				return team;
			}
		});
		
		JFXTreeTableColumn<Request, String> requestPosition = new JFXTreeTableColumn<>("Positions");
		
		requestPosition.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Request,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Request, String> param) {
				// TODO Auto-generated method stub
				ObservableValue<String> position= new SimpleStringProperty(param.getValue().getValue().getPosition());
				return position;
			}
		});
		
		ObservableList<Request> oRequestsList = FXCollections.observableArrayList(requests);
		
		final TreeItem<Request> root = new RecursiveTreeItem<Request>(oRequestsList, RecursiveTreeObject::getChildren);
		requestTable.getColumns().setAll(requestId,requestTeam,requestPosition);
		requestTable.setRoot(root);
		requestTable.setShowRoot(false);
		
	}
	
	@FXML
	public void onSubmitNewRfp(ActionEvent event) {
		
		String position = ((RfpConfig)teams.getValue()).getDescription();
		String team = ((RfpConfig)positions.getValue()).getDescription();
		
		Request req = new Request(null,position,team);
		requestService.insertRequest(req);
	}
}
