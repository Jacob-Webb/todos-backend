# Full Stack Todo List
## Front End - Angular
* Using Angular Material for styling framework

## Back End - Spring Boot
### Security
#### Spring Security and JWT authorization
### API Paths
#### Permissions
##### Roles are set by assigning certain Privileges. 
* User    ->  RW Todos and RW User Data
* Admin   ->  User + RW Users 
* SuperAdmin -> Admin + RW User Roles
##### CRUD for Permissions 
* Privileges
  - Only the name of a Privilege is used to create one
  - Associations with Roles is handled by the CRUD for Roles
* Roles
  - Roles are created using a name for the Role. 
  - If a Privilege is added to a Role, the Role also shows up in the Privilege's Role collection
  - All associations with Users are handled by Users

#### Users
##### CRUD for Users
* Create User
  - Users are created using username, password, first name, last name, and contact information
    * Username must be unique. If it isn't, ask for a new username. 
* Get all users 
  - For Admin and above: return username, first name, and last name
* Get a single User
  - For User (restricted to self): return username, first name, last name, and contact information
  - For User and above: return username, first name, last name, and contact information
  - For SuperAdmin: include Roles
* Update User 
  - For User (restricted to self):  access denied to Roles
  - For Admin: access denied to Roles
* Delete User
  - Search for User, if they exist, delete them from the database

#### Todos
##### CRUD
All todos are associated with a User. The backend finds the user by username, and then either creates, reads, updates, or deletes that User's todo. 
