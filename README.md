# Puppies Social Network API

This project is the back-end API for a social network for puppies. It provides various endpoints for users to interact and share their experiences about their loved pets' life.

![Build Status](https://img.shields.io/github/workflow/status/jpsanchez91/puppies-api/Run%20Tests?label=tests)


## API Documentation

Our API documentation is available at [localhost:8080/swagger-ui](http://localhost:8080/swagger-ui).

## Available Endpoints

Our API currently covers the following areas:

- User Registration and Authentication
- User Profile Management
- Posting
- Commenting on Posts
- Liking Posts
- Fetching Feed

Details about these endpoints, such as their paths, methods, request bodies, and responses, can be found in the [Swagger UI](http://localhost:8080/swagger-ui).

## Running Tests

To run tests, simply execute the following Maven command:

```
mvn test
```

### Noteworthy Test Features

This project makes use of Testcontainers for integration tests. This allows our tests to use real instances of our services in a controlled environment. More specifically, we use the official Docker image of PostgreSQL as our primary datastore for testing.

Furthermore, we have end-to-end tests that cover the main functionalities of our API. These tests help ensure the correct interaction and coherence among all layers of our application, from the database to the controller endpoints.

---

Feel free to explore and use the API endpoints. We are constantly working on adding more features and improving the existing ones. Contributions are welcome!