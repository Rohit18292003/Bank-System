docker run -d `
  --name bank-app `
  -p 8081:8081 `
  -e SPRING_PROFILES_ACTIVE=dev `
  -e DB_URL="jdbc:postgresql://host.docker.internal:5432/BankSystem" `
  -e DB_USERNAME="postgres" `
  -e DB_PASSWORD="root" `
  -e JWT_SECRET="LjUKbLecheh8I0XWzsfDrkdKVGfZB73Jx441RrmGaRr" `
  bank-sys:v2
