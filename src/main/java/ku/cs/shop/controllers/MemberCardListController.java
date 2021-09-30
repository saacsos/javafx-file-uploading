package ku.cs.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.shop.models.MemberCard;
import ku.cs.shop.models.MemberCardList;
import ku.cs.shop.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;

public class MemberCardListController {
//    @FXML private ListView<MemberCard> cardsListView;
    @FXML private TableView<MemberCard> memberCardTable;
    @FXML private Label cpLabel;
    @FXML private Label ptLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField phoneTextField;
    @FXML private ImageView userImageView;
    @FXML private Button updateInfoButton;
    @FXML private Button browseButton;

//    private MemberCardListHardCodeDataSource dataSource;
    private DataSource<MemberCardList> dataSource;
    private MemberCardList cardList;
    private ObservableList<MemberCard> memberCardObservableList;

    private MemberCard selectedMemberCard;
    @FXML
    public void initialize() {
//        dataSource = new MemberCardListHardCodeDataSource();
        dataSource = new MemberCardFileDataSource("data", "member.csv");
        cardList = dataSource.readData();
        System.out.println(cardList.getAllCards());
        System.out.println(System.getProperty("user.dir"));
        userImageView.setImage(new Image(getClass().getResource("/ku/cs/images/default.png").toExternalForm()));
        clearSelectedMemberCard();

        showMemberCardData();
        memberCardTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMemberCard(newValue);
            }
        });
    }

    private void showMemberCardData() {
        memberCardObservableList = FXCollections.observableArrayList(cardList.getAllCards());
        memberCardTable.setItems(memberCardObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Name", "field:name"));
        configs.add(new StringConfiguration("title:Phone", "field:phone"));
        configs.add(new StringConfiguration("title:Stamp", "field:stamp"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            memberCardTable.getColumns().add(col);
        }
    }


    private void showSelectedMemberCard(MemberCard card) {
        selectedMemberCard = card;
        nameTextField.setText(card.getName());
        phoneTextField.setText(card.getPhone());
        cpLabel.setText(String.format("%.2f", card.getCumulativePurchase()));
        ptLabel.setText(String.valueOf(card.getStamp()));
        userImageView.setImage(new Image("file:"+selectedMemberCard.getImagePath(), true));
        updateInfoButton.setDisable(false);
        browseButton.setDisable(false);
    }

    private void clearSelectedMemberCard() {
        selectedMemberCard = null;
        nameTextField.clear();
        phoneTextField.clear();
        cpLabel.setText("");
        ptLabel.setText("");
        userImageView.setImage(new Image(getClass().getResource("/ku/cs/images/default.png").toExternalForm()));
        updateInfoButton.setDisable(true);
        browseButton.setDisable(true);
    }

    @FXML public void handleUpdateButton(ActionEvent event){
        selectedMemberCard.setName(nameTextField.getText().trim());
        selectedMemberCard.setPhone(phoneTextField.getText().trim());
        clearSelectedMemberCard();
        memberCardTable.refresh();
        memberCardTable.getSelectionModel().clearSelection();
        dataSource.writeData(cardList);
    }

    @FXML public void handleUploadButton(ActionEvent event){
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                userImageView.setImage(new Image(target.toUri().toString()));
                selectedMemberCard.setImagePath(destDir + "/" + filename);
                dataSource.writeData(cardList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clearSelectedMemberCard();
    }


}
