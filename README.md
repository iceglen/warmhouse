# Warmhouse

# Задание 1. Анализ и планирование

### 1. Описание функциональности монолитного приложения

- __Sensor Management__: Full CRUD operations for smart home sensors

  - Create, read, update, and delete temperature sensors
  - Track sensor values, status, and metadata

- __Real-time Temperature Data__: Integrates with an external temperature API service

  - Fetches real-time temperature data for specific locations (Living Room, Bedroom, Kitchen)
  - Updates sensor readings with live data from the external API


### 2. Анализ архитектуры монолитного приложения

## Technical Stack

- __Backend__: Go (Golang) with Gin web framework
- __Database__: PostgreSQL with pgx driver
- __Containerization__: Docker and Docker Compose
- __API Integration__: HTTP client for external temperature synchronous API calls

## Current Architecture

The application is currently structured as a __monolithic application__ with:

- Main application server on port 8080
- PostgreSQL database for sensor data storage
- External temperature API integration (expected on port 8081)
- Docker-based deployment

### 3. Определение доменов и границы контекстов

- __Sensor management domain__: Sensor management domain (registration, removing, listing)
- __Sensor telemetry domain__: Sensor telemetry data domain (collection, listing), including temperature

### **4. Проблемы монолитного решения**

Since the company is pretty small monolithic solution can reduce maintenance and R&D costs in the beginning. If business show tendency to growth it can be migrated into more scalable solution. The only drawback is that each newly bought sensor or smart device require manual installation.

### 5. Визуализация контекста системы — диаграмма С4

```markdown
[C4 context scheme](https://www.plantuml.com/plantuml/png/PPB1QW8n48RlUOgf9mi5BnvBKLJKGq5qUvPqTzH0abacishVlXEoujAU0ict__VFiBCabgKBthouMFkkGNXVJ2UBwN51KV0k7yQ84uzm-4c20VfhM3fxPXlWH0o7e1cmeO0mRrllQYkEeZ4xv4Hnt2NaDnXzwJ7ISwi1R6j9S7Do0RaWf1m0bnoGYaO8Wetsh1SO4t426nkuEho1FhSQ81qZD5RiwDMOKcvSFbR7e55vok9FYkHLwMgmpvfvWk9oyLmw39L3Mw3eX5Yvf_EukiiuGqVREfLK0tw-ssQl4iZHUitbgwiHMgQhQxBkxopmRHCsG14yzVM8-pwmNMRj7ds_qJ9jsMpAearLxIRxrQ7APcKp-e065TSewR_vn1GU8R1ULJ0P85aymzZa7-KN)
```

# Задание 2. Проектирование микросервисной архитектуры

**Диаграмма контейнеров (Containers)**

[C4 containers scheme](https://www.plantuml.com/plantuml/png/RLNDRXit4BxhAQO-j3BW8fSSYoA95RG1Oa2svFQue7P7584bgNA6TeIYxzwXTbRcUdKJkPplPtv6duXDuknx-DiaVuYP4Bx5BMnEnEZXFWOsBc22bJCs6R_uoOMspnt2R_SVFzn_R2ulVbyirfWeXgNfl0jtSACOpuJfHeuF9XYB11s-k1Rf3gZ5O9AB12Pqu6Dm79CSUanCDxUBnG3GV8avT2QTbg-ZH46x9lVc5lvT0C2RxEL0rdZhMOBAqzNv1WOr7WDhS2MnGAhCGHjXe9WWeNN4oR2Bug-DOUzirkFmZWrd6gsuOCN5L8z7Jgvjw4nP6C_dIi9zx7jiUU0zfYWI20cIUHrL310qWnxJ-GQ-qPiA_kiFR3CBu2wJH1517wrhgrhi3647COnqdM4puphNIFozSNtMcuhh3vIdXb7hgxazXrOqV4X8XzXtcceLClEtscdogEbsbymHh4HV0IkCM-UHMc5OVId73KV11fRRNmcJGmA5W7sANXrUOzAs4QAwQQPawqXi4suUlrTSdNl1yj25VKp-t1h5rhHPtXC-PimexeYCnUQVSYe6XpESlnQA9xFRENvuBB__5D2-P9DEeHLqxtDmxS2tGtv53ENs60C6BWx_kupJrn-yT94RRV1t3d-6ZMP5FlnLAf5fJ1Gi9VvOQfgJA3naxph7fplmiH3V5gVrx0XjnLdyOR2OYZVzFQMvl2ZvOxHfyFDv_QriWoVic-UfylSf4-aTAJimzbZAVQf7Cn-xqf7d56wtwys7XyVjLj05lDuLS-XrtlME3aZrN02-Q7ebVZhRSmGfDsMliro1N8-UzBesbx4seJMCdNvMvldelrypJsYwI_CFqlT2FyO1bhMWhNaLS3xms-IinJGVw7g2hsBLUQvnsYA0149Vl44w_eM0JqUSh0Z5rx-R2dNsPpCRlofklZPNk7UXMf6L8st3xzDUgRQ5PleQRLNivcc-GUlnA7qityRKSeHCcCggZDaUe7FxFIOCN4sC0Z_NOwOEBt0XijlhbhYW5a-Vv47s_Vy0)

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

# TODO

# Задание 5. Работа с docker и docker-compose

Inside "apps" directory each application contains "Dockerfile" and "docker-compose.yml" files. The latter ones used only for development purposes. THe main one resides in the "apps" directory.

# **Задание 6. Разработка MVP**

"apps" directory contains entire set of applications. All of them interacts with each other (REST API and events through queue).