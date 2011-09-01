package org.hxzon.demo.javafx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListDemo extends Application {

	ListView<String> list = new ListView<String>();
	ObservableList<String> data = FXCollections.observableArrayList(
			"chocolate", "salmon", "gold", "coral", 
			"darkorchid", "darkgoldenrod", "lightsalmon", 
			"black", "rosybrown", "blue", "blueviolet",
			"brown");
	final Label label = new Label();

	@Override
	public void start(Stage stage) {
		VBox box = new VBox();
		Scene scene = new Scene(box, 200, 200);
		stage.setScene(scene);
		stage.setTitle("ListViewSample");
		box.getChildren().addAll(list, label);
		VBox.setVgrow(list, Priority.ALWAYS);

		label.setLayoutX(10);
		label.setLayoutY(115);
		label.setFont(new Font("Verdana", 20));

		list.setItems(data);
//out of date
//        final ListCellFactory cellFactory = new ListCellFactory() {
//            public ListCell create() {
//                final Rectangle rect = new Rectangle(100, 20);
//                final ListCell<String> cell = new ListCell<String>();
//                cell.setOnUpdate(new Runnable() {
//
//                    public void run() {
//                        String item = cell.getItem();
//                        if (item != null) {
//                            rect.setFill(Color.web(item));
//                            cell.setNode(rect);
//                        }
//                    }
//                });
//                return cell;
//            }
//        };

		Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {
			public ListCell<String> call(ListView<String> p) {
				return new ListCell<String>() {
					final Rectangle rect = new Rectangle(100, 20);
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item);
							setTextFill(Color.web(item));
							rect.setFill(Color.web(item));
							setGraphic(rect);
						}
					}
				};
			}
		};

		list.setCellFactory(cellFactory);
		list.setPrefHeight(100);

		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
				label.setText(new_val);
				label.setTextFill(Color.web(new_val));
			}
		});
		stage.setVisible(true);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
