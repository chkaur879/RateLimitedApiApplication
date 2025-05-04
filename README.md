# Rate-Limited-Public-API
Rate-Limited Public API using Spring Boot

Developed a Spring Boot application that exposes a public API endpoint with rate limiting based on client IP address. The API allows only 5 requests per minute per IP, and returns a 429 - Too Many Requests status if the limit is exceeded.


# Functional Requirements:

## Public API Endpoint

· Create a simple REST endpoint:

## GET /api/public-data

· The endpoint returns static or mock JSON data (e.g., a list of strings, products, or message).

## Rate Limiting Logic

· Each IP can call the API up to 5 times per minute.

· On the 6th request (within the same minute), return:

· HTTP Status: 429 TOO MANY REQUESTS

· Message: "Rate limit exceeded. Try again later."



# Technical Requirements:

Technologies:

· Java 17+

· Spring Boot

· Spring Web
