## Rest controller, supporting appropriate methods: POST, GET and DELETE:
### Technologies Used
- **Spring Boot** - A framework used to build stand-alone, production-grade Spring-based applications.

### Features
- POST: /api/employee - adds an employee
- DELETE: /api/employee/:id - deletes an employee
- GET: /api/employee/csv - returns all employees as a CSV file
- GET: /api/group - returns all groups
- POST: /api/group - adds a new group
- DELETE: /api/group/:id - deletes a group
- GET: /api/group/:id/employee - returns all employees in the group
- POST: /api/rating - adds a rating for the group

### Unit Tests
The project includes a set of unit tests to ensure the functionality of the REST controllers.
