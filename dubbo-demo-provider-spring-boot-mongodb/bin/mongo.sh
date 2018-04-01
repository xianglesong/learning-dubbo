mongo --port 27017

use admin

db.createUser(
    {
        user: "adminUser",
        pwd: "adminPass",
        roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
    }
)
