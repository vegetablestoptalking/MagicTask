

# MagicTask

[TOC]



## Проект

### Необходимое ПО для запуска

1. *MySQL*
2. *Java 8+*
3. Intellij IDEA



### Инструкция по запуска 

#Добавить после реализации 

### API

Все запросы(/api/task/**) должны быть отправлены с basic auth. 

#### [POST] /api/tasks/

Метод добавляет задачу в список задач авторизованного пользователя.

```json
Method: POST
```

##### Параметры запроса

| Имя параметра |   Комментарий   | Тип параметра |  Тип   |
| :-----------: | :-------------: | :-----------: | :----: |
|   nameTask    |   Имя задачи    |   form-data   | String |
|  description  | Доп. информация |   form-data   | String |

------



#### [GET] /api/tasks/

Метод возвращает все задачи пользователя

```
Method: GET
```

##### Пример запроса

```http
localhost:8080/api/tasks/
```

##### Пример ответа

```json
[
    {
        "id": 2,
        "nameTask": "hellosome",
        "description": "wow",
        "changes": [
            {
                "id": 3,
                "dateUpdate": "2020-04-04",
                "description": "Created"
            }
        ]
    },
    {
        "id": 4,
        "nameTask": "newTask",
        "description": "Test",
        "changes": [
            {
                "id": 5,
                "dateUpdate": "2020-04-04",
                "description": "Created"
            }
        ]
    }
]
```

##### Выходные параметры

|     Имя параметра     |     Тип     |        Комментарий         |
| :-------------------: | :---------: | :------------------------: |
|          id           |    Long     |    Уникальный id задачи    |
|       nameTask        |   String    |         Имя задачи         |
|      description      |   String    |      Описание задачи       |
|     (changes) id      |    Long     |        id изменения        |
| (changes) dateUpdate  | dateUpdate  |   Дата изменения статуса   |
| (changes) description | description | Описание изменения статуса |

Changes.description имеет 3 возможных статуса: "Created", "Updated", "Completed"

Created – задача создана

Updated – задача обновлена

Completed – задача закрыта

------

#### [GET] /api/tasks/{id}

Метод возвращает задачу по id

```
Method: GET
```

##### Пример ответа

```json
{
    "id": 2,
    "nameTask": "hellosome",
    "description": "wow",
    "changes": [
        {
            "id": 3,
            "dateUpdate": "2020-04-04",
            "description": "Created"
        }
    ]
}
```

[Посмотреть таблицу с расшифровкой параметров](#####выходные-параметры) 

------

#### /api/tasks/filter/uncompleted

```
Method: GET
```

##### Пример ответа

```json
[
    {
        "id": 2,
        "nameTask": "hellosome",
        "description": "wow",
        "changes": [
            {
                "id": 3,
                "dateUpdate": "2020-04-04",
                "description": "Created"
            }
        ]
    },
    {
        "id": 4,
        "nameTask": "newTask",
        "description": "Test",
        "changes": [
            {
                "id": 5,
                "dateUpdate": "2020-04-04",
                "description": "Created"
            }
        ]
    }
]
```

------

#### /api/tasks/filter/completed

```
Method: GET
```

##### Пример ответа

```

```



------



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