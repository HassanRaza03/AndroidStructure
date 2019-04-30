package com.android.structure.managers;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmCollection;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by muhammadmuzammil on 5/3/2017.
 */
public class RealmManager {
    private static RealmManager realmManager;

    public static RealmManager getInstance() {
        if (realmManager == null)
            realmManager = new RealmManager();
        return realmManager;
    }

    public void insertData(RealmModel object) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
        realm.close();
    }

    public void insertOrUpdate(RealmModel object) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) realm.beginTransaction();
        realm.insertOrUpdate(object);
        realm.commitTransaction();
        realm.close();
    }

    public void insertData(Collection<? extends RealmModel> objects) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) realm.beginTransaction();
        realm.insert(objects);
        realm.commitTransaction();
        realm.close();
    }


    public <E extends RealmModel> void delete(Class<E> clazz) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) realm.beginTransaction();
        realm.delete(clazz);
        realm.commitTransaction();
        realm.close();
    }

    public boolean delete(RealmCollection<? extends RealmModel> objects) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        boolean result = objects.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
        return result;
    }

    public <E extends RealmObject> void delete(E obj) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) realm.beginTransaction();
        E.deleteFromRealm(obj);
        realm.commitTransaction();
        realm.close();
    }

    public <E extends RealmModel> RealmQuery<E> getData(Class<E> clazz) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) realm.beginTransaction();
        RealmQuery<E> data = realm.where(clazz);
        realm.cancelTransaction();
        return data;
    }

    public <E extends RealmModel> int getNextId(Class<E> clazz, String fieldName) {
        Realm realm = Realm.getDefaultInstance();
        Number max = (realm.where(clazz).max(fieldName));
        return max == null ? 0 : (max.intValue() + 1);
    }

    public <E extends RealmModel> E getCopy(E realmObject) {
        if (realmObject == null)
            return null;
        return Realm.getDefaultInstance().copyFromRealm(realmObject);
    }

}
