# Warmhouse

# Задание 1. Анализ и планирование

### 1. Описание функциональности монолитного приложения

- __Sensor Management__: Полный набор операций CRUD для датчиков умного дома

  - Создание, чтение, обновление и удаление датчиков температуры
  - Отслеживание значений датчиков, статуса и метаданных

- __Real-time Temperature Data__: Интеграция с внешним сервисом API температуры

  - Получение данных о температуре в реальном времени для конкретных мест (Гостиная, Спальня, Кухня)
  - Обновление показаний датчиков живыми данными из внешнего API

### 2. Анализ архитектуры монолитного приложения

## Technical Stack

- __Backend__: Go (Golang) с веб-фреймворком Gin
- __Database__: PostgreSQL с драйвером pgx
- __Containerization__: Docker и Docker Compose
- __API Integration__: HTTP-клиент для синхронных вызовов внешнего API температуры

## Current Architecture

Приложение в настоящее время структурировано как __монолитное приложение__ с:

- Основным сервером приложения на порту 8080
- Базой данных PostgreSQL для хранения данных датчиков
- Интеграцией с внешним API температуры (ожидается на порту 8081)
- Развертыванием на основе Docker

### 3. Определение доменов и границы контекстов

- __Sensor management domain__: Домен управления датчиками (регистрация, удаление, перечисление)
- __Sensor telemetry domain__: Домен телеметрических данных датчиков (сбор, перечисление), включая температуру

### **4. Проблемы монолитного решения**

Поскольку компания довольно маленькая, монолитное решение может снизить затраты на обслуживание и разработку MVP в начале. Если бизнес покажет тенденцию к росту, его можно будет перенести в более масштабируемое решение. Единственным недостатком является то, что каждый вновь приобретенный датчик или умное устройство требует ручной установки.

### 5. Визуализация контекста системы — диаграмма С4


[C4 context scheme](https://www.plantuml.com/plantuml/png/PPB1QW8n48RlUOgf9mi5BnvBKLJKGq5qUvPqTzH0abacishVlXEoujAU0ict__VFiBCabgKBthouMFkkGNXVJ2UBwN51KV0k7yQ84uzm-4c20VfhM3fxPXlWH0o7e1cmeO0mRrllQYkEeZ4xv4Hnt2NaDnXzwJ7ISwi1R6j9S7Do0RaWf1m0bnoGYaO8Wetsh1SO4t426nkuEho1FhSQ81qZD5RiwDMOKcvSFbR7e55vok9FYkHLwMgmpvfvWk9oyLmw39L3Mw3eX5Yvf_EukiiuGqVREfLK0tw-ssQl4iZHUitbgwiHMgQhQxBkxopmRHCsG14yzVM8-pwmNMRj7ds_qJ9jsMpAearLxIRxrQ7APcKp-e065TSewR_vn1GU8R1ULJ0P85aymzZa7-KN)


# Задание 2. Проектирование микросервисной архитектуры

**Диаграмма контейнеров (Containers)**

[C4 containers scheme](https://www.plantuml.com/plantuml/png/VLN1ZY8t4Btp5HKE2IDjj9SzHj7k3glDI3iICp39iMMw2sEfRHDNUHWKvT_ZQcCmD9CRDbNllNgkglvCh097syDVAjYjZuJmu5UmF12ZXNllM1c70UP6wQ3iv2VZsZvs2B_TV_fu_wavHVm-cImma7SprLdZFi3qYF526ARf-AYSqaZGuQjfaJu0jUXKC9v0kGwiTuPzI8SU0zFqRZB90CrN7rsdmc6sBn8JsYsvqpluPm80PzcpJDPORJabIUXyk86ingBZabn9bBYScRKHEl810cf371GRd-fhlTiO7SjnY6F5aKefnkdftPKUjJEDLenxTKXyNnOFy3sVHf4MEPYseK6SQ1lEbTXxt_VOSbQu2pw99IG8Qb_qPWWQGHVZp-3Buqq5_-qDsyW9S1qfPH51xxLfgrTRAy8El2jqdM8ruXfUCpbnvlfolAculcCALOp7JZY-mf1QqV0s86rzt-Mmav1hpldwQ1urtVha7C4ywHCmOLmPYzGcXlbNEIxPXyJ8wVPNmc2GYdcm2TuUAxp5bDj4YEhskYHRU68TSFdqe-Bgp2jAe74R7-pGH5BMPLjT4pv5Z9Yg8r8Qcx_b90NcCmp_2iMpMgyDFpx9xpyIQ2zkqi6r2TtQw4oR-TR8UqGdjpllq0rZz-zfyBwzySnuRih4NbRuCoopAyBofxn4f68Kp5B-JjuqXgHm6wtf3AUMjrw8xwJIQig4jkAK-j1f359R-Nr1SueG_nXroFMaUPAzyOnzytAf_Dgo9BqhgmWOUvJdFjH377UTTEHWuMgrM7vyV5gj4de2hxVA67eHrxsXBL8z5u2lOc_Elvhj4K9wReespt8DSZkxxlNIN4hhWB9dkl9tpXsF_lMQUKRLKKdAqZU9lkG1obeGizy370_yAXYjCOm7kkI-AwxskSPfHG09H8uuGnh-XO0FExnO4Hc_V9YkTd0hYrFsKMQLDUEZtaAzd7D-bNYjRH7ysq508c584WAcnoHEMdpHQCzVdCnq2tLamVyYb-WJOExz7vSDMg-e_BpvvdBLL1tMF8_NTgqoZuzeah0iHTJTiWxYuEthQ73FL3LoFVeFnyb6WhtXBNHciy60ZiSeB_NEoCzy5kaycyrnvPsqYbUVKq2q_Ny0)

**Диаграмма компонентов (Components)**

[C4 components scheme](https://www.plantuml.com/plantuml/png/VLNBRjiw4DtxAtYzk7M00jrasHH9xG0D41UEbQu5Mfh81CIXoncv2OhyU_cWhOUiU6DnDEVCOmv7jyJISgFhsNyAYxefGNnTNdzPNkTBemy60VdRRFP9tDpSY9LakPC492IMulwLmQAiHVP639gyosnfaAL2iAlTeeIZAY0lTv_5_C4yYrKuqvbcxkmRGrnPo9uUvrSj-Ac11XOQY6G5-Mz_ShxhU1RXhGTlvMwdUFtae367tEMqK8Rp69e6WVyN6I0PIpr0Y0DOWKqnmhFtsRW_WmjPQeNkNOI8NmJMlKmzISRlfi5IMWMn3OazMTzEhT2LvbvEOYrHLg01EJ7DhyJVcN2_S_jFO7bG3fjms_liMTnj7dmRxbyFnWyb-WdTKWAM1wEGNJ6NEGci_szvisZmj3yabdLBQw5In5QoCXZcNnXyKLKJBPC1M7937Vvm5ck3YetrOTBZESuUPCtx4262fwXbuGEtr7VX_4454U3v0k4Mwb4RkfBDsDWMN4gM8sGQof3bezOvZccAjgOjEDKJKqfQm2iKpGG-OORNvTcgoWkvhNkox25qq-ngHNiW0KT07a7RkKuZe-EOO80OwcHfa1fz0Vy-6zqgMODbXLLEuOL3toNBz6KAcD3pvDsxIXjhZge4ycVnOgmuXHY940sh5rM4cNOc_pEPfwGO-4G9jJg2VHkojgieBshb3DsD99R1vDaUaA6oaXtX7yLx0QTLVCPtLTwjUIBjLYlUjJkkou0BUQuK7IGNVlHi-ecyfsNPVZ9y0JRfCYnC5wgxkCV2oW1BnmmrQE3GkGjYwg_-HqKCI66EXT5QEeQ4IokzjrdZAgPafBZY1u8IgfDuO1ftucmvPztjuIc3ZjVf-xAAgaX326riuhVcrj4qklu7)

**Диаграмма кода (Code)**

[Code scheme](https://www.plantuml.com/plantuml/png/TOy_JyGm3CLtVugCW7Jt2HYkkedXMFZ3xAOcPobDAjizWO3lJjii5n6oHF5x_hsNWngApMC4m5vDq9lp4LNTYLGf1P9kOlS5RZcNp8DRXiFnZDPvuvnkOax1pKhoCDpMfXM0WMh73q31FsRZT_QuGfv8BkoffDJ1EF4sx7EEX6a3Nw_-G-yYYN4AnvnCSbnU1TwCrN9NEMdrVRuiqf-4PueK1CUrtlttVbyLXhSpcdRJr5PVkITfIoVyQ0lGwdotspK-2TUBcwUn2t2WDCnZ_0K0)

# Задание 3. Разработка ER-диаграммы

[ER scheme](https://www.plantuml.com/plantuml/png/ZLBBJiCm4BplLrYzzy0Fe5QKGmzmAUNkEjjgaH-LlQbK8VwTO2UAko6576BBExEp7iygiOxSEWlXy866rGOlPF3jUaRr2QhxRF0dHGrytI14DUyhAdS_oNWA_aYdDcgcuAVqlC_4xKHinvhRDCTfCcPRJX97wivIPbIOwGXPPj1PctjJ9ODUHmfRgPflAvHDf3EFDzZrNDbxP7ZjSAor2MFVyH22HUqLfMUl3nOhNzOM8y-zq6jWEfAvQxJ-QJ3HsQ2b2yPQQPymJeI6uWjo9BFtn7CwBYDkBIfdxzgsvTvC3XDhTrOcecPirfL_HU9VRs_3u6OtOH_JnSD2BPUFUHq32hz4SeGwxQy36ke4ocwonLzWWEoc41eI0iD60aeueIoYM980-IzBTGu0Yf7rLDNpzPFB3WK0et4pewtGDwsptm00)

# Задание 4. Создание и документирование API

### 1. Тип API

Синхронный REST API для взаимодействия микросервисов между собой и монолитом. Для межсервисного взаимодействия во время отказа от легаси будет достаточно. Обработка данных от датчиков и команд от или к устройствам происходит через очередь на основе RabbitMQ.

### 2. Документация API

[metrics OpenAPI](https://github.com/iceglen/warmhouse/blob/warmhouse/apps/metrics/openapi.yaml)

[metrics AsyncAPI](https://github.com/iceglen/warmhouse/blob/warmhouse/apps/metrics/asyncapi.yaml)

[device-management AsyncAPI](https://github.com/iceglen/warmhouse/blob/warmhouse/apps/device-management/asyncapi.yaml)

# Задание 5. Работа с docker и docker-compose

Внутри каталога "apps" каждое приложение содержит файлы "Dockerfile" и "docker-compose.yml". Последние используются только для целей разработки. Основной файл находится в каталоге "apps".

# **Задание 6. Разработка MVP**

Каталог "apps" содержит полный набор приложений. Все они взаимодействуют друг с другом (REST API и события через очередь).
