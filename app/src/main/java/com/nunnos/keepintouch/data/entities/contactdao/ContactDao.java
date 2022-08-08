package com.nunnos.keepintouch.data.entities.contactdao;

import static com.nunnos.keepintouch.data.entities.contactdao.ContactsDaoConstants.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


/**
 * DAO of the Room database.
 */
@Dao
public interface ContactDao {
    @Insert
    void insert(ContactEntity contactEntity);

    @Update
    void update(ContactEntity contactEntity);

    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY Id DESC")
    List<ContactEntity> getAll();

    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY lastactionindex DESC")
    List<ContactEntity> getAllOrderByLastIndexDes();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE Id = :id")
    ContactEntity get(int id);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE Id = :id")
    void deleteById(int id);

    @Query("SELECT MIN(lastactionindex) FROM " + TABLE_NAME)
    int getLastIndex();

    /**
     * Normal Search
     */
    //TODO: Que busqui per nom i cognoms
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Name) LIKE '%' || UPPER(:name) || '%'")
    List<ContactEntity> searchByName(String name);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Gender) LIKE '%' || UPPER(:gender) || '%'")
    List<ContactEntity> searchByGender(String gender);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(SexualOrientation) LIKE '%' || UPPER(:sexualOrientation) || '%'")
    List<ContactEntity> searchBySexualOrientation(String sexualOrientation);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Birthday) LIKE '%' || UPPER(:birthday) || '%'")
    List<ContactEntity> searchByBirthday(String birthday);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Address) LIKE '%' || UPPER(:address) || '%'")
    List<ContactEntity> searchByAddress(String address);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Profession) LIKE '%' || UPPER(:profession) || '%'")
    List<ContactEntity> searchByProfession(String profession);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(PlaceOfWork) LIKE '%' || UPPER(:placeOfWork) || '%'")
    List<ContactEntity> searchByPlaceOfWork(String placeOfWork);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(HowWeMet) LIKE '%' || UPPER(:howWeMet) || '%'")
    List<ContactEntity> searchByHowWeMet(String howWeMet);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Language) LIKE '%' || UPPER(:language) || '%'")
    List<ContactEntity> searchByLanguage(String language);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Religion) LIKE '%' || UPPER(:religion) || '%'")
    List<ContactEntity> searchByReligion(String religion);

    //TODO: Agafar nom, buscar el ID que li correspon a aquesta persona i llavors buscar
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Relatives) LIKE '%' || UPPER(:relatives) || '%'")
    List<ContactEntity> searchByRelatives(String relatives);

    //TODO: mirar com fer
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Conversations) LIKE '%' || UPPER(:conversations) || '%'")
    List<ContactEntity> searchByConversations(String conversations);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Alias) LIKE '%' || UPPER(:alias) || '%'")
    List<ContactEntity> searchByAlias(String alias);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Telephone) LIKE '%' || UPPER(:telephone) || '%'")
    List<ContactEntity> searchByTelephone(String telephone);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Email) LIKE '%' || UPPER(:email) || '%'")
    List<ContactEntity> searchByEmail(String email);
    //TODO: mirar com fer
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Comments) LIKE '%' || UPPER(:comments) || '%'")
    List<ContactEntity> searchByComments(String comments);
    /**
     * Strict Search
     */
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE UPPER(Name) LIKE UPPER(:name)")
    List<ContactEntity> searchByNameStrict(String name);
}
