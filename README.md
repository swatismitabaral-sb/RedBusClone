﻿# RedBusClone

### Exposed API End-Points
#### Operator
1. Post `/api/bus-operator` : This endpoint saves the details of the passenger

#### User
1. Get `/api/user/searchBuses`: This endpoint retrieves the buses available in between two cities on provided date

#### Booking
1. Post `/api/bookings/`: This takes in busId and ticketId from user and book a bus and demand for payment, then confirms the same through email.
