# Ticket Order

**Ticket Order** – это REST API сервис для обработки заявок на покупку билетов.

---

## Запуск проекта

1. **Склонируйте репозиторий**  
   ```sh
   git clone https://github.com/Almazov-Artyom/TicketOrder.git
   ```

2. **Запустите сервис с помощью Docker Compose**  
   ```sh
   docker-compose up --build
   ```

3. **При запуске приложения автоматически создается пользователь с ролью "Администратор"**  
   ```sh
   ADMIN_EMAIL=admin@admin.com
   ADMIN_PASSWORD=password
   ```
4. **Swagger документация**  
   Swagger UI будет доступен по адресу:  
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
