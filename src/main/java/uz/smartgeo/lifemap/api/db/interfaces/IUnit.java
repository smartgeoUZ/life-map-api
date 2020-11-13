package uz.smartgeo.lifemap.api.db.interfaces;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface IUnit {

    Future<List<JsonObject>> getList();

    Future<JsonObject> getById(long id);

    Future<JsonObject> getByExtId(long extId, int authType);

    Future<JsonObject> getUserByExtIdAndUpdate(long extId, int authType);

    Future<List<JsonObject>> getUsersByRoleId(long roleId);

    Future<JsonObject> updateMobileNumber(long extId, int authType, String mobileNumber);

    Future<JsonObject> updateProfile(long userId, String firstName, String lastName, String email);

    Future<JsonObject> updateUserData(long extUserId, String login, String photoUrl, long authTypeId);

    Future<JsonObject> updateUserRegion(long userId, long regionId, long authTypeId);

    Future<JsonObject> updateUserRole(long userId, long roleId);

    Future<JsonObject> save(JsonObject formData);

    Future<List<JsonObject>> getRolePermissionsByUserId(long userId);

    Future<JsonObject> updateAccessValue(long id, int accessValue);

    Future<List<JsonObject>> getEventsForAnonymous(long categoryId, Boolean isActive, int filterStatusId, String startDate, String endDate);

    Future<List<JsonObject>> getRelevantEvents(long userId, long categoryId, int roleId, String startDate, String endDate);

    Future<List<JsonObject>> getAllEvents(long userId, long categoryId, int roleId, String startDate, String endDate);

    Future<List<JsonObject>> getOwnEvents(long userId, long categoryId, int roleId, String startDate, String endDate);

    Future<List<JsonObject>> getModeratingEvents(long userId, long categoryId, int roleId, String startDate, String endDate, long userRegionId);

    Future<List<JsonObject>> getEventsBySearchText(long userId, String searchText, boolean isForAnonymous);

    Future<JsonObject> deleteEvent(long eventId, long performDeletionUserId);

    Future<JsonObject> updateModerationStatus(long eventId, boolean isModerated);

    Future<JsonObject> getRegionByCoordinates(Double lon, Double lat);

    Future<List<JsonObject>> getDashboardDataEvents(long categoryId, long regionId, long userId, String startDate, String endDate);

    Future<List<JsonObject>> getDashboardDataUsers();

    Future<List<JsonObject>> getDashboardDataEventsByRegionFilter(String startDate, String endDate);

    Future<List<JsonObject>> getDashboardDataEventsByCategoryRegion(String startDate, String endDate);

    Future<List<JsonObject>> getDashboardDataEventsByCategoryFilter(String startDate, String endDate);

    Future<JsonObject> saveEventAction(long eventId, long responsedUserId, Long actionTypeId);

    Future<JsonObject> blockUser(long userId, long performBlockingUserId);

    Future<JsonObject> saveEventResponse(long eventId, long responsedUserId, String name, String message, String startDate);

    Future<JsonObject> updateExportedEvent(long externalId, long userId, String fullName, String phoneNumber);

    Future<List<JsonObject>> getListByLang(String lang);

    Future<List<JsonObject>> getActiveEventCategories();

    Future<JsonObject> updateIsActive(boolean isActive, long id);

    Future<JsonObject> updateDurationMin(long durationMin, long id);

    Future<List<JsonObject>> getListByAutoId(long auto_id);

    Future<List<JsonObject>> getListByGroupId(long group_id);

    Future<List<JsonObject>> getListByPerPage(long page, long perPage);

    Future<JsonObject> getCount();

    Future<JsonObject> getPageCount(long perPage);

    Future<JsonObject> update(JsonObject formData);

    Future<JsonObject> delete(JsonObject formData);

    Future<List<JsonObject>> getListBySearchField(JsonObject formData);

    Future<JsonObject> getCountBySearchField(JsonObject formData);



}
