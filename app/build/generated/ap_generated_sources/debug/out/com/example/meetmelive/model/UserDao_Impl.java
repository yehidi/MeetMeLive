package com.example.meetmelive.model;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `User` (`id`,`name`,`description`,`birthday`,`gender`,`lookingForGender`,`currentLocation`,`email`,`lastUpdated`,`password`,`city`,`profilePic`,`pic1`,`pic2`,`pic3`,`isActive`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.description == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.description);
        }
        stmt.bindLong(4, value.birthday);
        if (value.gender == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.gender);
        }
        if (value.lookingForGender == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.lookingForGender);
        }
        if (value.currentLocation == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.currentLocation);
        }
        if (value.email == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.email);
        }
        stmt.bindLong(9, value.lastUpdated);
        if (value.password == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.password);
        }
        if (value.city == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.city);
        }
        if (value.profilePic == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.profilePic);
        }
        if (value.pic1 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.pic1);
        }
        if (value.pic2 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.pic2);
        }
        if (value.pic3 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.pic3);
        }
        final int _tmp;
        _tmp = value.isActive ? 1 : 0;
        stmt.bindLong(16, _tmp);
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `User` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
      }
    };
  }

  @Override
  public void insertAll(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<User>> getAllActiveUsers() {
    final String _sql = "select * from User";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"User"}, false, new Callable<List<User>>() {
      @Override
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfBirthday = CursorUtil.getColumnIndexOrThrow(_cursor, "birthday");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfLookingForGender = CursorUtil.getColumnIndexOrThrow(_cursor, "lookingForGender");
          final int _cursorIndexOfCurrentLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLocation");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfProfilePic = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePic");
          final int _cursorIndexOfPic1 = CursorUtil.getColumnIndexOrThrow(_cursor, "pic1");
          final int _cursorIndexOfPic2 = CursorUtil.getColumnIndexOrThrow(_cursor, "pic2");
          final int _cursorIndexOfPic3 = CursorUtil.getColumnIndexOrThrow(_cursor, "pic3");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final User _item;
            _item = new User();
            _item.id = _cursor.getString(_cursorIndexOfId);
            _item.name = _cursor.getString(_cursorIndexOfName);
            _item.description = _cursor.getString(_cursorIndexOfDescription);
            _item.birthday = _cursor.getLong(_cursorIndexOfBirthday);
            _item.gender = _cursor.getString(_cursorIndexOfGender);
            _item.lookingForGender = _cursor.getString(_cursorIndexOfLookingForGender);
            _item.currentLocation = _cursor.getString(_cursorIndexOfCurrentLocation);
            _item.email = _cursor.getString(_cursorIndexOfEmail);
            _item.lastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            _item.password = _cursor.getString(_cursorIndexOfPassword);
            _item.city = _cursor.getString(_cursorIndexOfCity);
            _item.profilePic = _cursor.getString(_cursorIndexOfProfilePic);
            _item.pic1 = _cursor.getString(_cursorIndexOfPic1);
            _item.pic2 = _cursor.getString(_cursorIndexOfPic2);
            _item.pic3 = _cursor.getString(_cursorIndexOfPic3);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _item.isActive = _tmp != 0;
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
