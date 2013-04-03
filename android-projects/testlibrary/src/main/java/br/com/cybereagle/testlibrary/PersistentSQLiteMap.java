package br.com.cybereagle.testlibrary;

import org.robolectric.util.SQLiteMap;

public class PersistentSQLiteMap extends SQLiteMap {
    @Override
    public String getConnectionString() {
        return "jdbc:sqlite:" + EagleConstants.TEST_DB_FILE_NAME;
    }
}
