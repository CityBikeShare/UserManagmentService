### UserManagmentService

[![Build Status](https://travis-ci.org/CityBikeShare/UserManagmentService.svg?branch=master)](https://travis-ci.org/CityBikeShare/UserManagmentService)

- Service allows a user to create account and login later

#### Requests on kubernetes
    GET
        - 159.122.177.235:30002/sources/users                      ### Get all users
        - 159.122.177.235:30002/sources/users/{id}                 ### Get user by id
        - 159.122.177.235:30002/sources/users/region/{region}      ### Get users living in given region
        
    PUT
        - 159.122.177.235:30002/sources/users/registerUser         ### User registration [@RequestBody Users]
        - 159.122.177.235:30002/sources/users/update/{id}          ### Update a user by id with [@RequestBody Users]

    POST
        - 159.122.177.235:30002/sources/users/loginUser            ### User login [@QueryParam uname, @QueryParam passwd]

    DELETE
        - 159.122.177.235:30002/sources/users/delete/{id}          ### Delete user by id