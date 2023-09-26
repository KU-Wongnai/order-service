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

## Technologies used

- [Redis](https://redis.io/) - Used to store cart items

## API

Service running at http://localhost:8094

Currently all routes are required authentication using JWT.

### Cart

Add item to cart

```sh
POST -> /cart
```

```json
{
  "menuId": 1,
  "quantity": 2
}
```

View cart items

```sh
GET -> /cart
```

Remove items from cart

```sh
DELETE -> /cart?menuId=1
```
