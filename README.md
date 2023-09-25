# Order Service - KU Wongnai

> Contain only basic features, lot of things are missing

## Prerequisites

Copy `.env.example` to `.env`

```sh
cp .env.example .env
```

Set environment variables in `.env` file, which should come from user-service

```env
JWT_SECRET=
```

Run mysql and redis

```sh
docker-compose up -d
```

Start the server

```sh
mvn spring-boot:run
```

## API

service running at http://localhost:8094

### Cart

Add item to cart

```sh
POST -> /cart?userId=1
```

```json
{
  "menuId": 1,
  "quantity": 2
}
```

View cart items

```sh
GET -> /cart?userId=1
```
