# ResumeHub

ResumeHub is a web application designed to help users create, manage, and showcase their resumes. It provides a user-friendly interface for users to input their personal information, work experience, skills, education, and references, and then generates a visually appealing resume format. The application consists of a Spring Boot REST API backend and a React.js frontend.

## Features

- **User Authentication:** Users can sign up and log in to their accounts securely.
- **Resume Creation:** Users can input their personal information, work experience, skills, education, and references to create their resumes.
- **Resume Editing:** Users can edit and update their resumes as needed.
- **Resume Display:** Resumes are displayed in a professional format, making it easy for users to showcase their qualifications.
- **Responsive Design:** The frontend interface is designed to be responsive and accessible across different devices and screen sizes.

## Technologies Used

- **Spring Boot:** Powers the backend REST API for handling user data and resume generation.
- **React.js:** Provides the frontend interface for users to interact with the application and view their resumes.
- **MongoDB:** Stores user data and resumes in a NoSQL database.
- **JWT (JSON Web Tokens):** Used for user authentication and authorization.
- **Bootstrap:** Provides CSS styling and layout components for the frontend.

## Getting Started

To run the application locally, follow these steps:

1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd ResumeHub`
3. Set up the backend:
   - Navigate to the `backend` directory: `cd backend`
   - Configure MongoDB connection in `application.properties` file.
   - Run the Spring Boot application: `./mvnw spring-boot:run`
4. Set up the frontend:
   - Navigate to the `frontend` directory: `cd frontend`
   - Install dependencies: `npm install`
   - Start the React development server: `npm start`
5. Access the application in your web browser at `http://localhost:3000`.

## Contributing

Contributions are welcome! If you'd like to contribute to ResumeHub, please follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
