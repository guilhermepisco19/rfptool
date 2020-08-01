package application;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.domain.Permission;
import application.domain.Request;
import application.domain.RfpConfig;
import application.domain.Role;
import application.repositories.PermissionRepository;
import application.repositories.RfpConfigRepository;
import application.repositories.RoleRepository;
import application.services.RequestService;
import application.services.RfpConfigService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


@Component
public class MainWindowController implements Initializable{
	
	@FXML
	private JFXButton newReqButton;
	
	@FXML
	private JFXButton reqListButton;
	
	@FXML
	private JFXButton saveRolesBtn;
	
	@FXML
	private Pane newReqPane;
	
	@FXML
	private Pane reqListPane;
	
	@FXML
	private Pane adminPane;
	
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
	
	@FXML
	private JFXTreeTableView<Role> adminTable;
	
	@Autowired
	private RfpConfigService rfpConfigService;
	
	@Autowired
	private RequestService requestService;

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PermissionRepository permissionRepo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		RfpConfig rc = new RfpConfig(null, "000", "LiveWatch", "team");
		RfpConfig rc1 = new RfpConfig(null, "001", "Europagode", "team");
		RfpConfig rc2 = new RfpConfig(null, "002", "Amanda", "team");
		RfpConfig rc3 = new RfpConfig(null, "003", "AceTP", "team");
		RfpConfig rc4 = new RfpConfig(null, "000", "Developer", "position");
		RfpConfig rc5 = new RfpConfig(null, "001", "Business Analyst", "position");
		RfpConfig rc6 = new RfpConfig(null, "002", "Team Leader", "position");
		RfpConfig rc7 = new RfpConfig(null, "003", "DBA", "position");
		
		rfpToolRepository.saveAll(Arrays.asList(rc,rc1,rc2,rc3,rc4,rc5,rc6,rc7));
		
		Role role1 = new Role(null, "ADMIN");
		Role role2 = new Role(null, "TEAM_LEADER");
		Role role3 = new Role(null, "NORMAL_USER");
		
		
		Permission p1 = new Permission(null, "RFP_R");
		Permission p2 = new Permission(null, "RFP_W");
		
		/*role1.getPermissions().add(p1);
		role1.getPermissions().add(p2);
		role2.getPermissions().add(p1);
		role2.getPermissions().add(p2);
		role3.getPermissions().add(p1);
		
		p1.getRoles().add(role1);
		p1.getRoles().add(role2);
		p1.getRoles().add(role3);
		p2.getRoles().add(role1);
		p2.getRoles().add(role2);*/
		
		roleRepo.saveAll(Arrays.asList(role1, role2, role3));
		permissionRepo.saveAll(Arrays.asList(p1, p2));
		
		List<RfpConfig> teamsList = rfpConfigService.getRfpConfigByType("team");
		ObservableList<RfpConfig> oListTeams = FXCollections.observableArrayList(teamsList);
		teams.setItems(oListTeams);
		
		List<RfpConfig> positionsList = rfpConfigService.getRfpConfigByType("position");
		ObservableList<RfpConfig> oPositionsListTeams = FXCollections.observableArrayList(positionsList);
		positions.setItems(oPositionsListTeams);
		
		initAdminTable();
		
	}
	
	@FXML
	public void onNewRequest(ActionEvent event) {
		
		newReqPane.setVisible(true);
		reqListPane.setVisible(false);
		adminPane.setVisible(false);
	}
	
	@FXML
	public void onRequestList(ActionEvent event) {
		
		newReqPane.setVisible(false);
		adminPane.setVisible(false);
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
	
	@FXML
	public void onAdmin(ActionEvent event) {
		newReqPane.setVisible(false);
		reqListPane.setVisible(false);
		adminPane.setVisible(true);
		
		List<Role> roles = roleRepo.findAll();
		
		ObservableList<Role> rolesList = FXCollections.observableArrayList(roles);
		
		final TreeItem<Role> root = new RecursiveTreeItem<Role>(rolesList, RecursiveTreeObject::getChildren);
		
		adminTable.setRoot(root);
		adminTable.setShowRoot(false);
	}
	
	private void initAdminTable() {
		
		List<Permission> permissions = permissionRepo.findAll();
		
		JFXTreeTableColumn<Role, String> roleName = new JFXTreeTableColumn<>("Role Name");
		
		roleName.setCellValueFactory(x -> 
		new SimpleStringProperty(x.getValue().getValue().getName()));
		
		adminTable.getColumns().add(roleName);
		
		permissions.forEach(p -> {
			TreeTableColumn select = new TreeTableColumn(p.getValue());
	        //select.setMinWidth(200);
	        
	        select.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Role, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {

	            @Override
	            public ObservableValue<JFXCheckBox> call(TreeTableColumn.CellDataFeatures<Role, JFXCheckBox> arg0) {
	            	String columnName = arg0.getTreeTableColumn().getText();
	            	Role role = arg0.getValue().getValue();

	            	JFXCheckBox checkBox = new JFXCheckBox();

	                checkBox.selectedProperty().setValue(role.getPermissions().contains("RFP_R"));



	                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	                    public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {

	                        if(new_val) {
	                        	Permission p = permissionRepo.findByValue(columnName);
	                        	p.getRoles().add(role);
	                        	role.getPermissions().add(p);
	                        }
	                        else {
	                        	Permission permissionToTemove = role.getPermissions().stream()
	                        		.filter(x -> x.getValue().equals(columnName))
	                        		.findAny()
	                        		.orElse(null);
	                        	
	                        	if(permissionToTemove != null) {
	                        		role.getPermissions().remove(permissionToTemove);
	                        	}
	                        }

	                    }
	                });

	                return new SimpleObjectProperty<JFXCheckBox>(checkBox);

	            }

	        });
			
	        adminTable.getColumns().add(select);
		});
		
		
        adminTable.setEditable(true);
	}
	
	@FXML
	private void onSaveRoles(ActionEvent event) {
		List<Role> newRoles = adminTable.getRoot().getChildren().stream().map(x -> {
			Role role = new Role(x.getValue().getId(), x.getValue().getName());
			for(Permission p : x.getValue().getPermissions()) {
				Permission newPermission = new Permission(p.getId(), p.getValue());
				newPermission.getRoles().add(role);
				role.getPermissions().add(newPermission);
			}
			return role;
		}).collect(Collectors.toList());
		
		//List<Role> newRoles = adminTable.getRoot().getChildren().stream().map(x -> x.getValue()).collect(Collectors.toList());
		
		roleRepo.saveAll(newRoles);
		
	}
	
}
