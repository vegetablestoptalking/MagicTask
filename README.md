# MagicTask

[TOC]



## Проект

### Необходимое ПО для запуска

1. *MySQL*
2. *Java 8+*

### Инструкция по запуска 

#Добавить после реализации 

### API

#### POST /api/tasks 

#### GET /api/tasks

#### GET "filter" /api/tasks

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