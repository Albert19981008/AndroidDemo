package com.example.readhub.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.readhub.ReadHubApplication;


/**
 * 数据库helper类，定义了初始化数据库和更新数据库的方式
 */
public class DBHelper extends SQLiteOpenHelper {

    //单例模式 懒加载
    private static volatile DBHelper INSTANCE;

    //DB版本号
    private static final int VERSION = 12;

    //建表的整个String
    private static final String CREATE_NEWS =

            "create table News ("
                    + "timeStamp integer,"
                    + "id integer primary key,"
                    + "siteSource text,"
                    + "url text,"
                    + "headline text,"
                    + "type text)";

    //以时间戳这一列建立索引
    private static final String CREATE_INDEX =

            "create index timeStamp on News (timeStamp)";


    private DBHelper(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //单例类
    static DBHelper getInstance() {

        if (INSTANCE == null) {
            synchronized (DBHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBHelper(ReadHubApplication.getContext(), "newsDB.db", null, VERSION);
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 创建数据库
     *
     * @param db 数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
        db.execSQL(CREATE_INDEX);
    }

    /**
     * 升级数据库
     *
     * @param db         数据库
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table News");

        db.execSQL(CREATE_NEWS);
        db.execSQL(CREATE_INDEX);
    }
}
