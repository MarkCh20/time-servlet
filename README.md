# Time Servlet Project

## Description
This Java web application provides the current time based on UTC or a specified timezone via a servlet (`TimeServlet`). It includes a filter (`TimezoneValidateFilter`) that validates the timezone query parameter.

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Requirements](#requirements)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)


## Features
- Returns current time in UTC or user-defined timezone
- Supports query parameter `timezone` (e.g. `UTC`, `UTC+3`, `Europe/Kyiv`)
- Filters and rejects invalid timezones with HTTP 400 error
- Compatible with Tomcat 10+/11+

## Requirements
- Java 17+
- Apache Tomcat 10.1+ or 11
- Maven 3.8+

## Setup Instructions
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/time-servlet.git
   cd time-servlet

2. Build the project:
    ```bash
   mvn clean package

3. Deploy the ROOT.war (or time-servlet.war) to Tomcat's webapps folder.

## Usage
- Access via browser:
  ```bash
  http://localhost:8080/time
  http://localhost:8080/time?timezone=UTC+3
  http://localhost:8080/time?timezone=Europe/Kyiv

- Invalid timezone example:
  ```bash
  http://localhost:8080/time?timezone=InvalidZone
  => HTTP 400 Bad Request with message "Invalid timezone"

