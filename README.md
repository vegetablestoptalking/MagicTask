# MagicTask

[TOC]



## Проект

### Необходимое ПО для запуска

1. *MySQL*
2. *Java 8+*

### Инструкция по запуска 

#Добавить после реализации 

### API

#### /api/tasks/add

Метод добавляет задачу в список задач авторизованного пользователя.

```json
Method: POST
```

**Request Example**

```json
{
    
}
```

------



#### /api/tasks/

```
Method: GET
```

**Request Example**

```http
localhost:8080/api/tasks/
```

**Response Example**

```json
[
    {
        "id": 4,
        "nameTask": "Lookdsyer",
        "flag": false,
        "dateCreation": "2020-03-28",
        "dateCompletion": null,
        "description": "A description"
    },
    {
        "id": 5,
        "nameTask": "Saydsyer",
        "flag": false,
        "dateCreation": "2020-03-28",
        "dateCompletion": null,
        "description": "A description"
    }
]
```

------



#### /api/tasks/filter

```
Method: POST
```

Request Example

```json
{
    
}
```



### База данных

В качестве БД я буду использовать MySQL. БД будет состоять из 2 таблиц: Users и Tasks. Всё это будет сделано через спрингRepo.

#### Состав Users

1. id AI, NN *
2. email
3. password (hashed)
4. firstName
5. secondName

#### Состав Tasks

1. id AI, NN, UNIQUE
2. flag
3. dateCreation
4. dateCompletion 
5. Description 







#### 