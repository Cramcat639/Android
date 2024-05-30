# Android API CRUD + Arduino Relay Control

## Descripción

Este repositorio contiene una aplicación Android que implementa un sistema CRUD (Crear, Leer, Actualizar, Eliminar) y se conecta a una placa Arduino para controlar un relé. 
La aplicación permite al usuario gestionar una base de datos de elementos y, a través de la conexión con Arduino, activar y desactivar un relé.

## Características

- **CRUD Completo**: Permite crear, leer, actualizar y eliminar elementos en la base de datos.
- **Conexión Arduino**: Utiliza una placa Arduino para controlar un relé.
- **Interfaz de Usuario Intuitiva**: Diseñada para ser fácil de usar y navegar.

## Requisitos

- Android Studio
- Placa Arduino (ESP8266)
- Módulo Relé
- Cable USB para la conexión entre Android y Arduino
- IDE Arduino para cargar el código en la placa

## Instalación

1. **Clonar el repositorio**:

    ```sh
    git clone https://github.com/Cramcat639/Android-CRUD/tree/ApiRele
    ```

2. **Abrir el proyecto en Android Studio**:

    - Selecciona `File > Open` y navega hasta la carpeta del proyecto clonado.

3. **Configurar la conexión con Arduino**:

    - Asegúrate de que la placa Arduino esté correctamente conectada al dispositivo Android.
    - Cargar el código en la placa Arduino utilizando el IDE de Arduino (ver sección Arduino).

4. **Construir y ejecutar la aplicación en un dispositivo Android**:

    - Conecta tu dispositivo Android y asegúrate de que la depuración USB esté habilitada.
    - Selecciona `Run > Run 'app'` en Android Studio.

## Uso

1. **CRUD**:

    - **Crear**: Agrega nuevos elementos a la base de datos.
    - **Leer**: Visualiza los elementos almacenados.
    - **Actualizar**: Modifica los elementos existentes.
    - **Eliminar**: Borra los elementos no deseados.

2. **Control del Relé**:

    - Usa la interfaz de la aplicación para enviar comandos a la placa Arduino y activar o desactivar el relé.

## Licencia

- Este proyecto está licenciado bajo la Licencia MIT. Ver el archivo LICENSE para más detalles.
