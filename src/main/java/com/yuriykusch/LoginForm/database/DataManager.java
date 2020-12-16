package com.yuriykusch.LoginForm.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataManager {
    private static volatile DataManager instance;

    private ConnectionSource connectionSource;

    private Dao<User, Integer> usersDao;

    //Паттерн "Синглтон"
    private DataManager(){
        //Инициализируем подключение к БД
        establishTheConnection();
        //Объявляем объекты доступа к данным
        initDaos();
        //Создаем таблицы (если еще не существуют)
        createTables();
    }

    public static DataManager getInstance(){
        DataManager currentInstance = instance;
        if(currentInstance != null){
            return currentInstance;
        }
        synchronized (DataManager.class){
            if(instance == null){
                instance = new DataManager();
            }
            return instance;
        }
    }

    private void establishTheConnection(){
        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:db.sqlite");
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private void initDaos(){
        if(connectionSource == null)
            establishTheConnection();
        try{
            usersDao = DaoManager.createDao(connectionSource, User.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTables(){
        try{
            TableUtils.createTableIfNotExists(connectionSource, User.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Dao<User, Integer> getUsersDao(){
        return usersDao;
    }
}
