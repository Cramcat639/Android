#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#define RELAY_PIN 5
ESP8266WebServer server(80);

char ssid[] = "Docent";          //SSID de la red local.
char pass[] = "D4rthV4der+";        //Password.


// FUNCIONA A 3V

// Mirar la IP en Monitor Serie en mi caso http://192.168.2.54/ + 1 o 0.

void setup() 
{ 
       pinMode(RELAY_PIN, OUTPUT);
       digitalWrite(RELAY_PIN, HIGH);    //Defino el interuptor en apagado.
       Serial.begin(9600);
       WiFi.begin(ssid, pass);                     
       while (WiFi.status() != WL_CONNECTED) {       //Espera la conexión
             delay(500);
             Serial.println("Esperando conexión…");}
       Serial.print("Dirección IP: ");
       Serial.println(WiFi.localIP());                 //Envía através del Monitor Serial la IP Local
  
       server.on("/1", []()                              //Función administradora de /1
       {
         digitalWrite(RELAY_PIN, LOW);
         server.send(200, "text/plain", "RELE ENCENDIDO");
       });
  
       server.on("/0", []()                                            
       {
         digitalWrite(RELAY_PIN, HIGH);
         server.send(200, "text/plain", "RELE APAGADO");                  
       });

       server.begin();                                   
       Serial.println("Servidor escuchando...");
}

void loop()
{
  server.handleClient();                   //Manejo de los requests
}        