# Project Libraq

A **library management system** built using **Java** as part of the EECS 3311 Software Design course.  
This project demonstrates Agile software development practices, including sprint planning, Git flow branching, and collaborative version control.


## Motivation
**Libraq** is a modern web-based library management system designed for small community and school libraries that lack the advanced digital tools found in larger systems.
Many existing library systems and commercial companies offer physical and online lending, and personalized recommendations, however they are resource-heavy and not as flexible.
Our project aims to solve this by creating an accessible, efficient, and intuitive platform where users can:

 - Search, reserve, and check out books online
 - View due dates and current reservations
 - For librarians: easily add, update, or remove books from the collection


The project will make library management more efficient and user-friendly, providing libraries with digital functionality similar to large public systems - but with greater simplicity and flexibility.

## Installation and Setup

### Prerequisites
Make sure you have the following installed:
- Java 21
- Eclipse IDE
- Maven 3.x
- Git

Installation
Requirements

### Steps to Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/EECS3311F25/project-libraq.git
   cd project-libraq/libraq
   ```
2. **Import into Eclipse**
   - Open Eclipse → File → Import → Maven → Existing Maven Projects
   - Browse to the libraq folder (where pom.xml is)
3. **Run the application**
   - Open LibraqApplication.java in src/main/java/com/example/libraq
   - Right-click → Run As → Java Application
4. **Access in browser**
   - [http://localhost:8080/home](http://localhost:8080/home)

## Architecture Overview
Libraq follows the MVC (Model–View–Controller) pattern:

 - Model: Book entity, mapped with JPA to an H2 database

 - View: Thymeleaf HTML templates (in src/main/resources/templates)

 - Controller: Handles HTTP requests and connects model <-> view

## Contribution Guidelines

We follow the Git Flow branching model to organize development and maintain a clean project history

### Branching Strategy
| Branch Type |	Purpose |
|-------------|-------- |
| main| Contains stable, production-ready code (used for final sprint releases) |
| sprint/<number>	| A dedicated branch for each sprint cycle (e.g., sprint/1, sprint/2) |
| feature/<feature-name>	| Branches created from within the current sprint branch to develop individual features or fixes |

### Database Access
- We are using H2 for our DB layer
-  To access the DB go to [http://localhost:8080/h2-controller](http://localhost:8080/h2-controller) and connect using the following login:
| Entry | Value |
| :---- | :---- |
| Driver Class: | org.h2.Driver |
| JDBC URL: | jdbc:h2:./data/librarydb |
| User Name | sa |
| Password |  |


## Authors
- Taisia Zhizhina	
- Akhanali Amangeldi
- Timothy Tolstinev
- Parnia Esfandiari

EECS 3311 - Software Design, York University
Fall 2025
¯⁠\_(⁠ツ⁠)⁠_⁠/⁠¯
