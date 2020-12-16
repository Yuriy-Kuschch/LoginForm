package com.yuriykusch;

import com.yuriykusch.LoginForm.Main;
import com.yuriykusch.LoginForm.database.DataManager;
import com.yuriykusch.LoginForm.database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class MainController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ImageView imageView;

    public void onLoginClick(ActionEvent event){

        String login = loginField.getText();
        String password = passwordField.getText();

        User user = null;

        try {
            user = DataManager.getInstance().getUsersDao().queryBuilder()
                    .where()
                    .eq("login", login)
                    .queryForFirst();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(user == null){
            showError();
        }else{
            if(user.getPassword().equals(password)){
                showSuccess(login);
            }else {
                showError();
            }
        }
    }

    private void showError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText("Неверный логин или пароль!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showSuccess(String login){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удачно");
        alert.setContentText("Добро пожаловать, " + login + '!');
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
