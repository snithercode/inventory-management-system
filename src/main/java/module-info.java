module ims.inventoryapp {
        requires javafx.controls;
        requires javafx.fxml;


        opens ims.inventoryapp to javafx.fxml;
        exports ims.inventoryapp;
        exports controller;
        opens controller to javafx.fxml;
        exports model;
        opens model to javafx.fxml;
        }