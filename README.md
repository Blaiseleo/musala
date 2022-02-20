# Musala Assessment

## Tech Stack
-  Java
-  H2database
##Pre-Requisite Software:
-  Git
-  Maven
-  IntelliJ Idea
##Cloning Project Instructions:
- Clone drone service (master) from [here](https://github.com/Blaiseleo/musala.git) using cmd `Git clone` in your terminal

##Setup
- Clean project
- Compile project
- Run project

##EndPoints
- Register drone (POST) - http://localhost:9090/api/v1/drone/register
- Load drone with medication item (POST) - http://localhost:9090/api/v1/drone/load-drone
- Get all medication items (GET) - http://localhost:9090/api/v1/drone/get-all-medication-items?serialNumber=ABCdefGh
- Get all available drones (GET) - http://localhost:9090/api/v1/drone/get-all-available-drones
- Get drone battery level (GET) - http://localhost:9090/api/v1/drone/get-drone-battery-level?serialNumber=ABCdefGh


###You are all set now.