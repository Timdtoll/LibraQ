# Project Libraq

A **library management system** built using **Java** as part of the EECS 3311 Software Design course.  
This project demonstrates Agile software development practices, including sprint planning, Git flow branching, and collaborative version control.


## ✊ Motivation
**Libraq** is a modern web-based library management system designed to simplify how users borrow and manage books.
Many existing library systems are outdated; they require in-person visits to check availability, make reservations, or manage loans, which is inconvenient for both library users and staff.
Our project aims to solve this by creating an accessible, efficient, and user-friendly platform where users can:

 - Search, reserve, and check out books online
 - View due dates and current reservations
 - For librarians: easily add, update, or remove books from the collection


Libraq exists to make library services more convenient, automated, and digitally connected, improving the experience for people who want faster access to books and librarians who need better tools to manage it.
## ⚙️ Installation and Setup

### 🧰 Prerequisites
Make sure you have the following installed:
- Java 21
- Eclipse IDE
- Maven 3.x
- **Git** (for version control)  

Installation
Requirements

### 🪜 Steps to Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/EECS3311F25/project-libraq.git
   cd project-libraq/libraq
   ```bash
2. **Import into Eclipse**
   - Open Eclipse → File → Import → Maven → Existing Maven Projects
   - Browse to the libraq folder (where pom.xml is)
3. **Run the application**
   - Open LibraqApplication.java in src/main/java/com/example/libraq
   - Right-click → Run As → Java Application
4. **Access in browser**
   - http://localhost:8080/books[http://localhost:8080/books]

## 🧩 Architecture Overview
Libraq follows the MVC (Model–View–Controller) pattern:

 - Model: Book entity, mapped with JPA to an H2 database

 - View: Thymeleaf HTML templates (in src/main/resources/templates/books.html)

 - Controller: Handles HTTP requests and connects model <-> view

## ⛓  Contribution Guidelines

We follow the Git Flow branching model to organize development and maintain a clean project history

### 🌴  Branching Strategy
| Branch Type |	Purpose |
|-------------|-------- |
| main| Contains stable, production-ready code (used for final sprint releases) |
| dev	| Ongoing development branch that integrates all sprint branches |
| sprint/<number>	| A dedicated branch for each sprint cycle (e.g., sprint/1, sprint/2) |
| feature/<feature-name>	| Branches created from within the current sprint branch to develop individual features or fixes |


## 🧠 Authors
- Taisia Zhizhina	
- Akhanali Amangeldi
- Timothy Tolstinev
- Parnia Esfandiari

EECS 3311 - Software Design, York University
Fall 2025
