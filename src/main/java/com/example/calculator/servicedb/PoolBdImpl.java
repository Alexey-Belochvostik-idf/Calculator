package com.example.calculator.servicedb;

import java.sql.*;

public class PoolBdImpl implements PoolBd {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/calculator?serverTimezone=UTC";

    static final String USER = "root";
    static final String PASSWORD = "root";

    static final String READ_ID_NAME_WALL_TILES = "select * from walltiles where name = ?";
    static final String READ_ID_NAME_FLOOR_TILES = "select * from floortiles where name = ?";
    static final String PLUMBING = "select * from plumbing where name = ?";


    @Override
    public double wallTilesBd(String name) {

        initDriver();
        double wall = 0;

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement(READ_ID_NAME_WALL_TILES)) {

            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();


            while (rs.next()) {

                wall = rs.getDouble("coust");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wall;
    }

    @Override
    public double floorTilesBd(String name) {

        initDriver();
        double floor = 0;

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement(READ_ID_NAME_FLOOR_TILES)) {

            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                floor = rs.getDouble("coust");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return floor;
    }

    @Override
    public double plumbing(String[] name) {

        initDriver();
        double plumbing = 0;

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement(PLUMBING)) {
            String nameNew;
            for (String plum : name) {
                nameNew = plum;
                pst.setString(1, nameNew);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {

                    double plm = rs.getDouble("coust");
                    plumbing += plm;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plumbing;
    }

    public void initDriver() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
