#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>

const char* ssid = "XXX"; //WiFi网络名称
const char* password = "12345678"; //WiFi网络密码

ESP8266WebServer server(80);

#define LIGHTSENSORPIN A0 //Ambient light sensor reading

void setup() {  
    pinMode(LIGHTSENSORPIN, INPUT); 
    Serial.begin(115200);  
    
    //连接WiFi网络  
    WiFi.begin(ssid, password);  
    while (WiFi.status() != WL_CONNECTED) {   
         delay(1000);    
         Serial.println("Connecting to WiFi...");  
    }  
  Serial.println("Connected to WiFi");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP()); 

  server.on("/light", [](){
    float reading = analogRead(LIGHTSENSORPIN);
    String data = "Light: " + String(reading) + "lx"; 
    server.send(200, "text/plain", data);
  });

  server.begin();
  Serial.println("HTTP server started");
}

void loop(void) {
  server.handleClient();
} 