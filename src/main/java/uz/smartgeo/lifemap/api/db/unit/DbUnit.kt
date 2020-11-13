package uz.smartgeo.lifemap.api.db.unit

import java.sql.Timestamp

open class DbUnit
(var id: Long? = null,
 var status: String? = null,
 var regDate: Timestamp? = null,
 var modDate: Timestamp? = null,
 var expDate: Timestamp? = null)