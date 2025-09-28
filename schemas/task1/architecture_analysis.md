# Architecture Analysis of Smart Home Monolithic Application

## Application Overview

The smart home application is a monolithic Go application built with the Gin web framework. It provides REST API endpoints for managing temperature sensors and retrieving temperature data from an external service.

## Key Components

### Internal Components
- **Web Server**: Go application using Gin framework (port 8080)
- **Database**: PostgreSQL database for storing sensor data
- **Business Logic**: Handlers for sensor CRUD operations and temperature service integration

### External Components
- **Temperature API**: External HTTP service (port 8081) that provides random temperature values based on location or sensor ID

## Technology Stack
- **Programming Language**: Go
- **Web Framework**: Gin
- **Database**: PostgreSQL with pgx driver
- **External Integration**: HTTP REST API calls

## Interaction Patterns

1. **User to Application**: HTTP REST API calls (GET/POST/PUT/DELETE)
2. **Application to Database**: SQL queries via pgx connection pool
3. **Application to Temperature API**: HTTP GET requests to `/temperature` endpoint

## Database Schema
The application uses a `sensors` table with fields:
- id, name, type, location, value, unit, status, last_updated, created_at

## API Endpoints
- `GET /api/v1/sensors` - Retrieve all sensors
- `GET /api/v1/sensors/:id` - Get specific sensor
- `POST /api/v1/sensors` - Create new sensor
- `PUT /api/v1/sensors/:id` - Update sensor
- `DELETE /api/v1/sensors/:id` - Delete sensor
- `GET /health` - Health check endpoint

## External Dependencies
- Temperature API service must be available for sensor creation
- PostgreSQL database must be running for data persistence

## Deployment
- Dockerized application with Dockerfile
- Uses environment variables for configuration:
  - `DATABASE_URL`: PostgreSQL connection string
  - `TEMPERATURE_API_URL`: Temperature service endpoint
  - `PORT`: Application port (default: 8080)
