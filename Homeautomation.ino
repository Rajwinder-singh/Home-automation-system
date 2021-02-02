#include <ArduinoJson.h>
#include <Firebase.h>
#include <FirebaseArduino.h>
#include <FirebaseCloudMessaging.h>
#include <FirebaseError.h>
#include <FirebaseHttpClient.h>
#include <FirebaseObject.h>
#include<WiFiManager.h>
#include <ESP8266WiFi.h>
#define FIREBASE_HOST"homeautomation-20cff.firebaseio.com"
#define FIREBASE_AUTH "fu3jX4Sgag8dKDnaLvvWTvW72cEczYk0hxY7WKeV"
#define WIFI_SSID "Galaxy A50s3E0A"
#define WIFI_PASSWORD "23456789"
#define WIFI_SSID1 "ASUS_X01BDA"
#define WIFI_PASSWORD1 "23456789"

void setup() {
  // put your setup code here, to run once:
    Serial.begin(9600);
    WiFiManager wifiManager;
    wifiManager.setTimeout(10);
    WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
    if(!wifiManager.autoConnect("AutoConnectAP")){
        WiFi.begin(WIFI_SSID1,WIFI_PASSWORD1);
    }
    
    Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);
    pinMode(D1, OUTPUT);
    pinMode(D0, OUTPUT);
    pinMode(D5, OUTPUT);
    pinMode(D7,OUTPUT);
    pinMode(D3, OUTPUT);
    pinMode(D4, OUTPUT);
    pinMode(D6,OUTPUT);
    pinMode(D8,OUTPUT);
}
int a,b,c,d,e,f,g;

void loop() {
  // put your main code here, to run repeatedly:
    a=(int)Firebase.getInt("Bathroom");
    b=(int)Firebase.getInt("Drawinglight");
    c=(int)Firebase.getInt("LobbyLight1");
    d=(int)Firebase.getInt("MasterTube");
    e=(int)Firebase.getInt("Porch");
    f=(int)Firebase.getInt("Fan");
    g=(int)Firebase.getInt("FanSpeed");
    digitalWrite(D7,LOW);
    digitalWrite(D8,HIGH);
    if(Firebase.failed())
    {
      Serial.println(Firebase.error());
    }
    if(a==0)
    {
       Serial.println("Bathroom");
       digitalWrite(D1,LOW);
    }
    if(a==1){
       Serial.println(a);
       
      digitalWrite(D1,HIGH);
    }
    if(b==0)
    {
       Serial.println("Drawing room");
      digitalWrite(D5,LOW);
    }
    if(b==1){
       Serial.println(b);
      digitalWrite(D5,HIGH);
    }
    if(c==0)
    {
      Serial.println("Lobby");
      digitalWrite(D0,LOW);
    }
    if(c==1){
     Serial.println(c);
      digitalWrite(D0,HIGH);
    }
    if(d==0)
    {
      Serial.println("Master Bedroom");
      digitalWrite(D3,LOW);
    }
    if(d==1){
      Serial.println(d);
      digitalWrite(D3,HIGH);
    }
    if(e==0)
    {
      Serial.println("Porch");
      digitalWrite(D4,LOW);
    }
    if(e==1){
      Serial.println(e);
      digitalWrite(D4,HIGH);
    }
    if(f==0)
    {
      Serial.println("Porch");
      analogWrite(D6,0);
    }
    if(f==1){
      Serial.println(f);
      if(g<10){
          g=0;
          Firebase.setInt("FanSpeed",0);
      }
      if(g>10 && g<=25){
        g=25;
        Firebase.setInt("FanSpeed",25);
      }
      if(g>25 && g<=50){
        g=50;
        Firebase.setInt("FanSpeed",50);
      }
      if(g>50 && g<=75){
        g=75;
        Firebase.setInt("FanSpeed",75);
      }
      if(g>75 && g<=100){
        g=100;
        Firebase.setInt("FanSpeed",100);
      }
      analogWrite(D6,g);
    }
}
