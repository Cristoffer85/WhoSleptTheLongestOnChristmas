# Project name How long did you sleep on Christmas?

## Description

A Java SpringBoot application that uses Spring Security 6 for authorization and authentication of different Users in the application through 
* JWT (JSON Web Token)

to later perform different CRUD functionalities in the app based on authority level through an API and RESTful Web Service and store and fetch data from a local MongoDB database.

The program focuses on the security part, and making sure it encrypts the password sent in the requests and responses, and also makes a Token that is required to use valid only for one session (a new one is automatically generated for a new session) In the User creation process it is - beside the Username (Which must be unique due to duplication issues) and Password, being prompted of two extra values to enter a free value between 1 and 24 [hours] and free weekday [during christmas] to set as a record day one slept the longest during christmas.  
A user or admin can later choose to for example view all users to see who slept the longest.

* Authorities in the program are set so that a:

* ANYONE (Main Menu) can Create new USER
    * USER can Read (one or all) USERS
    * ADMIN can Create, Read (one or all), Update and Delete USERS


An ADMIN cant be created through the User Interface. It must be added manually in the class 'AdminSetup' See the 'AdminSetup' class to change current values of current ADMINs.
- What was your motivation?

In various tutorial videos online, there only existed various quality of more or less valid current videos of relational database-use in the latest Spring Security 6. Thats why i wanted to challenge this a bit, to first build together and understand, and complete a program that was originally intended and built for SQL [Structured Query Language] and to adapt it to be use a non-relational NoSQL [Not only Structured Query Language] database. 

And while doing so --> really grinding some hours in front of the screen late nights and long days, continuously really trying tro LEARN what and why im doing this [Also implementing and learning Lombok and various other annotations to cut the program down to be more clean and Easy-read] i feel as though i feel a much much more greater understanding of SpringBoot, how it works and Spring Security as a whole.

- What problem does it solve?

It lets a User login securely with a password that is encrypted for anyone to see (even in the database its encrypted, and when ADMIN is updating it as well) so your password is only allowed and known by you [And the ADMIN right now, initially before they forget about it :] And lets you insert two values of How many hours you slept during one day of christmas and which day. That data can then later be compared to other users to see how long they slept.

- What did you learn?

I learned initially and perhaps the most biggest part how SpringBoot works and functions, some various annotations, Lombok, Spring Security, JWT and how tokens in general work and why they exist, how to encrypt and decrypt passwords sent, the implementation of setting up your own API and Web Service and in big HOW that works. I also learned alot about both Relational and Non-relational structure building to the different types of databases through the API.

## Installation
1. Download and Set Up MongoDB Local on your device (Only local is required for this specific program) and start a connection on Localhost port default 27017.
2. Download POSTMAN if you dont already have it, watch quick tutorial if dont know how to use.
3. Clone this project from Github into your own IDE, start main() application.


Application is built in Maven so this project shall have every dependency required in the Pom.xml required to run this program.

Go further down to #Usage to learn how to use the application through the API and POSTMAN.

## Usage

####EITHER - Start the application directly in your IDE and choose options from the local UI, or follow below to test the API-functions specifically:

TEST instructions for API through through POSTMAN
        
* For Pre-CLARIFICATION - An ADMIN has all the Authoritites that a USER has -> That means, and ADMINs JW-Token is valid for USER Authorities as well.

-------------------------------

[MAIN MENU] ( -From Class AuthenticationController)

    CREATE USER [USER CLEARANCE] [Every new user is created by default as new USER]

    POST http://localhost:8080/auth/register

                                               [set] Body -> raw -> JSON format

                                               {
                                                "username": "<setusername>",
                                                "password": "<setpassword>",
                                                "maxHoursSlept": <setnumberbetween1and24>,
                                                "weekday":"<setweekdayinCAPITALletters>"
                                               }

                                               -> Send


LOGIN [USER]

    POST http://localhost:8080/auth/login
                                              [set] Body -> raw -> JSON format

                                              [USER]
                                               {
                                                "username": "<setusername>",
                                                "password": "<setpassword>"
                                               }

                                                                                        -> Send


                                               -> AFTER succesful login, check bottom of Response body in POSTMAN for "jwt" {JSON Web Token} copy that entire autogenerated Token-string -> use it in below USER or ADMIN CRUD Functionalities
                                                  ## >>> JW-Token is unique per session, so need to use new generated per session if close program/logout

LOGIN [ADMIN]

                                              [set] Body -> raw -> JSON format
                                           
                                              [ADMIN]
                                              {
                                               "username": "admin",
                                               "password": "superadminpassword"
                                              }

                                              --------------- OR ---------------

                                              {
                                               "username": "admin2",
                                               "password": "superadminpassword"
                                              }
                                                                                        -> Send

                                               -> AFTER succesful login, check bottom of Response body in POSTMAN for "jwt" {JSON Web Token} copy that entire autogenerated Token-string -> use it in below USER or ADMIN CRUD Functionalities
                                                  ## >>> JW-Token is unique per session, so need to use new generated per session if close program/logout

-------------------------------
[AUTHORITIES] CRUD Testing API against MongoDB Database

    BEFORE [set] Authorization -> Bearer Token -> Paste the earlier Copied "jwt"-String in Token. Then use whichever preferred authorized CRUD method below -> Send
  ---------------------------------------------------------------------------------------------------------------------------------------------
AFTER Typing above and choosing below free of choice CRUD implementation (response in Response Body)



        [USER AUTHORITIES]
    -----------------------------------
    CREATE USER [USER CLEARANCE]        - POST http://localhost:8080/auth/register  [Same as Main Menu Create User - Creates User by default]

    VIEW ONE USER [USER CLEARANCE]      - GET http://localhost:8080/user/getOneUser/<setusernamestoredfromdatabase>

    VIEW ALL USERS [USER CLEARANCE]     - GET http://localhost:8080/user/getAllUsers/





        [ADMIN AUTHORITIES]
    -----------------------------------
    CREATE USER [USER CLEARANCE]        - POST http://localhost:8080/auth/register  [Same as Main Menu Create User - Creates User by default]

    VIEW ONE USER [USER CLEARANCE]      - GET http://localhost:8080/admin/getOneUser/<setusernamestoredfromdatabase>

    VIEW ALL USERS [USER CLEARANCE]     - GET http://localhost:8080/admin/getAllUsers/

    UPDATE ONE USER [ADMIN CLEARANCE]   - PUT http://localhost:8080/admin/updateUser/

                                                set Body -> raw -> JSON format

                                           {
                                             "username": "<setnewusername>",
                                             "password": "<setnewpassword>",
                                             "maxHoursSlept": <setnewnumber>,
                                             "weekDay": "<setnewweekdayinCAPITALletters>"
                                           }
                                                                                        -> Send

    DELETE ONE USER [ADMIN CLEARANCE]   - DELETE http://localhost:8080/admin/deleteUser/<setusernamestoredfromdatabase>


HTTP:Responses that might appear after trying your CRUD methods with provided Tokens
    
    # IF Authorized - A response of "Status: 200 OK" will be shown in the top header of response body                   (Valid Token present)
    # IF Unauthorized - A response of "Status: 401 Unauthorized" will be shown in the top header of response body       (None, or faulty token)
    # IF NOT Authenticated - A response of "Status: 403 Forbidden" will be shown in the top header of response body     (Valid Token present, but wrong authority)



## Credits
Classmates from school for the various groupdiscussions, ball-plank, solving and implementations, Myself, my family, Mighty Duck rubber duck and some chatGPT for debugging.

## License
🏆 MIT License

## Badges
![badmath](https://img.shields.io/badge/Java-100%25-blue)

## Features
* ANYONE (Main Menu) can Create new USER
    * USER can Read (one or all) USERS
    * ADMIN can Create, Read (one or all), Update and Delete USERS


An ADMIN cant be created through the User Interface. It must be added manually in the class 'AdminSetup' See the 'AdminSetup' class to change current values of current ADMINs.

Once USER-creation is initialized, it can set the value of how many hours you slept in one day (value HOURS, between 1-24) and WeekDay. If a user later wish to update this value to another day when they eventually slept longer (:O) it can contact an admin to update that value if wishes are so.

## Tests
No tests are (yet) created and implemented in this application.
