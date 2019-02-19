package com.example.owl.todolist

import android.provider.BaseColumns

object DatabaseInfo {

    const val SQL_CREATE_TABLE_QUERY =
            "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${TableInfo.COLUMN_ITEM_NAME} TEXT," +
            "${TableInfo.COLUMN_ITEM_URGENCY} INTEGER," +
            "${TableInfo.COLUMN_DATE} TEXT)"

    const val SQL_DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"

    object TableInfo: BaseColumns {
        const val TABLE_NAME = "todoItemsTable"
        const val COLUMN_ITEM_NAME = "itemName"
        const val COLUMN_ITEM_URGENCY = "itemUrgency"
        const val COLUMN_DATE = "itemDate"
    }

}