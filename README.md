# QuickMail-Backend
Helper application for sending emails with .hbs templates.
Este `README.md` proporciona una guía clara y detallada para levantar la aplicación utilizando el método de `dotenv` en PowerShell.

# Guía para Levantar la Aplicación Spring Boot

Este documento describe los pasos necesarios para levantar una aplicación Spring Boot en Windows utilizando variables de entorno definidas en un archivo `.env`.

## Requisitos Previos

- **PowerShell**: Asegúrate de tener PowerShell instalado.
- **Node.js** (opcional): Necesario si decides instalar `dotenv` con `npm`.

## Paso 1: Instalar Dotenv para PowerShell

Para cargar correctamente las variables de entorno desde un archivo `.env`, es necesario instalar la biblioteca `dotenv` para PowerShell.

### Instalación con npm

1. Abre PowerShell como Administrador.
2. Ejecuta el siguiente comando para instalar `dotenv`:

    ```powershell
    npm install -g dotenv-cli
    ```

## Paso 2: Crear el Archivo `.env`

Crea un archivo `.env` en el mismo directorio donde se encuentra tu archivo `.jar`. Define en él las variables de entorno necesarias. Un ejemplo podría ser:

```plaintext
PORT = '8080'
MAIL_HOST = 'smtp.example.com'
MAIL_PORT = '587'
MAIL_USERNAME = 'testing_mail@example.com'
MAIL_PASSWORD = 'Examp13P42w00rd'
```

## Paso 3: Ejecutar via PowerShell

# Cargar variables desde el archivo .env
dotenv -- "java -jar QuickMail-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev"



