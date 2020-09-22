/**************************************************************
 *
 **************************************************************
 * Este projeto utiliza a biblioteca SimpleTimer:
 *   https://github.com/jfturcot/SimpleTimer
 * e a bilbioteca Adafruit DHT sensor:
 *   https://github.com/adafruit/DHT-sensor-library
 *
 **************************************************************/

#include <ArduinoJson.h>
#include <SPI.h>
#include <ESP8266WiFi.h>
#include <SimpleTimer.h>
#include <DHT.h>
#include <ESP8266HTTPClient.h>

// SSID e credenciais da rede WIFI.
//char ssid[] = "SUA_REDE";  //Informe o nome da rede WIFI
//char pass[] = "PASSWORD";  //Informe a senha da rede WIFI
char ssid[] = "AP901_2G_N";  //Informe o nome da rede WIFI
char pass[] = "Cati62_WIRELESS_901";  //Informe a senha da rede WIFI

//Digital 2
const int releUmidificador = D2;
//Digital 3
const int releArCondicionado = D3;
//Digital 4
const int DHTPIN = D4;

// Remova o comentário do tipo de sensor utilizado
#define DHTTYPE DHT11     // DHT 11
//#define DHTTYPE DHT22   // DHT 22, AM2302, AM2321
//#define DHTTYPE DHT21   // DHT 21, AM2301

//Iniciar sensor
DHT dht(DHTPIN, DHTTYPE);
//Timer
SimpleTimer timer;
//Receberá o estado do umidificador e do ar-condicionado (ON, OFF, NONE)
String estadoUmidificador, estadoArCondicionado;

//Esta função irá ler as informações de umidade e temperatura
//e enviá-las para uma aplicação web através de uma API Restful
void enviarInormacoesSensor() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  //Checa se as informações de umidade e temperatura foram obtidas
  if (isnan(h) || isnan(t)) {
    Serial.println("Não foi possível obter as informações de umidade e temperatura do sensor DHT!");
    return;
  }
  //Checa se está conectado no WIFI
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    //Substituir pelo ip/url da aplicação web
    http.begin("http://192.168.0.18:8080/public/v1/temperaturaUmidade");
    http.addHeader("Content-Type", "application/json");
    String body = "{\"temperatura\":\""+ String(t) +"\", \"umidade\":\""+ String(h) +"\"}";

    Serial.println(body);
    
    int httpCode = http.POST(body);
    if (httpCode == -1) {
      Serial.println("Não foi possível enviar as informações para a aplicação web... Status: " + httpCode);
    }
    //Encerra a conexão
    http.end();
  }
}
//Esta função irá obter as informações
//que indicarão quais equipamentos
//devem ser ligados/desligados
void obterEstadoEquipamentos() {
  //Checa se está conectado no WIFI
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    //Substituir pelo ip/url da aplicação web
    http.begin("http://192.168.0.18:8080/public/v1/estadoAparelhos");
    http.addHeader("Content-Type", "application/json");
    int httpCode = http.GET();
    //Status OK
    if (httpCode == 200) {
      StaticJsonDocument<200> doc;
      deserializeJson(doc, http.getString());
 
      const char * estadoUmidificadorChar = doc["estadoUmidificador"]; 
      const char * estadoArCondicionadoChar = doc["estadoArCondicionado"]; 
      estadoUmidificador = String(estadoUmidificadorChar);
      estadoArCondicionado = String(estadoArCondicionadoChar);

      //Se NONE, não altera o estado do umidificador
      if (!estadoUmidificador.equals("NONE")) {
        if (estadoUmidificador.equals("ON")) {
          Serial.println("Umidificador ligado");
          digitalWrite(releUmidificador, LOW);
        } else {
          Serial.println("Umidificador desligado");
          digitalWrite(releUmidificador, HIGH);
        }
      }
      //Se NONE, não altera o estado do ar-condicionado
      if (!estadoArCondicionado.equals("NONE")) {
        if (estadoArCondicionado.equals("ON")) {
          Serial.println("Ar-condicionado ligado");
          digitalWrite(releArCondicionado, LOW);
        } else {
          Serial.println("Ar-condicionado desligado");
          digitalWrite(releArCondicionado, HIGH);
        }
      }
    }
    http.end();
  }
}

void setup() {
  Serial.begin(9600);

  //Inicia equipamentos desligados
  estadoUmidificador = "OFF";
  estadoArCondicionado = "OFF";
  //Inicializar desligado: nível 0
  digitalWrite(releUmidificador, HIGH);
  digitalWrite(releArCondicionado, HIGH);
  //Saída para reles
  pinMode(releUmidificador, OUTPUT); 
  pinMode(releArCondicionado, OUTPUT); 
  
  //Inicia o WIFI
  WiFi.begin(ssid, pass);

  //Não prossegue enquanto não conectar no WIFI
  while (WiFi.status() != WL_CONNECTED) {
    delay(2000);
    Serial.println("Connecting ao " + String(ssid) + "...");
  }
  //Inicia o sensor de temperatura/umidade
  dht.begin();

  //Configura o timer para enviar as informações do sensor a cada 5 segundos
  timer.setInterval(5000L, enviarInormacoesSensor);
  //Configura o timer para obter o estado dos equipamentos a cada 1 segundo
  timer.setInterval(1000L, obterEstadoEquipamentos);
}

void loop() {
  //Executa o timer
  timer.run();
}
