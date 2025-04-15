# kata-alten

This is my answer of the Back-End test, using Java/spring Boot,

You will find the swagger of the api in src/main/resources/swagger,

You will find the requests Postman on the src/main/resources/postman,

Please add the Bearer to the headers params as : Authorization: Bearer {{jwt_token}}

For the reservation off manage product api for admin we should create an admin "admin@admin.com" with role 'ADMIN', and in the class SecurityConfig.cass in the src/java/../config/ ligne 49, we should uncomment this ligne to block all other users not having 'ADMIN' role for consume this api.

Thank you !
