# Overview
Medical Scheduling System is a console-based Java application designed to manage patients, doctors, and medical appointments.
This project was created for study purposes, applying fundamental concepts of object-oriented programming, clean architecture, data validation, and automated tests.

Features
* Patient Management: create, list, search, update email, and delete
* Doctor Management: create, list, search, and delete
* Appointment Management:
  * Schedule new appointments
  * List and filter appointments
  * Update appointment status (Completed / Canceled)
  * Delete appointments
* Input validation for dates, numeric values, and email uniqueness
* Domain-driven exception handling
* User-friendly console menu navigation

## Technologies
* Java (Console Application)
* JUnit (Automated Tests)
* No external dependencies


## Project Structure
```
src/
 ├── controllers/
 │    ├── AppointmentController.java
 │    ├── BaseController.java
 │    ├── DoctorController.java
 │    └── PatientController.java
 │
 ├── enums/
 │    ├── AppointmentStatus.java
 │    └── Specialty.java
 │
 ├── interfaces/
 │    ├── HasEmail.java
 │    ├── HasId.java
 │    ├── HasName.java
 │    ├── IsUpdatable.java
 │    └── Repository.java
 │
 ├── model/
 │    ├── domainService/
 │    │    └── AppointmentDomainService.java
 │    ├── exception/
 │    │    ├── DomainException.java
 │    │    ├── EntityAlreadyExistsException.java
 │    │    └── EntityNotFoundException.java
 │    ├── Appointment.java
 │    ├── Doctor.java
 │    ├── Patient.java
 │    └── Person.java
 │
 ├── repositories/
 │    ├── AppointmentRepository.java
 │    ├── DoctorRepository.java
 │    ├── PatientRepository.java
 │    └── RepositoryMemory.java
 │
 ├── services/
 │    ├── AppointmentService.java
 │    ├── BaseService.java
 │    ├── DoctorService.java
 │    ├── EmailValidatableService.java
 │    └── PatientService.java
 │
 ├── tests/
 │    ├── model/
 │    └── repositories/
 │
 ├── UI/
 │    └── MainMenu.java
 │
 └── utils/
      ├── Input.java
      └── PauseUI.java
```

## How To Run
1. Clone the repository  
2. Open the project in your IDE (IntelliJ, Eclipse, VS Code)
3. Ensure Java 17+ is installed

`Execute:  
App.java`

## Architecture Notes
* MVC-inspired structure for separation of concerns

* Domain validations inside models and domain services

* In-memory repository (easily replaceable by a database in the future)

* Exceptions emphasize business rules and constraints

## Tests

* Automated tests included for:
* Entity operations
* Repository behavior