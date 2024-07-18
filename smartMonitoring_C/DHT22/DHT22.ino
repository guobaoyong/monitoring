#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <DHT.h>
#define DHTTYPE DHT22
#define DHTPIN  2

const char* ssid     = "XXX";
const char* password = "12345678";

ESP8266WebServer server(80);

DHT dht(DHTPIN, DHTTYPE, 11);
 
float humidity, temp_f;
String webString="";

unsigned long previousMillis = 0;
const long interval = 2000;
 
void handle_root() {
  server.send(200, "text/plain", "ESP8266工作正常");
  delay(100);
}
 
void setup(void) {
  Serial.begin(115200);
  dht.begin();
  
  WiFi.begin(ssid, password);
  Serial.print("\n\r \n\rWorking to connect");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("DHT Weather Reading Server");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
   
  server.on("/", handle_root);
  
  server.on("/temp", [](){
    gettemperature();
    webString="Temperature: "+String((float)(temp_f-32)/1.8)+"C";
    server.send(200, "text/plain", webString);
  });

  server.on("/humidity", [](){
    gettemperature();
    webString="Humidity: "+String((float)humidity)+"%";
    server.send(200, "text/plain", webString);
  });
  
  server.begin();
  Serial.println("HTTP server started");
}
 
void loop(void) {
  server.handleClient();
} 

void gettemperature() {
  unsigned long currentMillis = millis();
 
  if(currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    humidity = dht.readHumidity();
    temp_f = dht.readTemperature(true);
    if (isnan(humidity) || isnan(temp_f)) {
      Serial.println("Failed to read from DHT sensor!");
      return;
    }
  }
}
