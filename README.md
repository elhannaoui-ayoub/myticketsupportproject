
# Ticket Support Project
This project consists of a Spring Boot backend (spring-app), an Oracle database (oracle-db), and a Swing-based UI (ticketsupportui). Follow the steps below to set up and run the application.

# Prerequisites
Docker: Installed and running (Docker Desktop recommended for Windows).
Java: JDK 17 installed (verify with java -version).
Terminal/PowerShell: For running commands.

# Setup Instructions
# Step 1;
Clone the repository or download the project ZIP file to your local machine.

# Step 2;
open Terminal/PowerShell and navigate to the project directory;

# Step 3;
run " docker-compose up oracle-db "
Monitor the logs to ensure the database is fully initialized:
Monitor the database container logs. Proceed to step 2 once the message 'DATABASE IS READY TO USE' appears

# Step 3;
run " docker-compose up spring-app "

# Step 4;
run " java -jar .\ticketsupportui\target\ticketsupportui-1.0-SNAPSHOT.jar "
