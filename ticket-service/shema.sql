SELECT
    t.id AS ticket_id,
    r.id AS route_id,
    r.origin AS route_origin,
    r.destination AS route_destination,
    c.id AS carrier_id,
    c.name AS carrier_name,
    c.phone_number AS carrier_phone_number,
    r.duration AS route_duration,
    t.departure_time AS ticket_departure_time,
    t.seat_number AS ticket_seat_number,
    t.price AS ticket_price,
    t.status AS ticket_status
FROM ticket AS t
         JOIN route AS r ON t.route_id = r.id
         JOIN carrier AS c ON r.carrier_id = c.id
WHERE 1=1
  AND t.status='AVAILABLE' ORDER BY t.departure_time LIMIT 2 OFFSET 0