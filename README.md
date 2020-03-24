# MagicTask

[TOC]



## Проект

### База данных

В качестве БД я буду использовать MySQL. БД будет состоять из 2 таблиц: Users и Tasks. Всё это будет сделано через спрингRepo.

#### Состав Users

1. id AI, NN *
2. email
3. password (hashed)
4. firstName
5. secondName

#### Состав Tasks

1. idTask AI, NN, UNIQUE
2. idOwner AI, NN *
3. flag
4. dateCreation
5. dateCompletion 
6. Description 

## Инструкция по запуску





#### 