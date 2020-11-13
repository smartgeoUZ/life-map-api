package uz.smartgeo.lifemap.api.db.impl;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;
import uz.smartgeo.lifemap.api.db.interfaces.IUnitPostgre;

import java.util.List;

public class UnitService implements IUnit {

    protected IUnitPostgre dbServiceImpl;

    public UnitService(Vertx vertx, JsonObject dbConfig, String dbName, JsonObject dbQueries) {
        dbServiceImpl = new UnitPostgreImpl(vertx, dbConfig, dbName, dbQueries);
    }

    @Override
    public Future<List<JsonObject>> getList() {
        return dbServiceImpl.getList();
    }

    @Override
    public Future<JsonObject> getById(long groupId) {
        return dbServiceImpl.getById(groupId);
    }

    @Override
    public Future<JsonObject> getByExtId(long extId, int authType) {
        return dbServiceImpl.getByExtId(extId, authType);
    }

    @Override
    public Future<JsonObject> getUserByExtIdAndUpdate(long extId, int authType) {
        return dbServiceImpl.getUserByExtIdAndUpdate(extId, authType);
    }

    @Override
    public Future<JsonObject> updateMobileNumber(long extId, int authType, String mobileNumber) {
        return dbServiceImpl.updateMobileNumber(extId, authType, mobileNumber);
    }

    @Override
    public Future<JsonObject> updateProfile(long userId, String firstName, String lastName, String email) {
        return dbServiceImpl.updateProfile(userId, firstName, lastName, email);
    }

    @Override
    public Future<JsonObject> updateUserData(long extUserId, String login, String photoUrl, long authTypeId) {
        return dbServiceImpl.updateUserData(extUserId, login, photoUrl, authTypeId);
    }

    @Override
    public Future<JsonObject> updateUserRegion(long userId, long regionId, long authTypeId) {
        return dbServiceImpl.updateUserRegion(userId, regionId, authTypeId);
    }

    @Override
    public Future<JsonObject> updateUserRole(long userId, long roleId) {
        return dbServiceImpl.updateUserRole(userId, roleId);
    }

    @Override
    public Future<JsonObject> save(JsonObject formData) {
        return dbServiceImpl.save(formData);
    }

    @Override
    public Future<List<JsonObject>> getRolePermissionsByUserId(long userId) {
        return dbServiceImpl.getRolePermissionsByUserId(userId);
    }

    @Override
    public Future<JsonObject> updateAccessValue(long id, int accessValue) {
        return dbServiceImpl.updateAccessValue(id, accessValue);
    }

    @Override
    public Future<List<JsonObject>> getUsersByRoleId(long roleId) {
        return dbServiceImpl.getUsersByRoleId(roleId);
    }

    @Override
    public Future<List<JsonObject>> getEventsForAnonymous(long categoryId, Boolean isActive, int filterStatusId, String startDate, String endDate) {
        return dbServiceImpl.getEventsForAnonymous(categoryId, isActive, filterStatusId, startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getRelevantEvents(long userId, long categoryId, int roleId, String startDate, String endDate) {
        return dbServiceImpl.getRelevantEvents(userId, categoryId, roleId, startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getAllEvents(long userId, long categoryId, int roleId, String startDate, String endDate) {
        return dbServiceImpl.getAllEvents(userId, categoryId, roleId, startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getOwnEvents(long userId, long categoryId, int roleId, String startDate, String endDate) {
        return dbServiceImpl.getOwnEvents(userId, categoryId, roleId, startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getModeratingEvents(long userId, long categoryId, int roleId, String startDate, String endDate, long userRegionId) {
        return dbServiceImpl.getModeratingEvents(userId, categoryId, roleId, startDate, endDate, userRegionId);
    }

    @Override
    public Future<List<JsonObject>>  getEventsBySearchText(long userId, String searchText, boolean isForAnonymous) {
        return dbServiceImpl. getEventsBySearchText(userId, searchText, isForAnonymous);
    }

//    @Override
//    public Future<List<JsonObject>> getRelevantEventsByFilter(JsonObject formData) {
//        return dbServiceImpl.getRelevantEventsByFilter(formData);
//    }
//
//    @Override
//    public Future<JsonObject> getRelevantEventsCountByFilter(JsonObject formData) {
//        return dbServiceImpl.getRelevantEventsCountByFilter(formData);
//    }

    @Override
    public Future<JsonObject> deleteEvent(long eventId, long performDeletionUserId) {
        return dbServiceImpl.deleteEvent(eventId, performDeletionUserId);
    }

    @Override
    public Future<JsonObject> updateModerationStatus(long eventId, boolean isModeration) {
        return dbServiceImpl.updateModerationStatus(eventId, isModeration);
    }

    @Override
    public Future<JsonObject> getRegionByCoordinates(Double lon, Double lat) {
        return dbServiceImpl.getRegionByCoordinates(lon, lat);
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEvents(long categoryId, long regionId, long userId, String startDate, String endDate) {
        return dbServiceImpl.getDashboardDataEvents(categoryId, regionId, userId, startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataUsers() {
        return dbServiceImpl.getDashboardDataUsers();
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEventsByRegionFilter(String startDate, String endDate) {
        return dbServiceImpl.getDashboardDataEventsByRegionFilter(startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEventsByCategoryRegion(String startDate, String endDate) {
        return dbServiceImpl.getDashboardDataEventsByCategoryRegion(startDate, endDate);
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEventsByCategoryFilter(String startDate, String endDate) {
        return dbServiceImpl.getDashboardDataEventsByCategoryFilter(startDate, endDate);
    }

    @Override
    public Future<JsonObject> saveEventAction(long eventId, long responsedUserId, Long actionTypeId) {
        return dbServiceImpl.saveEventAction(eventId, responsedUserId, actionTypeId);
    }

    @Override
    public Future<JsonObject> blockUser(long userId, long performBlockingUserId) {
        return dbServiceImpl.blockUser(userId, performBlockingUserId);
    }

    @Override
    public Future<JsonObject> saveEventResponse(long eventId, long responsedUserId, String name, String message, String startDate) {
        return dbServiceImpl.saveEventResponse(eventId, responsedUserId, name, message, startDate);
    }

    @Override
    public Future<JsonObject> updateExportedEvent(long externalId, long userId, String fullName, String phoneNumber) {
        return dbServiceImpl.updateExportedEvent(externalId, userId, fullName, phoneNumber);
    }

    @Override
    public Future<List<JsonObject>> getListByAutoId(long auto_id) {
        return dbServiceImpl.getListByAutoId(auto_id);
    }

    @Override
    public Future<List<JsonObject>> getListByGroupId(long group_id) {
        return dbServiceImpl.getListByGroupId(group_id);
    }

    @Override
    public Future<List<JsonObject>> getListByPerPage(long page, long per_page) {
        return dbServiceImpl.getListByPerPage(page, per_page);
    }

    @Override
    public Future<JsonObject> getCount() {
        return dbServiceImpl.getCount();
    }

    @Override
    public Future<JsonObject> getPageCount(long per_page) {
        return dbServiceImpl.getPageCount(per_page);
    }


    @Override
    public Future<JsonObject> update(JsonObject formData) {
        return dbServiceImpl.update(formData);
    }

    @Override
    public Future<JsonObject> delete(JsonObject formData) {
        return dbServiceImpl.delete(formData);
    }

    @Override
    public Future<List<JsonObject>> getListBySearchField(JsonObject formData) {
        return dbServiceImpl.getListBySearchField(formData);
    }

    @Override
    public Future<JsonObject> getCountBySearchField(JsonObject formData) {
        return dbServiceImpl.getCountBySearchField(formData);
    }

    @Override
    public Future<List<JsonObject>> getListByLang(String lang) {
        return dbServiceImpl.getListByLang(lang);
    }

    @Override
    public Future<List<JsonObject>> getActiveEventCategories() {
        return dbServiceImpl.getActiveEventCategories();
    }

    @Override
    public Future<JsonObject> updateIsActive(boolean isActive, long id) {
        return dbServiceImpl.updateIsActive(isActive, id);
    }

    public Future<JsonObject> updateDurationMin(long durationMin, long id) {
        return dbServiceImpl.updateDurationMin(durationMin, id);
    }

}
