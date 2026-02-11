# 🚗 Vehicle Hire Management System

## 📋 Overview

The **Vehicle Hire Management System** is a comprehensive Java application designed to simulate the core operations of a vehicle rental company. It manages the entire lifecycle of fleet operations, including vehicle acquisition, customer registration, rental contract processing, and fleet maintenance.

This project was built to demonstrate proficiency in **Object-Oriented Programming (OOP)**, **Design Patterns**, and **Defensive Programming** principles. It features a custom-built unit testing framework to ensure system reliability without reliance on external libraries.

---

## ✨ Key Features

### 1. Fleet Management
* **Polymorphic Vehicle Handling**: Supports multiple vehicle types (`Car`, `Van`) with distinct behaviors using a unified `AbstractVehicle` architecture.
* **Smart ID Generation**: Implements a custom algorithm to generate unique, format-specific IDs (e.g., Odd numeric suffixes for Vans, Even for Cars) to ensure data integrity.
* **Maintenance Tracking**: Automatically tracks mileage and triggers service requirements based on vehicle-specific thresholds (10,000 miles for Cars, 5,000 for Vans).

### 2. Rental Logic Engine
* **Eligibility Validation**: Enforces strict business rules:
    * Age restrictions (18+ for Cars, 23+ for Vans).
    * License validation (Commercial C-License required for Vans).
    * Rental caps (Maximum 3 active rentals per customer).
* **Inventory Control**: Prevents double-booking and manages vehicle availability states (Hired/Available).
* **Van Inspection Protocol**: Automatically flags Vans for safety inspections if rented for extended periods (>10 days).

### 3. Customer Management
* **Immutable Records**: Customer data is designed using immutable patterns to prevent accidental state mutation.
* **Duplicate Prevention**: Logic ensures unique customer registration based on personal details.

---

## 🛠 Architecture & Technical Highlights：

### 🏭 Design Patterns
* **Factory Pattern**: Used in `AbstractVehicle.getInstance()` and `VehicleID.getInstance()` to encapsulate complex object creation logic and ID generation rules.
* **Template Method / Strategy**: Shared logic is handled in `AbstractVehicle`, while specific behaviors (like `getDistanceRequirement`) are delegated to concrete subclasses (`Car`, `Van`).

### 🛡 Defensive Programming
* **Immutability**: The `Name` and `CustomerRecord` classes are immutable. Defensive copying is strictly applied to mutable fields (like `java.util.Date`) in constructors and getters to prevent external modification of internal state.
* **Encapsulation**: Strict access control is used to protect the integrity of the `VehicleManager` state.

### 🧪 Custom Testing Framework
* Instead of using JUnit, a lightweight **Assertion Framework** (`Assertions.java`) was architected from scratch.
* This demonstrates a deep understanding of how testing libraries work under the hood, including exception assertions (`assertExpectedThrowable`) and boundary testing.

---

## 📂 Project Structure

```bash
src/
├── VehicleManager.java     # Core Controller: Manages fleet, customers, and rentals
├── Vehicle.java            # Interface defining the contract for all vehicles
├── AbstractVehicle.java    # Abstract base class implementing shared logic
├── Car.java                # Concrete implementation for Cars
├── Van.java                # Concrete implementation for Vans (w/ inspection logic)
├── VehicleID.java          # Utility class for unique ID generation
├── CustomerRecord.java     # Immutable data class for customer details
├── Name.java               # Immutable value object
├── Assertions.java         # Custom unit testing utility
└── Test.java               # Comprehensive test suite covering all scenarios
