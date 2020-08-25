# Full Stack Todo List
## Front End - Angular

## Back End - Spring Boot
### Security
#### Spring Security and JWT authorization
### API Paths
#### Permissions
##### Roles are set by assigning certain Privileges. 
* User    ->  RW Todos and RW User Data
* Admin   ->  User + RW Users
* SuperAdmin -> Admin + RW User Roles

User Data is defined by things such as username, password, and personal information.

SuperAdmins are allowed to see and update roles assigned to users