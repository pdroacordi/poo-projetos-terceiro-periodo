package com.example.data.protocols.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectToDatabase {
    Connection connectToDatabase();
}
