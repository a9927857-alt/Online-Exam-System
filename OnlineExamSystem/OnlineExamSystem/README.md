# Online Examination System

A comprehensive web-based online examination system built with Java Servlets, JSP, and MySQL.

## Features

✅ **Student Login** - Secure student authentication
✅ **Multiple Choice Questions** - Comprehensive question management
✅ **Exam Timer** - Auto-submit when time runs out
✅ **Automatic Result Calculation** - Instant score calculation
✅ **Result History** - Track student performance over time
✅ **Admin Panel** - Add and manage questions
✅ **Session Management** - Secure session handling
✅ **Responsive UI** - Beautiful and user-friendly interface

## Technologies Used

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java Servlets, JSP
- **Database**: MySQL
- **Server**: Apache Tomcat v11
- **IDE**: Eclipse

## Project Structure

```
OnlineExamSystem/
├── src/
│   └── com/exam/
│       ├── model/          (User, Question classes)
│       ├── servlet/        (Login, Exam, Result servlets)
│       ├── dao/            (Database access objects)
│       └── util/           (Database utilities)
├── WebContent/
│   ├── WEB-INF/
│   │   ├── web.xml         (Web application configuration)
│   │   └── lib/            (JAR files)
│   ├── JSP/                (JSP pages)
│   └── assets/
│       ├── css/            (Stylesheets)
│       └── js/             (JavaScript files)
├── database/
│   └── exam_system.sql     (Database schema & sample data)
└── .classpath, .project    (Eclipse configuration files)
```

## Setup Instructions

### 1. Database Setup

- Open MySQL Command Line or MySQL Workbench
- Execute the SQL script: `database/exam_system.sql`

```bash
mysql -u root -p < database/exam_system.sql
```

Or copy the contents and run in MySQL client.

### 2. Eclipse Project Setup

1. Open Eclipse IDE
2. Go to **File → Import → General → Existing Projects into Workspace**
3. Select the `OnlineExamSystem` folder
4. Click **Finish**

### 3. Configure Tomcat Server

1. Go to **Window → Preferences**
2. Search for "Server Runtime Environments"
3. Click **Add...**
4. Select **Apache Tomcat v11.0**
5. Browse and select your Tomcat installation directory
6. Click **Apply and Close**

### 4. Add Project to Tomcat

1. Right-click on the project in Eclipse
2. Go to **Properties**
3. Search for "Project Facets"
4. Click on the **Runtimes** tab
5. Check **Apache Tomcat v11.0**
6. Click **Apply and Close**

### 5. Configure Database Connection

Edit `src/com/exam/util/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/exam_system";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root";
```

Update these values according to your MySQL setup.

### 6. Add MySQL JDBC Driver

1. Download MySQL Connector/J (mysql-connector-java-X.X.XX.jar)
2. Place it in `WebContent/WEB-INF/lib/`

### 7. Run on Server

1. Right-click on the project
2. Go to **Run As → Run on Server**
3. Select **Apache Tomcat v11.0**
4. Click **Finish**

## Default Login Credentials

### Student Account
- **Username**: student1
- **Password**: password123

### Admin Account
- **Username**: admin1
- **Password**: admin123

## Application Features Explained

### Student Dashboard
- View available exams
- Start exam and answer questions
- Timer countdown with auto-submit
- View instant results after exam submission
- Check result history and performance

### Admin Panel
- Add new questions to exams
- Manage exam questions
- View student performance statistics (can be extended)

### Exam System
- 60-minute exam duration (configurable)
- 50 questions per exam (can be modified)
- Multiple choice format (A, B, C, D)
- Automatic answer evaluation
- Instant result calculation with percentage score

## Database Tables

### users
- user_id (Primary Key)
- username (Unique)
- password
- email
- role (student/admin)
- active (Boolean)

### exams
- exam_id (Primary Key)
- exam_name
- description
- duration_minutes
- total_questions
- passing_percentage

### questions
- question_id (Primary Key)
- exam_id (Foreign Key)
- question_text
- option_a, option_b, option_c, option_d
- correct_answer (A/B/C/D)

### results
- result_id (Primary Key)
- user_id (Foreign Key)
- exam_id (Foreign Key)
- total_questions
- correct_answers
- percentage
- submission_date

## Port Configuration

- Default Tomcat Port: **8080**
- Application URL: `http://localhost:8080/OnlineExamSystem/`

## Browser Compatibility

✅ Chrome
✅ Firefox
✅ Safari
✅ Edge
✅ Opera

## Troubleshooting

### Issue: Database Connection Error
- Check MySQL server is running
- Verify database credentials in DBConnection.java
- Ensure MySQL JDBC driver is in WEB-INF/lib/

### Issue: Page Not Found (404)
- Verify Tomcat is running
- Check project is deployed on server
- Clear browser cache and try again

### Issue: Timer Not Working
- Enable JavaScript in browser
- Check browser console for errors
- Verify exam.js file is loaded

### Issue: Exam Auto-Submit Not Working
- Check system time on your computer
- Verify JavaScript is enabled
- Check if form validation is blocking submission

## Future Enhancements

- Add user registration feature
- Implement question categories
- Add difficulty levels
- Create analytics dashboard
- Support for different question types
- Integration with email notifications
- Mobile app version
- Question bank import/export

## Security Notes

⚠️ **Important**: This is a demo application. For production use:
- Hash passwords using BCrypt or similar
- Implement proper input validation
- Use prepared statements (already done)
- Add CSRF protection
- Enable HTTPS
- Implement rate limiting
- Add admin authorization checks
- Sanitize user inputs

## Support & Contact

For issues or questions, please refer to the code comments or modify as per your requirements.

## License

Free to use for educational and commercial purposes.

---

**Created**: 2026
**Version**: 1.0
**Status**: Ready to Deploy
