# Spring AI Travel Assistant

A demonstration project showcasing Spring AI capabilities for building intelligent travel assistant applications.

## Project Overview

This project demonstrates the integration of Spring AI with a travel planning application, showcasing how to leverage AI capabilities to enhance user experiences in travel planning and recommendations.

## Spring AI Features Explored

### 1. Spring AI Core Integration

- **ChatClient**: Integration with OpenAI's models using Spring AI's abstraction layer
- **Prompt Engineering**: Structured system and user prompts for consistent AI responses
- **Response Handling**: Processing and formatting AI responses for web applications

### 2. Tool Calling

The primary focus of this project is demonstrating Spring AI's tool calling capabilities:

- **Function Registration**: Registering Java functions as AI-callable tools
- **Tool Callbacks**: Using `FunctionToolCallback` to enable the AI model to call Java methods
- **Parameter Handling**: Proper JSON schema generation for tool parameters
- **Response Processing**: Handling structured responses from tools

### 3. Tool Implementation Approaches

We explored multiple approaches to implementing tools:

- **@Tool Annotation**: Initially using the annotation-based approach
- **Spring Bean Functions**: Registering tools as Spring beans returning `Function` instances
- **Functional Programming**: Modern Java functional programming for cleaner tool implementations

### 4. Custom Tools Implemented

1. **Weather Tools**:
   - `getCurrentWeather`: Provides current weather information for travel destinations
   - `getBestTimeToVisit`: Recommends optimal seasons to visit destinations

2. **Country Tools**:
   - `countryByCapital`: Retrieves country information based on capital city

## Technical Stack

- **Spring Boot**: 3.5.4
- **Java**: 21
- **Spring AI**: 1.0.1
- **OpenAI Integration**: GPT models via Spring AI's OpenAI adapter
- **Lombok**: For reducing boilerplate code in DTOs

## Project Structure

- **Controllers**: REST endpoints for interacting with the AI assistant
- **Services**: Business logic and AI integration
- **Tools**: Custom tool implementations for AI function calling
- **DTOs**: Data transfer objects for structured data exchange

## Getting Started

### Prerequisites

- Java 21+
- Maven
- OpenAI API key

### Configuration

1. Set your OpenAI API key in `application.properties`:
   ```properties
   spring.ai.openai.api-key=your-api-key
   ```

### Running the Application

```bash
mvn spring-boot:run
```

## API Endpoints

### Travel Planning

- `GET /travel/plan?destination={destination}` - Get a travel plan for a destination
- `GET /travel/recommend?preferences={preferences}` - Get travel recommendations based on preferences

### Weather Tool Demo

- `GET /demo/weather/info?destination={destination}` - Get weather information using AI tool calling
- `GET /demo/weather/plan?destination={destination}&days={days}` - Get a weather-aware trip plan
- `GET /demo/weather/direct/weather?destination={destination}` - Direct access to weather tool
- `GET /demo/weather/direct/best-time?destination={destination}` - Direct access to best time to visit tool

## Key Learnings

1. **Tool Registration**: Tools must be properly registered with matching bean names for the AI to find them
2. **Parameter Wrapping**: OpenAI function calling requires object parameters, not primitive types
3. **Functional Approach**: Using Java's functional programming features creates cleaner, more maintainable tool implementations
4. **Prompt Engineering**: Effective prompts are crucial for guiding the AI to use tools appropriately
