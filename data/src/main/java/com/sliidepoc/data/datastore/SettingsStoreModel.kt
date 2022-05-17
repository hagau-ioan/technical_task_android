package com.sliidepoc.data.datastore

/**
 * Setting up some preference information related to different part of the app.
 * These settings are depending by the user action.
 * In case another table is required on Data Base store we need to construct another class, to keep
 * a clean architecture structure.
 * Contract enum class
 *
 * @author Ioan Hagau
 * @since 2020.11.30
 */
enum class SettingsStoreModel(val key: String) {

    // Table Name, just only table name defined here per class.
    SETTINGS("settings");

    // ANY CHANGE in this are CRUD operation will require the app to be uninstalled and installed again.
    // Fields
    enum class Keys(val key: String) {
        CLIENT_SECRET("client_secret"),
        CLIENT_ID("client_id");
    }
}
