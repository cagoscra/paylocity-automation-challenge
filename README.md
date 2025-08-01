# Paylocity Automation Challenge

## Overview
This project contains automated tests for the Paylocity Benefits Dashboard application, covering both API and UI testing scenarios.

## API Testing Results
✅ **23 tests passing, 0 failures**

### API Endpoints Tested
- `GET /Api/Employees` - Retrieve all employees ✅
- `GET /Api/Employees/{id}` - Retrieve specific employee ✅
- `POST /Api/Employees` - Create new employee ✅
- `DELETE /Api/Employees/{id}` - Delete employee ✅
- `PUT /Api/Employees/{id}` - Update employee ❌ (405 Method Not Allowed)

### Key Findings
1. **Benefits Calculation Formula**: `(1000 + (dependants * 500)) / 26`
2. **PUT Method Not Supported**: Returns 405 Method Not Allowed
3. **DELETE Behavior**: Returns 200 but actual deletion behavior needs verification
4. **Default Values**: All employees have salary=52000, gross=2000

### Test Coverage
- ✅ Status code validation
- ✅ Response structure validation
- ✅ Data integrity checks
- ✅ Benefits calculation accuracy
- ✅ Error handling (405 for PUT)
- ✅ Field presence validation

## API Tests Setup

### Prerequisites
- Postman installed
- Valid authorization token

### Running API Tests
1. Import `api-tests/paylocity-api-collection.json` into Postman
2. Set up environment with authorization header:
    - Key: `Authorization`
    - Value: `Basic VGVzdFVzZXI3NzM6NnEwXWwkQktPVWIh`
3. Run the collection using Postman Runner

### Authorization
All API requests require Basic Auth token in headers:
```
Authorization: Basic VGVzdFVzZXI3NzM6NnEwXWwkQktPVWIh
```

## UI Testing
[To be implemented]

## Test Environment
- Base URL: `https://wmxrwq14uc.execute-api.us-east-1.amazonaws.com/Prod`
- Test User: `TestUser773`
- Environment: AWS Lambda/API Gateway

## Notes
- API documentation was not accessible, endpoints were discovered through exploration
- All tests designed to be non-destructive where possible
- Benefits calculations validated against business rules (employee=$1000/year, dependent=$500/year, 26 pay periods)

## Author
Oscar - Paylocity STE Assessment





To run the test it's only necesary to install gradle and run with
./gradlew test

<img width="1241" height="598" alt="image" src="https://github.com/user-attachments/assets/5aac785f-1911-4777-9457-32c0a48f5df4" />
