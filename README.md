# Setup and execution
Download the repository. In the root folder run:  
docker-compose up -d  
The frontend, backend, and database all launch and interconnect using this command. 
To access the frontend use port 5173. For backend access use port 8080. The database uses port 27017.  
To access the frontend, go to:  
http://localhost:5173/  
# Technology Choices  
- Java 17
- Maven for spring-boot compilation
- Npm for react compilation
- Mongodb for database
- Default Java PasswordEncoder class for password hashing
- JWT authentication
- Default ports for each application used
- Docker containers for frontend, backend, and database
# Assumptions made
- Instead of successful login message, automatically navigates to dashboard page displaying user's full name and email address
- Password visibility toggle button during account registration
- Email address format validation during account registration
- Emails addresses are stored and validated as entirely lowercase, due to capitalization having no distinguishment in modern email addressing
- First and last name fields capitalize first letters and lower case remainders of names during account registration
- Fields are trimmed during account registration
