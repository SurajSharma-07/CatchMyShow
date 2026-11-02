# CatchMyShow — Movie Ticket Booking System

A full-stack movie ticket booking application built with Java 21, Spring Boot, Spring Data JPA, H2, and a responsive HTML/CSS/JavaScript frontend. The UI is served from the same Spring Boot application, so no separate frontend server is required.

## Features

- Browse, search, and view now-showing movies
- View showtimes with screen and ticket price
- Live seat map with booked-seat protection
- Customer checkout with validation
- Dummy payment simulation for UPI, card, and net banking
- Booking confirmation code and booking lookup
- Seeded demo data on first run
- H2 database and H2 console for easy development
- Responsive, polished frontend with no frontend build step

## Run locally

Prerequisite: **Java 21+** only. Maven is bundled via the Maven Wrapper (downloaded automatically on first run).

```bash
# Windows
run.bat            (or: mvnw.cmd spring-boot:run)

# Mac / Linux
./run.sh           (or: ./mvnw spring-boot:run)
```

Open [http://localhost:8080](http://localhost:8080).

To build and run the packaged JAR:

```bash
mvn clean package
java -jar target/movie-ticket-booking-1.0.0.jar
```

The H2 console is available at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) with JDBC URL `jdbc:h2:file:./data/movie_booking_db`, username `sa`, and an empty password.

## API endpoints

- `GET /api/movies?search=` — list/search movies
- `GET /api/movies/{id}/showtimes` — list movie showtimes
- `GET /api/showtimes/{id}/seats` — get booked seats and seat grid metadata
- `POST /api/bookings` — create a booking
- `GET /api/bookings/{bookingCode}` — look up a booking
- `GET /api/bookings/lookup?email=` — find the latest booking for an email

Example booking payload:

```json
{
  "showtimeId": 1,
  "customerName": "Alex Johnson",
  "customerEmail": "alex@example.com",
  "seats": ["C3", "C4"],
  "paymentMethod": "UPI"
}
```

## Project structure

`src/main/java` contains the domain models, repositories, services, controllers, and seed data. `src/main/resources/static` contains the frontend. Runtime H2 data is written to `./data` and is ignored by Git.

## Production notes

The current payment flow is intentionally a dummy simulation for project demonstration. For production, replace H2 with PostgreSQL/MySQL, add authentication and role-based administration, integrate Razorpay/Stripe/PayU with server-side signature verification and webhooks, store posters in object storage, add email delivery, and configure CORS/security headers as appropriate.

## Authentication and roles

The application uses Spring Security session authentication with two roles:

- `CUSTOMER` — can register, sign in, select seats, make demo-payment bookings, and look up bookings.
- `ADMIN` — can sign in and access the protected admin dashboard with movie, showtime, booking, and customer metrics.

Demo accounts:

```text
Admin:    admin@catchmyshow.com    / admin123
Customer: customer@reelseat.com    / customer123
```

Authentication endpoints:

- `POST /api/auth/register` — create a customer account
- `POST /api/auth/login` — start a session
- `GET /api/auth/me` — inspect the current session
- `POST /api/auth/logout` — end the current session
- `GET /api/admin/dashboard` — admin-only dashboard metrics

## Seating UI

The seat selector uses an original cinema-auditorium layout inspired by modern cinema booking experiences. It includes a curved screen indicator, premium and classic seating zones, row labels, aisle separation, live booked-seat state, selected-seat state, seat count, and live total price calculation. It does not copy PVR source code, branding, or proprietary assets.

## Admin Management Console

Sign in as `admin@catchmyshow.com` and click **Admin** in the navigation. The console provides:

- Movie create, edit, and archive actions
- Showtime create, edit, and archive actions
- Screen, date/time, format, and pricing controls
- Movie, showtime, booking, and customer metrics
- Pricing preview for Classic, Premium, and Recliner seats

Movie and showtime deletion is implemented as a safe archive operation. Archived records are hidden from customers but retained so historical bookings are not corrupted.

Admin endpoints:

- `GET /api/admin/movies`
- `POST /api/admin/movies`
- `PUT /api/admin/movies/{id}`
- `DELETE /api/admin/movies/{id}`
- `GET /api/admin/showtimes`
- `POST /api/admin/showtimes`
- `PUT /api/admin/showtimes/{id}`
- `DELETE /api/admin/showtimes/{id}`

## Seat-category pricing

Each showtime supports three seat categories:

- Rows A-B: Premium
- Rows C-F: Classic
- Rows G-H: Recliner

The final price is calculated per seat:

```text
seat price = category price + format surcharge
booking total = sum of every selected seat price
```

For example, if a 3D showtime has a ₹40 surcharge and the selected seats are Premium ₹300, Classic ₹200, and Recliner ₹450, the total is:

```text
(300 + 40) + (200 + 40) + (450 + 40) = ₹1,070
```
