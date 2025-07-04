# Chatbot API powered by Spring AI

A Spring Boot-based chatbot application powered by Spring AI, OpenAI and PostgreSQL with pgvector for semantic search and embeddings.

## 🚀 Features

- **AI-Powered Chat**: Interactive chat interface with OpenAI GPT-4
- **Document Embedding**: Upload and embed large text documents for context-aware responses
- **Semantic Search**: PostgreSQL with pgvector for efficient similarity search
- **Modern UI**: Beautiful, responsive web interface with real-time chat
- **File Upload**: Support for text files (.txt, .md, .csv) for embedding
- **Embedding Management**: Clear all embeddings functionality
- **Get Current Date/Time**: Ask the chatbot for the current date or time, powered by an MCP server (runs in Docker)

## 🏗️ Architecture

- **Backend**: Spring Boot 3.4.2 with Java 21
- **AI Models**: OpenAI GPT-4 and OpenAI Text Embedding 3 Small
- **Database**: PostgreSQL with pgvector extension
- **Frontend**: Vanilla JavaScript with modern CSS
- **Embedding Store**: Spring AI PgVector Embedding Store

## 📋 Prerequisites

- Java 21 or higher
- OpenAI API key

## 🛠️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd chatbot-api-v1
```

### 2. Environment Variables

Create a `.env` file in the project root or set the following environment variables:

```bash
# OpenAI API Configuration
OPENAI_API_KEY=your_openai_api_key_here
```

**Note**: This demo uses Testcontainers for the database, so no external PostgreSQL setup is required!

### 3. MCP Server Configuration

The chatbot uses an MCP (Model Context Protocol) server to answer questions about the current date and time. This is configured in `src/main/resources/mcp-servers.json` and runs the `mcp/time` Docker image automatically. No manual setup is required—Docker must be installed and running on your system.

### 4. Testcontainers Setup

This demo automatically uses Testcontainers to provide an embedded PostgreSQL database with pgvector extension. This eliminates the need for any external database setup.

The Testcontainers setup automatically:
- Starts a PostgreSQL 15 container with pgvector extension
- Creates the necessary database schema
- Provides isolated database instances for each run

## 🚀 Running the Application

```bash
# Build the project
./mvnw clean install

# Run the application (uses Testcontainers automatically)
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

**Note**: The first run may take a bit longer as Testcontainers downloads the PostgreSQL image.

## 📱 Usage

### Web Interface

1. **Chat Interface** (`http://localhost:8080/index.html`)
   - Interactive chat with the AI assistant
   - Real-time responses with typing indicators
   - Modern, responsive design

2. **Embedding Interface** (`http://localhost:8080/embed.html`)
   - Upload text files or paste large text blocks
   - Process embeddings for semantic search
   - Clear all embeddings functionality

### API Endpoints

#### Chat Endpoint
```http
POST /chatbot/chat
Content-Type: application/json

{
  "message": "Your question here"
}
```

#### Embed Endpoint
```http
POST /chatbot/embed
Content-Type: application/json

{
  "text": "Large text block to embed"
}
```

#### Clear Embeddings Endpoint
```http
DELETE /chatbot/embed
```

## ⏰ Date/Time via MCP Server

You can now ask the chatbot questions like:

- "What is the current date?"
- "Tell me the time now."

The chatbot will use the configured MCP server (via Docker) to fetch and return the current date and time.

**Note:** The MCP server is defined in `src/main/resources/mcp-servers.json` and is started automatically when needed. Ensure Docker is running on your machine for this feature to work.

## 🏗️ Project Structure

```
springai-chatbot-api-v1/
├── src/
│   ├── main/
│   │   ├── java/lab/maq/springai/chatbot/
│   │   │   ├── ChatBotConfig.java    # Spring configuration
│   │   │   ├── Main.java                    # Application entry point
│   │   │   └── impl/
│   │   │       ├── ChatBotController.java   # REST endpoints
│   │   │       ├── ChatBotService.java      # Business logic
│   │   │       ├── ChatModel.java           # Chat request/response model
│   │   │       └── EmbedModel.java          # Embedding request model
│   │   └── resources/
│   │       ├── application.yml              # Application configuration (uses Testcontainers)
|   |       ├── init.sql                     # Vector store initialization script
|   |       ├── mcp-servers.json             # MCP server configuration
|   |       ├── qa.prompt                    # System Prompt
│   │       └── static/
│   │           ├── index.html               # Chat interface
│   │           └── embed.html               # Embedding interface
└── pom.xml                                  # Maven dependencies
```

## 🔍 Key Components

### ChatBotService
- Handles document embedding and chunking
- Manages semantic search queries
- Processes chat interactions with context

### Embedding Store
- Uses PostgreSQL with pgvector for vector storage
- Supports similarity search for context retrieval
- Handles large text document processing

### Frontend
- Modern, responsive design
- Real-time chat interface
- File upload and embedding management
- Navigation between chat and embed pages

## 🚨 Troubleshooting

### Common Issues

1. **OpenAI API Errors**
   - Verify your API key is correct
   - Check API key permissions
   - Ensure sufficient API credits

2. **Port Already in Use**
   - Change the port in `application.yml`
   - Or kill the process using the port

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- [Spring AI](https://docs.spring.io/spring-ai/reference/index.html) for the Spring AI framework
- [Awesome Spring AI](https://github.com/spring-ai-community/awesome-spring-ai) for Spring AI examples
- [OpenAI](https://openai.com/) for the AI models
- [pgvector](https://github.com/pgvector/pgvector) for PostgreSQL vector operations
- [Spring Boot](https://spring.io/projects/spring-boot) for the application framework 
